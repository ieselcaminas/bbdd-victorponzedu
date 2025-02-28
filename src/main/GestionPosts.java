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
            System.out.print(AnsiColor.BLUE.getCode());
            System.out.print(" 1 - Nuevo Post | ");
            System.out.print(" 2 - Todos los posts | ");
            System.out.print(" 3 - Todos mis posts | ");
            System.out.print(AnsiColor.RED.getCode());
            System.out.println("-1 para Salir");
            System.out.print(AnsiColor.RESET.getCode());
            opcion = sc.nextInt();
            if (opcion == 1){
                //Método para insertar un post
                newPost();
            }else if(opcion == 2){
                listarTodosLosPostsConComentarios();
            }else if(opcion == 3){
                listarTodosMisPosts();
            }
        }

    }

    /**
     * Lista todos los posts de la red social
     * @throws SQLException
     */
    public static void listarTodosLosPosts() throws SQLException {
        // Recogemos la conexión
        Connection con = Main.connection;
        //Creamos sl SQL
        PreparedStatement st = con.prepareStatement("SELECT p.id, p.texto, p.likes, p.fecha, u.nombre" +
                " FROM posts as p " +
                " INNER JOIN usuarios as u ON p.id_usuario = u.id");
        //Y recogemos los datos
        ResultSet rs = st.executeQuery();
        while (rs.next()){//Mientras haya datos, vamos de 1 en 1
            //Aquí va a ir el código para imprimir un post
            //Pero lo haremos en otro método llamado printPost al que
            //se le pasa la fila actual del rs
            printPost(rs);
        }
    }

    /**
     * Lista todos los posts de la red social
     * @throws SQLException
     */
    public static void listarTodosLosPostsConComentarios() throws SQLException {
        // Recogemos la conexión
        Connection con = Main.connection;
        //Creamos sl SQL
        PreparedStatement st = con.prepareStatement("SELECT p.id, p.texto, p.likes, p.fecha, u.nombre FROM posts as p " +
                "INNER JOIN usuarios as u ON p.id_usuario = u.id");
        //Y recogemos los datos
        ResultSet rs = st.executeQuery();
        while (rs.next()){//Mientras haya datos, vamos de 1 en 1
            //Aquí va a ir el código para imprimir un post
            //Pero lo haremos en otro método llamado printPost al que
            //se le pasa la fila actual del rs
            printPost(rs);
            printComments(rs.getInt(1));
        }
    }

    /**
     * Lista todos los posts del usuario logeado de la red social
     * @throws SQLException
     */
    public static void listarTodosMisPosts() throws SQLException {
        // Recogemos la conexión
        Connection con = Main.connection;
        //Creamos sl SQL
        PreparedStatement st = con.prepareStatement("SELECT p.id, p.texto, p.likes, p.fecha, u.nombre FROM posts as p " +
                "INNER JOIN usuarios as u ON p.id_usuario = u.id WHERE u.id = ?");
        //Y recogemos los datos
        st.setInt(1, Main.id_usuario);
        ResultSet rs = st.executeQuery();
        while (rs.next()){//Mientras haya datos, vamos de 1 en 1
            //Aquí va a ir el código para imprimir un post
            //Pero lo haremos en otro método llamado printPost al que
            //se le pasa la fila actual del rs
            printPost(rs);
            printComments(rs.getInt(1));
        }
    }

    private static void printComments(int idPost) throws SQLException {
        // Recogemos la conexión
        Connection con = Main.connection;
        //Creamos sl SQL
        PreparedStatement st = con.prepareStatement("SELECT c.id, c.texto, c.fecha, u.nombre" +
                " FROM comentarios as c " +
                " INNER JOIN usuarios as u ON c.id_usuario = u.id " +
                " INNER JOIN posts as p ON c.id_post = p.id"+
                " WHERE p.id = ?");
        //Y recogemos los datos
        st.setInt(1, idPost);
        ResultSet rs = st.executeQuery();
        while (rs.next()){//Mientras haya datos, vamos de 1 en 1
            //Aquí va a ir el código para imprimir un comentario
            //Pero lo haremos en otro método llamado GestionComentarios.printComment() al que
            //se le pasa la fila actual del rs
            GestionComentarios.printComment(rs);
        }
    }


    /**
     * Imprimimos los campos del recordset
     *
     * @param rs
     * @throws SQLException
     */
    public static void printPost(ResultSet rs) throws SQLException {
        System.out.println(rs.getInt(1) + " " +
                rs.getString(2) + " likes:" +
                rs.getInt(3) + " " + rs.getDate(4) +
                " " + rs.getString(5));

    }

    /**
     * Creamos un post pidiendo los datos al usuario mediante un escáner
     * @throws SQLException
     */
    public static void newPost() throws SQLException {
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
        System.out.println("Introduce el texto");
        String texto = sc.nextLine();

        //La fecha la obtenemos del momento en que se inserta el post
        java.sql.Date fecha = new java.sql.Date(new Date().getTime());
        PreparedStatement st = con.prepareStatement("INSERT INTO posts (texto, likes, fecha, id_usuario) VALUES (? , ?, ?, ?)");
        /*
         *  Como hay 4 parámetros hay que hacer 4 set....()
         */
        st.setString(1, texto);
        st.setInt(2, 0);
        st.setDate(3, fecha);
        st.setInt(4, Main.id_usuario);

        /*
         * Y no se nos olvide lo más importante: ejecutar el INSERT
         */
        st.executeUpdate();

    }
}
