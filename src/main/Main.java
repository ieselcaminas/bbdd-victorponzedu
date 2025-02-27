import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    /** Estas tres variables siguientes vamos a usarlas para
     * compartir información entre todas las clases. La forma
     * de acceder a ellas desde otro clase es Main.connection
     * Main.usuario y Main.id_usuarios
     *
     */
    static java.sql.Connection connection;
    //Vamos a almacenar el nombre del usuario loegado para que sea
    //accesible en todas las clases
    static String usuario = "";
    //Y lo mismo para el id_usuario. Se inicializa a -1 porque este valor
    //no es un valor válido para el id del usuario ya que éste empieza en 0
    static int id_usuario = -1;

    public static java.sql.Connection getConnection(){
        //Para cambiar de base de dato sólo hay que cambiar esta cadena
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
        printBanner();
        //Inicializamos esta variable estática
        connection = getConnection();
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        while (opcion != -1){
            System.out.print(AnsiColor.BLUE.getCode());
            if (!usuario.isEmpty()){
                System.out.print(usuario + " | ");
            }
            System.out.print(" 1 - Usuarios | ");
            System.out.print(" 2 - Posts | ");
            System.out.print(" 3 - Comentarios | ");
            System.out.print(AnsiColor.RED.getCode());
            System.out.println("-1 para Salir");
            System.out.println(AnsiColor.RESET.getCode());
            opcion = sc.nextInt();
            if (opcion == 1){
                GestionUsuarios.gestionMenu();
            }else if (opcion == 2){
                GestionPosts.gestionMenu();
            }else if (opcion == 3){
                GestionComentarios.gestionMenu();
        }
        }
    }
    private static void printBanner() {
        System.out.println(AnsiColor.GREEN.getCode());
        System.out.println("   _____            _       _   _   _      _                      _    ");
        System.out.println("  / ____|          (_)     | | | \\ | |    | |                    | |   ");
        System.out.println(" | (___   ___   ___ _  __ _| | |  \\| | ___| |___      _____  _ __| | __");
        System.out.println("  \\___ \\ / _ \\ / __| |/ _` | | | . ` |/ _ \\ __\\ \\ /\\ / / _ \\| '__| |/ /");
        System.out.println("  ____) | (_) | (__| | (_| | | | |\\  |  __/ |_ \\ V  V / (_) | |  |   < ");
        System.out.println(" |_____/ \\___/ \\___|_|\\__,_|_| |_| \\_|\\___|\\__| \\_/\\_/ \\___/|_|  |_|\\_\\");
        System.out.println(AnsiColor.RESET.getCode());
    }
}
