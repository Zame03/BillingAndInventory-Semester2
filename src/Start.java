import java.util.Scanner;

public class Start {
    Scanner scan;
    String user_name;
    int user_id;


    public Start(Scanner scanner) throws Exception{
        user_id = 0;
        user_name = "";
        scan = scanner;
    }


    public void LogIn() throws Exception{
        String path = "files\\users.csv";
        DataManager users = new DataManager(path);

        String user = "", password = "";
        int tries = 2;

        System.out.println("\nIngresa 'salir' en cualquier momento para salir del programa");

        while (true) {
            System.out.println("Ingrese el nombre de usuario");
            // user = scan.next();
            user = "juanbedoya";

            if (user.toLowerCase().equals("salir")) {
                System.exit(0);
            }
            
            if (verificator(users, user)) {
                break;
            }
        }


        int pass_col = users.getColumn("password");

        while (true) {
            System.out.println("Ingrese la contraseña");
            // password = scan.next();
            password = "pass2";

            if (password.toLowerCase().equals("salir")) {
                System.exit(0);
            }
            
            if (password.equals(users.getData(user_id, pass_col)) == false && tries > 0) {
                System.out.println("Contraseña invalida, tiene " + tries + " intentos restantes");
                tries -= 1;
            }
            else {
                break;
            }
        }
    }


    private boolean verificator(DataManager users, String user) throws Exception{
        boolean user_catch = false;
        int name_col = users.getColumn("username");
        int rows = users.rows;

        for (int i  = 0; i < rows; i++) {
            if (user.equals(users.getData(i, name_col))) {
                user_catch = true;
                user_name = users.getData(i, name_col);
                user_id = i;
                break;
            }
        }

        if (user_catch) {
            return true;
        }
        else {
            System.out.println("Error usuario no existe");
            return false;
        }
    }
}