package SongLib.view;

import java.util.Collection;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

	@FXML Button ADD;
	@FXML Button EDIT;
	@FXML Button DELETE;
	
	@FXML TextField name_of_song_add;
	@FXML TextField artist_add;
	@FXML TextField album_add;
	@FXML TextField year_add;
	
	@FXML TextField artist_else;
	@FXML TextField name_of_song_else;
	@FXML TextField album_else;
	@FXML TextField year_else;
	
	//ListView information
	ListView<String> listView;                
    private ObservableList<String> obsList;       
    public void start() { 
    	
    }
	Stage mainStage;
	
	public void setMainStage(Stage stage) {
		mainStage = stage;
	}
	
	
	
	//add button, get information from the fields.
	public void addbutton(ActionEvent e){
		String name_of_song_temp;
		String artist_temp;
		String album_temp;
		String year_temp;
		
		name_of_song_temp = name_of_song_add.getText();
		artist_temp = artist_add.getText();
		album_temp = album_add.getText();
		year_temp = year_add.getText();
		
		Songs temp = new Songs(name_of_song_temp, artist_temp, album_temp, year_temp);
		System.out.println(temp.song + " " + temp.artist + " " + temp.album + " " + temp.year);
		System.out.println("Add button clicked");
	}
	//whenever which button is clicked edit or delete will be called
	public void whichbutton(ActionEvent e) {
		Button b = (Button)e.getSource();
	    if(b == EDIT){
			System.out.println("Edit button clicked");
		}
		else{
			System.out.println("Delete button clicked");
		}
	}
	
	/*
	@FXML
	private void makeSandwich(ActionEvent evt) {
		// get meats
		String meats="";
		int count=0;
		if (ham.isSelected()) {
			meats += "Ham";
			count++;
		}
		if (turkey.isSelected()) {
			if (count != 0) {
				meats += ", Turkey";
			} else {
				meats += "Turkey";
			}
			count++;
		}
		if (roastBeef.isSelected()) {
			if (count != 0) {
				meats += ", Roast Beef";
			} else {
				meats += "Roast Beef";
			}
			count++;
		}
		//System.out.println("Meat: " + meats);
		
		// get the cheese
		String cheese = cheeseCombo.getSelectionModel().getSelectedItem();
		if (cheese == null) { // nothing selected
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(mainStage);
			alert.setTitle("Select Cheese");
			alert.setHeaderText("Cheese not selected");
			alert.setContentText("Please select a cheese");
			alert.showAndWait();
			return;
		}
		//System.out.println("Cheese: " + cheese);
		
		// get veggies
		String veggies="";
		count=0;
		if (lettuce.isSelected()) {
			veggies += "Lettuce";
			count++;
		}
		if (tomato.isSelected()) {
			if (count != 0) {
				veggies += ", Tomato";
			} else {
				veggies += "Tomato";
			}
			count++;
		}
		if (olives.isSelected()) {
			if (count != 0) {
				veggies += ", Olives";
			} else {
				veggies += "Olives";
			}
			count++;
		}
		//System.out.println("Veggies: " + veggies);
		sandwich.setText("Meat: " + meats + "\nCheese: " + cheese + "\nVeggies: " + veggies);
				
	}
	
	private void meat(ActionEvent event) {
		CheckBox cb = (CheckBox)event.getSource();
		if (cb.isSelected()) {
			System.out.println(cb.getText());
		}
	}
	*/
}
