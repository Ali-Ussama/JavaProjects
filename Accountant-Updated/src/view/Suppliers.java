package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.SupplierAction;

public class Suppliers {
	private Scene scene;
	public static Stage stage;
	private TextField TFID,TFName,TFAddress,TFPhone;
	private Label LblID,LblName,LblAddress,LblPhone;
	private VBox LblVbox ,TFVbox,TableVbox;
	private HBox CenterHbox , RightHBox,TopHbox;
	private BorderPane root;
	private Button AddButton,UpdateButton,DeleteButton;
	private SupplierAction mSupplierAction;
	public static TableView table;
	private TableColumn IDColumn ,NameColumn,AddressColumn,PhoneColumn;

	public void Initialize(){
		//BorderPane
		root = new BorderPane();
		//Stage
		stage = new Stage();
		//Scene
		scene = new Scene(root,900,470);
		// TextFields
		TFID = new TextField();
		TFName = new TextField();
		TFAddress = new TextField();
		TFPhone = new TextField();
		TFID.setPromptText("ID");
		TFName.setPromptText("Name");
		TFAddress.setPromptText("Address");
		TFPhone.setPromptText("Phone");
		//Vbox
		LblVbox = new VBox(20);
		TFVbox = new VBox(10);
		TableVbox = new VBox(10);
		//Hbox
		TopHbox = new HBox(20);
		CenterHbox = new HBox(25);
		RightHBox = new HBox(10);
		//Buttons
		AddButton = new Button("Add");
		UpdateButton = new Button("Update");
		DeleteButton = new Button("Delete");
		AddButton.setPrefWidth(60);
		//SupplierAction.
		mSupplierAction = new SupplierAction();
		//Create Table View.
        TableView();
		
	}
	public void AddComponent(){
		TableVbox.getChildren().add(table);
		TopHbox.getChildren().addAll(TFID,TFName,TFAddress,TFPhone,UpdateButton,AddButton,DeleteButton);
		root.setTop(TopHbox);
		root.setCenter(TableVbox);
		stage.setScene(scene);
	}
	public void paddings(){
		TopHbox.setPadding(new Insets(10, 10, 20, 10));
		TableVbox.setPadding(new Insets(10, 0, 5, 0));
	}
	public void StartSupplier(){
		//Initialize.
		Initialize();
		//Add Components.
		AddComponent();
		//Padding
		paddings();
		stage.setTitle("Suppliers");
		stage.show();
		
		AddButton.setOnAction(e-> mSupplierAction.onAddSupplierListener(TFID, TFName, TFAddress, TFPhone));
		UpdateButton.setOnAction(e->mSupplierAction.onUpdateSupplierListener(TFID, TFName, TFAddress, TFPhone));
		DeleteButton.setOnAction(e->mSupplierAction.onDeleteSupplierListener(TFID));
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
		        AddressColumn = new TableColumn("Address");
		        AddressColumn.setCellValueFactory(new PropertyValueFactory("Address"));
		        AddressColumn.setPrefWidth(80);
		        PhoneColumn = new TableColumn("Phone");
		        PhoneColumn.setCellValueFactory(new PropertyValueFactory("Phone"));
		        table.getColumns().addAll(IDColumn,NameColumn,AddressColumn,PhoneColumn);
		        mSupplierAction.TableRefresh(table);
		        
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
