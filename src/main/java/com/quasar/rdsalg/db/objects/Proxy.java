package com.quasar.rdsalg.db.objects;

import com.quasar.rdsalg.FileSystemHelper;
import com.quasar.rdsalg.PropertiesLoader;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.logging.Logger;

public class Proxy {
    private final Logger logger = Logger.getLogger(Proxy.class.getCanonicalName());

    private final String name;
    private final Set<String> imports = new TreeSet<>(new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            if (s1 == null)
                throw new RuntimeException("Proxy [" + name + "] has null import");
            return s1.compareTo(s2);
        }
    });
    private final Set<Procedure> storedProcedures = new HashSet<>();
    private final Set<Enum> enums = new HashSet<>();

    public Proxy(String name) {
        this.name = name + "_2";
        imports.add("java.sql.CallableStatement");
        imports.add("java.sql.Connection");
        imports.add("java.sql.ResultSet");
        imports.add("java.sql.SQLException");
        imports.add("java.util.logging.Level");
        imports.add("java.util.logging.Logger");
        imports.add("org.apache.commons.lang3.time.StopWatch");
        imports.add(PropertiesLoader.loadProperties(this.getClass().getClassLoader()).getProperty("class.connection.owner"));
    }

    public String getName() {
        return name;
    }

    public Set<String> getImports() {
        return imports;
    }

    private void addImports(Set<String> imports) {
        for (String s : imports)
            if (s == null) {
                Thread.currentThread().getStackTrace();
            }
        this.imports.addAll(imports);
    }

    public Set<Procedure> getStoredProcedures() {
        return Collections.unmodifiableSet(storedProcedures);
    }

    public void addProcedure(Procedure procedure) {
        this.storedProcedures.add(procedure);
        for (String s : procedure.getImports())
            if (s == null)
                throw new RuntimeException("Procedure [" + procedure.getName() + "] has null import");
        this.imports.addAll(procedure.getImports());
    }

    public void generateWithJTwig(String modelPackage, String modelPackagePath, boolean overwrite) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/proxy.twig");
        JtwigModel model = JtwigModel.newModel()
                .with("package", modelPackage)
                .with("proxy", this);
        System.out.println("proxy name: " + getName());

        String fileName = String.format("%s%s.java", modelPackagePath, name);
        File newFile = new File(fileName);
        if (newFile.exists()) {
            if (!overwrite) {
                System.out.printf("File [%s] exists and overwriting is disabled.%n", newFile.getName());
                return;
            } else {
                newFile.delete();
                newFile = new File(fileName);
            }
        }
        try (OutputStream outputStream = new FileOutputStream(newFile)) {
            FileSystemHelper.createDirectoriesIfNecessary(newFile.getParentFile().getAbsolutePath());
            template.render(model, outputStream);
            template.render(model, System.out);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateReturnTypes(Set<Table> tables) {
        for (Procedure procedure : storedProcedures) {
            procedure.findCustomTypeTable(tables);
        }
    }
}