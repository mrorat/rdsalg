package com.quasar.rdsalg;

import java.util.Properties;

public class Program {

    public static void main(String[] args) throws Exception {
        new Program();
    }

    private Properties properties;

    private Program() {
        loadProperties();
        InformationSchemaReader informationSchemaReader = new InformationSchemaReader(properties);
    }

    private void loadProperties() {
        properties = PropertiesLoader.loadProperties(this.getClass().getClassLoader());
    }
}