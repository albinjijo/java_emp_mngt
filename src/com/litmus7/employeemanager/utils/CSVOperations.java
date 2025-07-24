package com.litmus7.employeemanager.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVOperations {
	public static List<String[]> readFromCSV(String filename) {
        String line;
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br =  new BufferedReader(new FileReader(filename))) {
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                data.add(values);
            }
        } catch (IOException e) {
            System.err.println(" Error: " + e.getMessage());
        }
        return data;
    }

}
