import javafx.application.Application;
import javafx.stage.Stage;
import view.*;
public class Launch extends Application{

	

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		view.Login login = new Login();
		login.start(primaryStage);
		
	}

}
