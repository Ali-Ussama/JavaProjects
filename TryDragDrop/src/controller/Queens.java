package controller;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.ComputerPlay;
import view.UserPlay;

public class Queens extends Application {
	BorderPane mBorderPane;
	Scene mScene;
	Button Computer , Player;
	HBox HboxCentreView;
	final String Url = "Background2.png";
	static UserPlay UserGame;
	static ComputerPlay ComputerGame;
	@Override
	public void start(Stage primaryStage) {
		try{
		    mBorderPane = new BorderPane();
		    mBorderPane.setId("Border");
			mScene = new Scene(mBorderPane, 400, 400);
		    mBorderPane.setStyle("-fx-background-image: url("+Url+");");
			Computer = new Button("Computer");
			Player = new Button("Player");
			HboxCentreView = new HBox(20);			
			UserGame = new UserPlay();
			ComputerGame = new ComputerPlay();
			Computer.setPrefWidth(100);
			Computer.setPrefHeight(20);
			Player.setPrefWidth(100);
			Player.setPrefHeight(20);

			HboxCentreView.setPadding(new Insets(180, 100, 200, 100));
			HboxCentreView.getChildren().addAll(Computer,Player);
			mBorderPane.setCenter(HboxCentreView);

			primaryStage.setTitle("8 Queens Puzzle");
			primaryStage.setScene(mScene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			Player.setOnAction(e->UserGame.start(primaryStage));
			Computer.setOnAction(e->ComputerGame.start(primaryStage));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
