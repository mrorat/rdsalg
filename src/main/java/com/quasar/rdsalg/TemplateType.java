package com.quasar.rdsalg;

public class TemplateType {

    private final TemplateTypeFile templateTypeFile;

    public enum TemplateTypeFile {CLASS, ENUM, PROXY}

    private TemplateType(TemplateTypeFile templateTypeFile) {
        this.templateTypeFile = templateTypeFile;
    }

    public String getTemplateFileName() {
        switch (templateTypeFile) {
            case CLASS:
                return "class.twig";
            case ENUM:
                return "templates/enum.twig";
            case PROXY:
                return "proxy.twig";
            default:
                throw new RuntimeException("Template type [" + templateTypeFile + "] is not implemented!");
        }
    }

    public static TemplateType getClassTemplate() {
        return new TemplateType(TemplateTypeFile.CLASS);
    }

    public static TemplateType getEnumTemplate() { return new TemplateType(TemplateTypeFile.ENUM); }

    public static TemplateType getProxyTemplate() {
        return new TemplateType(TemplateTypeFile.PROXY);
    }
}
