import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagerController {
	
	public static Connection getDBConnection() {
        String URL = "jdbc:mysql://localhost:3306/employee_management";
        String USERNAME = "root";
        String PASSWORD = "#12345678#";
        Connection conn = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }
        catch (SQLException e) {
            System.err.println("Connect SQL Error: " + e.getMessage());
        }

        return conn;
    }
	
	public List<String[]> readFromCSV(String filename) {
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
	
	public static Response writeToDB(String filename){
		if (!Validator.isValidCSVFileName(filename)) {
			return new Response(400, "invalid filename");
		}
		EmployeeManagerController obj = new EmployeeManagerController();
        Connection conn = getDBConnection();
        
        int statuscode=200;
        String errormsg = null;
        
        

        List<String[]> data = obj.readFromCSV(filename);

        try {
            String sql = "INSERT INTO employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (String[] row : data) {
            	if ((!Validator.isValidName(row[1])) || (!Validator.isValidName(row[2])) || (!Validator.isValidEmail(row[3])) || (!Validator.isValidPhone(row[4]))) {
                   continue;
                }
                
                Integer id = Integer.parseInt(row[0]);
                String first_name = row[1];
                String last_name = row[2];
                String email = row[3];
                String phone = row[4];
                String department = row[5];
                double salary = Double.parseDouble(row[6]);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(row[7], formatter);

                ps.setInt(1, id);
                ps.setString(2, first_name);
                ps.setString(3, last_name);
                ps.setString(4, email);
                ps.setString(5, phone);
                ps.setString(6, department);
                ps.setDouble(7, salary);
                ps.setDate(8, Date.valueOf(date));
                ps.addBatch(); 
            }

            ps.executeBatch();
            ps.close();
            
            System.out.println("Data inserted successfully!");
            conn.close();
        } catch (SQLException e) {
            statuscode=400;
            errormsg=e.getMessage();
            
        }
        
        return new Response(statuscode, errormsg); 
        
    }

}
