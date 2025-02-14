import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        while (opcion != -1){
            System.out.println("1 - Usuarios");
            System.out.println("2 - Posts");
            System.out.println("3 - Comentarios");
            opcion = sc.nextInt();
            if (opcion == 1){
                GestionUsuarios.gestionMenu();
            }
        }
    }
}
