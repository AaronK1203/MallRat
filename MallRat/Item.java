public class Item{
    /**
     * The name of the item
     */
    private String name;
    /**
     * The price of one item
     */
    private double price;
    /**
     * The quantity of the items being sold
     */
    private int quantity;
    /**
     * The tax that is applied to this item
     */
    private Tax tax;
    
    
    public Item(String name, double price){
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}