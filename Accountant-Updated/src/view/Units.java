package view;

import controller.UnitAction;
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

public class Units {
	private Stage stage;
	private Scene scene;
	private BorderPane root;
	private TextField TFName, TFID;
	private Label LblID,LblName;
	private HBox TopHBox;
	private VBox CenterVBox;
	private Button AddBtn,UpdateBtn,DeleteBtn;
	public static TableView table ;
	private TableColumn IDColumn ,NameColumn;
	private UnitAction mUnitAction;
	
 	public void StartUnits() {
			Initialize();
			AddComponent();
			Padding();
			stage.setScene(scene);
			stage.setTitle("Sub Brand");
			stage.show();
			AddBtn.setOnAction(e->mUnitAction.onAddMajorBrandListener(TFName));
			UpdateBtn.setOnAction(e->mUnitAction.onUpdateMajorBrandListener(TFID, TFName));
			DeleteBtn.setOnAction(e->mUnitAction.onDeleteMajorBrandListener(TFID));
	}

	private void Padding() {
		TopHBox.setPadding(new Insets(20, 5, 10, 20));
	}
	private void AddComponent() {
		TopHBox.getChildren().addAll(LblID,TFID,LblName,TFName,AddBtn,UpdateBtn,DeleteBtn);
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
		//Labels
		LblID = new Label("ID");
		LblName = new Label("Name");
		//layouts.
		TopHBox = new HBox(20);
		CenterVBox = new VBox(20);
		//buttons
		AddBtn = new Button("Add");
		UpdateBtn = new Button("Update");
		AddBtn.setPrefWidth(60);
		DeleteBtn = new Button("Delete");
		//Action
		mUnitAction = new UnitAction();
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
		        NameColumn.setPrefWidth(500);
		        table.getColumns().addAll(IDColumn,NameColumn);
		        mUnitAction.TableRefresh(table);
		        
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
}
