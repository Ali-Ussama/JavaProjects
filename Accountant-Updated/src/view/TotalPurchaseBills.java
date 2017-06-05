package view;

import controller.PurchaseBillAction;
import controller.SalesBillAction;
import controller.TotalPurchaseBillsAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static view.Sales_Bill.table;
import static view.TotalSalesBills.table;

public class TotalPurchaseBills {

    private BorderPane root;
    private Scene scene;
    private Stage stage;

    private Button SearchBtn, ViewAllBtn;
    private VBox CenterVbox, LblVbox, TFVbox;
    private HBox BottomHbox, TopHbox;
    public static TableView table;
    private TableColumn NoColumn, SSNColumn, TotalColumn, Date_TimeColumn,
            Bill_Type, Supplier_id;    
    private RadioButton RadioSSN, RadioDate;
    private TextField TFInput;
    private ToggleGroup group;
    private TotalPurchaseBillsAction mTotalPurchaseBillAction;

    public void Start(String ItemID) {
        //Initialize
        Initilaize(ItemID);
        //Add Components
        AddComponent(ItemID);

        //Padding
        Padding();
        stage.setScene(scene);
        stage.setTitle("Total Purchase");
        stage.show();
        
        SearchBtn.setOnAction(e -> mTotalPurchaseBillAction.PurchaseSearch(RadioSSN,RadioDate, table,TFInput));
        ViewAllBtn.setOnAction(e -> mTotalPurchaseBillAction.TableRefresh(table));
        TFInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
            	mTotalPurchaseBillAction.PurchaseSearch(RadioSSN,RadioDate, table,TFInput);
            }}});
    }

    private void Initilaize(String ItemID) {
        stage = new Stage();
        root = new BorderPane();
        scene = new Scene(root, 610, 520);
        // Total Purchase Bill Action Object
        mTotalPurchaseBillAction = new TotalPurchaseBillsAction();
        //Buttons
        SearchBtn = new Button("Search");
        ViewAllBtn = new Button("View All");
        //Layout
        CenterVbox = new VBox(20);
        LblVbox = new VBox(30);
        TFVbox = new VBox(20);
        BottomHbox = new HBox(20);
        TopHbox = new HBox(20);
        //RadioButtons.
        RadioSSN = new RadioButton("SSN");
        RadioSSN.setSelected(true);
        RadioDate = new RadioButton("Date");
        //Input text Field
        TFInput = new TextField();
        //ToggleGroup.
        group = new ToggleGroup();
        RadioSSN.setToggleGroup(group);
        RadioDate.setToggleGroup(group);
        //Table
        TableView(ItemID);
    }

    private void AddComponent(String ItemID) {
        LblVbox.getChildren().addAll(RadioSSN, RadioDate);
        TFVbox.getChildren().addAll(TFInput);
        TopHbox.getChildren().addAll(LblVbox, TFVbox,SearchBtn, ViewAllBtn);
        CenterVbox.getChildren().add(table);
        root.setTop(TopHbox);
        root.setCenter(CenterVbox);       
    }

    private void Padding() {
        TopHbox.setPadding(new Insets(20, 0, 0, 20));
        CenterVbox.setPadding(new Insets(20, 20, 0, 10));
    }

    private void TableView(String ItemID) {
        table = new TableView<>();

        NoColumn = new TableColumn<>("No");
        NoColumn.setPrefWidth(50);
        NoColumn.setCellValueFactory(new PropertyValueFactory<>("No"));
        
        SSNColumn = new TableColumn<>("SSN");
        SSNColumn.setCellValueFactory(new PropertyValueFactory<>("SSN"));
        
        TotalColumn = new TableColumn<>("Total");
        TotalColumn.setCellValueFactory(new PropertyValueFactory<>("Total"));
        
        Date_TimeColumn = new TableColumn<>("Date_Time");
        Date_TimeColumn.setCellValueFactory(new PropertyValueFactory<>("Date_Time"));
        
        Bill_Type = new TableColumn<>("Bill_Types");
        Bill_Type.setCellValueFactory(new PropertyValueFactory<>("BillType"));
        
        Supplier_id = new TableColumn<>("Supplier_id");
        Supplier_id.setCellValueFactory(new PropertyValueFactory<>("Supplier"));

        table.getColumns().addAll(NoColumn, SSNColumn, TotalColumn, Date_TimeColumn,
                Bill_Type, Supplier_id);

        mTotalPurchaseBillAction.TableRefresh(table);

        //else
        //	table.getColumns().addAll(NoColumn,BarcodeColumn,NameColumn,MajorBrandColumn,SubBrandColumn,
        //			  UnitColumn,CustomerPriceColumn,SupplierPriceColumn);
    }
}
