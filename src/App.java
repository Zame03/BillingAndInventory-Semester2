import java.io.IOException;
import java.util.Scanner;

public class App {
   

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        Start user = new Start(scan);
        user.LogIn();

        int option = 0;
        String error = "Valor fuera del rango, vuelve a intentar";
        

        while (true) {
            System.out.println("\nSelecciona una opcion (Usuario: " + user.user_id + ")\n1.Nueva entrada\n2.Ver lista de productos\n3.Ver inventario\n4.Cerrar Sesion");
            option = scan.nextInt();

            if (option == 1) {
                DataManager newEntry = new DataManager("files\\entry.csv");
                newEntry(newEntry, scan);

            }
            else if (option == 2) {
                DataManager products = new DataManager("files\\productos.csv");
                products.getData();
                products.viewData();

                System.out.println("¿Quieres modificar datos? \n1.Si \n2.No");
                option = scan.nextInt();

                if (option == 1) {
                    product(products, scan);
                    continue;
                }
                else if (option == 2) {
                    continue;
                }
                else {
                    System.out.println(error);
                }
            }
            else if (option == 3) {
              
            }
            else if (option == 4) {
                user.LogIn();
                continue;
            }
            else {
                System.out.println(error);
            }
        }
    }

    public static void newEntry (DataManager entry, Scanner scan) throws Exception{
        int column_id = 0;
        int row = 0;
        String product_id = "";

        DataManager products = new DataManager("files\\productos.csv");
        column_id = products.getColumn("Product ID");

        while (true) {
            boolean found = false;
            System.out.println("Ingrese el codigo del producto que desea agregar: ");
            product_id = scan.nextLine();

            if (product_id.equals("salir")) {
                    break;
                }

            try {
                Double.parseDouble(product_id);
            }
            catch (Exception e){
                continue;
            }



            for (int i = 0; i < products.rows; i++) {
                if (product_id.equals(products.getData(i, column_id))) {
                    row = i;
                    found = true;
                }
            }


            if (found) {
                entry.newRow();

                for (int i = 0; i < products.columns; i++){
                    entry.modifyData(entry.rows - 1, i, products.getData(row, i));
                    
                }
                entry.saveData();
            }
        }

    }


    public static void product (DataManager product, Scanner scan) throws Exception{
        String error = "Valor fuera del rango, vuelve a intentar", option;
        boolean dothis = true;
        
        int row = 0;

        String[][] data = product.getData();

        while (dothis) {
            boolean row_find = false;

            System.out.println("Para terminar el proceso ingrese en cualquier momento 'salir'");
            System.out.print("Indique el numero de id del producto a modificar: ");
            option = scan.nextLine();
            

            if (option.equals("salir")) {
                saveData(product, scan);
                dothis = false;
                break;
            }
            
            for (int i = 0; i < product.rows; i++) {
                if (data[i][0].equals(option)) {
                    row_find = true;
                    row = i;
                    break;
                }
            }

            if (!row_find) {
                System.out.println(error);
                continue;
            }

            System.out.println(data[0][0] + " " + data[0][1] + " " + data[0][2]);
            System.out.println(data[row][0] + " " + data[row][1] + " " + data[row][2]);

            System.out.println("Que desea editar 1. nombre del producto 2. valor del producto 3. cancelar");
            option = scan.nextLine();

            if (option.equals("salir")) {
                saveData(product, scan);
                dothis = false;
                break;
            }
            else if (option.equals("cancelar")) {
                continue;
            }

            if (option.equals("1")) {
                while (true) {
                    String confirmation;
                    System.out.print("Ingrese el nuevo nombre: ");
                    option = scan.nextLine();

                    if (option.toLowerCase().equals("salir")) {
                        saveData(product, scan);
                        dothis = false;
                        break;
                    }


                    System.out.println(data[0][0] + " " + data[0][1] + " " + data[0][2]);
                    System.out.println(data[row][0] + " " + option + " " + data[row][2]);

                    System.out.println("Desea confirmar el cambio y/n");
                    confirmation = scan.nextLine();

                    if (option.equals("salir")) {
                        dothis = false;
                        break;
                    }
                    else if (confirmation.equals("y")) {
                        product.modifyData(row, 1, option);
                        System.out.println("Cambio realizado");
                        break;
                    }
                    else if (confirmation.equals("n")) {
                        System.out.println("Cambio denegado, volviendo a intentar");
                        System.out.println("Cambio denegado, volviendo a intentar");
                        continue;
                    }
                    else {
                        System.out.println(error);
                        continue;
                    }
                }
            }
            else if (option.equals("2")) {
                while (true) {
                    String confirmation;
                    System.out.print("Ingrese el nuevo precio: ");
                    option = scan.nextLine();

                    if (option.equals("salir")) {
                        saveData(product, scan);
                        dothis = false;
                        break;
                    }

                    try {
                        Double.parseDouble(option);
                    }
                    catch (Exception e){
                        continue;
                    }

                    System.out.println(data[0][0] + " " + data[0][1] + " " + data[0][2]);
                    System.out.println(data[row][0] + " " + data[row][1] + " " + option);

                    System.out.println("Desea confirmar el cambio y/n");
                    confirmation = scan.nextLine();

                    if (option.equals("salir")) {
                        dothis = false;
                        break;
                    }
                    else if (confirmation.equals("y")) {
                        product.modifyData(row, 2, option);
                        System.out.println("Cambio realizado");
                        break;
                    }
                    else if (confirmation.equals("n")) {
                        System.out.println("Cambio denegado, reiniciando");
                        continue;
                    }
                    else {
                        System.out.println(error);
                        continue;
                    }
                }
            }
            else {
                System.out.println(error);
                continue;
            }
        }
    }


    public static void saveData(DataManager data, Scanner scan) throws IOException{
        String answer;

        System.out.println("Desea guardar los cambios al archivo? y/n");
        answer = scan.nextLine();

        if (answer.equals("y")) {
            data.saveData();
            System.out.println("Cambios guardados");
        }
        else if (answer.equals("n")) {
            System.out.println("Cambios desechados");
        }

        return;
    }

}
