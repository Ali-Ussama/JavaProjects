package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import controller.ProductAction.Product_Table;
import controller.PurchaseBillAction.Purchase_bill_table;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.DataBase;
import view.Product;

public class ProductAction{
	private TextField TFBarcode,TFName,TFQuantity,TFCustomerPrice,TFSupplierPrice;
	private ComboBox<String> CBMajorBrand,CBSubBrand,CBUnits,CBSuppliers;
	private String StrName,StrBarcode;
	private int IntQuantity,IntCustomerPrice,IntSupplierPrice,IntMajorBrand,IntSubBrand,IntUnits,IntSuppliers;
	private int IntNo = 0;
	private ObservableList<Product_Table> TableData;
	private ResultSet LoopResult;
	
	private DataBase mDataBase;
	
	public ProductAction(){
		mDataBase = new DataBase();
	}
	
	private void readInputs(){
		StrBarcode = TFBarcode.getText();
		StrName = TFName.getText();
		IntQuantity = Integer.parseInt(TFQuantity.getText());
		IntCustomerPrice = Integer.parseInt(TFCustomerPrice.getText());
		IntSupplierPrice = Integer.parseInt(TFSupplierPrice.getText());
		IntMajorBrand =mDataBase.GetID(CBMajorBrand.getValue(),"major_brand");
		IntSubBrand = mDataBase.GetID(CBSubBrand.getValue(),"sub_brand");
		IntUnits = mDataBase.GetID(CBUnits.getValue(),"unit");
		IntSuppliers = mDataBase.GetID(CBSuppliers.getValue(),"supplier");
	}

	public void onAddProductListener(TextField bardcode,TextField name,TextField quantity,TextField CustomerPrice,
							  TextField SupplierPrice,ComboBox<String> majorbrand,ComboBox<String> subbrand,
							  ComboBox<String> units,ComboBox<String> suppliers){
		try {
			//initialize Components
			TFBarcode = bardcode;
			TFName = name;
			TFQuantity = quantity;
			TFCustomerPrice = CustomerPrice;
			TFSupplierPrice = SupplierPrice;
			CBMajorBrand = majorbrand;
			CBSubBrand = subbrand;
			CBUnits = units;
			CBSuppliers = suppliers;
			//ReadInputs
			readInputs();
			//Add Inputs to DataBase.
			 mDataBase.InsertProduct(StrBarcode, StrName, IntQuantity, IntCustomerPrice,IntSupplierPrice, IntMajorBrand, IntSubBrand, IntUnits, IntSuppliers);
			JOptionPane.showMessageDialog(null, "Product Added Successfully");
			TableRefresh(view.Mainwindow.table);
		}catch(MySQLIntegrityConstraintViolationException e){
			JOptionPane.showMessageDialog(null, "The Barcode "+StrBarcode+" is used , Enter another Barcode");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Enter Correct Data");
		}
	}

	public void onUpdateProductListener(TextField bardcode,TextField name,TextField quantity,TextField CustomerPrice,
			  TextField SupplierPrice,ComboBox<String> majorbrand,ComboBox<String> subbrand,
			  ComboBox<String> units,ComboBox<String> suppliers) {
		try {
			//initialize Components
			TFBarcode = bardcode;
			TFName = name;
			TFQuantity = quantity;
			TFCustomerPrice = CustomerPrice;
			TFSupplierPrice = SupplierPrice;
			CBMajorBrand = majorbrand;
			CBSubBrand = subbrand;
			CBUnits = units;
			CBSuppliers = suppliers;
			
			//ReadInputs
			readInputs();
			
			//Add Inputs to DataBase.
			mDataBase.UpdateProduct(StrBarcode, StrName, IntQuantity, IntCustomerPrice,IntSupplierPrice, IntMajorBrand, IntSubBrand, IntUnits, IntSuppliers);
			JOptionPane.showMessageDialog(null, "Product Updated Successfully");
			
			//Refresh Table.
			TableRefresh(view.Mainwindow.table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Enter Correct Data");
		}
	}
	

	public void onDeleteProductListener(TextField barcode) {
		try{
		TFBarcode = barcode;
		StrBarcode = TFBarcode.getText();
		//check if BarCode is valid or not.
		mDataBase.mResultset = mDataBase.SelectProduct(StrBarcode);
		if(!mDataBase.mResultset.next())
			JOptionPane.showMessageDialog(null, "Invalide Barcode.");
		
		//check if user Enter a BarCode or not.
		if(StrBarcode.matches(""))
			JOptionPane.showMessageDialog(null, "Please Enter Product Barcode.");
		
		//Delete Product From DataBase.
		mDataBase.DeleteProduct(StrBarcode);
		JOptionPane.showMessageDialog(null, "Product Deleted Successfully.");

		//Refresh the table.
		TableRefresh(view.Mainwindow.table);
		}catch(Exception e){

		}
	}
	public void TableRefresh(TableView T) {
		try {			
		IntNo = 0;
		TableData = FXCollections.observableArrayList();
		
				LoopResult = mDataBase.SelectAllProducts();
					while(LoopResult.next()){
							TableData.add(new Product_Table(
									++IntNo,
									LoopResult.getString("product.barcode"),
									LoopResult.getString("product.name"),
									LoopResult.getInt("product.quantity"),
									LoopResult.getString("major_brand.name"),
									LoopResult.getString("sub_brand.name"),
									LoopResult.getString("unit.name"),
									LoopResult.getInt("product.customer_price"),
									LoopResult.getInt("product.supplier_price"),
									LoopResult.getString("supplier.name")));
							T.setItems(TableData);
					}
	} catch (Exception e) {
		e.printStackTrace();
	}		
	}
	public TableRow onProductTableRowListener(){
		TableRow<Product_Table> row = new TableRow<>();
		 //if double click on row .. it read barcode of this row.
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (!row.isEmpty()) ){
			    	System.out.println("Before");
					String str = "Ali";
		        }
		    });
		  return row;
	}
	
	public class Product_Table{
		private SimpleIntegerProperty No;
		private SimpleStringProperty Barcode;
		private SimpleStringProperty Name;
		private SimpleIntegerProperty Quantity;
		private SimpleStringProperty MajorBrand;
		private SimpleStringProperty SubBrand;
		private SimpleStringProperty Unit;
		private SimpleIntegerProperty CustomerPrice;
		private SimpleIntegerProperty SupplierPrice;
		private SimpleStringProperty SupplierName;
	
		public Product_Table(int no,String barcode,String name,int quantity,String majorbrand,String subbrand,String unit,int customerprice,int supplierprice,String suppliername){
			No = new SimpleIntegerProperty(no);
			Barcode = new SimpleStringProperty(barcode);
			Name = new SimpleStringProperty(name);
			Quantity = new SimpleIntegerProperty(quantity);
			MajorBrand = new SimpleStringProperty(majorbrand);
			SubBrand = new SimpleStringProperty(subbrand);
			Unit = new SimpleStringProperty(unit);
			CustomerPrice = new SimpleIntegerProperty(customerprice);
			SupplierPrice = new SimpleIntegerProperty(supplierprice);
			SupplierName = new SimpleStringProperty(suppliername);
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
		public int getSupplierPrice(){
			return SupplierPrice.get();
		}
		public void setSupplierPrice(int supplierprice){
			SupplierPrice.set(supplierprice);
		}
		public String getSupplierName(){
			return SupplierName.get();
		}
		public void setSupplierName(String suppliername){
			SupplierName.set(suppliername);
		}
}
}
