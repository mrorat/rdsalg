package com.quasar.rdsalg.db.objects;

import com.quasar.rdsalg.JTwigGenerator;
import com.quasar.rdsalg.TemplateType;
import org.jtwig.JtwigModel;

import java.io.IOException;
import java.util.Set;

public class Enum {
    private final String name;
    private Set<String> values;

    public Enum(String name, Set<String> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void generateWithJTwig(String modelPackage, String modelPackagePath) {

        JtwigModel model = JtwigModel.newModel()
                .with("package", modelPackage)
                .with("values", values)
                .with("name", getName());
        String fileName = String.format("%s%s.java", modelPackagePath, name);
        JTwigGenerator.generateWithJTwig(TemplateType.getEnumTemplate(), name, modelPackage, modelPackagePath, model, false);
    }
}
