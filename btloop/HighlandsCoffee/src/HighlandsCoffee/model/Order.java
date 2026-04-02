package model;

import java.util.Date;
import enums.OrderStatus;

public class Order {
    private String Order_id;
    private Date Order_date;
    private float Total_amount;
    private OrderStatus Order_status;

    //constructor
    public Order(){
        this.Order_id="";
        this.Order_date=null;
        this.Total_amount=0;
        this.Order_status=OrderStatus.PENDING;
    }
    public Order(String orderid, Date orderdate, float totalamount, OrderStatus orderstatus){
        this.Order_id=orderid;
        this.Order_date=orderdate;
        this.Total_amount=totalamount;
        this.Order_status=orderstatus;
    }

    //getter
    public String getOrder_id(){
        return Order_id;
    }
    public Date getOrder_date(){
        return Order_date;
    }
    public float getTotal_amount(){
        return Total_amount;
    }
    public OrderStatus getOrder_status(){
        return Order_status;
    }

    //setter
    public void setOrder_id(String orderid){
        this.Order_id=orderid;
    }
    public void setOrder_date(Date orderdate){
        this.Order_date=orderdate;
    }
    public void setTotal_amount(float totalamount){
        this.Total_amount=totalamount;
    }
    public void setOrder_status(OrderStatus orderstatus){
        this.Order_status=orderstatus;
    }
}
