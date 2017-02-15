package app;

import java.util.Comparator;

public class Song {
	
	//song object properties
	String songName ="";
	String artist = "";
	String year = "";
	String album = "";
	boolean firstSong = false;
		
	//song constructor
	public Song(String songName, String artist, String year, String album){
		this.songName = songName;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}	
	
	//return song properties
		public String getSongName() {
			return this.songName;
		}
		public String getArtistName() {
			return this.artist;
		}
		public String getAlbumName() {
			return this.album;
		}
		public String getYear() {
			return this.year;
		}
		public boolean getFirstSong(){
			return this.firstSong;
		}
		
	//change song properties
	public void editName(String songName) {
		this.songName = songName;
	}
	public void editArtist(String artistName) {
		this.artist = artistName;
	}
	public void editAlbum(String albumName) {
		this.album = albumName;
	}
	public void editYear(String editedYear) {
		this.year = editedYear;
	}
	public void setFirstSong(boolean bool){
		this.firstSong = bool;
	}
	
	//check equivalencies
	public static class CustomComparator implements Comparator<Song> {
	    @Override
	    public int compare(Song o1, Song o2) {
	        return o1.getSongName().compareToIgnoreCase(o2.getSongName());
	    }
	}
	
	public boolean equals(String secondSong, String secondArtist){
		if (this.songName.equalsIgnoreCase(secondSong) && this.artist.equalsIgnoreCase(secondArtist)){
			return true;
		} 
		
		return false;
	}
}
