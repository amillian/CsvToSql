/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cvstosql;



import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.util.logging.Logger;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
/**
 *
 * @author Andrew
 */
public class Central {
    
    private Connection connect() {
        // SQLite connection string
        String url = "In"; // neeed to redo connection
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void insert(String A, String B, String C, String D, String E, String F, double G, boolean H, boolean I, String J) {
        String sql = "INSERT INTO TableX (A, B, C, D, E, F, G, H, I, J) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, A);
            pstmt.setString(2, B);
            pstmt.setString(3, C);
            pstmt.setString(4, D);
            pstmt.setString(5, E);
            pstmt.setString(6, F);
            pstmt.setDouble(7, G);
            pstmt.setBoolean(8, H);
            pstmt.setBoolean(9, I);
            pstmt.setString(10, J);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

/*    
        public static void createNewTable() {
        // SQLite connection string
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "	A text PRIMARY KEY,\n"
                + "	B text PRIMARY KEY,\n"
                + "	C text PRIMARY KEY,\n"
                + "	D text PRIMARY KEY,\n"
                + "	E text PRIMARY KEY,\n"
                + "	F text PRIMARY KEY,\n"
                + "	G text PRIMARY KEY,\n"
                + "	H text PRIMARY KEY,\n"
                + "	I text PRIMARY KEY,\n"
                + ");";
        
        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 */

    
     public static void main(String[] args) {
         int numSuceeded = 0;//counter for successful 
         int numChecked = 0;
         int numFailed = 0;
         boolean validEntry = true;
         System.out.println(numSuceeded);
         Central app = new Central();
         double G = 0;
         boolean H = true;
         boolean I = true;
         String J = new String();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date();
        System.out.println(new Timestamp(date.getTime()));
        String csvFile = "C:\\Users\\Andrew\\Documents\\NetBeansProjects\\CVSTOSQL\\src\\main\\java\\com\\mycompany\\cvstosql\\ms3Interview.csv";
        //String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String writtenCsvFile = "Bad-data<"+date.getTime()+">.csv";
        File file = new File(writtenCsvFile); 
        System.out.println(writtenCsvFile);

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
                        if ("".equals(line[i])) { //checks if the entry is empty.
                            validEntry = false;
                        } else {
                            switch (i) { //talor made for switch statements
                                case 6: // change to a double
                                    //G = Double.parseDouble(line[i].substring(1)); //snips the dollar sign off of the entry, and converts to double for later insertions
                                    break;
                                case 7: //changes input to a proper boolean value for later insertion
                                    if ("TRUE".equals(line[i])) {
                                        H = true;
                                    } else if ("FALSE".equals(line[i])) {
                                        H = false;
                                    } else {
                                        validEntry = false;
                                    }
                                    break;
                                case 8: //changes input to a proper boolean value for later insertion
                                    if ("TRUE".equals(line[i])) {
                                        I = true;
                                    } else if ("FALSE".equals(line[i])) {
                                        I = false;
                                    } else {
                                        validEntry = false;
                                    }
                                    break;
                                default: //others are strings, and switching them to a distinct variable is a waste of time
                                    break;
                            }
                        }
                    }
                    if (validEntry) {
                        numSuceeded++;
                        //app.insert(line[0], line[1], line[2], line[3], line[4], line[5], G, H, I, line[9]);
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
        Logger logger = Logger.getLogger(Central.class.getName());
        logger.log(Level.INFO, "Number checked = {0}", numChecked);
        logger.log(Level.INFO, "Number passed = {0}", numSuceeded);
        logger.log(Level.WARNING, "Number failed = {0}", numFailed);
        /* Initial checker, now irrelevent.
        System.out.println("Number checked = " + numChecked);
        System.out.println("Number  = " + numSuceeded);
        System.out.println("Number failed = " + numFailed); */
    }
}
