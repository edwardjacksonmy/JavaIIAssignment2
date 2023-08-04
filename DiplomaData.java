import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DiplomaData {
    private String category;
    private String name;
    private int total;
    private int max;
    private int min;

    // Constructor
    public DiplomaData(String category, String name, int total, int max, int min) {
        this.category = category;
        this.name = name;
        this.total = total;
        this.max = max;
        this.min = min;
    }

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    // Optional toString method for printing the object's details
    @Override
    public String toString() {
        return "DiplomaData{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", total=" + total +
                ", max=" + max +
                ", min=" + min +
                '}';
    }

    public static void main(String[] args) {
        // Specify the file path
        String filePath = "C:\\Users\\edwar\\IdeaProjects\\DiplomaData\\src\\diploma.csv";

        // 1. File Existence Check
        if (Files.exists(Paths.get(filePath))) {
            System.out.println("File exists.");
        } else {
            System.out.println("File does not exist.");
            return; // Exit the program if the file does not exist
        }

        // 2. File Readability Check
        if (Files.isReadable(Paths.get(filePath))) {
            System.out.println("File is readable.");
        } else {
            System.out.println("File is unreadable.");
            return; // Exit the program if the file is not readable
        }

        // ArrayList to hold objects of the DiplomaData class
        ArrayList<DiplomaData> diplomaDataList = new ArrayList<>();

        // ArrayList to hold the lines with invalid data
        ArrayList<String> invalidDataLines = new ArrayList<>();

        // ArrayLists to hold names for Diploma Lanjutan and Kursus Pengkhususan
        ArrayList<String> diplomaLanjutan = new ArrayList<>();
        ArrayList<String> kursusPengkhususan = new ArrayList<>();

        // ArrayList to hold the total, max, and min values for each category
        ArrayList<String> diplomaLanjutanData = new ArrayList<>();
        ArrayList<String> kursusPengkhususanData = new ArrayList<>();

        // 3. Using try-with-resources to read and process the CSV file safely
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the first line (header) of the CSV file
            br.readLine();

            String line;
            int lineNumber = 2; // Start from line 2 (since we skipped the header)
            while ((line = br.readLine()) != null) {
                // Split the CSV line by commas to extract individual data elements
                String[] data = line.split(",");
                if (data.length == 6) {
                    String category = data[1].trim();
                    String name = data[2].trim();
                    int total = Integer.parseInt(data[3].trim());
                    int max = Integer.parseInt(data[4].trim());
                    int min = Integer.parseInt(data[5].trim());

                    // Create a DiplomaData object and add it to the ArrayList
                    DiplomaData diplomaData = new DiplomaData(category, name, total, max, min);
                    diplomaDataList.add(diplomaData);

                    // Add the name to the appropriate ArrayList based on the category
                    if (category.equals("Diploma Lanjutan")) {
                        diplomaLanjutan.add(name);
                        diplomaLanjutanData.add(name + ": Total = " + total + ", Max = " + max + ", Min = " + min);
                    } else if (category.equals("Kursus Pengkhususan")) {
                        kursusPengkhususan.add(name);
                        kursusPengkhususanData.add(name + ": Total = " + total + ", Max = " + max + ", Min = " + min);
                    }
                } else {
                    // Log the line number and the invalid data line
                    String invalidLineInfo = "Line " + lineNumber + ": " + line;
                    invalidDataLines.add(invalidLineInfo);
                    System.out.println("Invalid data format in the CSV line: " + invalidLineInfo);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        // Write data to separate text files based on categories
        try {
            // Writing data to the Diploma Lanjutan text file
            BufferedWriter diplomaLanjutanWriter = new BufferedWriter(new FileWriter("Diploma Lanjutan.txt"));
            for (String data : diplomaLanjutanData) {
                diplomaLanjutanWriter.write(data);
                diplomaLanjutanWriter.newLine();
            }
            diplomaLanjutanWriter.close();

            // Writing data to the Kursus Pengkhususan text file
            BufferedWriter kursusPengkhususanWriter = new BufferedWriter(new FileWriter("Kursus Pengkhususan.txt"));
            for (String data : kursusPengkhususanData) {
                kursusPengkhususanWriter.write(data);
                kursusPengkhususanWriter.newLine();
            }
            kursusPengkhususanWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing the data to files: " + e.getMessage());
        }
        // If there were any lines with invalid data, print them to a file or console


    }

}