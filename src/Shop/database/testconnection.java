package Shop.database;
import java.sql.Connection;

public class testconnection {
    public static void main(String[] args) {
        Connection connection = databaseconnection.getConnection();
        if (connection != null) {
            System.out.println("Connection test passed! ");
            databaseconnection.closeConnection(connection);
        } else {
            System.out.println("Connection test failed! ");
        }
    }
}
