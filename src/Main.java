import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);

        //inicio_sesion(scan);
        facturacion(scan);


        // String[][] cedulas = {
        //     {"nombre", "cedula"},
        //     {"Juan Diego", "1152187878"},
        //     {"Juan Bedoya", "1128904414"},
        //     {"Juan Duque", "1025761547"}
        // };

        // String[][] productos = {
        //     {"codigo", "nombre", "precio", "inventario"},
        //     {"1", "jamon", "5700", "100"},
        //     {"2", "lecherita", "2500", "100"},
        //     {"3", "Cocacola", "3500", "5500", "100"},
        //     {"4", "Cocacolate", "1500", "2500", "100"},
        //     {"5", "Chocorramo", "2500", "100"},
        //     {"6", "Galletasfestival", "1500", "100"},
        //     {"7", "panqueque", "4500", "100"},
        //     {"101", "pan", "15000", "100"},
        //     {"102", "yogurtx6", "5800", "100"},
        //     {"103", "queso crema", "6000", "100"},
        //     {"104", "arepas", "1800", "100"},
        //     {"105", "mayonesa", "3000", "100"},
        //     {"106", "jabon", "6000", "100"},
        //     {"107", "leche", "4500", "50"},
        //     {"108", "huevos", "18000", "50"},
        //     {"109", "panela", "15000", "100"},
        //     {"110", "doritos", "1500", "120"},
        //     {"111", "Carne", "8000", "70"}
        // };
    }

    public static void inicio_sesion (Scanner scan) throws Exception { 
        CSVManager cedulas = new CSVManager("files/usuarios.csv");

        String cedula;
        int columna = cedulas.getColumn("cedula");
        int fila = 0;
        boolean encontrado = false;

    
        while (true) {
            System.out.println("Ingrese cedula del cajero: ");
            cedula = scan.nextLine();

            for (int i = 1; i < cedulas.filas; i++){
                if (cedula.equals(cedulas.getData(i, columna))) {
                    fila = i;
                    encontrado = true;
                    break;
                }
            }   

            if (encontrado == true){
                System.out.println("Bienvenido, " + cedulas.getData(fila, cedulas.getColumn("nombre")));
                break;
            } 
            else {
                System.out.println("La cedula ingresada es invalida, reintente");  
            }
        }
    }


    public static void facturacion (Scanner scan) throws Exception {
        String filepathProductos = "files/productos.csv";
        CSVManager productos = new CSVManager(filepathProductos);
        CSVManager factura = new CSVManager("files/plantilla_factura.csv");

        int fila_producto = 0, sumaventa = 0, ubicacion = 0, cantidad;
        int columna_codigo = productos.getColumn("codigo");
        int columna_precio = productos.getColumn("precio");
        int columna_inventario = productos.getColumn("inventario");
        boolean encontrado;
        String codigo;

        productos.viewData();

        while (true) {
            encontrado = false;
            fila_producto += 1;
            System.out.print("Ingrese código producto: ");
            codigo = scan.nextLine();

            if (codigo.equals("salir")) {
                break;
            }

            for (int i = 1; i < productos.filas; i++) {
                if (codigo.equals(productos.getData(i, columna_codigo))) {
                    encontrado = true;
                    ubicacion = i;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("El producto no fue encontrado, reintente.");
                continue;
            }

            while (true) {
                System.out.println("Cual es la cantidad a comprar: ");
                cantidad = Integer.parseInt(scan.nextLine());

                if (cantidad <= 0 || cantidad > Integer.parseInt(productos.getData(ubicacion, productos.getColumn("inventario")))) {
                    System.out.println("Cantidad de venta invalida, reintente.");
                    continue;
                } 
                else {
                    sumaventa += Integer.parseInt(productos.getData(ubicacion, columna_precio)) * cantidad;
                    productos.modifyCell(ubicacion, columna_inventario, Integer.toString(Integer.parseInt(productos.getData(ubicacion, columna_inventario)) - cantidad));
                    break;
                }
            }
            
            factura.newRow();
            
            for (int i = 0; i < 3; i++) {
                factura.modifyCell(fila_producto, i, productos.getData(ubicacion, i));
            }

            factura.modifyCell(fila_producto, factura.getColumn("cantidad"), Integer.toString(cantidad));
            factura.modifyCell(fila_producto, factura.getColumn("precio_total"), Integer.toString(Integer.parseInt(factura.getData(fila_producto, factura.getColumn("precio_unitario"))) * cantidad));
            factura.modifyCell(fila_producto, factura.getColumn("precio_acumulado"), Integer.toString(sumaventa));
        }

        productos.saveData(filepathProductos);
        factura.saveData("facturas/facturadeprueba.csv");

        System.out.println("Ya no podras agregar mas productos e iras directamente al pago.");
        // totalventa += sumaventa;

        // while (true) {
        //     System.out.println("Ingrese el metodo de pago 1.Efectivo 2. Tarjeta: ");
        //     tipoPago = scan.nextInt();

        //     if (tipoPago == 1) {
        //         break;
        //     } else if (tipoPago == 2) {
        //         descuento = totalventas * 0.5;
        //         break;
        //     } else {
        //         System.out.println("Metodo de pago es invalido reintente.");
        //     }
        // }

        // if (totalventa > 1000000 && totalventa > 2000000) {
        //     bonificacion = totalventa * 0.2;
        // } else if (totalventa >= 200000) {
        //     bonificacion = totalventa * 0.4;
        // }

        // System.out.println("el total de la venta fue: " + totalVenta);
        // System.out.println("la bonificacion del cajero es :" + bonificacion);

    }
}