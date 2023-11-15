import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CSVManager {
    int filas, columnas;
    String filepath;
    String[][] datos;

    public CSVManager(String path) {
        String line = "";
        int fila = 0;

        try { 
            BufferedReader br = new BufferedReader(new FileReader(path));
            
            br.mark(1);
            line = br.readLine();
            br.reset();

            while(br.readLine() != null) {
                fila++;
            }

            br.close();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        filepath = path;
        filas = fila;
        columnas = line.split(",").length;
        datos = new String[filas][columnas];

        getData();
    }

    public CSVManager(int row_num, int column_num) {
        filas = row_num;
        columnas = column_num;

        datos = new String[filas][columnas];
    }


    public String[][] getData() {
        try { 
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            int i = 0;

            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                for (int j = 0; j < columnas; j++) {
                    datos[i][j] = values[j];
                }

                i++;
            }

            br.close();
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return datos;
    }


    public String getData(int fila, int columna) {
        return datos[fila][columna];
    }


    public int getColumn(String columna) throws Exception{
        //columna = columna.toLowerCase();
        int found = -1;

        for (int i = 0; i < columnas; i++) {
            if (columna.equals(datos[0][i].toLowerCase())) {
                found = i;
                break;
            }
        }

        if (found == -1) {
            throw new IllegalArgumentException("La columna no existe verifica su nombre " + columna);
        }
        else {
            return found;
        }
    }


    public void modifyCell(int fila, int columna, String input) throws Exception {
        datos[fila][columna] = input;
    }

    public void newRow() throws Exception {
        String[][] temp = new String[filas + 1][columnas];
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                temp[i][j] = datos[i][j];
            }
        }

        filas++;
        datos = new String[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datos[i][j] = temp[i][j];
            }
        }
    }

    public void newCol (String name) throws Exception {
        String[][] temp = new String[filas][columnas + 1];
        
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                temp[i][j] = datos[i][j];
            }
        }

        columnas++;
        datos = new String[filas][columnas];

        for (int i = 0; i < columnas; i++) {
            datos[0][i] = temp[0][i];
        }
        datos[0][columnas - 1] = name;

        for (int i = 1; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datos[i][j] = temp[i][j];
            }
        }
    }

    public void viewData() throws Exception {
        Arrays.stream(datos).forEach((i) -> {
            Arrays.stream(i).forEach((j) -> System.out.print(j + " - "));
            System.out.println();
        });

    }

    public void saveData(String path) throws Exception {
        FileWriter fw = new FileWriter(path);
        String out = "";

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                out += datos[i][j];
                if (j < columnas - 1) {
                    out += ",";
                }
            }

            out += "\n";
        }

        fw.write(out);
        fw.close();
    }
}