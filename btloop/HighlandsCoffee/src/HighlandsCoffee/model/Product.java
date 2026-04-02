package HighlandsCoffee.model;

public class Product {
    private String Product_id;
    private String Product_name;
    private float price;
    private String unit;
    private Category category_id;
    public Product(){

    }
    public Product(String id, String name, float gia, String donvi, Category c_id){
        this.Product_id=id;
        this.Product_name=name;
        this.price=gia;
        this.unit=donvi;
        this.category_id=c_id; 
    }
    public String getProduct_id(){
        return Product_id;
    }
    public String getProduct_name(){
        return Product_name;
    }
    public float getPrice(){
        return price;
    }
    public String getUnit(){
        return unit;
    }
    public Category getC_id(){
        return category_id;
    }
    public void setProduct_id(String id){
        this.Product_id=id;
    }
    public void setProduct_name(String name){
        this.Product_name=name;
    }
    public void setPrice(float gia){
        this.price=gia;
    }
    public void setUnit(String donvi){
        this.unit=donvi;
    }
    public void setC_id(Category id){
        this.category_id=id;
    } 
}
