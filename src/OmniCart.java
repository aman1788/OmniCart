import java.util.ArrayList;
import java.util.List;

public class OmniCart {
    private List<OmniCartItem> omniCartItems;

    public OmniCart() {
        this.omniCartItems = new ArrayList<>();
    }
    public void addProduct(Product product) {
        // Checking if product is already in the cart
        for (OmniCartItem omniCartItem : omniCartItems) {
            if (omniCartItem.getProduct().getProductId().equals(product.getProductId())) {
                omniCartItem.incrementQuantity();
                return;
            }
        }

        // If product is not in cart, add a new item
        omniCartItems.add(new OmniCartItem(product, 1));
    }

    public List<OmniCartItem> getCartItems() {
        return omniCartItems;
    }

    @Override
    public String toString() {
        return omniCartItems.toString();
    }


}
