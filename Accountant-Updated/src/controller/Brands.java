package controller;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public interface Brands {
	public void onAddMajorBrandListener(TextField name);
	public void onUpdateMajorBrandListener(TextField id,TextField name);
	public void onDeleteMajorBrandListener(TextField Name);
	public void TableRefresh(TableView T);
	public TableRow onTableMajorRowListener(TableView T,TextField Id,TextField Name);
}
