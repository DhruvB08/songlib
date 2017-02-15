package view;

import java.util.*;
import app.Song;
import app.Song.CustomComparator;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.collections.*;
import javafx.stage.Stage;

public class Controller {
	
	@FXML ListView<String> songListView;
	@FXML TextField displaySong;
	@FXML TextField displayArtist;
	@FXML TextField displayAlbum;
	@FXML TextField displayYear;
	@FXML Button addButton;
	@FXML Button editButton;
	@FXML Button deleteButton;
	@FXML Button confirmEdit;
	@FXML Button cancelEdit;
	private ObservableList<String> obsList;
	private ArrayList<Song> songs;
	
	void displaySong(ArrayList<Song> songs, int index){
		displaySong.setText(songs.get(index).getSongName());
		displayArtist.setText(songs.get(index).getArtistName());
		displayAlbum.setText(songs.get(index).getAlbumName());
		displayYear.setText(songs.get(index).getYear());
	}
	
	void editOrNot(boolean bool){
		displaySong.setEditable(bool);
		displaySong.getStyleClass().clear(); 
		displaySong.getStyleClass().addAll("text-field", "text-input", "acceptingInput");
		
		displayArtist.setEditable(bool);
		displayArtist.getStyleClass().clear(); 
		displayArtist.getStyleClass().addAll("text-field", "text-input", "acceptingInput");
		
		displayAlbum.setEditable(bool);
		displayAlbum.getStyleClass().clear(); 
		displayAlbum.getStyleClass().addAll("text-field", "text-input", "acceptingInput");
		
		displayYear.setEditable(bool);
		displayYear.getStyleClass().clear(); 
		displayYear.getStyleClass().addAll("text-field", "text-input", "acceptingInput");
	}
	
	void sameSong(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Error");
		alert.setContentText("A song with this name and artist already exsits.");
		alert.show();
	}
	
	void moreInput(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Error");
		alert.setContentText("You require at least a name and artist for each song!");
		alert.show();
	}
	
