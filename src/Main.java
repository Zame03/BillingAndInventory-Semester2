import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);

        inicio_sesion(scan);
        facturacion(scan);

        scan.close();
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
        
        int bonificacion = 0;
        int tipoPago = 0;
        int totalventa = 0; 
        int num_factura = 0;
        int fila_producto = 1;
        int sumaventa = 0; 
        int ubicacion = 0; 
        int cantidad = 0;
        int columna_codigo = productos.getColumn("codigo");
        int columna_precio = productos.getColumn("precio");
        int columna_inventario = productos.getColumn("inventario");
        boolean encontrado;
        String codigo;
        String desicion;

        productos.viewData();

        while (true) {
            CSVManager factura = new CSVManager("files/plantilla_factura.csv");
            num_factura++;
            sumaventa = 0;
            fila_producto = 0;

            while (true) {
                encontrado = false;
                
                System.out.print("Para terminar la factura escriba 'terminado' en cualquier momento, \nIngrese código producto: ");
                codigo = scan.nextLine();
                codigo = codigo.toLowerCase();

                if (codigo.equals("terminado")) {
                    System.out.println("Ya no podras agregar mas productos e iras directamente al pago.");

                    System.out.println("El valor total es: " + sumaventa);

                    while (true) {
                        System.out.println("Ingrese el metodo de pago 1.Efectivo 2. Tarjeta: ");
                        tipoPago = Integer.parseInt(scan.nextLine());

                        if (tipoPago == 1) {
                            break;
                        } 
                        else if (tipoPago == 2) {
                            sumaventa -= sumaventa * 0.05;
                            break;
                        } 
                        else {
                            System.out.println("Metodo de pago es invalido reintente.");
                        }
                    }

                    System.out.println("El valor a pagar es de: " + sumaventa);

                    if (totalventa > 2000000) {
                        bonificacion += totalventa * 0.04;
                    } 
                    else if (totalventa > 1000000) {
                        bonificacion += totalventa * 0.02;
                    }

                    totalventa += sumaventa;

                    factura.saveData("facturas/factura" + num_factura + ".csv");
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

                fila_producto++;
                factura.newRow();
                
                for (int i = 0; i < 3; i++) {
                    factura.modifyCell(fila_producto, i, productos.getData(ubicacion, i));
                }

                factura.modifyCell(fila_producto, factura.getColumn("cantidad"), Integer.toString(cantidad));
                factura.modifyCell(fila_producto, factura.getColumn("precio_total"), Integer.toString(Integer.parseInt(factura.getData(fila_producto, factura.getColumn("precio_unitario"))) * cantidad));
                factura.modifyCell(fila_producto, factura.getColumn("precio_acumulado"), Integer.toString(sumaventa));
            }

            while (true) {
                System.out.print("Desea continuar con otra factura? (#" + (num_factura + 1) + ") Si/No: ");
                desicion = scan.nextLine();

                System.out.println(desicion);
                
                if (desicion.equals("si") || desicion.equals("no")) {
                    break;
                } 
                else {
                    System.out.println("Respuestas invalidas reintente.");
                    continue;
                }
            }

            if (desicion.equals("si")) {
                continue;
            } 
            else if (desicion.equals("no")) {
                break;
            }  
        }

        productos.saveData(filepathProductos);

        System.out.println("El total de las ventas fue: " + totalventa);
        System.out.println("La bonificacion del cajero es :" + bonificacion);

    }
}