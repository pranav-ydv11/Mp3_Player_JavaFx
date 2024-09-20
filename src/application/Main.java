package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage)throws IOException {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/Mp3Controller.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(event -> {
			
			event.consume();
			logout(stage);
			});
		}catch(Exception e) {
			e.printStackTrace();
			}
		
	}
	 public void logout(Stage stage) {
	   	  
	   	  Alert alert=new Alert(AlertType.CONFIRMATION);
	   	  alert.setTitle("Alert");
	   	  alert.setHeaderText("Are Sure To Exit");
	   	  alert.setContentText("Do you want save befor exiting" );
	   	  
	   	  if(alert.showAndWait().get()==ButtonType.OK) { 	
	   		  
	   	  System.out.println("You have log out SuccessFull");
	   	  stage.close();
	   	  }
	     }
		
		public static void main(String[] args) {
			launch(args);
		}

}
