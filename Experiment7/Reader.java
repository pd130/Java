import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList; 

public class Reader {
    public static void main(String[] args) {
        String fileName = "students.csv";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write("3,Ved,A,65,45,78,75,80,\n");
            writer.write("4,Anita,A,43,48,89,56,70,\n");
            writer.write("5,Sunita,B,53,58,99,66,40,\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Calculating Percentages");
        CalculatePercentage(fileName);

        System.out.println("Deleting Record with ID 4");
        deleteRecord(fileName, "4");

        
        System.out.println("Final File Contents");
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void CalculatePercentage(String fileName) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    updatedLines.add(line);
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 8) {
                    try {
                        double sum = 0;
                        for (int i = 3; i <= 7; i++) {
                            sum += Double.parseDouble(data[i]);
                        }
                        double percentage = (sum / 500.0) * 100;
                        String separator = line.endsWith(",") ? "" : ",";
                        String newLine = line + separator + String.format("%.2f%%", percentage);
                        updatedLines.add(newLine);
                    } catch (NumberFormatException e) {
                        updatedLines.add(line); 
                    }
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + "\n");
            }
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }

    public static void deleteRecord(String fileName, String targetId) {
        ArrayList<String> keptLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    String currentId = data[0].trim();
                    if (!currentId.equals(targetId)) {
                        keptLines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Read error during deletion: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            for (String keptLine : keptLines) {
                writer.write(keptLine + "\n");
            }
        } catch (IOException e) {
            System.err.println("Write error during deletion: " + e.getMessage());
        }
    }
}
