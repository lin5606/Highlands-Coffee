package model;

import enums.TableStatus;

public class Table {
    private String Table_id;
    private String Table_number;
    private int Seat;
    private TableStatus Table_status;
    public Table(){
        Table_id=Table_number="";
        Seat=0;
        Table_status=TableStatus.TRONG;
    }
    public Table(String id, String number, int seat, TableStatus tablestatus){
        this.Table_id=id;
        this.Table_number=number;
        this.Seat=seat;
        this.Table_status=tablestatus;
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
    public TableStatus getStatus(){
        return Table_status;
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
    public void setStatus(TableStatus tablestatus){
        this.Table_status=tablestatus;
    }
}