	public void start(Stage main, ArrayList<Song> songs) {
		 obsList = FXCollections.observableArrayList();
		 this.songs = songs;
		 for(int i = 0; i < songs.size(); i++){
			 if(songs.isEmpty()){
				 break;
			 }
			 obsList.add(songs.get(i).getSongName());
		 }
		
		 songListView.setItems(obsList);
		 songListView.getSelectionModel().select(0);
		 if(!songs.isEmpty()){
			 displaySong(songs, 0);
		 }

		 songListView.getSelectionModel().selectedItemProperty();
		 songListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
					 showItem(main);
	            }
	     });
	 } 
	 
	 public void showItem(Stage main){
		 int index = songListView.getSelectionModel().getSelectedIndex();
		 if (index==-1) {
			 for (int i=0; i < songs.size(); i++) {
				 if (songs.get(i).getFirstSong()){
					 index = i;
					 break;
				 }
			 }
		 }
		 if (index == -1){
			 index = 0;
		 }
		 songListView.getSelectionModel().clearAndSelect(index);
		 displaySong(songs, index);
	 }
	
	public void addButtonClicked(ActionEvent e){ 
		songListView.getSelectionModel().clearSelection();
		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		dialog.setTitle("Add New Song");
		dialog.setHeaderText("Enter song details.");
		
		Label songLabel = new Label("Song: ");
		Label artistLabel = new Label("Artist: ");
		Label albumLabel = new Label("Album: ");
		Label yearLabel = new Label("Year: ");
		
		TextField songField = new TextField();
		TextField artistField = new TextField();
		TextField albumField = new TextField();
		TextField yearField = new TextField();

		GridPane gridpane = new GridPane();
		gridpane.add(songLabel, 0, 0);
		gridpane.add(artistLabel, 0, 1);
		gridpane.add(albumLabel, 0, 2);
		gridpane.add(yearLabel, 0, 3);
		
		gridpane.add(songField, 1, 0);
		gridpane.add(artistField, 1, 1);
		gridpane.add(albumField, 1, 2);
		gridpane.add(yearField, 1, 3);
		
		dialog.getDialogPane().setGraphic(gridpane);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		Optional<ButtonType> result = dialog.showAndWait();
		
		if(result.get() == ButtonType.OK){
			String song = songField.getText();
			String artist = artistField.getText();
			String album = albumField.getText();
			String year = yearField.getText();
			
			if(song.equals("") || song == null || artist.equals("") || artist == null){
				moreInput();
				return;
			}
			
			int indexOfSong = searchForSong(songs, song, artist);
			if (indexOfSong == -1) {
				Song newSong = new Song(song, artist, album, year);
				for(int i = 0; i < songs.size(); i++){
					songs.get(i).setFirstSong(false);
				}
				newSong.setFirstSong(true);
				songs.add(newSong);
				Collections.sort(songs, new CustomComparator());
				obsList.clear();
				int index = 0;
				for(int i = 0; i < songs.size(); i++){
					obsList.add(songs.get(i).getSongName());
				}
				for(int i = 0; i < songs.size(); i++){
					if(songs.get(i).getFirstSong()){
						index = i;
					}
				}
				songListView.getSelectionModel().clearAndSelect(index);
				displaySong(songs, index);
				FXCollections.sort(obsList, String.CASE_INSENSITIVE_ORDER);
			} else {
				sameSong();
				return;
			}
		}
		
		return;	
	}
	
	public void editButtonClicked(ActionEvent e){ 
		editOrNot(true);
	}
	
	public void deleteButtonClicked(ActionEvent e){ 
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Song");
		alert.setHeaderText("Really delete this song?");
		Optional<ButtonType> result = alert.showAndWait();
		
		int index;
		if(result.get() == ButtonType.OK){
			String name = displaySong.getText();
			String artist = displayArtist.getText();
			
			int i = searchForSong(songs, name, artist);
			index = i;
			if(index == (songs.size()-1)){
				index--;
			}
			
			songs.remove(i);
			obsList.remove(i);
		} else {
			return;
		}

		obsList.clear();
		for(int i = 0; i < songs.size(); i++){
			obsList.add(songs.get(i).getSongName());
		}
		songListView.getSelectionModel().clearAndSelect(index);
		
		if (songs.size() >= 1){
			displaySong(songs, index);
		}
		
		if (songs.size() == 0){
			displaySong.clear();
			displayArtist.clear();
			displayAlbum.clear();
			displayYear.clear();
		}
	}
	
	public void confirmEditClicked(ActionEvent e){ 
		if(!displaySong.isEditable()){
			return;
		}
		
		String song = displaySong.getText();
		String artist = displayArtist.getText();
		String album = displayAlbum.getText();
		String year = displayYear.getText();

		int index = songListView.getSelectionModel().getSelectedIndex();
		int indexOfSong = searchForSong(songs, song, artist);
		
		if(song.equals("") || song == null || artist.equals("") || artist == null){
			moreInput();
			return;
		}
		
		if (indexOfSong != -1){
			sameSong();
			return;
		}
		
		obsList.set(index, song);
		songs.get(index).editName(song);
		songs.get(index).editArtist(artist);
		songs.get(index).editAlbum(album);
		songs.get(index).editYear(year);
		
		Collections.sort(songs, new CustomComparator());
		FXCollections.sort(obsList);
		
		editOrNot(false);
	}
	
	public void cancelEditClicked(ActionEvent e){ 
		if(!displaySong.isEditable()){
			return;
		}
		
		editOrNot(false);
		displaySong.setText(songs.get(0).getSongName());
		displayArtist.setText(songs.get(0).getArtistName());
		displayAlbum.setText(songs.get(0).getAlbumName());
		displayYear.setText(songs.get(0).getYear());
	}
	
	public int searchForSong(ArrayList<Song> songs, String songName, String artist){
		for (int j = 0; j < songs.size(); j++){
			if (songs.get(j).equals(songName, artist)){
				return j;
			}
		}
		
		return -1;
	}
}
