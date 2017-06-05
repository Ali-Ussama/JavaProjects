package view;

import java.sql.ResultSet;
import java.util.ArrayList;

import controller.ProductAction;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DataBase;

public class Product {
		private Stage stage;
		private Scene scene;
		private BorderPane root;
		private Label LblBarcode ,LblName , LblCustomerPrice ,LblSupplierPrice, LblQuantity,LblMajorBrand,LblSubBrand,LblUnits,
		 			  LblSuppliers;
		public static ArrayList<String> MajorBrandList ,SubBrandList, UnitsList  , SuppliersList ;
		public static TextField TFBarcode ,TFName , TFCustomerPrice ,TFSupplierPrice, TFQuantity;
		public static ComboBox<String> CBMajorBrand,CBSubBrand,CBUnits,CBSuppliers;
		private Button Addbtn,Updatebtn,Deletebtn;
		private VBox TFVbox,LblVBox,LeftVbox;
		private HBox LeftHbox,ButtonsHbox;
		private DataBase mDataBase;
		private ProductAction mProductAction;
		public void Initialize() throws Exception{
			//DataBase
			mDataBase = new DataBase();
			//Stage - scene - borderPane.
			stage = new Stage();
			root = new BorderPane();
			scene = new Scene(root, 300, 460);
			//Layouts
			TFVbox = new VBox(20);
			LblVBox = new VBox(29);
			LeftVbox = new VBox(20);
			LeftHbox = new HBox(20);
			ButtonsHbox = new HBox(10);
			//TextFields.
			TFBarcode = new TextField();
			TFName = new TextField();
			TFCustomerPrice = new TextField();
			TFSupplierPrice = new TextField();
			TFQuantity = new TextField();
			//labels
			LblBarcode = new Label("Barcode");
			LblName = new Label("Name");
			LblCustomerPrice = new Label("Customer Price");
			LblSupplierPrice = new Label("Supplier Price");
			LblQuantity = new Label("Quantity");
			LblMajorBrand = new Label("Major Brand");
			LblSubBrand = new Label("Sub Brand");
			LblUnits = new Label("Units");
			LblSuppliers = new Label("Suppliers");
			//ComboBox.
			CBMajorBrand = new ComboBox<>();
			CBSubBrand = new ComboBox<>();
			CBUnits = new ComboBox<>();
			CBSuppliers = new ComboBox<>();
			//Array List
			MajorBrandList = new ArrayList<String>();
			SubBrandList = new ArrayList<String>();
			UnitsList = new ArrayList<String>();
			SuppliersList = new ArrayList<String>();
			//ComboBox List Arrays.
			FillArrayList();
			//Buttons.
			Addbtn = new Button("Add");
			Addbtn.setPrefWidth(60);
			Updatebtn = new Button("Update");
			Deletebtn = new Button("Delete");
			//Actions
			mProductAction = new ProductAction();
		}
		public void FillArrayList() throws Exception{
				FillListsFromDataBase(MajorBrandList,"major_brand");
				FillListsFromDataBase(SubBrandList,"sub_brand");
				FillListsFromDataBase(SuppliersList,"supplier");
				FillListsFromDataBase(UnitsList,"unit");
		}
	    private void FillListsFromDataBase(ArrayList <String>ArList,String TableName) throws Exception{
			ResultSet mResultSet = mDataBase.SelectTable(TableName);
			while(mResultSet.next()){
				ArList.add(mResultSet.getString("name"));
			}
		}
	    private void AddComponents(){
	    CBMajorBrand.getItems().addAll(MajorBrandList);
	    CBSubBrand.getItems().addAll(SubBrandList);
	    CBUnits.getItems().addAll(UnitsList);
	    CBSuppliers.getItems().addAll(SuppliersList);
	    LblVBox.getChildren().addAll(LblBarcode,LblName,LblQuantity,LblCustomerPrice,LblSupplierPrice,LblMajorBrand,LblSubBrand,LblUnits,LblSuppliers);
	    TFVbox.getChildren().addAll(TFBarcode,TFName,TFQuantity,TFCustomerPrice,TFSupplierPrice,CBMajorBrand,CBSubBrand,CBUnits,CBSuppliers);
	    LeftHbox.getChildren().addAll(LblVBox,TFVbox);
	    ButtonsHbox.getChildren().addAll(Addbtn,Updatebtn,Deletebtn);
	    LeftVbox.getChildren().addAll(LeftHbox,ButtonsHbox);
	    root.setLeft(LeftVbox);
	    }
	    private void Paddings(){
	    	ButtonsHbox.setPadding(new Insets(0, 0, 20, 100));
	    	LeftHbox.setPadding(new Insets(20, 0, 0, 10));
	    }	    
	    public void StartProduct(){
			try {
				Initialize();
				AddComponents();
				Paddings();
				stage.setScene(scene);
				stage.setTitle("Product");
				stage.show();
				
				Addbtn.setOnAction(e->mProductAction.onAddProductListener(TFBarcode, TFName, TFQuantity, TFCustomerPrice,TFSupplierPrice, CBMajorBrand, CBSubBrand, CBUnits, CBSuppliers));
				Updatebtn.setOnAction(e->mProductAction.onUpdateProductListener(TFBarcode, TFName, TFQuantity, TFCustomerPrice,TFSupplierPrice, CBMajorBrand, CBSubBrand, CBUnits, CBSuppliers));
				Deletebtn.setOnAction(e->mProductAction.onDeleteProductListener(TFBarcode));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    
	    
}
