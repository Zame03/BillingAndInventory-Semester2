import java.io.IOException;
import java.util.Scanner;

public class App {
   

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        String[] entry = {"hola", "adios", "bienvenido"};

        CSVManager trie = new CSVManager(2, 2);

        for (int i = 0; i < trie.columns; i++) {
            trie.modifyCell(0, i, entry[i]);
        }


        trie.newCol("bienvenido 26");
        trie.saveData("files/trie.csv");

       /*  Start user = new Start(scan);
        user.LogIn(); */

        // CSVManager entry = new CSVManager("files/entry.csv");
        // CSVManager entry2 = new CSVManager("files/productos.c6*36sv");

    //     String error = "Valor fuera del rango, vuelve a intentar", option;
        

    //     while (true) {
    //         System.out.println("\nSelecciona una opcion (Usuario: 0)\n1.Nueva entrada\n2.Ver lista de productos\n3.Ver inventario\n4.Cerrar Sesion");
    //         option = scan.nextLine();

    //         if (option.equals("1")) {
    //             CSVManager newEntry = new CSVManager   ("files/entry.csv");
    //             newEntry(newEntry, scan);

    //         }
    //         else if (option.equals("2")) {
    //             CSVManager products = new CSVManager("files/productos.csv");
    //             products.viewData();

    //             System.out.println("¿Quieres modificar datos? \n1.Si \n2.No");
    //             option = scan.nextLine();

    //             if (option.equals("1")) {
    //                 product(products, scan);
    //                 continue;
    //             }
    //             else if (option.equals("2")) {
    //                 continue;
    //             }
    //             else {
    //                 System.out.println(error);
    //             }
    //         }
    //         else if (option.equals("3")) {
              
    //         }
    //         else if (option.equals("4")) {
    //             // user.LogIn();
    //             continue;
    //         }
    //         else if (option.toLowerCase().equals("salir")) {
    //             System.exit(0);
    //         }
    //         else {
    //             System.out.println(error);
    //         }
    //     }
    // }

    // public static void newEntry (CSVManager entry, Scanner scan) throws Exception {
    //     int column_id = 0;
    //     int row = 0;
    //     String product_id = "";

    //     CSVManager products = new CSVManager("files/productos.csv");
    //     column_id = products.getColumn("product id");

    //     while (true) {
    //         System.out.println("Ingrese el codigo del producto que desea agregar: ");
    //         product_id = scan.nextLine();

    //         boolean found = false;

    //         if (product_id.equals("salir")) {
    //                 break;
    //             }

    //         try {
    //             Double.parseDouble(product_id);
    //         }
    //         catch (Exception e){
    //             System.out.println("Error: " + product_id + " does not exist, retry");
    //             continue;
    //         }



    //         for (int i = 0; i < products.rows; i++) {
    //             if (product_id.equals(products.getData(i, column_id))) {
    //                 row = i;
    //                 found = true;
    //             }
    //         }


    //         if (found) {
    //             entry.newRow();

    //             for (int i = 0; i < products.columns; i++){
    //                 entry.modifyCell(entry.rows - 1, i, products.getData(row, i));
                    
    //             }
    //             entry.saveData();
    //         }
    //         else {
    //             System.out.println("Error: " + product_id + " does not exist, retry");
    //         }
    //     }
    }


    public static void product (CSVManager product, Scanner scan) throws Exception{
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
                        product.modifyCell(row, 1, option);
                        System.out.println("Cambio realizado");
                        break;
                    }
                    else if (confirmation.equals("n")) {
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
                        product.modifyCell(row, 2, option);
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


    public static void saveData(CSVManager data, Scanner scan) throws IOException{
        String answer;

        System.out.println("Desea guardar los cambios al archivo? y/n");
        answer = scan.nextLine();

        if (answer.equals("y")) {
            data.saveData(data.filepath);
            System.out.println("Cambios guardados");
        }
        else if (answer.equals("n")) {
            System.out.println("Cambios desechados");
        }

        return;
    }

}
