
package com.litmus7.employeemanager.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ErrorCodeProperties {

    private static final Properties PROPERTIES = new Properties(); 
    private static final String PROPERTIES_FILE_NAME = "error.properties";

    static {
        loadProperties();
    }

    private ErrorCodeProperties() {}
    
    private static void loadProperties() {
        try (InputStream input = DatabaseProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            if (input == null) {
                System.err.println("Sorry, unable to find " + PROPERTIES_FILE_NAME + " in the classpath.");
                throw new RuntimeException(" properties file not found: " + PROPERTIES_FILE_NAME);
                

            }
            PROPERTIES.load(input);
        } catch (IOException ex) {
            System.err.println("Error loading  properties: " + ex.getMessage());
            throw new RuntimeException("Failed to load  properties", ex);
        }
    }

    public static String getErrorCodeMeaning(String errorcode) {
        return PROPERTIES.getProperty(errorcode);
    }

    
}
