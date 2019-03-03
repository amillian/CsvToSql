/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cvstosql;



import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.apache.commons.logging.Log;
/**
 *
 * @author Andrew
 */
public class Central {
    
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/test.db"; // neeed to redo connection
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void insert(String A, String B, String C, String D, String E, String F, String G, String H, String I, String J) {
        String sql = "INSERT INTO TableX (A, B, C, D, E, F, G, H, I, J) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, A);
            pstmt.setString(2, B);
            pstmt.setString(3, C);
            pstmt.setString(4, D);
            pstmt.setString(5, E);
            pstmt.setString(6, F);
            pstmt.setString(7, G);
            pstmt.setString(8, H);
            pstmt.setString(9, I);
            pstmt.setString(10, J);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
     public static void main(String[] args) {
         int numSuceeded = 0;
         int numChecked = 0;
         int numFailed = 0;
         boolean validEntry = true;
         System.out.println(numSuceeded);
         Central app = new Central();

        String csvFile = "C:\\Users\\Andrew\\Documents\\NetBeansProjects\\CVSTOSQL\\src\\main\\java\\com\\mycompany\\cvstosql\\ms3Interview.csv";
        //String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String writtenCsvFile = "Bad-data.csv";
        File file = new File(writtenCsvFile); 

        CSVReader reader = null;
        try {
            FileWriter outputfile = new FileWriter(file);
            reader = new CSVReader(new FileReader(csvFile));
            CSVWriter writer = new CSVWriter(outputfile);
            String[] line;
            line = reader.readNext(); //skips first line, due to being a header file
            System.out.println(Arrays.toString(line));//checking assumption;
            while ((line = reader.readNext()) != null) {
                validEntry = true;
                numChecked++;
                if (line.length == 10) { //if it isn't 10, not only is it wrong, the secondary check will cause an index out of bounds exception.
                    for (int i = 0; i < 10 && validEntry; i++) {
                        if ("".equals(line[i])) {
                            validEntry = false;
                        }
                    }
                    if (validEntry) {
                        numSuceeded++;
                        //app.insert(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7], line[8], line[9]);
                    } else {
                        numFailed++;
                        writer.writeNext(line);
                    }
                } else {
                    numFailed++;
                    writer.writeNext(line);
                }

                
            }
        } catch (IOException e) {
        }
        System.out.println("Number checked = " + numChecked);
        System.out.println("Number passed = " + numSuceeded);
        System.out.println("Number failed = " + numFailed);
    }
}
