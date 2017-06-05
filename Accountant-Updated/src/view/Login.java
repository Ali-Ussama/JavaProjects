package view;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import model.DataBase;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import controller.*;


public class Login extends Application {
	private BorderPane root;
	private Scene scene;
	private Button LoginBtn;
	private TextField TFUsername ;
	private PasswordField TFPassword;
	private Label LblUsername, LblPassword;
	private HBox TopHBox;
	private VBox CenterVBox;
	private loginAction mOnLoginListner;
	public Login(){
		new DataBase();
	}
	private void Initialize(){
		//Main Layout and scene.
		root = new BorderPane();
		root.setId("root");
	    scene = new Scene(root,300,450);
	    
	    //Login Button
	    LoginBtn = new Button("Login");
	    LoginBtn.setPrefSize(200, 30);
	    LoginBtn.setId("LoginBtn");
	    
	    //Labels.
//	    LblPassword = new Label("Password");
//	    LblUsername = new Label("Username");
	    
	    //TextFields
	    TFPassword = new PasswordField();
	    TFUsername = new TextField();
	    TFPassword.setPromptText("Password");
	    TFUsername.setPromptText("Username");
	    
	    //Layouts.
	    TopHBox = new HBox(20);
	    CenterVBox = new VBox(20);
	    
	    //ActionsListner for Login.
	    mOnLoginListner = new loginAction();
	    
	}
	private void AddComponents(){
		CenterVBox.getChildren().addAll(TFUsername,TFPassword,LoginBtn);
		root.getStylesheets().addAll(this.getClass().getResource("Login.css").toExternalForm());
		root.setCenter(CenterVBox);
//		TopHBox.getChildren().addAll(LblUsername,TFUsername,LblPassword,TFPassword,LoginBtn);
//		root.setTop(TopHBox);
	}
	private void Padding(){
//		TopHBox.setPadding(new Insets(10, 10, 10, 10));
		CenterVBox.setPadding(new Insets(100, 50, 100, 50));
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Initialize Components.
			Initialize();
			//Add components on layouts.
			AddComponents();
			//Padding
			Padding();
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.show();
			
			LoginBtn.setOnAction(e->mOnLoginListner.onLoginListner(TFUsername,TFPassword,primaryStage));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
