import java.util.Comparator;

public class OutputLine {
    int finalQty;
    String productID;

    public OutputLine (int qty, String prodId){
        finalQty = qty;
        productID = prodId;
    }

    public static Comparator<OutputLine> prodQtyComparator = new Comparator<OutputLine>() {
        public int compare (OutputLine qty1, OutputLine qty2){
            int quantityValue1 = qty1.finalQty;
            int quantityValue2 = qty2.finalQty;
            return quantityValue2 - quantityValue1;
            // reversed order to reverse sort
        }
    };
}
