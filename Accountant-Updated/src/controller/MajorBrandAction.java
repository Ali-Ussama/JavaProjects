package controller;


import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.SalesBillAction.sales_bill_table;
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

public class MajorBrandAction implements Brands{
	private TextField ID,Name;
	private String StrName, Query;
	private int IntID = 0;
	private ObservableList<MajorBrandTable> TableData;
	private DataBase mDataBase;
    public MajorBrandAction() {
    	mDataBase = new DataBase();
	}
	@Override
	public void onAddMajorBrandListener(TextField name) {
		try {
//			ID = id;
			Name = name;
//			if(ID.getText().matches("")){
//				JOptionPane.showMessageDialog(null, "Enter Correct ID.");
//				return;
//			}
//			IntID = Integer.parseInt(ID.getText());
			StrName = Name.getText();
			
			if(StrName.matches("")){
				JOptionPane.showMessageDialog(null, "Enter Major Brand Name.");
				return;
			}
			mDataBase.InsertMajorBrand(StrName);
			TableRefresh(view.Major_Brand.table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Enter Correct Data.");
		}
	}

	@Override
	public void onUpdateMajorBrandListener(TextField id,TextField name) {
		try {
			ID = id;
			Name = name;
			IntID = Integer.parseInt(ID.getText());
			StrName = Name.getText();

			if(StrName.matches(""))
				JOptionPane.showMessageDialog(null, "Enter Major Brand Name.");
			
			mDataBase.UpdateMajorBrand(IntID, StrName);
			TableRefresh(view.Major_Brand.table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Enter Correct Data.");
		}
	}
	@Override
	public void onDeleteMajorBrandListener(TextField name){
		try{
			Name = name;
			//check if user Enter a BarCode or not.
			StrName = Name.getText();
			//check if id is valid or not.
			ResultSet mResultset = mDataBase.SelectRow("major_brand", "name", StrName, 0);
//			while(mResultset.next())
//				if(!mResultset.getString("name").matches(StrName))
//					return;
			
			if(!mResultset.next()){
				JOptionPane.showMessageDialog(null, "Invalide Name.");
				return;
			}
			
			//Delete Product From DataBase.
			mDataBase.DeleteMajorBrand(StrName);			
			//Refresh the table.
			TableRefresh(view.Major_Brand.table);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Invalide Name.");
				e.printStackTrace();
			}
	}
	@Override
	public void TableRefresh(TableView T) {
		try {
		    
			mDataBase.mResultset = mDataBase.SelectTable("major_brand");
			
			TableData = FXCollections.observableArrayList();
			while(DataBase.mResultset.next()){
				TableData.add(new MajorBrandTable(
						DataBase.mResultset.getInt("id"),
						DataBase.mResultset.getString("name")
						));
				T.setItems(TableData);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
	}
	@Override
	public TableRow onTableMajorRowListener(TableView T,TextField Id,TextField Name) {
		TableRow<MajorBrandTable> row = new TableRow<>();
		 //if double click on row .. it read ID && Name of this row.
		System.out.println("Hello");
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (!row.isEmpty())){
		        	MajorBrandTable rowData = row.getItem();
		        	Id.setText(rowData.ID.getValue().toString());
		        	Name.setText(rowData.Name.getValue());
		        }
		    });
		  return row;
	}
	public class MajorBrandTable {
		private SimpleIntegerProperty ID;
		private SimpleStringProperty Name ;
		
		private MajorBrandTable(int id,String name){
			ID = new SimpleIntegerProperty(id);
			Name = new SimpleStringProperty(name);
		}
		 
		 public int getID(){
	    	 return ID.get();
		 }
	     public void setID(int id){
	    	 ID.set(id);
	     }
	     public String getName(){
	    	 return Name.get();
	     }
	     public void setName(String name){
	    	 Name.set(name);
	     }
	}	
}
