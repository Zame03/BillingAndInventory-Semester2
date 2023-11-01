import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        int cedula, filasCedulas, filasProductos, cdProducto, nproducto, producto, tipoPago, ubicacion, sumaventa, bonificacion, totalventa;
        String seguir="si";
        boolean encontrado= false;
        Scanner scan =new Scanner(System.in);
        String[][] cedulas = {
                {"nombre", "cedula"},
                {"Juan Diego", "1152187878"},
                {"Juan Bedoya", "1128904414"},
                {"Juan Duque", "1025761547"}
        };
        String[][] productos = {
                {"codigo", "nombre", "precio", "inventario"},
                {"1", "jamon", "5700", "100"},
                {"2", "lecherita", "2500", "100"},
                {"3", "Cocacola", "3500", "5500", "100"},
                {"4", "Cocacolate", "1500", "2500", "100"},
                {"5", "Chocorramo", "2500", "100"},
                {"6", "Galletasfestival", "1500", "100"},
                {"7", "panqueque", "4500", "100"},
                {"101", "pan", "15000", "100"},
                {"102", "yogurtx6", "5800", "100"},
                {"103", "queso crema", "6000", "100"},
                {"104", "arepas", "1800", "100"},
                {"105", "mayonesa", "3000", "100"},
                {"106", "jabon", "6000", "100"},
                {"107", "leche", "4500", "50"},
                {"108", "huevos", "18000", "50"},
                {"109", "panela", "15000", "100"},
                {"110", "doritos", "1500", "120"},
                {"111", "Carne", "8000", "70"}
        };
        //Inicio de sesión
        while (seguir==si){
            System.out.print("Ingrese cedula del cajero: ");
            cedula= scan.nextInt();
            for (int i=0; i<filasCedulas; i++){
                if (cedula==cedulas[i][1]){
                    int ubNomnbre=1;
                    encontrado=true;
                    break;
                }
            }
            if (encontrado==false){
                System.out.print("La cedula ingresada es invalida, reintente");
            } else {
                System.out.print("Bienvenido, cedula valida");
                cedulas[i][0];
                seguir="no";
            }
        }
        seguir="si";
        encontrado=false;

        //Ingreso de productos y procesos
        while(true){
            System.out.print();
        }

    }
}