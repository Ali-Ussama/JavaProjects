package controller;

import java.util.ArrayList;
import java.util.InputMismatchException;

import javax.swing.JOptionPane;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.Mainwindow;
import view.Login;
public class loginAction{
	
	String Username,Password;
	private Mainwindow mMainwindow;
	private Login mLogin;
	
	public void onLoginListner(TextField username, PasswordField password, Stage primaryStage) {

		try {
		mMainwindow = new Mainwindow();
		mMainwindow.StartMainWindow();
		primaryStage.close();
//			mMainwindow = new Mainwindow();
//			this.Username = username.getText();
//			this.Password = password.getText();
//			if(Username.matches("ali")&& Password.matches("123456")){
//				mMainwindow.StartMainWindow();
//				primaryStage.close();
//			}
//			else
//				JOptionPane.showMessageDialog(null, "Invalid Username / Password");
//
		} catch (InputMismatchException e) {
			JOptionPane.showMessageDialog(null, "Invalid Username / Password");

		}	

	}

}
