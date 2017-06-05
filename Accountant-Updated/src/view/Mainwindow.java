package view;

import controller.ProductAction;
import controller.ProductAction.Product_Table;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.Suppliers;

public class Mainwindow {
	private static Stage stage;
	private static Scene scene;
	private static BorderPane root;
	
	private static MenuBar menuBar;
	private static Menu menuProduct,menubill,menuSupplier,menuTotal;
	private static VBox menuVbox;
	private static MenuItem SupplierMenuItem,MajorBrandMenuItem,SubBrandMenuItem,UnitsMenuItem,ProductMenuItem,PurchaseBillMenuItem,
							SalesBillMenuItem,PurchaseBillMenuTotal, SalesBillMenuTotal;;
	private static Suppliers mSuppliers;
	private static Major_Brand mMajorBrand;
	private static Sub_Brand msubBrand;
	private static Units mUnits;
	private static Product mproduct;
	private static Sales_Bill mSalesBill;
	private ProductAction mProductAction;
	public static TableView<ProductAction.Product_Table> table;
	public static TableColumn NoColumn,BarcodeColumn,NameColumn,MajorBrandColumn,SubBrandColumn,QuantityColumn,
	 					UnitColumn,CustomerPriceColumn,SupplierPriceColumn,SupplierNameColumn;
	private static TotalPurchaseBills mTotalPurchase;
    private static TotalSalesBills mTotalSales;

	public void StartMainWindow(){
		try{
		//Initialize
		Initialize();
		//Add Components.
		AddComponents();
		stage.setScene(scene);
		stage.setTitle("Main Window");
		stage.show();
//		table.setRowFactory(tv -> {
//			TableRow<ProductAction.Product_Table> row = new TableRow<>();		
//			    row.setOnMouseClicked(event -> {
//			        if (event.getClickCount() == 1 && (!row.isEmpty()) ) {
//				    	System.out.println("Before");
//			        }
//			    });
//			  return row;
//		});
		SupplierMenuItem.setOnAction(e -> mSuppliers.StartSupplier());
		MajorBrandMenuItem.setOnAction(e -> mMajorBrand.StartMajorBrand());
		SubBrandMenuItem.setOnAction(e -> msubBrand.StartSubBrand());
		UnitsMenuItem.setOnAction(e -> mUnits.StartUnits());
		ProductMenuItem.setOnAction(e -> mproduct.StartProduct());
		SalesBillMenuItem.setOnAction(e -> mSalesBill.Start(SalesBillMenuItem.getId()));
		PurchaseBillMenuItem.setOnAction(e -> mSalesBill.Start(PurchaseBillMenuItem.getId()));
		PurchaseBillMenuTotal.setOnAction(e -> mTotalPurchase.Start(PurchaseBillMenuTotal.getId()));
        SalesBillMenuTotal.setOnAction(e -> mTotalSales.Start(SalesBillMenuTotal.getId()));
        table.setRowFactory( tv -> mProductAction.onProductTableRowListener());
        
		}catch(Exception e1){
			e1.printStackTrace();
		}
	}
	
    private void Initialize(){
		//Stage - scene.
		stage = new Stage();
		root = new BorderPane();
		scene = new Scene(root,712,400);
		//Layout
		menuVbox = new VBox(10);
		//classes objects.
		mSuppliers = new Suppliers();
		mMajorBrand = new Major_Brand();
		msubBrand = new Sub_Brand();
		mUnits = new Units();
		mproduct = new Product();
		mSalesBill = new Sales_Bill();
		mProductAction = new ProductAction();
		mTotalPurchase = new TotalPurchaseBills();
	    mTotalSales = new TotalSalesBills();
		//Menu
		menuBar = new MenuBar();
		menuProduct = new Menu("Product");
		menubill = new Menu("bills");
		menuSupplier = new Menu("Supplier");
		menuTotal = new Menu("Total");
		//Menu Items
		SupplierMenuItem = new MenuItem("Suppliers");
		MajorBrandMenuItem = new MenuItem("Major Brand");
		SubBrandMenuItem = new MenuItem("Sub Brand");
		UnitsMenuItem = new MenuItem("Units");
		ProductMenuItem  = new MenuItem("Product");
		PurchaseBillMenuItem = new MenuItem("Purchase Bill");
		PurchaseBillMenuItem.setId("Purchase Bill");
		SalesBillMenuItem = new MenuItem("Sales Bill");
		SalesBillMenuItem.setId("Sales Bill");
		PurchaseBillMenuTotal = new MenuItem("Total Purchase");
        PurchaseBillMenuTotal.setId("Total Purchase");
        SalesBillMenuTotal = new MenuItem("Total Sales");
        SalesBillMenuTotal.setId("Total Sales");
		//TableView
		TableView();
		mProductAction.TableRefresh(table);
		
		
	}
	private void AddComponents(){
		//add menus to menuBar
				menuBar.getMenus().addAll(menuSupplier,menuProduct,menubill,menuTotal);
				menuVbox.getChildren().addAll(menuBar);
				menuSupplier.getItems().addAll(SupplierMenuItem);
				menuProduct.getItems().addAll(ProductMenuItem,MajorBrandMenuItem,SubBrandMenuItem,UnitsMenuItem);
				menubill.getItems().addAll(PurchaseBillMenuItem,SalesBillMenuItem);
				menuTotal.getItems().addAll(PurchaseBillMenuTotal, SalesBillMenuTotal);
				root.setTop(menuVbox);
				root.setCenter(table);
	}
	public void TableView(){
		
		table = new TableView<>();
		
		NoColumn = new TableColumn<>("No");
		NoColumn.setPrefWidth(50);
		NoColumn.setCellValueFactory(new PropertyValueFactory<>("No"));
		BarcodeColumn = new TableColumn<>("Barcode");
		BarcodeColumn.setCellValueFactory(new PropertyValueFactory<>("Barcode"));
		NameColumn = new TableColumn<>("Name");
		NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		QuantityColumn = new TableColumn<>("Quantity");
		QuantityColumn.setCellValueFactory(new PropertyValueFactory("Quantity"));
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
		SupplierNameColumn = new TableColumn<>("Supplier");
		SupplierNameColumn.setCellValueFactory(new PropertyValueFactory("SupplierName"));
		
		table.getColumns().addAll(NoColumn,BarcodeColumn,NameColumn,QuantityColumn,MajorBrandColumn,SubBrandColumn,
					  UnitColumn,CustomerPriceColumn,SupplierPriceColumn,SupplierNameColumn);
	}
	
}
