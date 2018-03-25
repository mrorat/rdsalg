package com.quasar.rdsalg.db.objects;

import java.util.*;

import com.quasar.rdsalg.MySqlParameterDirection;
import com.quasar.rdsalg.PropertiesLoader;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import com.quasar.rdsalg.params.Parameter;
import com.quasar.rdsalg.params.ParameterFactory;
import org.junit.Assert;

public class Procedure {
    private Table customTypeTable;

    public Procedure(String name, ParameterFactory paramFactory) {
        Assert.assertNotNull(name);
        this.name = name;
        this.paramFactory = paramFactory;
    }

    private final ParameterFactory paramFactory;
    private final String name;
    private String proxyName = "Unpackaged";
    private final List<Parameter> inParameters = new ArrayList<>();
    private final List<Parameter> outParameters = new ArrayList<>();
    //private List<Parameter> inOutParameters = new ArrayList<Parameter>();
    public int ResultSet = 1;
    public boolean SkipProcedure = false;

    private Set<String> extraFields = new HashSet<>();
    private Set<String> omitFields = new HashSet<>();
    private final Set<String> imports = new HashSet<>();
    private final Set<Enum> enums = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }

    public Set<String> getImports() {
        return imports;
    }

    public Set<Enum> getEnums() {
        return enums;
    }

    public void addParameter(Parameter parameter, Set<Table> tables) {
        switch (parameter.getDirection()) {
            case In:
                inParameters.add(parameter);
                break;
            case InOut:
                throw new NotImplementedException("InOut parameters aren't implemented!");
//				inOutParameters.Add(parameter);
//				break;
            case Out:
                if (outParameters.size() > 0)
                    throw new NotImplementedException("Multiple out parameters aren't implemented!");
                outParameters.add(parameter);
                break;
            default:
                break;
        }

        if (parameter.getHasImport()) {
            if (parameter.isCustomType())
                imports.add(PropertiesLoader.loadProperties(this.getClass().getClassLoader()).getProperty("package.model") + "." + parameter.getName());
            else if (parameter.getImport() != null)
                imports.add(parameter.getImport());
        }

        if (parameter.getType().startsWith("enum("))
            enums.add(new Enum(parameter.getNameCapitalized().substring(2),
                    new HashSet<>(Arrays.asList(parameter.getType().substring(5, parameter.getType().length() - 1).trim().replace("'", "").split(",")))));
    }

    public boolean hasOutParameter() {
        return outParameters.size() > 0;
    }

    public void setExtraFields(Set<String> extraFields) {
        this.extraFields = extraFields;
    }

    public void setOmitFields(Set<String> omitFields) {
        this.omitFields = omitFields;
    }

    public Parameter getFirstOutParameter() {
        return outParameters.get(0);
    }

    public String getReturnType() {
        // do we have out param?
        if (outParameters.size() == 1) {
            if (outParameters.get(0).isCustomType()) {
                if (outParameters.get(0).getHasImport() && outParameters.get(0).getImport() != null)
                    imports.add(outParameters.get(0).getImport());
                return outParameters.get(0).getMultipleRowsReturn()
                        ? ("List<" + outParameters.get(0).getName() + ">") : (outParameters.get(0).getName());
            } else {
                return outParameters.get(0).getJavaType();
            }
        } else
            return "void";
    }

    private String getQuestionMarksForParameters(int inParamsQty) {
        if (inParamsQty == 0)
            return "";
        String parameterTemplate = "?,";
        return StringUtils.repeat(parameterTemplate, inParamsQty).substring(0, inParamsQty * 2 - 1);
    }

    public boolean hasOneOutParameterWithCustomType() {
        return (outParameters.size() == 1 && outParameters.get(0).isCustomType());
    }

    public void findCustomTypeTable(Set<Table> tables) {
        if (outParameters.size() == 1) {
            for (Table t : tables) {
                if (t.getName().equalsIgnoreCase(outParameters.get(0).getName())) {
                    List<Parameter> columnsWithoutOmitedFields = new ArrayList<>();
                    for (Parameter parameter : t.getColumns()) {
                        if (!omitFields.contains(parameter.getName()))
                            columnsWithoutOmitedFields.add(parameter);
                    }
                    for (String extraField : extraFields) {
                        String[] extraFieldElements = extraField.split("\\s\\|\\s");
                        columnsWithoutOmitedFields.add(paramFactory.createParameter(extraFieldElements[0].trim(), extraFieldElements[1].trim(),
                                MySqlParameterDirection.Out, 1, true, true));
                    }
                    customTypeTable = new Table(t.getName());
                    customTypeTable.setColumns(columnsWithoutOmitedFields);
                    break;
                }
            }
            if (customTypeTable == null)
                throw new RuntimeException("not set");
        }
    }

    public Table getCustomTypeTable() {
        return customTypeTable;
    }

    public List<Parameter> getInParameters() {
        return Collections.unmodifiableList(inParameters);
    }

    public String toString() {
        return this.getClass().getCanonicalName() + " - Procedure, name: " + name;
    }
}
