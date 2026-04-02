package model;

public class OrderItem {

    private String Order_item_id;
    private Order order;
    private Product product;
    private int Quantity;
    private float Price;

    //constructor
    public OrderItem(){
        this.Order_item_id="";
        this.order= null;
        this.product=null;
        this.Quantity=0;
        this.Price=0;
    }
    public OrderItem(String orderitemid, Order order, Product product, int quantity) {
        this.Order_item_id=orderitemid;
        this.order = order;
        this.product = product;
        this.Quantity = quantity;
        this.Price = product.getPrice(); 
    }

    //getter
    public String getOrder_item_id(){
        return Order_item_id;
    }
    public Order getorder(){
        return order;
    }
    public Product getProduct(){
        return product;
    }
    public int getQuantity(){
        return Quantity;
    }
    public float getPrice(){
        return Price;
    }
    public double getSubtotal() {
        return Quantity * Price;
    }

    //setter
    public void setOrder_item_id(String orderitemid){
        this.Order_item_id=orderitemid;
    }
    public void setorder(Order order){
        this.order=order;
    }
    public void setProduct(Product product){
        this.Product=product;
        if(product!=null){
            this.Price=product.getPrice();
        }
    }
    public void setQuantiry(int quantity){
        this.Quantity=quantity;
    }
}
