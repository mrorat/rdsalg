package com.quasar.rdsalg;

public class Precision {

    private int precision = -1;
    private int precision2 = -1;
    private String typeWithouPrecision;

    public static Precision getPrecision(String type) {
        int bracketIndex = type.indexOf("(");
        if (bracketIndex != -1 && !type.substring(0, bracketIndex).equalsIgnoreCase("enum")) {
            Precision p = new Precision();
            int commaIndex = type.indexOf(',');
            if (commaIndex != -1) {
                p.setPrecision(Integer.parseInt(type.substring(bracketIndex + 1, commaIndex)));
                p.setPrecision2(Integer.parseInt(type.substring(commaIndex + 1, type.indexOf(')'))));
            } else {
                p.setPrecision(Integer.parseInt(type.substring(bracketIndex + 1, type.indexOf(')'))));
            }
            p.typeWithouPrecision = type.substring(0, bracketIndex);
            return p;
        }
        return null;
    }

    private void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getPrecision() {
        return precision;
    }

    private void setPrecision2(int precision2) {
        this.precision2 = precision2;
    }

    public int getPrecision2() {
        return precision2;
    }

    public String getTypeWithouPrecision() {
        return typeWithouPrecision;
    }

    public boolean getHasDoublePrecision() {
        return precision2 > -1;
    }
}
