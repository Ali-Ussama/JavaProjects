package controller;

import java.util.ArrayList;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public interface Bills {
	public void onAddBillListener(TextField Barcode,TextField Total);
	public void onDeleteBillListener(TextField Barcode,TextField Total);
	public void onSaveSalesBillListener(TextField Total,Label Date_Time,ComboBox<String> CBBillType);
	public void onSavePurchaseBillListener(TextField Total,Label Date_Time,ComboBox<String> CBBillType,ComboBox<String> Supplier);
	public void TableRefresh(TableView T);
	public TableRow onTableRowListener(TableView T,TextField Barcode);
	public ArrayList<String> FillSupplierList(ArrayList<String> supplierList);

}
