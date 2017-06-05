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

public class TotalSalesBillAction  {

    public DataBase mDataBase;
    private ObservableList<Total_Sales_bill_table> TableData;
    private TextField TFInput;
    private int IntNo = 0, IntTotal = 0;
    private String Key = "",StrInput;
    private ResultSet LoopResult;

    public TotalSalesBillAction() {
        mDataBase = new DataBase();
    }
    
    public void TableRefresh(TableView T) {
        try {
            IntNo = 0;
            TableData = FXCollections.observableArrayList();
            LoopResult = mDataBase.SelectBill("sales_bill");
            while (LoopResult.next()) {
                TableData.add(new Total_Sales_bill_table(
                        ++IntNo,
                        LoopResult.getInt("ssn"),
                        LoopResult.getInt("total"),
                        LoopResult.getInt("profit"),
                        LoopResult.getString("date_time"),
                        LoopResult.getString("bill_type")));

                T.setItems(TableData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Enter Correct Barcode");
        }
    }
    
    public void SalesSearch(RadioButton RadioSSN,RadioButton RadioDate, TableView T, TextField tf) {
    	try {
    		TFInput = tf;
    		if (RadioSSN.isSelected()) {
                Key = RadioSSN.getText();

            } else if (RadioDate.isSelected()) {
                Key = RadioDate.getText();
            }
    		
    		if(Key.matches("Date"))
            	if(StrInput.length() < 8 ){
                    JOptionPane.showMessageDialog(null, "Enter Correct Date");
                    return;
            	}
            IntNo = 0;
            StrInput = TFInput.getText();
            TableData = FXCollections.observableArrayList();
            LoopResult = mDataBase.searchSSN(Key, "sales_bill",StrInput);
            while (LoopResult.next()) {
                TableData.add(new Total_Sales_bill_table(
                        ++IntNo,
                        LoopResult.getInt("ssn"),
                        LoopResult.getInt("total"),
                        LoopResult.getInt("profit"),
                        LoopResult.getString("date_time"),
                        LoopResult.getString("bill_type")));
                T.setItems(TableData);
            }
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Enter Correct SSN");
        }
    }

    public class Total_Sales_bill_table {

        private SimpleIntegerProperty No;
        private SimpleIntegerProperty SSN;
        private SimpleIntegerProperty Total;
        private SimpleIntegerProperty Profit;
        private SimpleStringProperty Date_Time;
        private SimpleStringProperty Bill_Type;

        public Total_Sales_bill_table(int No, int SSN, int Total, int Profit, String Date_Time, String Bill_Type) {
            this.No = new SimpleIntegerProperty(No);
            this.SSN = new SimpleIntegerProperty(SSN);
            this.Total = new SimpleIntegerProperty(Total);
            this.Profit = new SimpleIntegerProperty(Profit);
            this.Date_Time = new SimpleStringProperty(Date_Time);
            this.Bill_Type = new SimpleStringProperty(Bill_Type);
        }

        public int getNo() {
            return No.get();
        }

        public int getSSN() {
            return SSN.get();
        }

        public int getTotal() {
            return Total.get();
        }

        public int getProfit() {
            return Profit.get();
        }

        public String getDate_Time() {
            return Date_Time.get();
        }

        public String getBill_Type() {
            return Bill_Type.get();
        }

        public void setNo(SimpleIntegerProperty No) {
            this.No = No;
        }

    }
}
