import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GestionUsuarios {
    public static void gestionMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3){
            System.out.println("1 - Logearse");
            System.out.println("2 - Nuevo usuario");
            System.out.println("3 - Salir");

            opcion = sc.nextInt();
            if (opcion == 1){
                //Llamada a método para logearase
                boolean logeado = existeUsuario();
            }else if(opcion == 2){
                //Llamada a método para insertar un usuario
            }
        }

    }
    public static boolean existeUsuario() throws SQLException {
        //Crear la conexión
        java.sql.Connection con = Main.connection;

        //Coger el nombre de usuario y la contraseña
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce tu usuario:");
        String nombre = sc.nextLine();
        System.out.println("Introduce tu contraseña:");
        String contrasenya = sc.nextLine();

        //Hacer la consulta preparada
        PreparedStatement st = con.prepareStatement("SELECT * FROM usuarios WHERE nombre = ? AND contrasenya = ?");
        //Y ahora, fijar el valor de cada uno de los ? dependiendo del tipo en la base de datos
        st.setString(1, nombre);
        st.setString(2, contrasenya);
        ResultSet rs = st.executeQuery();
        return rs.next();
    }

}
