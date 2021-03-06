import java.util.Comparator;

public class OutputLine {
    // attributes
    
    int finalQty;
    String productID;

    public OutputLine (int qty, String prodId){
        // simple assignment of parameters to attributes

        finalQty = qty;
        productID = prodId;
    }

    public static Comparator<OutputLine> prodQtyComparator = new Comparator<OutputLine>() {
        // similar Comaprator to the Orderline one, but the order of comparison is reversed to effectively 'reverse sort'

        public int compare (OutputLine qty1, OutputLine qty2){
            int quantityValue1 = qty1.finalQty;
            int quantityValue2 = qty2.finalQty;
            return quantityValue2 - quantityValue1;
        }
    };
}
