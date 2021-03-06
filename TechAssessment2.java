import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

//=========================================
// This is round two of the same tech assessment, while the general order of operations is similar (format the data for processing -> sort the data -> condense the data -> sort the data again -> format the data for output) I took a different approach after learning about a few more concepts, namely how to build objects and use ArrayList properly (and how Comparators work).  
//=========================================

public class TechAssessment2 {
    public static ArrayList<Orderline> getInfoAsArr(File csvFile){
        // Takes the File as a parameter, reads it line by line, and creates an object (Orderline) for each line, then pushes that object into an ArrayList.  
        // The (Orderline) object has attributes related to the relevant information from the file it's reading, and also a [Comparator] method to enable sorting

        ArrayList<Orderline> arrOfObj = new ArrayList<Orderline>();
        try{
            Scanner csvReader = new Scanner (csvFile);
            while(csvReader.hasNextLine()){
                // reads every line of the csv file, passes that line's information into Orderline which creates an object of that line's data
                
                String lineData = csvReader.nextLine(); 
                Orderline lineObj = new Orderline(lineData);
                if (lineObj.failed){System.exit(0);}
                    // breaks out of the while loop if there's an issue reading the data, so it doesn't read extra errors
                    // I _feel_ like (System.exit(0)) is bad practice, but it does the job in this case.

                arrOfObj.add(lineObj);
                    // adds that object to the ArrayList
            }
            csvReader.close();
        } catch (FileNotFoundException event){
            event.printStackTrace();
        }
        return arrOfObj;
    }

    public static ArrayList<OutputLine> sortCondenseInfo(ArrayList<Orderline> orderlineObjs){
        // this function sorts and condenses the ArrayList of Orderline objects, by first sorting the ArrayList by productID, because we know there is an order where all identical IDs are going to be grouped, the program can iterate through them all and continually add the quantities together. 
        // when that ID changes, it detects it and pushes the ID/QTY pairing as an object (OutputLine) to the (condensedData) ArrayList.  

        Collections.sort(orderlineObjs, Orderline.prodIdComparator);
            // Sort works by running Comparator within (Orderline) object

        ArrayList<OutputLine> condensedData = new ArrayList<OutputLine>();
            // Declare a new ArrayList to hold output data

        int currentProductID = orderlineObjs.get(0).productInt;
            // Set the initial ID as the first item in the sorted array passed in as parameter

        int ongoingSum = 0;
            // set the initial sum to 0

        for (int i = 0; i < orderlineObjs.size(); i++){
            // loop through entire array, based on it's size, if the ID between objects matches continually add their quantities together.  

            Orderline currentLine = orderlineObjs.get(i);
            if (currentLine.productInt == currentProductID){
                // if the current object's ID matches the currently 'matched' ID, sum them together

                ongoingSum = ongoingSum + currentLine.quantity;                
                
                if (i == (orderlineObjs.size() - 1)){
                    // This nested conditional catches the last object in the loop. 
                
                    OutputLine newLineObj = new OutputLine(ongoingSum, orderlineObjs.get(i - 1).productStr);
                    condensedData.add(newLineObj);
                }
            } else {
                OutputLine newLineObj = new OutputLine(ongoingSum, orderlineObjs.get(i - 1).productStr);
                condensedData.add(newLineObj);
                ongoingSum = currentLine.quantity;
                currentProductID = currentLine.productInt;
            }
        }
        Collections.sort(condensedData, OutputLine.prodQtyComparator);
        return condensedData;
            // sorts the data by quantity, and the returns that condensed and sorted array (which will be printed)
    }

    public static void printToFile(ArrayList<OutputLine> dataToPrint){
        // Takes the data to print (which is an ArrayList of objects, which contain the ID and QTY), loops through and writes them line by line to the file ABC.csv
        // In the testcode.txt file there is a version of this code that will write new files everytime -- ABC0.csv, ABC1.csv, ABC2.csv... etc -- I am not confident it's stable (and also isn't asked about in the assessment outline) so I didn't include it, very nervous about some unforseen circumstance in which it causes major memory issues.  
        
        try {
            FileWriter finalFileWrite = new FileWriter("ABC.csv");
            for (int i = 0; i < dataToPrint.size(); i++){
                finalFileWrite.write(dataToPrint.get(i).productID + ";" + dataToPrint.get(i).finalQty + "\n");
            }
            System.out.println("ABC.csv successfully created!");
            finalFileWrite.close();
        } catch (IOException event){
            event.printStackTrace();
        }
    }

    public static void main (String[] args){
            // Program checks for an argument, if one exists it runs the program, otherwise prints helpful information

        if (args.length == 1){
            File csvFile = new File(args[0]);
            ArrayList<Orderline> orderLinesArray = getInfoAsArr(csvFile);
                // if the argument exists it continues, sets that argument as a File, pushes file to the filereader function as argument (getInfoAsArr) and returns ArrayList of "Orderline" Objects containing all information, 

            ArrayList<OutputLine> condensedData = sortCondenseInfo(orderLinesArray);
                // pushes that array into a sorting/condensing function, which returns a condensed ArrayList of "OutputLine" objects,  

            printToFile(condensedData);
                // feeds the condensed data to a fileprinting function, which will create and write the file

        } else {
            System.out.println("TechAssessment2 takes exactly 1 argument");
            System.out.println("First Argument Required As File That Exists In Working Directory, (ie, 'java TechAssessment2 orderlines.csv')");
        }
    }
}