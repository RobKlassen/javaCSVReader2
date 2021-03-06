import java.util.Comparator;

public class Orderline {
    // attributes
    
    String orderNum;
    String productStr;
    int quantity;
    int productInt;
    boolean failed = false;
    
    public Orderline(String lineInfo){
        // Orderline will try to make an object, if it fails it attempts to tell you why, and also switches a fail attribute so the program can stop running when there's a problem with the data.
        // It splits by the ";" delimiter, 
            // INDEX 0 is assigned the order number ((NOTE: This is never used, I want to just not include it because it's unnecessary BUT I also want to acknowledge that it's an important part of this object.  I guess in the real world I would _Ask the Supervisor_))
            // INDEX 1 is assigned the "Product_{integer}" string, 
            // INDEX 2 is assigned the Quantity
                // it then takes index 1, the Product_{int}, and removes the 'Product_' and converts the remaining number to an integer, which is easy to sort. 
  
        try {
            String[] dataArr = lineInfo.split(";");
            orderNum = dataArr[0];
            productStr = dataArr[1];
            quantity = Integer.parseInt(dataArr[2]);
            productInt = Integer.parseInt(dataArr[1].replace("Product_", ""));
        } catch (Exception event){
            System.out.println("=====================================================");
            System.out.println("This File isn't formatted for TechAssessment2 \nUse a .csv with 3 values delimited by \";\" \nindex 0 is irrelevant, \nindex 1 must be formatted \"Product_{integer}\"\nindex 2 must be formatted \"{integer}\"");
            System.out.println("\nLine that caused issue: " + lineInfo);
            System.out.println("\nEXACT ERROR: " + event);
            System.out.println("=====================================================");
            failed = true;
        }
    }

    public static Comparator<Orderline> prodIdComparator = new Comparator<Orderline>() {
        // Comparator allows comparison and sorting of two Orderline objects, 

        public int compare (Orderline id1, Orderline id2){
            int idVal1 = id1.productInt;
            int idVal2 = id2.productInt;
            return idVal1 - idVal2;
        }
    };
}
