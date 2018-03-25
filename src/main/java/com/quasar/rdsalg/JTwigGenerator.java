package com.quasar.rdsalg;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;

public class JTwigGenerator {

    public static void generateWithJTwig(TemplateType templateType, String name, String modelPackage, String modelPackagePath, JtwigModel model, boolean overwrite) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templateType.getTemplateFileName());

        String fileName = String.format("%s%s.java", modelPackagePath, name);
        File newFile = new File(fileName);
        if (newFile.exists()) {
            if (!overwrite) {
                System.out.printf("File [%s] exists and overwriting is disabled.%n", newFile.getName());
                return;
            } else {
                if (!newFile.delete())
                    throw new RuntimeException("Unable to delete file: " + newFile.getAbsolutePath());
                newFile = new File(fileName);
            }
        }
        FileSystemHelper.createDirectoriesIfNecessary(newFile.getParentFile().getAbsolutePath());
        try (OutputStream outputStream = new FileOutputStream(newFile)) {
            template.render(model, outputStream);
//            template.render(model, System.out);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}