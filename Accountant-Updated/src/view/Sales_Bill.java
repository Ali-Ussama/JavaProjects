package view;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import controller.PurchaseBillAction;
import controller.SalesBillAction;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Sales_Bill {
	
	private BorderPane root;
	private Scene scene;
	private Stage stage;
	private TextField TFBarcode,TFTotal;
	private Label LblBarcode,LblBillType,LblDateTime,LblTime,LblTotal,LblSupplierName;
	private ComboBox CBBillType,CBSupplier;
	private Button AddBtn,DeleteBtn,SaveBtn;
	private VBox CenterVbox,LblVbox,TFVbox;
	private HBox BottomHbox,TopHbox;
	public static TableView table;
	public static TableColumn NoColumn,BarcodeColumn,NameColumn,MajorBrandColumn,SubBrandColumn,
	 											  UnitColumn,CustomerPriceColumn,SupplierPriceColumn;
	private ArrayList<String> BillTypeArrayList,SupplierArraylist;
	private DateFormat dateFormat;
	private Calendar calendar;
	private SalesBillAction mSalesBillAction;
	private PurchaseBillAction mPurchaseBillAction;
	
	public void Start(String ItemID){
		//Initialize
		Initialize(ItemID);
		//Add Components
		AddComponents(ItemID);
		//Padding
		Padding();
		stage.setScene(scene);
		stage.setTitle("Sales Bill");
		stage.show();
		stage.toFront();
		if(ItemID.matches("Sales Bill")){
					AddBtn.setOnAction(e->mSalesBillAction.onAddBillListener(TFBarcode,TFTotal));
					TFBarcode.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if(event.getCode() == KeyCode.ENTER)
							mSalesBillAction.onAddBillListener(TFBarcode, TFTotal);
					}
					});
					DeleteBtn.setOnAction(e->mSalesBillAction.onDeleteBillListener(TFBarcode,TFTotal));
					SaveBtn.setOnAction(e->mSalesBillAction.onSaveSalesBillListener(TFTotal, LblDateTime, CBBillType));
					table.setRowFactory( tv -> mSalesBillAction.onTableRowListener(table,TFBarcode));
		}else{
			AddBtn.setOnAction(e->mPurchaseBillAction.onAddBillListener(TFBarcode, TFTotal));
			TFBarcode.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if(event.getCode() == KeyCode.ENTER)
						mPurchaseBillAction.onAddBillListener(TFBarcode, TFTotal);
				}
				});
			DeleteBtn.setOnAction(e->mPurchaseBillAction.onDeleteBillListener(TFBarcode, TFTotal));
			SaveBtn.setOnAction(e->mPurchaseBillAction.onSavePurchaseBillListener(TFTotal, LblDateTime, CBBillType,CBSupplier));
			table.setRowFactory(tv -> mPurchaseBillAction.onTableRowListener(table, TFBarcode));
		}
	}
	private void Initialize(String ItemID){
		stage = new Stage();
		root = new BorderPane();
		if(ItemID.matches("Sales Bill"))
			scene = new Scene(root, 610, 600);
		else
			scene = new Scene(root, 680, 600);
		// Sales Bill Action Object
		mSalesBillAction = new SalesBillAction();
		mPurchaseBillAction = new PurchaseBillAction();
		//TextField
		TFBarcode = new TextField();
		TFTotal = new TextField("0");
		TFTotal.setEditable(false);
		//Date Format & Calender.
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    calendar = Calendar.getInstance();
		//Label
		LblBarcode = new Label("Barcode");
		LblBillType = new Label("Bill Type");
		LblSupplierName = new Label("Supplier");
		LblDateTime = new Label(dateFormat.format(calendar.getTime()));
		LblTotal= new Label("Total");
		//ArrayList
		BillTypeArrayList = new ArrayList<String>();
		BillTypeArrayList.add("Cash");
		BillTypeArrayList.add("Returned");
		SupplierArraylist = new ArrayList<>();
		SupplierArraylist = mPurchaseBillAction.FillSupplierList(SupplierArraylist);
		//ComboBox
		CBBillType = new ComboBox<>();
		CBBillType.getItems().addAll(BillTypeArrayList);
		CBSupplier = new ComboBox<>();
		CBSupplier.getItems().addAll(SupplierArraylist);
		//Buttons
		AddBtn = new Button("Add");
		DeleteBtn = new Button("Delete");
		SaveBtn = new Button("Save");
		//Layout
		CenterVbox = new VBox(20);
		LblVbox = new VBox(30);
		TFVbox = new VBox(20);
		BottomHbox = new HBox(20);
		TopHbox = new HBox(20);
		//Table
		TableView(ItemID);
	}
	private void AddComponents(String ItemID){
		CenterVbox.getChildren().add(table);
		if(ItemID.matches("Purchase Bill")){
		LblVbox.getChildren().addAll(LblBarcode,LblBillType,LblSupplierName);
		TFVbox.getChildren().addAll(TFBarcode,CBBillType,CBSupplier);
		}else{
			LblVbox.getChildren().addAll(LblBarcode,LblBillType);
			TFVbox.getChildren().addAll(TFBarcode,CBBillType);
		}
		TopHbox.getChildren().addAll(LblVbox,TFVbox);
		BottomHbox.getChildren().addAll(LblTotal,TFTotal,LblDateTime,AddBtn,DeleteBtn,SaveBtn);
		
		root.setTop(TopHbox);
		root.setCenter(CenterVbox);
		root.setBottom(BottomHbox);
	}
	private void Padding(){
		TopHbox.setPadding(new Insets(20, 0, 0, 20));
		CenterVbox.setPadding(new Insets(20, 20, 0, 10));
		BottomHbox.setPadding(new Insets(20, 0, 10, 5));
	}
	public void TableView(String ItemID){
		table = new TableView<>();
		
		NoColumn = new TableColumn<>("No");
		NoColumn.setPrefWidth(50);
		NoColumn.setCellValueFactory(new PropertyValueFactory<>("No"));
		BarcodeColumn = new TableColumn<>("Barcode");
		BarcodeColumn.setCellValueFactory(new PropertyValueFactory<>("Barcode"));
		NameColumn = new TableColumn<>("Name");
		NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		MajorBrandColumn = new TableColumn<>("MajorBrand");
		MajorBrandColumn.setCellValueFactory(new PropertyValueFactory<>("MajorBrand"));
		SubBrandColumn = new TableColumn<>("SubBrand");
		SubBrandColumn.setCellValueFactory(new PropertyValueFactory<>("SubBrand"));
		UnitColumn = new TableColumn<>("Unit");
		UnitColumn.setCellValueFactory(new PropertyValueFactory<>("Unit"));
		CustomerPriceColumn = new TableColumn<>("Customer Price");
		CustomerPriceColumn.setPrefWidth(100);
		CustomerPriceColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerPrice"));
		SupplierPriceColumn = new TableColumn<>("Supplier Price");
		SupplierPriceColumn.setPrefWidth(100);
		SupplierPriceColumn.setCellValueFactory(new PropertyValueFactory<>("SupplierPrice"));
		if(ItemID.matches("Sales Bill"))
				table.getColumns().addAll(NoColumn,BarcodeColumn,NameColumn,MajorBrandColumn,SubBrandColumn,
				  UnitColumn,CustomerPriceColumn);
		else
			table.getColumns().addAll(NoColumn,BarcodeColumn,NameColumn,MajorBrandColumn,SubBrandColumn,
					  UnitColumn,CustomerPriceColumn,SupplierPriceColumn);
	}
	
}
