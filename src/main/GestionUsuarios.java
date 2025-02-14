import java.util.Scanner;

public class GestionUsuarios {
    public static void gestionMenu(){
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != -1){
            System.out.println("1 - Logearse");
            System.out.println("2 - Nuevo usuario");
            opcion = sc.nextInt();
            if (opcion == 1){
                //Llamada a método para logearase
            }
        }

    }
}
