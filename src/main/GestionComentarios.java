import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class GestionComentarios {
    public static void gestionMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        while (opcion != -1){
            System.out.print(AnsiColor.BLUE.getCode());
            System.out.print(" 1 - Nuevo Comentario | ");
            System.out.print(AnsiColor.RED.getCode());
            System.out.println("-1 para Salir");
            System.out.print(AnsiColor.RESET.getCode());
            opcion = sc.nextInt();
            if (opcion == 1){
                //Método para insertar un post
                //newPost();
                newComment();
            }
        }

    }
    private static void newComment() throws SQLException {
        //Sólo se puede postear si nos hemos logeado en GestionUsuarios.existeUsuario()
        if(Main.id_usuario == -1) {
            GestionUsuarios.gestionMenu();
            return;
        }

        Connection con = Main.connection;
        /* Para activar las claves ajenas se debe configurar sqlite
        Statement s = con.createStatement();
        s.executeUpdate("PRAGMA foreign_keys = ON");
        */
        /*
            Recogemos los datos introducidos por el usuario
         */

        Scanner sc = new Scanner(System.in);
        System.out.println("Selecciona el id del post");
        int id_post = getPost();
        System.out.println("Introduce el texto");
        String texto = sc.nextLine();


        //La fecha la obtenemos del momento en que se inserta el post
        java.sql.Date fecha = new java.sql.Date(new Date().getTime());
        PreparedStatement st = con.prepareStatement("INSERT INTO comentarios (texto, fecha, id_usuario, id_post) VALUES (? , ?, ?, ?)");
        /*
         *  Como hay 4 parámetros hay que hacer 4 set....()
         */
        st.setString(1, texto);
        st.setDate(2, fecha);
        st.setInt(3, Main.id_usuario);
        st.setInt(4, id_post);

        /*
         * Y no se nos olvide lo más importante: ejecutar el INSERT
         */
        st.executeUpdate();
    }

    private static int getPost() throws SQLException {
        GestionPosts.listarTodosLosPosts();
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static void printComment(ResultSet rs) throws SQLException {
        System.out.println("\t\t\t" + rs.getString(2) + " - " +
                rs.getDate(3 ) + " - " + rs.getString(4));
    }

}
