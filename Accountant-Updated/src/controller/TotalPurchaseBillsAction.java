package controller;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DataBase;

public class TotalPurchaseBillsAction{

    private DataBase mDataBase;
    private ObservableList<Total_Purchase_bill_table> TableData;
    private TextField TFInput;
    private String key = "",StrInput;
    private int IntNo = 0;
    private ResultSet LoopResult;

    public TotalPurchaseBillsAction() {
        mDataBase = new DataBase();
    }

    public void TableRefresh(TableView T) {
        try {
            IntNo = 0;
            TableData = FXCollections.observableArrayList();
            LoopResult = mDataBase.SelectBill("purchase_bill");
            while (LoopResult.next()) {
                    TableData.add(new Total_Purchase_bill_table(
                            ++IntNo,
                            LoopResult.getInt("purchase_bill.ssn"),
                            LoopResult.getInt("purchase_bill.total"),
                            LoopResult.getString("purchase_bill.date_time"),
                            LoopResult.getString("purchase_bill.bill_type"),
                            LoopResult.getString("supplier.name")));
                            T.setItems(TableData);
                }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter Correct Barcode");
        }
    }

    public void PurchaseSearch(RadioButton RadioSSN,RadioButton RadioDate, TableView T, TextField tf) {
        try {
        	TFInput = tf;
            if (RadioSSN.isSelected())
                key = RadioSSN.getText();
            else if (RadioDate.isSelected()) 
                key = RadioDate.getText();
            StrInput = TFInput.getText();
            
            if(key.matches("Date"))
            	if(StrInput.length() < 8 ){
                    JOptionPane.showMessageDialog(null, "Enter Correct Date");
                    return;
            	}

            IntNo = 0;
            TableData = FXCollections.observableArrayList();
            LoopResult = mDataBase.searchSSN(key, "purchase_bill",StrInput);
            while (LoopResult.next()) {
               TableData.add(new Total_Purchase_bill_table(
                            ++IntNo,
                            LoopResult.getInt("purchase_bill.ssn"),
                            LoopResult.getInt("purchase_bill.total"),
                            LoopResult.getString("purchase_bill.date_time"),
                            LoopResult.getString("purchase_bill.bill_type"),
                            LoopResult.getString("supplier.name")));
                T.setItems(TableData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter Correct SSN");

        }
    }
    public class Total_Purchase_bill_table {

        private SimpleIntegerProperty No;
        private SimpleIntegerProperty SSN;
        private SimpleIntegerProperty Total;
        private SimpleStringProperty Date_Time;
        private SimpleStringProperty Bill_Type;
        private SimpleStringProperty Supplier;

        public Total_Purchase_bill_table(int No, int SSN, int Total, String Date_Time,String Bill_type, String supplier) {
            this.No = new SimpleIntegerProperty(No);
            this.SSN = new SimpleIntegerProperty(SSN);
            this.Total = new SimpleIntegerProperty(Total);
            this.Date_Time = new SimpleStringProperty(Date_Time);
            this.Bill_Type = new SimpleStringProperty(Bill_type);
            this.Supplier= new SimpleStringProperty(supplier);
        }

        public int getNo() {
            return No.get();
        }
        public void SetNo(int no){
        	No.set(no);
        }
        public int getSSN() {
            return SSN.get();
        }
        public void SetSSN(int ssn){
        	SSN.set(ssn);
        }
        public int getTotal() {
            return Total.get();
        }
        public void SetTotal(int total){
        	Total.set(total);
        }

        public String getDate_Time() {
            return Date_Time.get();
        }
        public void SetDate_Time(String date_time){
        	Date_Time.set(date_time);
        }
        public String getBillType() {
            return Bill_Type.get();
        }
        public void setBillType(String bill) {
        	Bill_Type.set(bill);
        }
        public String getSupplier() {
            return Supplier.get();
        }
        public void setSupplier_id(String supplier) {
            Supplier.set(supplier);
        }
        
    }
	
}
