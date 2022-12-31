package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class accessFile {

    public void writeToFile(String filename, String valueToWrite) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(valueToWrite);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFromFile (String filename) {
        String valueReturned = "";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                valueReturned = data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return valueReturned;
    }

}
