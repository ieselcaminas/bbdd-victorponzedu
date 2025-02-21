import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class GestionPosts {
    public static void gestionMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        String usuario;
        while (opcion != -1){
            System.out.print(" 1 - Nuevo Post | ");
            System.out.println("-1 - Salir");

            opcion = sc.nextInt();
            if (opcion == 1){
                //Método para insertar un post
                newPost();
            }else if(opcion == 2){
                listarTodosLosPosts();
            }
        }

    }

    private static void listarTodosLosPosts() throws SQLException {
        Connection con = Main.connection;
        PreparedStatement st = con.prepareStatement("SELECT * FROM posts");
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            //Aquí va a ir el código para imprimir un post
            //Pero lo haremos en otro método llamado printPost al que
            //se le pasa la fila actual del rs

        }
    }

    public static void newPost() throws SQLException {
        if(Main.id_usuario == -1) {
            GestionUsuarios.gestionMenu();
            return;
        }

        Connection con = Main.connection;
        /* Para activar las claves ajenas se debe configurar sqlite
        Statement s = con.createStatement();
        s.executeUpdate("PRAGMA foreign_keys = ON");
        */
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el texto");
        String texto = sc.nextLine();
        java.sql.Date fecha = new java.sql.Date(new Date().getTime());
        PreparedStatement st = con.prepareStatement("INSERT INTO posts (texto, likes, fecha, id_usuario) VALUES (? , ?, ?, ?)");
        st.setString(1, texto);
        st.setInt(2, 0);
        st.setDate(3, fecha);
        st.setInt(4, Main.id_usuario);
        st.executeUpdate();

    }
}
