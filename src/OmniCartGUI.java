import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OmniCartGUI {
    private static DefaultTableModel tableModel;
    private static DefaultTableModel shoppingCartTableModel;
    private static JFrame frame;
    private static JLabel selectedProductLabel;
    private static JTextArea productDetailsTextArea;

    private static JTable shoppingCartTable;

    private static OmniCart omniCart;

    private static String productType;
    private static String productDetails;


    public static void displayGUI() {
        omniCart = new OmniCart();
        // Main Window
        JFrame frame = new JFrame("Westminster Shopping Centre");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPanel for components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // JPanel for the shopping cart button and combo box
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Text Label for the panel
        JLabel label = new JLabel("Select Product Category:");

        // Combobox
        String[] productTypes = {"All", "Electronics", "Clothes"};
        JComboBox<String> comboBox = new JComboBox<>(productTypes);

        // ActionListener for selection change responses
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProductType = (String) comboBox.getSelectedItem();
                System.out.println("Selected Product Type: " + selectedProductType);
            }
        });

        //Shopping Cart button
        JButton shoppingCartButton = new JButton("Shopping Cart");

        //ActionListener for Shopping Cart button
        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCartWindow();
            }
        });

        // Adding components to comboPanel
        comboPanel.add(label);
        comboPanel.add(comboBox);
        comboPanel.add(shoppingCartButton);

        // Add comboPanel to the main panel
        panel.add(comboPanel);

        // Setting padding between the comboPanel and the table
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Creating table model with columns - ProductID, Name, Category, Price, Info
        tableModel = new DefaultTableModel(new Object[]{"Product ID", "Name", "Category", "Price($)", "Info"}, 0);

        for (Product product : OmniCartManager.productsList) {
            String productClass;
            String productInfo;
            if (product instanceof Electronics){
                String productBrand = ((Electronics) product).getBrand();
                String productWarranty = ((Electronics) product).getWarranty();
                productInfo = productBrand + ", " + productWarranty;
                productClass = "Electronics";
            }else {
                String productSize = ((Clothing) product).getSize();
                String productColour = ((Clothing) product).getColour();
                productInfo = productSize + ", " + productColour;
                productClass = "Clothing";
            }
            Object[] row = {product.getProductId(), product.getProductName(),
                    productClass,product.getProductPrice(), productInfo};
            tableModel.addRow(row);
        }

        // Creating a JTable with the table model
        JTable table = new JTable(tableModel);

        table.setPreferredScrollableViewportSize(new Dimension(500, 150));

        // ListSelectionListener for row selection changes
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rowSelected = table.getSelectedRow();
                if (rowSelected >= 0) {
                    // Displaying the selected product/row details
                    displaySelectedProductDetails(rowSelected);
                }
            }
        });

        // Adding the table to a JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        // Set padding between the table and the product details section
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Label for the product details title
        JLabel productDetailsTitleLabel = new JLabel("Product Details");
        panel.add(productDetailsTitleLabel);

        // TextArea for displaying product details
        productDetailsTextArea = new JTextArea();
        productDetailsTextArea.setEditable(false);
        productDetailsTextArea.setPreferredSize(new Dimension(500, 150));
        panel.add(productDetailsTextArea);

        // Add to Cart button
        JButton addToCartButton = new JButton("Add to Cart");

        //ActionListener for the "Add to Cart" button
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Getting selected row from the table
                int selectedRow = table.getSelectedRow();

                if (selectedRow >= 0) {
                    // get the product details from the selected row
                    String productId = (String) tableModel.getValueAt(selectedRow, 0);
                    Product productSelected = getProductById(productId);

                    if (productSelected != null) {
                        // Adding the selected product to the shopping cart
                        omniCart.addProduct(productSelected);
                        productSelected.setNoOfAvailableItems(productSelected.getNoOfAvailableItems()-1);
                        JOptionPane.showMessageDialog(frame, "Product added to the shopping cart.");
                    } else {
                        // if product not found
                        JOptionPane.showMessageDialog(frame, "Error: Product not found!!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        panel.add(addToCartButton);

        frame.getContentPane().add(panel);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void displaySelectedProductDetails(int selectedRow) {
        // get the product details from the selected row
        String productID = (String) tableModel.getValueAt(selectedRow, 0);

        // get the product from the productList
        Product selectedProduct = getProductById(productID);

        // Displaying product details in the TextArea
        if (selectedProduct != null) {
            String productDetails = """
                    Selected Product Details :
                    
                    
                    Product ID: %s
                    Name: %s
                    Available Items: %d
                    Price: %s
                    """.formatted(selectedProduct.getProductId(), selectedProduct.getProductName(), selectedProduct.getNoOfAvailableItems(),
                    selectedProduct.getProductPrice());
            productDetailsTextArea.setText(productDetails);
        }
    }


    private static Product getProductById(String productID) {
        for (Product product : OmniCartManager.productsList) {
            if (product.getProductId() == productID) {
                return product;
            }
        }
        return null;
    }


    private static void openShoppingCartWindow() {
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table model with columns: Product ID, Name, Quantity, Price
        shoppingCartTableModel = new DefaultTableModel(new Object[]{"Product ID", "Name", "Quantity", "Price"}, 0);

        shoppingCartTable = new JTable(shoppingCartTableModel);
        JScrollPane scrollPane = new JScrollPane(shoppingCartTable);

        List<OmniCartItem> omniCartItems = omniCart.getCartItems();
        for (OmniCartItem omniCartItem : omniCartItems) {
            Product product = omniCartItem.getProduct();
            int quantity = omniCartItem.getQuantity();

            Object[] row = {product.getProductId(), product.getProductName(), quantity, product.getProductPrice()};
            shoppingCartTableModel.addRow(row);
        }


        // Adding components to shopping cart window
        shoppingCartFrame.getContentPane().add(scrollPane);

        // Size of shopping cart frame
        shoppingCartFrame.setSize(400, 300);

        // Centering the shopping cart frame
        shoppingCartFrame.setLocationRelativeTo(frame);

        shoppingCartFrame.setVisible(true);
    }

}
