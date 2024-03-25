public class Electronics extends Product {

    private String brand;
    private String warranty;

    Electronics(String productID, String productName, int price,int noOfAvailableItems, String brand, String warranty){
        super(productID,productName,price,noOfAvailableItems);
        setBrand(brand);
        setWarranty(warranty);
    }

    public String getBrand(){
        return brand;
    }
    public String getWarranty(){
        return warranty;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }


}
