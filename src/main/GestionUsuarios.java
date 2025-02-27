import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionUsuarios {
    /**
     * Va a permitir gestionar el menú mediante un escáner de consola
     * @throws SQLException
     */
    public static void gestionMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        String usuario;
        while (opcion != -1){
            System.out.print(AnsiColor.BLUE.getCode());
            System.out.print(" 1 - Login | ");
            System.out.print(" 2 - Nuevo usuario | ");
            System.out.print(AnsiColor.RED.getCode());
            System.out.println("-1 para Salir");
            System.out.print(AnsiColor.RESET.getCode());
            opcion = sc.nextInt();
            if (opcion == 1){
                usuario = existeUsuario();
                if (!usuario.isEmpty()){
                    //Volvemos a la pantalla de inicio y fijamos el valor de la variable
                    //usuario que está definida en la clase Main.
                    Main.usuario = usuario;
                    System.out.println("Bienvenido/a");
                    break;
                }else{
                    System.out.println("Usuario/a no encontrado");
                }

            }else if (opcion == 2){
                //Llamada a método para insertar un usuario
                usuario = insertarUsuario();
                Main.usuario = usuario;
                break;
            }
        }

    }

    /**
     *
     * Pedir los datos por consola de usuario y contraseña
     * Comprobar mediante un preparedStatement si dicho usuario existe en la BD
     * @return true si existe
     * @throws SQLException
     */
    public static String existeUsuario() throws SQLException {
        Connection con = Main.connection;
        Scanner sc = new Scanner(System.in);
        String usuario;
        String contrasenya;

        System.out.println("Introduzca el usuario:");
        usuario = sc.nextLine();

        System.out.println("Introduzca la contraseña:");
        contrasenya = sc.nextLine();

        //Aol ser un SELECT, usamos executeQuery
        PreparedStatement st = con.prepareStatement("SELECT * FROM usuarios WHERE nombre = ?" +
                " AND contrasenya = ?");
        //Dependiendo del tipo de dato, usaremos setString, setInt, setDate, ...
        st.setString(1, usuario);
        st.setString(2, contrasenya);
        ResultSet rs = st.executeQuery();
        //Si hay algún registro, quiere decir que hay algún usuario con esas credenciales.
        if (rs.next()){
            Main.id_usuario = rs.getInt(1);
            return rs.getString(2);
        }
        //Main.id_usuario = rs.getInt(1);
        return "";

    }

    /**
     *
     * @return
     * @throws SQLException
     */
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

        PreparedStatement st = con.prepareStatement("INSERT INTO usuarios (nombre, apellidos, contrasenya) VALUES (?, ?, ?)");
        st.setString(1, nombre);
        st.setString(2, apellidos);
        st.setString(3, contrasenya);

        //Como no se recuperan datos de la BD, no hay ResultSet
        st.executeUpdate();
        return "";

    }
}
