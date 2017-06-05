package controller;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.SupplierAction.SupplierTable;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DataBase;

public class SalesBillAction implements Bills{
	private DataBase mDataBase;
	private ObservableList<sales_bill_table> TableData;
	private TextField TFBarcode,TFTotal;
	private String StrBarcode,StrDate_Time,StrBillType;
	private int IntNo = 0,IntTotal = 0;
	private ArrayList<String> BarcodeArrayList = new ArrayList<String>();;
	private ResultSet LoopResult;
	
	
	public SalesBillAction() {
		mDataBase = new DataBase();
		
	}
	
	@Override
	public void onAddBillListener(TextField Barcode,TextField Total) {
		try {
			IntTotal = 0;
			TFBarcode = Barcode;
			TFTotal = Total;
			//Read Input from user.
			StrBarcode = TFBarcode.getText();
			//Check Barcode exists befor or not.
			if(!mDataBase.CheckBarcode(StrBarcode)){
				JOptionPane.showMessageDialog(null, "Incorrect Barcode");
				return;
			}
				
			//Add barcode to ArrayList.
			BarcodeArrayList.add(StrBarcode);
			TFBarcode.clear();
			//Refresh table view.
			TableRefresh(view.Sales_Bill.table);
			//set Total to total textfield.
			TFTotal.setText(Integer.toString(IntTotal));	
		} catch (Exception e) {
//			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Incorrect Data");

		}
	}
	@Override
	public void onDeleteBillListener(TextField Barcode,TextField Total) {
		IntTotal = 0;
		TFBarcode = Barcode;
		TFTotal = Total;
		//Read Input from user.
		StrBarcode = TFBarcode.getText();
		BarcodeArrayList.remove(StrBarcode);
		TableRefresh(view.Sales_Bill.table);	
		TFTotal.setText(Integer.toString(IntTotal));
	}
	@Override
	public void onSaveSalesBillListener(TextField Total,Label Date_Time,ComboBox<String> CBBillType) {
		try {
			StrDate_Time = Date_Time.getText();
			StrBillType = CBBillType.getValue();
			//Check if Bill Type been checked or not.
			if(StrBillType == null){
				JOptionPane.showMessageDialog(null, "Please Select Bill Type");
				return;
			}
			//Add Bill to Database.
			mDataBase.InsertSalesBill(IntTotal, BarcodeArrayList,StrDate_Time,StrBillType);	
		} catch (SQLException e) {

		}catch (Exception e) {
			
		}
	}

	@Override
	public void TableRefresh(TableView T) {
		try {
			IntNo = 0;
			TableData = FXCollections.observableArrayList();
			//if Arraylist of Barcode not empty then products will appear on table view.
			if(!BarcodeArrayList.isEmpty()){
				for(int i = 0 ; i < BarcodeArrayList.size() ; i++){
					//read products from database &  add them to table && calculate total.
					LoopResult = mDataBase.SelectProduct(BarcodeArrayList.get(i));
						while(LoopResult.next()){
								TableData.add(new sales_bill_table(
										++IntNo,
										LoopResult.getString("product.barcode"),
										LoopResult.getString("product.name"),
										LoopResult.getInt("product.quantity"),
										LoopResult.getString("major_brand.name"),
										LoopResult.getString("sub_brand.name"),
										LoopResult.getString("unit.name"),
										LoopResult.getInt("product.customer_price")));
								IntTotal += LoopResult.getInt("product.customer_price");
								T.setItems(TableData);
						}
					}
			//if array list of Barcode empty table view will be empty.
			}else if(BarcodeArrayList.isEmpty()){
			   TableData.clear();
			   T.setItems(TableData);
			}
		} catch (Exception e) {

		}		
	}	
	@Override
	public TableRow onTableRowListener(TableView T,TextField Barcode) {
		TableRow<sales_bill_table> row = new TableRow<>();
		 //if double click on row .. it read barcode of this row.
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (!row.isEmpty())) {
		        	System.out.println("Hio");
		        	sales_bill_table rowData = row.getItem();
		            Barcode.setText(rowData.Barcode.getValue());
		        }
		    });
		  return row;
	}
	public class sales_bill_table{
		private SimpleIntegerProperty No;
		private SimpleStringProperty Barcode;
		private SimpleStringProperty Name;
		private SimpleIntegerProperty Quantity;
		private SimpleStringProperty MajorBrand;
		private SimpleStringProperty SubBrand;
		private SimpleStringProperty Unit;
		private SimpleIntegerProperty CustomerPrice;
		
		public sales_bill_table(int no,String barcode,String name,int quantity,String majorbrand,String subbrand,String unit,int price){
			No = new SimpleIntegerProperty(no);
			Barcode = new SimpleStringProperty(barcode);
			Name = new SimpleStringProperty(name);
			Quantity = new SimpleIntegerProperty(quantity);
			MajorBrand = new SimpleStringProperty(majorbrand);
			SubBrand = new SimpleStringProperty(subbrand);
			Unit = new SimpleStringProperty(unit);
			CustomerPrice = new SimpleIntegerProperty(price);
		}
		public int getNo(){
			return No.get();
		}
		public void setNo(int no){
			No.set(no);
		}
		public String getBarcode(){
			return Barcode.get();
		}
		public void setBarcode(String barcode){
			Barcode.set(barcode);
		}
		public String getName(){
			return Name.get();
		}
		public void setName(String name){
			Name.set(name);
		}
		public int getQuantity(){
			return Quantity.get();
		}
		public void setQuantity(int quantity){
			Quantity.set(quantity);
		}
		public String getMajorBrand(){
			return MajorBrand.get();
		}
		public void setMajorBrand(String majorbrand){
			MajorBrand.set(majorbrand);
		}
		public String getSubBrand(){
			return SubBrand.get();
		}
		public void setSubBrand(String subbrand){
			SubBrand.set(subbrand);
		}
		public String getUnit(){
			return Unit.get();
		}
		public void setUnit(String unit){
			Unit.set(unit);
		}
		public int getCustomerPrice(){
			return CustomerPrice.get();
		}
		public void setCustomerPrice(int price){
			CustomerPrice.set(price);
		}
	}
	@Override
	public void onSavePurchaseBillListener(TextField Total, Label Date_Time, ComboBox<String> CBBillType,
			ComboBox<String> Supplier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> FillSupplierList(ArrayList<String> supplierList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
