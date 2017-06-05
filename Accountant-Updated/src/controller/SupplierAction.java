package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
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
public class SupplierAction {
	private TextField TFID, TFName,TFAddress,TFPhone;
	private String StrName,StrAddress,StrPhone;
	private int IntID;
	private DataBase mDataBase;
	private ObservableList<SupplierTable> TableData;
	public SupplierAction(){
		mDataBase = new DataBase();
	}
	public void readInput(){
		//Read Inputs.
		IntID = Integer.parseInt(TFID.getText());
		StrName = TFName.getText();
		StrAddress = TFAddress.getText();
		StrPhone = TFPhone.getText();
	}

	public void onAddSupplierListener(TextField ID,TextField Name,TextField Address,TextField phone) {
		try {
			//Initialize TextFields.
			TFID = ID;
			TFName = Name;
			TFAddress = Address;
			TFPhone = phone;
			//Read Input Data.
			readInput();
			//Database Query and statements.
			mDataBase.InsertSupplier(IntID, StrName,StrPhone,StrAddress);
			//Refresh Suppliers Table
			TableRefresh(view.Suppliers.table);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Re-Enter Correct Data.");
		}
	}
	public void onUpdateSupplierListener(TextField ID, TextField Name, TextField Address, TextField phone) {
		try {
			//Initialize TextFields.
			TFID = ID;
			TFName = Name;
			TFAddress = Address;
			TFPhone = phone;
			//Read Input Data.
			readInput();
			//Database Query and statements.
			mDataBase.UpdateSupplier(IntID, StrName, StrPhone, StrAddress);
			//Refresh Suppliers Table
			TableRefresh(view.Suppliers.table);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Re-Enter Correct Data.");
		}
	}
	
	public void onDeleteSupplierListener(TextField ID) {
		
		try {
			IntID = Integer.parseInt(ID.getText());
			mDataBase.Delete(IntID, "supplier");
			//Refresh Suppliers Table
			TableRefresh(view.Suppliers.table);
			JOptionPane.showMessageDialog(null, "Supplier Updated Successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Re-Enter Correct ID.");
		}
		
	}
	
	public void TableRefresh(TableView T) {
		try {
			ResultSet mResultset = mDataBase.SelectTable("supplier");
			TableData = FXCollections.observableArrayList();
			while(mResultset.next()){
				TableData.add(new SupplierTable(
						mResultset.getInt("id"),
						mResultset.getString("name"),
						mResultset.getString("phone"),
						mResultset.getString("address")
						));
				T.setItems(TableData);
			}
		} catch (Exception e) {
		}
		
	}
	public class SupplierTable {
		private SimpleIntegerProperty ID;
		private SimpleStringProperty Name ;
		private SimpleStringProperty Address;
		private SimpleStringProperty Phone;
		private SupplierTable(int id,String name,String phone,String address){
			ID = new SimpleIntegerProperty(id);
			Name = new SimpleStringProperty(name);
			Address = new SimpleStringProperty(address);
			Phone = new SimpleStringProperty(phone);
		}
		 
		 public int getID(){
	    	 return ID.get();
		 }
	     public void setID(int id){
	    	 ID.set(id);
	     }
	     public String getAddress(){
	    	 return Address.get();
	     }
	     public void setAddress(String address){
	    	 Address.set(address);
	     }
	     public String getName(){
	    	 return Name.get();
	     }
	     public void setName(String name){
	    	 Name.set(name);
	     }
	     public String getPhone(){
	    	 return Phone.get();
	     }
	     public void setPhone(String phone){
	    	 Phone.set(phone);
	     }
	}
	
}
