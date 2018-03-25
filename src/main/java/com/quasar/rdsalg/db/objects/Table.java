package com.quasar.rdsalg.db.objects;

import com.quasar.rdsalg.FileSystemHelper;
import com.quasar.rdsalg.params.EnumParameter;
import com.quasar.rdsalg.params.Parameter;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class Table {
    private final Logger logger = Logger.getLogger(Table.class.getCanonicalName());
    private final String name;
    private final Set<String> imports = new TreeSet<>(new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    });
    private List<Parameter> columns = new ArrayList<>();

    public Table(String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getName() {
        return name;
    }

    public String getBaseClassName() {
        return name + "_db";
    }

    public Set<String> getImports() {
        return Collections.unmodifiableSet(imports);
    }

    public void addColumn(Parameter column) {
        columns.add(column);
        if (column.getHasImport() && column.getImport() != null)
            imports.add(column.getImport());
    }

    public List<Parameter> getColumns() {
        return columns != null ? columns : new ArrayList<Parameter>();
    }

    public boolean hasColumnEnum() {
        for (Parameter c : columns) {
            if (c instanceof EnumParameter)
                return true;
        }
        return false;
    }

    public Set<Enum> getEnumColumns() {
        Set<Enum> enums = new HashSet<>();
        for (Parameter c : columns) {
            if (c instanceof EnumParameter) {
                EnumParameter enumColumn = (EnumParameter) c;
                Enum e = new Enum(enumColumn.getEnumName(), enumColumn.getEnumValues());
                enumColumn.setEnumClassName(e.getName());
                enums.add(e);
            }
        }
        return enums;
    }

    public void generateBaseClassWithJTwig(String modelPackage, String modelPackagePath, boolean overwrite) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/class.twig");
        JtwigModel model = JtwigModel.newModel()
                .with("package", modelPackage)
                .with("imports", getImports())
                .with("className", getBaseClassName())
                .with("parameters", getColumns())
                .with("accessModifier", "public");
        System.out.println("proxy name: " + getName());

        String fileName = String.format("%s%s.java", modelPackagePath, getBaseClassName());
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateWithJTwig(String modelPackage, String modelPackagePath, boolean overwrite) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/class.twig");
        JtwigModel model = JtwigModel.newModel()
                .with("package", modelPackage)
                .with("accessModifier", "public")
                .with("className", getName())
                .with("extendsClass", getBaseClassName());
        System.out.println("Class name: " + getName());

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

    public void setColumns(List<Parameter> columns) {
        this.columns = columns;
    }
}