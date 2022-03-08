import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Collections;


public class TechAssessment2 {

    public static ArrayList<Orderline> getInfoAsArr(){
        ArrayList<Orderline> arrOfObj = new ArrayList<Orderline>();
        try{
            // File csvFile = new File("testCSV.csv");
            File csvFile = new File("orderlines.csv");
            Scanner csvReader = new Scanner (csvFile);
            while(csvReader.hasNextLine()){
                String lineData = csvReader.nextLine(); 
                Orderline lineObj = new Orderline(lineData);
                arrOfObj.add(lineObj);
            }
            csvReader.close();
        } catch (FileNotFoundException event){
            event.printStackTrace();
        }
        return arrOfObj;
    }

    public static ArrayList<OutputLine> sortInfo(ArrayList<Orderline> orderlineObjs){
        Collections.sort(orderlineObjs, Orderline.prodIdComparator);
        ArrayList<OutputLine> condensedData = new ArrayList<OutputLine>();
        int currentProductID = orderlineObjs.get(0).productInt;
        int ongoingSum = 0;

        for (int i = 0; i < orderlineObjs.size(); i++){
            Orderline currentLine = orderlineObjs.get(i);

            if (currentLine.productInt == currentProductID){
                ongoingSum = ongoingSum + currentLine.quantity;
                if (i == (orderlineObjs.size() - 1)){
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
        return condensedData;
    }

    public static void printToFile(ArrayList<OutputLine> dataToPrint){
        
        try {
            File finalFile = new File("ABC.csv");
            if(finalFile.createNewFile()){
                finalFile.getName();
            }
            FileWriter finalFileWrite = new FileWriter("ABC.csv");
            for (int i = 0; i < dataToPrint.size(); i++){
                // System.out.println(dataToPrint.get(i).productID + ";" + dataToPrint.get(i).finalQty + "\n");
                finalFileWrite.write(dataToPrint.get(i).productID + ";" + dataToPrint.get(i).finalQty + "\n");
            }
            finalFileWrite.close();
        } catch (IOException event){
            event.printStackTrace();
        }
    }

    public static void main (String[] args){
        ArrayList<Orderline> orderLinesArray = getInfoAsArr();
        ArrayList<OutputLine> condensedData = sortInfo(orderLinesArray);
        Collections.sort(condensedData, OutputLine.prodQtyComparator);
        printToFile(condensedData);
    }
}