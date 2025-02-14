import javax.swing.plaf.nimbus.State;
import java.net.StandardSocketOptions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConectarSql {

    static java.sql.Connection connection;

    public static java.sql.Connection getConnection(){
        String host = "jdbc:sqlite:src/main/resources/network.sqlite";
        if (connection == null) {
            try {
                connection = java.sql.DriverManager.getConnection(host);
            }catch (SQLException sql){
                System.out.println(sql.getMessage());
                System.exit(0);
            }
        }
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        java.sql.Connection con = getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");
        //stmt.executeUpdate("CREATE TABLE T1 (c1 VARCHAR(20))");
        while (rs.next()){
            System.out.println(rs.getInt(1));
            System.out.println("\t" + rs.getString(2));
            System.out.println("\t" + rs.getString(3));
        }
        stmt.close();

        //insertUser();
        //insertUserPreparedStatement();
        deleteUserPreparedStatement();
    }
    public static void insertUser() throws SQLException {
        Statement st = connection.createStatement();
        st.executeUpdate("INSERT INTO usuarios (nombre, apellidos) VALUES ('Janet', 'Espinosa')");
    }

    public static void insertUserPreparedStatement() throws SQLException {
        PreparedStatement st = null;
        String sql = "INSERT INTO usuarios (nombre, apellidos) VALUES (?, ?)";
        st = connection.prepareStatement(sql);
        st.setString(1, "Juan");
        st.setString(2, "Martínez");
        st.executeUpdate();
    }
    public static void deleteUserPreparedStatement() throws SQLException {
        PreparedStatement st = null;
        //String sql = "INSERT INTO usuarios (nombre, apellidos) VALUES (?, ?)";
        String sql = "DELETE FROM usuarios WHERE id = ?";
        st = connection.prepareStatement(sql);
        st.setInt(1, 5);
        st.executeUpdate();

    }
}
