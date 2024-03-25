public class Clothing extends Product{

    private String size;
    private String colour;

    Clothing(String productID, String productName, int price, int noOfAvailableItems, String size, String colour){
        super(productID,productName,price,noOfAvailableItems);
        setSize(size);
        setColour(colour);
    }

    public String getSize(){
        return size;
    }

    public String getColour(){
        return colour;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColour(String colour){
        this.colour = colour;
    }

}
