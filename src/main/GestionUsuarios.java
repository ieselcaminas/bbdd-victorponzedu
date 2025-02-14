import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionUsuarios {
    public static void gestionMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        String usuario;
        while (opcion != -1){
            System.out.println(" 1 - Login");
            System.out.println(" 2 - Nuevo usuario");
            System.out.println("-1 - Salir");

            opcion = sc.nextInt();
            if (opcion == 1){
                //Llamada a método para logearase
                usuario = existeUsuario();
                //Podemos a la pantalla de inicio
                if (!usuario.isEmpty()){
                    Main.usuarioLogeado = usuario;
                    break;
                }

            }else if (opcion == 2){
                //Llamada a método para insertar un usuario
                usuario = insertarUsuario();
            }
        }

    }
    public static String existeUsuario() throws SQLException {
        //Crear la conexión
        java.sql.Connection con = Main.connection;

        //Coger el nombre de usuario y la contraseña
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce tu usuario:");
        String nombre = sc.nextLine();
        System.out.println("Introduce tu contraseña:");
        String contrasenya = sc.nextLine();

        //Hacer la consulta preparada
        PreparedStatement st = con.prepareStatement("SELECT nombre FROM usuarios WHERE nombre = ? AND contrasenya = ?");
        //Y ahora, fijar el valor de cada uno de los ? dependiendo del tipo en la base de datos
        st.setString(1, nombre);
        st.setString(2, contrasenya);
        ResultSet rs = st.executeQuery();
        if (rs.next())
            return rs.getString(1);
        else
            return "";

    }
    public static String insertarUsuario() throws SQLException {
        //Crear la conexión
        java.sql.Connection con = Main.connection;

        //Coger el nombre de usuario y la contraseña
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce tu nombre:");
        String nombre = sc.nextLine();
        System.out.println("Introduce tus apellidos:");
        String apellidos = sc.nextLine();
        System.out.println("Introduce tu contraseña:");
        String contrasenya = sc.nextLine();

        //Hacer la consulta preparada
        PreparedStatement st = con.prepareStatement("INSERT INTO usuarios (nombre, apellidos, contrasenya) VALUES (?, ?, ?)");
        //Y ahora, fijar el valor de cada uno de los ? dependiendo del tipo en la base de datos
        st.setString(1, nombre);
        st.setString(2, apellidos);
        st.setString(3, contrasenya);
        st.executeUpdate();
        return nombre;

    }
}
