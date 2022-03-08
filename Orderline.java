import java.util.Comparator;

public class Orderline {
    String orderNum = "";
    String productStr = "";
    int quantity = 0;
    int productInt = 0;
    
    public Orderline(String lineInfo){
        String[] dataArr = lineInfo.split(";");
        orderNum = dataArr[0];
        productStr = dataArr[1];
        quantity = Integer.parseInt(dataArr[2]);
        productInt = Integer.parseInt(dataArr[1].replace("Product_", ""));
    }

    public static Comparator<Orderline> prodIdComparator = new Comparator<Orderline>() {
        public int compare (Orderline id1, Orderline id2){
            int idVal1 = id1.productInt;
            int idVal2 = id2.productInt;
            return idVal1 - idVal2;
        }
    };
}
