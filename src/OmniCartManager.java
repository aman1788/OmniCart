import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class OmniCartManager implements ShoppingManager{
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Product> productsList = new ArrayList<>();
    private static String userInput = "";
    private static final String menu = """
                                Westminster Shopping Manager
                                ---------------------------- 
                                      
                      1.  Add product 
                      2.  Delete a product
                      3.  Display list of products in the system
                      4.  Save products list to a file 
                      5.  Load product list details from the file
                      6.  Open the GUI
                      7.  Exit      
                     
            """;


    public static void main(String[] args) {
        OmniCartManager shoppingManager = new OmniCartManager();

        while(!userInput.equals("6")){
//            display menu
            System.out.println(menu);

//            user input for menu
            System.out.println("Enter Option (1 - 7) : ");
            userInput = scanner.next();

            switch (userInput) {
                case "1" -> shoppingManager.addProduct();
                case "2" -> shoppingManager.deleteProduct();
                case "3" -> shoppingManager.displayProducts();
                case "4" -> shoppingManager.saveFile();
                case "5" -> shoppingManager.loadFile();
                case "6" -> shoppingManager.displayGUI();
                case "7" -> System.out.println("System Exited");
                default -> System.out.println("Invalid code please try again!");
            }

        }
    }


    /**
     * Add products to the system
     */
    @Override
    public void addProduct() {
        while (true) {
            try {
                System.out.println("Add Electronics or Clothing :");
                String user_response = scanner.next().toLowerCase();

                if (!user_response.equals("electronics") && !user_response.equals("electronic") && !user_response.equals("clothing")) {
                    System.out.println("Wrong input! Enter either 'Clothing' or 'Electronics' ");
                    continue;
                }

                System.out.println("Product Name : ");
                String productName = scanner.next();

                System.out.println("Product ID : ");
                String productId = scanner.next();

                System.out.println("Product Price : ");
                int productPrice = scanner.nextInt();

                System.out.println("No. of available items : ");
                int noOfAvailableItems = scanner.nextInt();

                if (user_response.equals("electronics") || user_response.equals("electronic") ) {
                    System.out.println("Product Brand : ");
                    String productBrand = scanner.next();

                    System.out.println("Product Warranty : ");
                    String productWarranty = scanner.next();

                    Electronics electronic = new Electronics(productId, productName, productPrice, noOfAvailableItems, productBrand, productWarranty);
                    if (productsList.size() != 50) {
                        productsList.add(electronic);
                    } else {
                        System.out.println("System full, maximum of 50 products only!");
                    }

                } else {
                    System.out.println("Product Size : ");
                    String productSize = scanner.next();

                    System.out.println("Product Colour : ");
                    String productColour = scanner.next();

                    Clothing clothing = new Clothing(productId, productName, productPrice, noOfAvailableItems, productSize, productColour);
                    if (productsList.size() != 50) {
                        productsList.add(clothing);
                    } else {
                        System.out.println("System full, maximum of 50 products only!");
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter the correct data type.");
                scanner.next();
            }
        }
    }


    /**
     * delete products from the system
     */
    @Override
    public void deleteProduct() {
        try {
            ArrayList<Product> productToDelete = new ArrayList<>();
            boolean productDeleted = false;

            while (!productDeleted) {
                System.out.println("Enter Product ID (enter 7 to go back): ");
                String productId = scanner.next();

                for (Product product : productsList) {
                    if (productId.equals(product.getProductId())) {
                        productToDelete.add(product);
                        System.out.println("Deleted product " + product.getProductName());
                        productDeleted = true;
                        break;
                    }
                }

                if (!productDeleted) {
                    System.out.println("Cannot find product with ID " + productId);
                }

                if (productId.equals("7")) {
                    break;
                }

                // Remove product
                productsList.removeAll(productToDelete);
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }


    /**
     * this method returns a string representation of the product details of any type of product passed.
     * @param product the product for which to display details.
     * @return String which is a concatenation of both String of common product details + its more specific
     * instance variable information.
     */
    public String displayProductDetails(Product product) {
        String productClass ;
        if (product instanceof Electronics){
            productClass = "Electronics";
        }else {
            productClass = "Clothing";
        }

        String productName = product.getProductName();
        String productID = product.getProductId();
        double productPrice = product.getProductPrice();
        int noOfAvailableItems = product.getNoOfAvailableItems();

        String productDetails = """
                %s - %s 
                  ID - %s
                  Price - %.2f
                  Available items - %d
                """.formatted(productClass,productName,productID,productPrice, noOfAvailableItems);

        if (productClass.equals("Electronics")){
            String productBrand = ((Electronics) product).getBrand();
            String productWarranty = ((Electronics) product).getWarranty();
            String electronicsAttributes = """
                      Brand - %s
                      Warranty - %s
                    """.formatted(productBrand,productWarranty);
            return productDetails + electronicsAttributes;
        } else {
            String productSize = ((Clothing) product).getSize();
            String productColour = ((Clothing) product).getColour();
            String clothingAttributes = """
                      Size - %s
                      Colour - %s
                    """.formatted(productSize,productColour);
            return productDetails + clothingAttributes;
        }

    }


    /**
     * calls the displayProductDetails() method and passes all products from productsList arraylist and
     * displays the product details.
     */
    @Override
    public void displayProducts() {
        System.out.println("""
                                 List of products
                                 ----------------
                """);

        if (productsList.isEmpty()) {
            System.out.println("Product List is Empty");
        }

        //Sort and Display Products by the Product ID
//        Collections.sort(productsList);
        for (Product product : productsList) {
            String productDetails = displayProductDetails(product);
            System.out.println(productDetails);
        }

    }


    /**
     * saves product information to a text file.
     * calls the displayProductDetails() method and gets the String detail of each product to write to the file.
     */
    @Override
    public void saveFile() {
        try {
            FileWriter writer = new FileWriter("SystemData.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.newLine();

            int count = 1;
            for (Product product : productsList) {
                String productDetails = displayProductDetails(product);
                bufferedWriter.write( productDetails);
                count++;
            }

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("An error occurred...try again!");
        }

        System.out.println("Program Data Stored. ");
    }


    /**
     * loads/reads the text file in which product details are stored and displays it.
     */
    @Override
    public void loadFile() {
        try {
            File file = new File("SystemData.txt");
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String file_data = fileReader.nextLine();
                System.out.println(file_data);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred...try again!");
        }
    }


    /**
     * method to run the GUI
     */
    @Override
    public void displayGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OmniCartGUI omniCartGUI = new OmniCartGUI();
                OmniCartGUI.displayGUI();
            }
        });
    }



}
