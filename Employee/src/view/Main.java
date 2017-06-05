package view;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Model;
import view.SearchView;

public class Main extends Application {	
	private View view;
	private SearchView Searchview;
	private Scene scene;
	private MenuBar menuBar;
	private Menu menuFile;
	private MenuItem mAdd , mDelete , mUpdate, mSearch,mViewAll;
	private TableView<Worker>table ;
	private TableColumn<Worker, String> IDColumn ,SSNColumn, NameColumn ,AddressColumn, PhoneColumn, SalaryColumn, AgeColumn , JobColumn , DepartmentColumn;
	private static ObservableList<Worker> TableData ;
	private VBox Scenevbox;
	private Submit submit;
    
 public void Initialize(){
		//Object from Class View .
		view = new View(new Submit());
		//Open Connection with database.
		new Model();
		//Object from Class SearchView .
		Searchview = new SearchView(new ShowSearchResult());
		 
		//Layouts.
		 Scenevbox = new VBox();	     
		 //Scene
		 scene = new Scene(Scenevbox,620,400);
//		 scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	     // MenuBar
		  menuBar = new MenuBar();
	     //Menu File
	      menuFile = new Menu("file");
	     //Menu Add
	      mAdd= new MenuItem("Add");
	      mAdd.setId("Add");
	     //Menu Delete
          mDelete = new MenuItem("Delete");
          mDelete.setId("Delete");
         //Menu Update
          mUpdate = new MenuItem("Update");
          mUpdate.setId("Update");
         //Menu Search
          mSearch = new MenuItem("Search");
          mSearch.setId("Search");
          //Menu View All
          mViewAll = new MenuItem("View All");
          //TableView
          table = new TableView<Worker>();
          //Submit Class 
          submit = new Submit();
	}
	 @Override
 public void start(Stage primaryStage)  {
		try {
			//Initialize Components.
		    Initialize();
	        //Create Table View.
	        TableView();
	        
	        menuBar.getMenus().addAll(menuFile);
	        menuFile.getItems().addAll(mAdd,mDelete,mUpdate,mSearch,mViewAll);
	        
	        Scenevbox.getChildren().addAll(menuBar,table);
        	primaryStage.setScene(scene);
			primaryStage.setTitle("Faculty management");
			primaryStage.show();
			
	        mAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent e) {
	        	// TODO Auto-generated method stub
	        	view.Start(mAdd.getId());
	        }
			});
	        mDelete.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					view.Start(mDelete.getId());
				}
			});
	        mUpdate.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					view.Start(mUpdate.getId());	
				}
			});
	        mSearch.setOnAction(new EventHandler<ActionEvent>() {
	        	@Override
				public void handle(ActionEvent arg0) {
					Searchview.start();				
				}
			});
	        mViewAll.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					submit.onSubmitListener();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	 
 public static void main(String[] args) {
			try {
			launch(args);
			} catch (Exception e) {
				e.printStackTrace();
			}
 }
	 
 public void TableView(){
		 try {
			// initialize the columns in the table.   
		        IDColumn = new TableColumn<Worker, String>("ID");
		        IDColumn.setCellValueFactory(new PropertyValueFactory<Worker, String>("ID"));
		        SSNColumn = new TableColumn<Worker, String>("SSN");
		        SSNColumn.setCellValueFactory(new PropertyValueFactory<Worker, String>("SSN"));
		        NameColumn = new TableColumn<Worker, String>("Name");
		        NameColumn.setCellValueFactory(new PropertyValueFactory<Worker, String>("Name"));
		        AddressColumn = new TableColumn<Worker, String>("Address");
		        AddressColumn.setCellValueFactory(new PropertyValueFactory<Worker, String>("Address"));
		        PhoneColumn = new TableColumn<Worker, String>("Phone");
		        PhoneColumn.setCellValueFactory(new PropertyValueFactory<Worker, String>("Phone"));
		        AgeColumn = new TableColumn<Worker, String>("Age");
		        AgeColumn.setCellValueFactory(new PropertyValueFactory<Worker, String>("Age"));
		        SalaryColumn = new TableColumn<Worker, String>("Salary");
		        SalaryColumn.setCellValueFactory(new PropertyValueFactory<Worker, String>("Salary"));
		        JobColumn = new TableColumn<Worker, String>("Job");
		        JobColumn.setCellValueFactory(new PropertyValueFactory<Worker, String>("Job"));
		        DepartmentColumn = new TableColumn<Worker, String>("Department");
		        DepartmentColumn.setCellValueFactory(new PropertyValueFactory<Worker, String>("Department"));
		        table.getColumns().addAll(IDColumn,SSNColumn, NameColumn,AgeColumn,AddressColumn,PhoneColumn,SalaryColumn,JobColumn,DepartmentColumn);
		        
		        //call method onSubmitListner to add the data to table
		        submit.onSubmitListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
 public class Worker {
	 
	private SimpleIntegerProperty ID;
	private SimpleIntegerProperty SSN;
	private SimpleStringProperty Name;
	private SimpleStringProperty Address;
	private SimpleStringProperty Phone;
	private SimpleStringProperty Age;
	private SimpleStringProperty Salary;
	private SimpleStringProperty Job;
	private SimpleStringProperty Department;
     
     public Worker(int id,int ssn,String name,String address,String phone,String age,String salary,String job,String Dep) {
         this.ID = new SimpleIntegerProperty(id);
         this.SSN = new SimpleIntegerProperty(ssn);
         this.Name = new SimpleStringProperty(name);
         this.Address = new SimpleStringProperty(address);
         this.Phone = new SimpleStringProperty(phone);
         this.Age = new SimpleStringProperty(age);
         this.Salary = new SimpleStringProperty(salary);
         this.Job = new SimpleStringProperty(job);
         this.Department = new SimpleStringProperty(Dep);
     }
     public int getID(){
    	 return ID.get();
     }
     public void setID(int id){
    	 ID.set(id);
     }
     public int getSSN(){
    	 return SSN.get();
     }
     public void setSSN(int ssn){
    	 SSN.set(ssn);
     }
     public String getName(){
    	 return Name.get();
     }
     public void setName(String name){
    	 Name.set(name);
     }
     public String getAddress(){
    	 return Address.get();
     }
     public void setAddress(String address){
    	 Address.set(address);
     }
     public String getPhone(){
    	 return Phone.get();
     }
     public void setPhone(String phone){
    	 Phone.set(phone);
     }
     public String getAge(){
    	 return Age.get();
     }
     public void setAge(String age){
    	 Age.set(age);
     }
     public String getSalary(){
    	 return Salary.get();
     }
     public void setSalary(String salary){
    	 Salary.set(salary);
     }
     public String getJob(){
    	 return Job.get();
     }
     public void setJob(String job){
    	 Job.set(job);
     }
     public String getDepartment(){
    	 return Department.get();
     }
     public void setDepartment(String dep){
    	 Department.set(dep);
     }
     
 }

 public class Submit implements SubmitListener{

	@Override
	public void onSubmitListener() {
		try {
			String Query = "select worker.id ,worker.ssn,worker.name,worker.address,worker.phone,worker.age,worker.salary,job.name ,department.name "
					+ "from worker,department,job where worker.dep_id = department.dep_id and worker.job_id = job.job_id order by worker.ssn";

			Model.myRes = Model.statement.executeQuery(Query);
        	TableData = FXCollections.observableArrayList();
			while(Model.myRes.next()){
				TableData.add(new Worker(
								  Model.myRes.getInt("id"), 
								  Model.myRes.getInt("ssn"),
								  Model.myRes.getString("name"),
								  Model.myRes.getString("address"),
								  Model.myRes.getString("phone"),
								  Model.myRes.getString("age"),
								  Model.myRes.getString("salary"),
								  Model.myRes.getString("job.name"),
								  Model.myRes.getString("department.name")));
			}
        	table.setItems(TableData);
		} catch (Exception e) {

		}
	}

	@Override
	public void onSubmitListener(String s) {
		try {
			String Query = s;
			System.out.println(s);
			Model.myRes = Model.statement.executeQuery(Query);
        	TableData = FXCollections.observableArrayList();
			while(Model.myRes.next()){
				TableData.add(new Worker(
								  Model.myRes.getInt("id"), 
								  Model.myRes.getInt("ssn"),
								  Model.myRes.getString("name"),
								  Model.myRes.getString("address"),
								  Model.myRes.getString("phone"),
								  Model.myRes.getString("age"),
								  Model.myRes.getString("salary"),
								  Model.myRes.getString("job.name"),
								  Model.myRes.getString("department.name")));
			}
        	table.setItems(TableData);
		} catch (Exception e) {

		}
		
	}
	
}
 private class ShowSearchResult implements SubmitListener{

	@Override
	public void onSubmitListener(String s) {
		
		try {
			Model.myRes = Model.statement.executeQuery(s);
			
        	TableData = FXCollections.observableArrayList();
			while(Model.myRes.next()){
					TableData.add(new Worker(
								  Model.myRes.getInt("id"), 
								  Model.myRes.getInt("ssn"),
								  Model.myRes.getString("name"),
								  Model.myRes.getString("address"),
								  Model.myRes.getString("phone"),
								  Model.myRes.getString("age"),
								  Model.myRes.getString("salary"),
								  Model.myRes.getString("job.name"),
								  Model.myRes.getString("department.name")));
			}
        	table.setItems(TableData);
		} catch (Exception e) {

		}
	}

	@Override
	public void onSubmitListener() {
		// TODO Auto-generated method stub
	}
}
	
}
