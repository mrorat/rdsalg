package com.quasar.rdsalg.params;

import com.quasar.rdsalg.MySqlParameterDirection;
import com.quasar.rdsalg.PropertiesLoader;
import com.quasar.rdsalg.TextHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EnumParameter extends Parameter {

    EnumParameter(String name, String type,
                  MySqlParameterDirection direction, int position, String typeDefinition) {
        super(name, type, direction, position);
        parseEnumValues(typeDefinition);
        boolean removeParameterPrefix =
            Boolean.parseBoolean(PropertiesLoader.getProperty("stored.procedures.remove.parameter.prefix"));
        if (removeParameterPrefix) {
            String parameterPrefixToRemove =
                    PropertiesLoader.loadProperties(this.getClass().getClassLoader()).getProperty("stored.procedures.parameter.prefix");
            if (name.startsWith(parameterPrefixToRemove))
                enumClassName = name.replaceFirst(parameterPrefixToRemove, "");
        }
    }

    private final Set<String> enumValues = new HashSet<>();
    private String selectedValue;
    private String enumClassName;


    public String getEnumName() {
        return TextHelper.capitalizeFirstLetter((this.getNameCapitalized().endsWith("Type") ? this.getNameCapitalized() : this.getNameCapitalized() + "Type"));
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public String getCallableStatementType() {
        return "String";
    }

    public String getResultSetTypeGetter() {
	    return "Enum.valueOf(" + getNameCapitalized() + ".class, rs.getString(\"" + name + "\"))";
    }

    @Override
    public String getTypeFromTypes() {
        return "Types.VARCHAR";
    }

    @Override
    public String getMethodParameter() {
        return String.format("%s %s", getNameCapitalized(), getName());
    }

    public Set<String> getEnumValues() {
        return enumValues;
    }

    private void parseEnumValues(String enumDefinition) {
        int firstBracket = enumDefinition.indexOf("(") + 1;
        int lastBracket = enumDefinition.indexOf(")", firstBracket);
        String values = enumDefinition.substring(firstBracket, lastBracket);
        for (String value : Arrays.asList(values.split(","))) {
            value = value.trim();
            value = value.substring(1, value.length() - 1);
            enumValues.add(value);
        }
    }

    @Override
    public String getJavaType() { return "Enum"; }

    public String getEnumClassName() {
        return enumClassName;
    }

    public void setEnumClassName(String name) {
        this.enumClassName = name;
    }

    @Override
    public String getImport() {
        return String.format("%s.%s", PropertiesLoader.getProperty("package.model.enums"), super.getNameCapitalized(enumClassName != null ? enumClassName : name)) + "Type";
    }

    public String getNameCapitalized() {
        String x = (enumClassName == null ? super.getNameCapitalized(name) + "Type" : enumClassName);
        System.out.println("Name: [" + name + "] - [" + x + "]");
        return x;
    }

    public String getCallableStatementSetParameter() {
        return name + ".name()";
    }

    @Override
    public boolean getIsEnum() {
        return true;
    }
    public boolean getHasImport() {
        return true;
    }

}