import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CSVManager {
    int rows, columns;
    String filepath;
    String[][] data;

    public CSVManager(String path) {
        String line = "";
        int row = 0;

        try { 
            BufferedReader br = new BufferedReader(new FileReader(path));
            
            br.mark(1);
            line = br.readLine();
            br.reset();

            while(br.readLine() != null) {
                row++;
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
        rows = row;
        columns = line.split(",").length;
        data = new String[rows][columns];

        getData();
    }

    public CSVManager(int row_num, int column_num) {
        rows = row_num;
        columns = column_num;

        data = new String[rows][columns];
    }


    public String[][] getData() {
        try { 
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            int i = 0;

            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                for (int j = 0; j < columns; j++) {
                    data[i][j] = values[j];
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

        return data;
    }


    public String getData(int row, int column) {
        return data[row][column];
    }


    public int getColumn(String column) throws Exception{
        //column = column.toLowerCase();
        int found = -1;

        for (int i = 0; i < columns; i++) {
            if (column.equals(data[0][i].toLowerCase())) {
                found = i;
                break;
            }
        }

        if (found == -1) {
            throw new IllegalArgumentException("La columna no existe verifica su nombre " + column);
        }
        else {
            return found;
        }
    }


    public void modifyCell(int row, int column, String input) throws Exception {
        data[row][column] = input;
    }

    public void newRow() throws Exception {
        String[][] temp = new String[rows + 1][columns];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                    temp[i][j] = data[i][j];
            }
        }

        rows++;
        data = new String[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                    data[i][j] = temp[i][j];
            }
        }
    }

    public void newCol (String name) throws Exception {
        String[][] temp = new String[rows][columns + 1];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                    temp[i][j] = data[i][j];
            }
        }

        columns++;
        data = new String[rows][columns];

        for (int i = 0; i < columns; i++) {
            data[0][i] = temp[0][i];
        }
        data[0][columns - 1] = name;

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j] = temp[i][j];
            }
        }
    }

    public void viewData() throws IOException {
        Arrays.stream(data).forEach((i) -> {
            Arrays.stream(i).forEach((j) -> System.out.print(j + " - "));
            System.out.println();
        });

    }

    public void saveData(String path) throws IOException {
        FileWriter fw = new FileWriter(path);
        String out = "";

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                out += data[i][j];
                if (j < columns - 1) {
                    out += ",";
                }
            }

            out += "\n";
        }

        fw.write(out);
        fw.close();
    }
}