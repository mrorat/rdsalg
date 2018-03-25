package com.quasar.rdsalg;

import com.quasar.rdsalg.db.ConnOwner;
import com.quasar.rdsalg.db.objects.Enum;
import com.quasar.rdsalg.db.objects.Procedure;
import com.quasar.rdsalg.db.objects.Proxy;
import com.quasar.rdsalg.db.objects.Table;
import com.quasar.rdsalg.params.ParameterFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class InformationSchemaReader {

    private final String QUERY_GET_TABLE_STRUCTURE =
            "SELECT COLUMN_NAME, IS_NULLABLE, DATA_TYPE, COLUMN_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION, COLUMN_COMMENT "
                    + "FROM `information_schema`.`columns` WHERE table_schema='%s' AND table_name='%s' ORDER BY ORDINAL_POSITION;";
    private final String QUERY_GET_ROUTINES = "SELECT routine_name FROM information_schema.ROUTINES WHERE routine_type='PROCEDURE'"
            + " AND routine_schema='%s' ORDER BY routine_name";
    private final String QUERY_SHOW_CREATE_PROCEDURE = "SHOW CREATE PROCEDURE %s.%s";
    private final String QUERY_GET_ROUTINE_PARAMETERS = "SELECT SPECIFIC_CATALOG, SPECIFIC_SCHEMA, SPECIFIC_NAME, ORDINAL_POSITION, "
            + "PARAMETER_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, "
            + "NUMERIC_PRECISION, NUMERIC_SCALE, DATETIME_PRECISION, CHARACTER_SET_NAME, COLLATION_NAME, "
            + "DTD_IDENTIFIER, PARAMETER_MODE, ROUTINE_TYPE FROM information_schema.PARAMETERS WHERE SPECIFIC_NAME='%s' "
            + "ORDER BY SPECIFIC_NAME,ORDINAL_POSITION";

    private final Logger logger = Logger.getLogger(Program.class.getCanonicalName());
    private final Map<String, String> procBodies = new TreeMap<>();
    private final Map<String, Proxy> proxies = new HashMap<>();
    private final Set<Table> tables = new HashSet<>();
    private final Map<String, String> customTypeNames = new HashMap<>();
    private final Properties properties;
    private String dbHost, dbSchema, dbUser, dbPassword, generatedJavaPath;
    private ParameterFactory paramFactory;

    InformationSchemaReader(Properties properties) {
        this.properties = properties;
        readDatabaseProperties();
        generateAccessLayer();
    }

    private void readDatabaseProperties() {
        dbHost = PropertiesLoader.getProperty("db.host");
        if (dbHost == null || dbHost.isEmpty())
            dbHost = "localhost";
        dbSchema = PropertiesLoader.getProperty("db.schema");
        dbUser = PropertiesLoader.getProperty("db.user");
        dbPassword = PropertiesLoader.getProperty("db.password");
        generatedJavaPath = PropertiesLoader.getProperty("generated.java.path");//PropertiesLoader.getProperty();
        paramFactory = new ParameterFactory(properties);
    }


    private void generateAccessLayer() {
        Map<String, Procedure> procedures = getProcedureDefinitions();
        parseProcedureBodies(procedures);

        getTablesStructure();
//		parseTableBodies();

        generateClasses();
        generateEnumTypes();
        updateProceduresInProxies();
        generateProxies();
        logger.info("\nDONE !");

        //Logger.StopIfNeeded();
    }

    private void updateProceduresInProxies() {
        for (Proxy proxy : proxies.values()) {
            proxy.updateReturnTypes(tables);
        }
    }

    private void getTablesStructure() {
        logger.info("Requesting table bodies...");
        List<String> tableNames = new ArrayList<>();
        String query = String.format("SELECT table_name FROM information_schema.`TABLES` T WHERE table_schema = '%s';", dbSchema);

        try (Connection conn = ConnOwner.getConnection()) {
            try (Statement st = conn.createStatement()) {
                try (ResultSet res = st.executeQuery(query)) {

                    while (res.next()) {
                        String tName = res.getString(1);
                        tableNames.add(tName);
                        System.out.print(tName + ", ");
                    }
                    res.close();

                    logger.info("");

                    for (String tableName : tableNames) {
                        query = String.format(QUERY_GET_TABLE_STRUCTURE, dbSchema, tableName);
                        try (ResultSet res2 = st.executeQuery(query)) {

                            System.out.printf("Downloading table structure: %s.%s%n", dbSchema, tableName);

                            Table table = new Table(tableName);
                            int counter = 0;
                            while (res2.next()) {
                                String columnName = res2.getString("COLUMN_NAME");
                                String columnType = res2.getString("COLUMN_TYPE");
                                //            		String columnComment = res2.getString("COLUMN_COMMENT");
                                boolean isNullable = res2.getBoolean("IS_NULLABLE");
                                Long characterMaxLength = res2.getLong("CHARACTER_MAXIMUM_LENGTH");
                                if (res2.wasNull())
                                    characterMaxLength = null;
                                Long numericPrecision = res2.getLong("NUMERIC_PRECISION");
                                if (res2.wasNull())
                                    numericPrecision = null;
                                table.addColumn(
                                        paramFactory.createParameter(columnName, columnType, MySqlParameterDirection.In, counter,
                                                columnType, characterMaxLength, numericPrecision, isNullable));
                                //isNullable, characterMaxLength, numericPrecision));
                            }
                            tables.add(table);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            logger.info("Error " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void generateEnumTypes() {
        logger.info("Generating enum types file: ");
        Set<com.quasar.rdsalg.db.objects.Enum> enumTypes = new HashSet<>();

        for (Table t : tables) {
            if (t.hasColumnEnum()) {
                enumTypes.addAll(t.getEnumColumns());
            }
        }

        for (Enum anEnum : enumTypes) {
            String enumPackageName = PropertiesLoader.getProperty("package.model.enums");
            String enumPackagePath = generatedJavaPath + enumPackageName.replace('.', '/') + "/";

            anEnum.generateWithJTwig(enumPackageName, enumPackagePath);
        }
    }

    private void generateClasses() {
        String modelPackageName = PropertiesLoader.getProperty("package.model");
        String modelPackagePath = generatedJavaPath + modelPackageName.replace('.', '/') + "/";

        FileSystemHelper.createDirectoriesIfNecessary(modelPackagePath);
        for (Table t : tables) {
            t.generateWithJTwig(modelPackageName, modelPackagePath, false);
            t.generateBaseClassWithJTwig(modelPackageName, modelPackagePath, true);
        }
    }

    private Map<String, Procedure> getProcedureDefinitions() {
        final Map<String, Procedure> procedures = new HashMap<>();
        List<String> procNames = new ArrayList<>();

        String extString = "";
        try (Connection conn = ConnOwner.getConnection()) {
            Statement st = conn.createStatement();
            try (ResultSet res = st.executeQuery(String.format(QUERY_GET_ROUTINES, dbSchema))) {
                while (res.next()) {
                    procNames.add(res.getString(1));
                }
            }

            for (String routineName : procNames) {
                extString = routineName;
                Statement st2 = conn.createStatement();
                try (ResultSet res2 = st2.executeQuery(String.format(QUERY_SHOW_CREATE_PROCEDURE, dbSchema, routineName))) {
                    logger.info("Downloading procedure: " + routineName);
                    while (res2.next()) {
                        procBodies.put(routineName, res2.getString(3));
                        if (PropertiesLoader.getProperty("stored.procedures.save").equalsIgnoreCase("true"))
                            saveProcedureAsFile(routineName, procBodies.get(routineName));
                        //logger.info(routine + " - " + reader.GetString(2));
                    }
                }

                Statement st3 = conn.createStatement();
                try (ResultSet res3 = st3.executeQuery(String.format(QUERY_GET_ROUTINE_PARAMETERS, routineName))) {
                    logger.info("Creating procedure parameters: " + routineName);
                    if (routineName.equalsIgnoreCase("insert_varchar_50_return_saved_value"))
                        continue;
                    Procedure procedure = new Procedure(routineName, paramFactory);
                    while (res3.next()) {
                        String parameterName = res3.getString("PARAMETER_NAME");
                        String parameterType = res3.getString("DATA_TYPE");
                        Long characterMaxLength = res3.getLong("CHARACTER_MAXIMUM_LENGTH");
                        if (res3.wasNull())
                            characterMaxLength = null;
                        Long numericPrecision = res3.getLong("NUMERIC_PRECISION");
                        if (res3.wasNull())
                            numericPrecision = null;
                        procedure.addParameter(paramFactory.createParameter(
                                parameterName,
                                parameterType,
                                parseParameterMode(res3.getString("PARAMETER_MODE")),
                                Integer.parseInt(res3.getString("ORDINAL_POSITION")),
                                res3.getString("DTD_IDENTIFIER"),
                                characterMaxLength,
                                numericPrecision,
                                true), tables);
                    }
                    procedures.put(routineName, procedure);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Procedure name: " + extString, e);
            // System.Exception: Procedure name: Annotate ---> System.Data.SqlTypes.SqlNullValueException: Data is Null. This method or property cannot be called on Null values.
            // This exception is most likely caused by situation where the routine was created by user (most likely root) different than used here to fetch data
        }
        return procedures;
    }

    private MySqlParameterDirection parseParameterMode(String mode) {
        if (mode.equalsIgnoreCase("IN")) return MySqlParameterDirection.In;
        if (mode.equalsIgnoreCase("OUT")) return MySqlParameterDirection.Out;
        else
            return MySqlParameterDirection.InOut;
    }

    private void parseProcedureBodies(Map<String, Procedure> procedures) {
        for (String routineName : procBodies.keySet()) {
            if (routineName.equalsIgnoreCase("insert_varchar_50_return_saved_value"))
                continue;
            System.out.println("Parsing procedure: " + routineName);

            String body = procBodies.get(routineName);
            int firstBracket = body.indexOf("(");
            int tempBracket = firstBracket + 1;
            int lastBracket = body.indexOf(")", tempBracket);

            while (body.indexOf("(", tempBracket) != -1 && body.indexOf("(", tempBracket) < lastBracket) {
                tempBracket = body.indexOf(")", tempBracket) + 1;
                lastBracket = body.indexOf(")", tempBracket);
            }
//    		String paramDefinition = body.substring(firstBracket+1, lastBracket);
            Procedure sp = procedures.get(routineName);

            int customTypePosition = body.indexOf("-- returns:");
            if (customTypePosition != -1) {
                int customTypePositionEnd = body.indexOf(":", customTypePosition + 11);
                boolean multipleRowsReturn;
                switch (body.getBytes()[customTypePositionEnd + 1]) {
                    case '1':
                        multipleRowsReturn = false;
                        break;
                    case '*':
                        multipleRowsReturn = true;
                        break;
                    default:
                        throw new RuntimeException("Invalid return type definition in sp " + routineName);
                }


                String customParamName = body.substring(customTypePosition + 11, customTypePositionEnd);
                //sp.addParameter(new Parameter(customParamName, customParamName, MySqlParameterDirection.Out, -1, true, multipleRowsReturn));
                sp.addParameter(
                        paramFactory.createCustomParameter(
                                customParamName,
                                customParamName,
                                MySqlParameterDirection.Out,
                                -1,
                                true,
                                multipleRowsReturn),
                        tables);
                if (!customTypeNames.containsKey(customParamName.toUpperCase()))
                    customTypeNames.put(customParamName.toUpperCase(), customParamName);
            }

            int customAdHocTypePosition = body.indexOf("-- returnsAdHoc:");
            if (customAdHocTypePosition != -1) {
                int customAdHocTypePositionEnd = body.indexOf(":", customAdHocTypePosition + 16);
                boolean multipleRowsReturn;
                switch (body.getBytes()[customAdHocTypePositionEnd + 1]) {
                    case '1':
                        multipleRowsReturn = false;
                        break;
                    case '*':
                        multipleRowsReturn = true;
                        break;
                    default:
                        throw new RuntimeException("Invalid return type definition in sp " + routineName);
                }


                String customParamName = body.substring(customAdHocTypePosition + 16, customAdHocTypePositionEnd - customAdHocTypePosition - 16);
//    			sp.addParameter(new Parameter(customParamName, customParamName, MySqlParameterDirection.Out, -1, true, multipleRowsReturn, true));
                sp.addParameter(
                        paramFactory.createParameter(
                                customParamName,
                                customParamName,
                                MySqlParameterDirection.Out,
                                -1,
                                true,
                                multipleRowsReturn),
                        tables);
                if (!customTypeNames.containsKey(customParamName.toUpperCase()))
                    customTypeNames.put(customParamName.toUpperCase(), customParamName);
            }

            int proxyNamePosition = body.indexOf("-- proxy:");
            if (proxyNamePosition != -1) {
                int proxyNamePositionEnd = body.indexOf(":", proxyNamePosition + 9);
                sp.setProxyName(body.substring(proxyNamePosition + 9, proxyNamePositionEnd));
            }

            if (body.contains("-- no-proxy")) {
                sp.SkipProcedure = true;
            } else {

                int extraFieldsPosition = body.indexOf("-- extraFields:");
                if (extraFieldsPosition != -1) {
                    int extraFieldsPositionEnd = body.indexOf(":", extraFieldsPosition + 15);
                    sp.setExtraFields(new HashSet<>(Arrays.asList(body.substring(extraFieldsPosition + 15,
                            extraFieldsPositionEnd).split(","))));
                }

                int omitFieldsPosition = body.indexOf("-- omitFields:");
                if (omitFieldsPosition != -1) {
                    int omitFieldsPositionEnd = body.indexOf(":", omitFieldsPosition + 14);
                    sp.setOmitFields(new HashSet<>(Arrays.asList(body.substring(omitFieldsPosition + 14,
                            omitFieldsPositionEnd).split(","))));
                }

                int resultSetPosition = body.indexOf("-- resultSet:");
                if (resultSetPosition != -1) {
                    int resultSetPositionEnd = body.indexOf(":", resultSetPosition + 13);
                    sp.ResultSet = Integer.parseInt(body.substring(resultSetPosition + 13, resultSetPositionEnd - resultSetPosition - 13));
                }
            }
            if (!proxies.containsKey(sp.getProxyName()))
                proxies.put(sp.getProxyName(), new Proxy(sp.getProxyName()));
            proxies.get(sp.getProxyName()).addProcedure(sp);
        }
    }

    private void generateProxies() {
        String modelAdHocDirectory = generatedJavaPath + PropertiesLoader.getProperty("package.model.adHoc").replace('.', '/');
        FileSystemHelper.createDirectoriesIfNecessary(modelAdHocDirectory);
        FilenameFilter filter = (directory, fileName) -> fileName.endsWith(".java");
        String[] files = new File(modelAdHocDirectory).list(filter);
        int length = files != null ? files.length : 0;
        for (int i = 0; i < length; i++) {
            new File(files[i]).delete();
        }
        String proxyDirectory = generatedJavaPath + PropertiesLoader.getProperty("package.proxy").replace('.', '/');

        FileSystemHelper.createDirectoriesIfNecessary(proxyDirectory);

        String proxyPackageName = PropertiesLoader.getProperty("package.proxy");
        String proxyPackagePath = generatedJavaPath + proxyPackageName.replace('.', '/') + "/";

        for (Proxy proxy : proxies.values()) {
            System.out.println("JTwig - Generating proxy: " + proxy.getName());
            proxy.generateWithJTwig(proxyPackageName, proxyPackagePath, true);
        }
    }


    private void saveProcedureAsFile(String rountineName, String routineBody) throws IOException {
        assert routineBody != null;
        String body = "DELIMITER $$\n\n"
                + "DROP PROCEDURE IF EXISTS `" + rountineName + "` $$\nCREATE"
                + routineBody.substring(routineBody.indexOf(" PROCEDURE "))
                + " $$\n\nDELIMITER ;";
        if (!new File(PropertiesLoader.getProperty("stored.procedures.path")).exists())
            new File(PropertiesLoader.getProperty("stored.procedures.path")).mkdirs();

        FileSystemHelper.saveFile(PropertiesLoader.getProperty("stored.procedures.path") + rountineName + ".sql", body, true);
    }
}