package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.MajorBrandAction.MajorBrandTable;
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

public class SubBrandAction implements Brands{
	private TextField ID,Name;
	private String StrName , Query;
	private int IntID;
	private ObservableList<SubBrandTable> TableData;
	private DataBase mDataBase;
	
	public SubBrandAction() {
		mDataBase = new DataBase();
	}
	
	@Override
	public void onAddMajorBrandListener(TextField name) {
		try {
//			ID = id;
			Name = name;
			IntID = Integer.parseInt(ID.getText());
			StrName = Name.getText();

			if(StrName.matches("")){
				JOptionPane.showMessageDialog(null, "Enter Major Brand Name.");
				return;
			}
			mDataBase.InsertSubBrand(IntID, StrName);
			TableRefresh(view.Sub_Brand.table);
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
			mDataBase.UpdateSubBrand(IntID, StrName);
			TableRefresh(view.Sub_Brand.table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Enter Correct Data.");

		}
	}
	
	@Override
	public void onDeleteMajorBrandListener(TextField id) {
		try{
			ID = id;
			//check if user Enter a BarCode or not.
			if(ID.getText().matches(""))
				JOptionPane.showMessageDialog(null, "Please Enter Major ID.");
			else
				IntID = Integer.parseInt(ID.getText());
			//check if BarCode is valid or not.
			mDataBase.mResultset = mDataBase.SelectRow("sub_brand", "id", null, IntID);
			if(!mDataBase.mResultset.next())
				JOptionPane.showMessageDialog(null, "Invalide ID.");
			
			//Delete Product From DataBase.
			mDataBase.DeleteSubBrand(IntID);			
			//Refresh the table.
			TableRefresh(view.Sub_Brand.table);
			}catch(Exception e){

			}
	}
	@Override
	public void TableRefresh(TableView T) {
		try {
		    Query = "select * from sub_brand order by id";
			DataBase.mResultset = DataBase.mstatement.executeQuery(Query);
			
			TableData = FXCollections.observableArrayList();
			while(DataBase.mResultset.next()){
				TableData.add(new SubBrandTable(
						DataBase.mResultset.getInt("id"),
						DataBase.mResultset.getString("name")
						));
				T.setItems(TableData);
			}
		} catch (Exception e) {
			
		}
		
	}
	@Override
	public TableRow onTableMajorRowListener(TableView T,TextField Id,TextField Name) {
		TableRow<SubBrandTable> row = new TableRow<>();
		 //if double click on row .. it read ID && Name of this row.
		System.out.println("Hello");
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (!row.isEmpty())){
		        	SubBrandTable rowData = row.getItem();
		        	Id.setText(rowData.ID.getValue().toString());
		        	Name.setText(rowData.Name.getValue());
		        }
		    });
		  return row;
	}

	public class SubBrandTable {
		private SimpleIntegerProperty ID;
		private SimpleStringProperty Name ;
		
		private SubBrandTable(int id,String name){
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
