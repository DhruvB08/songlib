package app;

import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import app.Song.CustomComparator;
import view.Controller;

public class SongLib extends Application { 
	public static void main(String[] args) {
		songList = new ArrayList<Song>();
		File fp = new File("Songs.txt");
		
		if (fp.exists()){
			loadFromFile(fp);
		}
		
		launch(args);
		saveInFile();
	}
	
	Stage primaryStage;
	AnchorPane root;
	static ArrayList<Song> songList;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Song Library");
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/interface.fxml"));
			
			root = (AnchorPane) loader.load();
			Controller listController = loader.getController();
			listController.start(primaryStage, songList);
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();	
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	static void loadFromFile(File fp){
		try {
			Scanner input = new Scanner(fp);
			
			while(input.hasNext()){
				String[] token = input.nextLine().split("/");
				for(int i = 0; i < 4; i++){
					if(token[i] == null){
						token[i] = " ";
					}
				} 
				
				Song song = new Song(token[0], token[1], token[2], token[3]);
				songList.add(song);
			}
		
			try {
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Collections.sort(songList, new CustomComparator());
	}
	
	public static void saveInFile(){
		File fp = new File("Songs.txt");
		
		if (fp.exists()) {
			fp.delete();
		}
		
		PrintWriter writer;
		
		try {
			writer = new PrintWriter("Songs.txt", "UTF-8");
			
			for (int i=0; i < songList.size(); i++) {
				String song = "";
				String songName = songList.get(i).getSongName();
				String artist = songList.get(i).getArtistName();
				String album = songList.get(i).getAlbumName();
				String year = songList.get(i).getYear();
				
				if(songName==null){
					songName = " ";
					artist = " ";
					album = " ";
					year = " ";
				} else{
					if(album.equals("")){
						album = " ";
					}
					if(year.equals("")){
						year = " ";
					}
				}
								
				song = songName+"/"+artist+"/"+album+"/"+year;
				writer.println(song);
			}
		
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
