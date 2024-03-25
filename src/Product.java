abstract class Product{

    private String productID;
    private String productName;
    private double price;
    private int noOfAvailableItems;

    Product(String productID, String productName, double price, int noOfAvailableItems){
        setProductID(productID);
        setProductName(productName);
        setPrice(price);
        setNoOfAvailableItems(noOfAvailableItems);
    }

    public String getProductId() {
        return productID;
    }
    public String getProductName() {
        return productName;
    }
    public double getProductPrice() {
       return price;
    }

    public int getNoOfAvailableItems(){
        return noOfAvailableItems;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public void setNoOfAvailableItems(int noOfAvailableItems){
        this.noOfAvailableItems = noOfAvailableItems;
    }


}
