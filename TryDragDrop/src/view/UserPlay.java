package view;

import javax.swing.JOptionPane;

import controller.Queens;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class UserPlay extends Application {
	
	static BorderPane mBorderPane;
	static Rectangle mRectangleWhite;
	static Rectangle mRectangleBlack;
	static Rectangle Queen;
	static Rectangle Cells[][];
	static GridPane mGridPane;
	static Scene scene ;
	static boolean Droped = true;
	static boolean CellsFinished = false;
	static boolean QueenFinished = false;
	static int visited[][];
	static Dragboard mDragbord ;
	static ClipboardContent content;
	static Button BackBtn , SolveBtn, PlayAgainBtn;
	static HBox TopHbox;
	static Queens MainWindow;
	static ComputerPlay mComputerPlay;
	
	private int[][] setVisited(int row, int col,int visited[][]) {
		int r = row;
		int c = col;
		visited[r][c] = 1;
		
		while(--r >= 0)
			visited[r][c] = 1;
		
		 r = row;
		while(++r < 8)
			visited[r][c] = 1;
		r = row;
		while(--c >= 0)
			visited[r][c] = 1;
		 c = col;
		while(++c < 8)
			visited[r][c] = 1;
		r = row;
		c = col;
		while(++c < 8 && ++r < 8)
			visited[r][c] = 1;
		r = row;
		c = col;
		while(--c >= 0 && --r  >= 0)
			visited[r][c] = 1;
		r = row;
		c = col;
		while(--c >=0 && ++r < 8)
			visited[r][c] = 1;
		r = row;
		c = col;
		while(--r >=0 && ++c < 8)
			visited[r][c] = 1;
		return visited;
	}

	
	public void start(Stage primaryStage) {
		
		try {
			MainWindow = new Queens();
			mComputerPlay = new ComputerPlay();
			BuildGrid(primaryStage);
		
			for(Node node: mGridPane.getChildren()){
				int row = GridPane.getRowIndex(node);
				int col = GridPane.getColumnIndex(node);
						if(col > 7)
						node.setOnDragDetected(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent event) {
								if(((Shape) node).getFill().getClass().equals(ImagePattern.class)){
									mDragbord= ((Shape) node).startDragAndDrop(TransferMode.MOVE);
								    content = new ClipboardContent();
								content.putImage(new Image("/queen3.png"));
								mDragbord.setContent(content);
								}
								event.consume();
							}
						});

						node.setOnDragOver(new EventHandler<DragEvent>(){
							@Override
							public void handle(DragEvent event) {
								event.acceptTransferModes(TransferMode.ANY);
								if(col < 8)
								if(visited[row][col] == 0){
								((Shape) node).setFill(Color.CHARTREUSE);
								}else if(visited[row][col] == 1 && !((Shape) node).getFill().getClass().toString().matches(ImagePattern.class.toString())){
									((Shape) node).setFill(Color.BROWN);
								}
								event.consume();
							}
						});

						node.setOnDragDropped(new EventHandler<DragEvent>() {
							@Override
							public void handle(DragEvent event) {
								boolean success = false;
								Dragboard mDragboard = event.getDragboard();
								if(visited[row][col] == 0 && mDragboard.hasImage()){
//									System.out.println("Droped "+ row + "/" + col);
									visited = setVisited(row,col,visited);
									((Shape) node).setFill(new ImagePattern(mDragboard.getImage()));
									success = true;
									Droped = true;
								}
								event.setDropCompleted(success);
								event.consume();
							}
						});
						node.setOnDragExited(new EventHandler<DragEvent>() {
							@Override
							public void handle(DragEvent event) {
								event.acceptTransferModes(TransferMode.MOVE);
								if(Droped == false && col < 8 && !((Shape) node).getFill().getClass().toString().matches(ImagePattern.class.toString())){
									if((col+row)%2 == 0){
									((Shape) node).setFill(Color.WHITE);
									}else{
										((Shape) node).setFill(Color.BLACK);
									}
								}else
									Droped = false;
								event.consume();
							}
						});
						
						node.setOnDragDone(new EventHandler<DragEvent>() {
							@Override
							public void handle(DragEvent event) {
								if(event.getTransferMode() == TransferMode.MOVE){
									((Shape) node).setFill(Color.WHITE);
									for(int i = 0 ; i < 8 ; i++){
										for(int j = 0 ; j < 8 ; j++){
											if(visited[i][j] == 0){
												CellsFinished = true;
												break;
											}
										}
									}
									for(int i = 0 ;i < 8 ; i++){
										if(Cells[i][8].getFill().getClass().equals(ImagePattern.class)){
											QueenFinished = true;
											break;
										}
									}
									if(CellsFinished == false && QueenFinished == false){
										JOptionPane.showMessageDialog(null, "Well Done !");
									}else if(CellsFinished == false && QueenFinished){
										JOptionPane.showMessageDialog(null, "Try Again");
									}
									else{
										CellsFinished = false;
										QueenFinished = false;
									}	
								}
								event.consume();
							}
						});
			}
		 BackBtn.setOnAction(e->MainWindow.start(primaryStage));
		 SolveBtn.setOnAction(e->mComputerPlay.start(primaryStage));
		 PlayAgainBtn.setOnAction(e->start(primaryStage));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void BuildGrid(Stage primaryStage) {
		mBorderPane = new BorderPane();
	    scene = new Scene(mBorderPane, 450, 425);
		mGridPane = new GridPane();
		BackBtn = new Button("Back To Main");
		SolveBtn = new Button("Solve");
		PlayAgainBtn = new Button("Play Again");
		SolveBtn.setPrefWidth(90);
		TopHbox = new HBox(100);
		
		Cells = new Rectangle[8][9];
		visited = new int[8][8];
		for(int row = 0 ; row < 8 ; row++){
			for(int col = 0 ; col < 9 ; col++){
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
					}else if(col == 8){
						Image image = new Image("/queen3.png");
						Queen = new Rectangle();
						Queen.setWidth(50); 
						Queen.setHeight(50); 
						Queen.setFill(new ImagePattern(image));
						mGridPane.add(Queen, col, row);
						Cells[row][col] = Queen;
					}
				}
		}
		TopHbox.getChildren().addAll(BackBtn,PlayAgainBtn,SolveBtn);
		mBorderPane.setTop(TopHbox);
		mBorderPane.setCenter(mGridPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("8 Queens Puzzle");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}
