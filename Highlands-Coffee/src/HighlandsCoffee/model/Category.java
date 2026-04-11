package HighlandsCoffee.model;

public class Category {
    private String Category_id;
    private String Category_name;
    private String Description;
    public Category(){
        Category_id=Category_name=Description="";
    }
    public Category(String id, String name, String mota){
        this.Category_id=id;
        this.Category_name=name;
        this.Description=mota;
    }
    public String getCategory_id(){
        return Category_id;
    }
    public String getCategory_name(){
        return Category_name;
    }
    public String getDescription(){
        return Description;
    }
    public void setCategory_id(String id){
        this.Category_id=id;
    }
    public void setCategory_name(String name){
        this.Category_name=name;
    }
    public void setDescription(String mota){
        this.Description=mota;
    }
}
