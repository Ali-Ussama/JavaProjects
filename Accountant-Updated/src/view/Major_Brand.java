package view;

import controller.MajorBrandAction;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Major_Brand {
	private Stage stage;
	private Scene scene;
	private BorderPane root;
	private TextField TFName, TFID;
	private Label LblID,LblName;
	private HBox TopHBox;
	private VBox CenterVBox;
	private Button AddBtn,UpdateBtn,DeleteBtn;
	public static TableView table ;
	public static TableColumn IDColumn ,NameColumn;
	private MajorBrandAction mMajorBrandAction;
	
 	public void StartMajorBrand(){
			Initialize();
			AddComponent();
			Padding();
			stage.setScene(scene);
			stage.setTitle("Major Brand");
			stage.show();
			AddBtn.setOnAction(e->mMajorBrandAction.onAddMajorBrandListener(TFName));
			UpdateBtn.setOnAction(e->mMajorBrandAction.onUpdateMajorBrandListener(TFID, TFName));
			DeleteBtn.setOnAction(e->mMajorBrandAction.onDeleteMajorBrandListener(TFName));
			table.setRowFactory(e->mMajorBrandAction.onTableMajorRowListener(table, TFID, TFName));
	}

	private void Padding() {
		TopHBox.setPadding(new Insets(20, 5, 10, 20));
	}
	private void AddComponent() {
		TopHBox.getChildren().addAll(TFName,AddBtn,UpdateBtn,DeleteBtn);
		CenterVBox.getChildren().addAll(table);
		root.setTop(TopHBox);
		root.setCenter(CenterVBox);
	}
	private void Initialize() {
		//Stage - scene - borderPane.
		stage = new Stage();
		root = new BorderPane();
		scene = new Scene(root, 660, 400);
		//TextFields
		TFID = new TextField();
		TFName = new TextField();
		TFID.setPromptText("ID");
		TFName.setPromptText("Name");
		//Labels
//		LblID = new Label("ID");
//		LblName = new Label("Name");
		//layouts.
		TopHBox = new HBox(20);
		CenterVBox = new VBox(20);
		//buttons
		AddBtn = new Button("Add");
		UpdateBtn = new Button("Update");
		AddBtn.setPrefWidth(60);
		DeleteBtn = new Button("Delete");
		//Action
        mMajorBrandAction = new MajorBrandAction();
		//Create Table View.
        TableView();
        
        
	}
	private void TableView() {
		try {
			//Initialize Table.
			table = new TableView<>();
			// initialize the columns in the table.   
		        IDColumn = new TableColumn("ID");
		        IDColumn.setCellValueFactory(new PropertyValueFactory("ID"));
		        NameColumn = new TableColumn("Name");
		        NameColumn.setCellValueFactory(new PropertyValueFactory("Name"));
		        NameColumn.setPrefWidth(100);
		        table.getColumns().addAll(IDColumn,NameColumn);
		        mMajorBrandAction.TableRefresh(table);
		        
		}catch (Exception e) {

		}	
	}


}
