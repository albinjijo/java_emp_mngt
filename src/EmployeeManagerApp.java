import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeManagerApp {
    public static void main(String[] args) {
        
        try {
            Connection conn = EmployeeManagerController.GetConnect();

            EmployeeManagerController.writeDB();
            conn.close();

        } catch (SQLException e) {
            System.err.println("Begin SQL Error: " + e.getMessage());
        }
    }
}