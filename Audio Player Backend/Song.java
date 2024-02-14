/* Jana Habibi
 * A Song is a type of AudioContent. A Song has extra fields such as Artist (person(s) singing the song) and composer 
 */
public class Song extends AudioContent implements Comparable<Song>
{
	public static final String TYPENAME =	"SONG";
	
	public static enum Genre {POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL}; 
	private String artist; 		
	private String composer; 	
	private Genre  genre; 
	private String lyrics;
	
	
	
	public Song(String title, int year, String id, String type, String audioFile, int length, String artist,
			String composer, Song.Genre genre, String lyrics)
	{
		super(title, year, id, type, audioFile, length); // Making use of the constructor in the super class AudioContent. 
		this.artist = artist;
		this.composer = composer;
		this.genre = genre;
		this.lyrics = lyrics;
		// Initialize additional Song instance variables. 
	}
	
	public String getType()
	{
		return TYPENAME;
	}
	
	// Printing information about the song. First print the basic information of the AudioContent 
	// by making use of the printInfo() method in superclass AudioContent and then print artist, composer, genre 
	public void printInfo()
	{
		super.printInfo();
		System.out.println("Artist: "+ this.artist +" Composer: "+ this.composer+" Genre: "+this.genre); //prints generic info than specifics of song
	}
	
	// Playing the song by setting the audioFile to the lyrics string and then calling the play() method of the superclass
	public void play()
	{
		this.setAudioFile(lyrics); // setting audiofile to the lyrics that are given 
		super.play();

	}
	
	public String getComposer()
	{
		return composer;
	}
	public void setComposer(String composer)
	{
		this.composer = composer;
	}
	
	public String getArtist()
	{
		return artist;
	}
	public void setArtist(String artist)
	{
		this.artist = artist;
	}
	
	public String getLyrics()
	{
		return lyrics;
	}
	public void setLyrics(String lyrics)
	{
		this.lyrics = lyrics;
	}

	public Genre getGenre()
	{
		return genre;
	}

	public void setGenre(Genre genre)
	{
		this.genre = genre;
	}	
	
	
	public boolean equals(Object other)
	{
		Song oth = (Song) other; // casting song object uding other
		if(super.equals(oth) && this.artist.equals(oth.artist) && this.composer.equals(oth.composer)) return true; 
		return false; 
	}
	
	
	public int compareTo(Song other)
	{
		return this.getTitle().compareTo(other.getTitle());
	}
}
