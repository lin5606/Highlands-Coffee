package model;

public class Table {
    private String Table_id;
    private String Table_number;
    private int Seat;
    private String Status;
    public Table(){
        Table_id=Table_number=Status="";
        Seat=0;
    }
    public Table(String id, String number, int seat, String status){
        this.Table_id=id;
        this.Table_number=number;
        this.Seat=seat;
        this.Status=status;
    }
    public String getTable_id(){
        return Table_id;
    }
    public String getTable_number(){
        return Table_number;
    }
    public int getSeat(){
        return Seat;
    }
    public String getStatus(){
        return Status;
    }
    public void setTable_id(String id){
        this.Table_id=id;
    }
    public void setTable_number(String number){
        this.Table_number=number;
    }
    public void setSeat(int seat){
        this.Seat=seat;
    }
    public void setStatus(String status){
        this.Status=status;
    }
}
