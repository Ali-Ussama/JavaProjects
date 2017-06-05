package view;

import java.util.ArrayList;
import java.util.Arrays;

import controller.Queens;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ComputerPlay extends Application {
	static Queens MainWindow;
	static UserPlay mUserPlay;
	static BorderPane mBorderPane;
	static Rectangle mRectangleWhite;
	static Rectangle mRectangleBlack;
	static Rectangle Queen;
	static Rectangle Cells[][];
	static GridPane mGridPane;
	static Scene scene ;
	static Button BackBtn , PlayBtn;
	static HBox TopHbox;
	static boolean Droped = true;
	static int n;
	int[] board;
	static ArrayList<int[]> Solutions;
	Queens mQeens;
	
	@Override
	public void start(Stage primaryStage) {
		MainWindow = new Queens();
		mUserPlay = new UserPlay();
		BuildGrid(primaryStage);
		n = 8;
		Solutions = new ArrayList<>();
	    board = new int[n]; //hold the column position of n queens
	    placeQueenOnBoard(0, board);
//	    System.out.println(Solutions.size());
	    int RandomSolution = (int) (Math.random() * Solutions.size());
//	    System.out.println(RandomSolution);
	    board = Solutions.get(RandomSolution);
	    PutQeensOnBoard(board);
	    mQeens = new Queens();
	    BackBtn.setOnAction(e->mQeens.start(primaryStage));
		PlayBtn.setOnAction(e->mUserPlay.start(primaryStage));
	}
	
	private void PutQeensOnBoard(int[] board2) {
		Image image = new Image("/queen3.png");
		int i = 0;
			for(int j = 0 ; j < 8 ; j++){
				Cells[i++][board2[j]].setFill(new ImagePattern(image));
			}
		
	}

	private static void placeQueenOnBoard(int Qi, int[] board) {
	    int n = board.length;
	    //base case
	    if (Qi == n) {// a valid configuration found.
	    	int Sol[] = new int[8];
	    	for(int i = 0 ; i < board.length ; i++)
	    		Sol[i] = board[i];
	    	Solutions.add(Sol);
//	    	System.out.println(Arrays.toString(Sol));
	    } else {
	      //try to put the ith Queen (Qi) in all of the columns
	      for (int column = 0; column < n; column++) {
	        if (isSafePlace(column, Qi, board)) {
	          board[Qi] = column;
	          //then place remaining queens.
	          placeQueenOnBoard(Qi + 1, board);
	          board[Qi] = -1;
	        }
	      }
	    }
	  }
	 //check if the column is safe place to put Qi (ith Queen)
	  private static boolean isSafePlace(int column, int Qi, int[] board) {
	 
	    //check for all previously placed queens
	    for (int i = 0; i < Qi; i++) {
	      if (board[i] == column) { // the ith Queen(previous) is in same column
	        return false;
	      }
	      //the ith Queen is in diagonal
	      //(r1, c1) - (r2, c1). if |r1-r2| == |c1-c2| then they are in diagonal
	      if (Math.abs(board[i] - column) == Math.abs(i - Qi)) {
	        return false;
	      }
	    }
	    return true;
	  }
	private void BuildGrid(Stage primaryStage) {
		mBorderPane = new BorderPane();
	    scene = new Scene(mBorderPane, 400, 425);
		mGridPane = new GridPane();
		Cells = new Rectangle[8][8];
		BackBtn = new Button("Back To Main");
		PlayBtn =  new Button("Play");
		PlayBtn.setPrefWidth(90);
		TopHbox = new HBox(222);
		for(int row = 0 ; row < 8 ; row++){
			for(int col = 0 ; col < 8 ; col++){
				Cells[row][col] = new Rectangle();
				if(col < 8){
						if((row + col ) % 2 == 0){
							mRectangleWhite = new Rectangle();
							mRectangleWhite.setWidth(50); 
							mRectangleWhite.setHeight(50); 
							mRectangleWhite.setFill(Color.WHITE);
							mGridPane.add(mRectangleWhite, col, row);
							Cells[row][col] = mRectangleWhite;
						}else{
							mRectangleBlack = new Rectangle();
							mRectangleBlack.setWidth(50); 
							mRectangleBlack.setHeight(50); 
							mRectangleBlack.setFill(Color.BLACK);
							mGridPane.add(mRectangleBlack, col, row);
							Cells[row][col] = mRectangleBlack;
						}
					}
				}
		}
		TopHbox.getChildren().addAll(BackBtn, PlayBtn);
		mBorderPane.setTop(TopHbox);
		mBorderPane.setCenter(mGridPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("8 Queens Puzzle");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
