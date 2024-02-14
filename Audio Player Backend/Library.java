import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* Jana Habibi
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	

	

	public Library()
	{
		songs 	= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	
	public void download(AudioContent content)						//becomes void 
	{
		if(content.getType().equals(Song.TYPENAME)){ //if the audiocontent is a song
			if(songs.contains((Song)content)){ //check if the song list already contains that song
				throw new AudioContentExistsException("Song" + " " + content.getTitle()+ " already downloaded"); //set error message
			}
			else{
				songs.add((Song)content); //if not in song list, add it to the list
				System.out.println(content.getType()+ " " + content.getTitle() + " Added to Library");
			}
		}
		if(content.getType().equals(AudioBook.TYPENAME)){ //if the audiocontent is an audiobook
			if(audiobooks.contains((AudioBook)content)){ //check if the audobook list already contains that audiobook
				throw new AudioContentExistsException("Audiobook" + " " + content.getTitle()+ " already downloaded");
			}
			else{
				audiobooks.add((AudioBook)content); //if not in audiobook list, add to the list
				System.out.println(content.getType()+ " " + content.getTitle() + " Added to Library");

			}
		}
		 
	}
	public void downloadArtistGenre(AudioContent content){
		if(content.getType() == Song.TYPENAME){ //check the type of given content
			for(Song s : songs){   //check if the song is already in the songs arraylist
				if(s.equals(content)){
					throw new AudioContentExistsException("Song "+content.getTitle()+" already downloaded");
				}
			}		
			songs.add((Song)content);  //adding the content to songs arraylist by typecasting it to Song and returning true
		}
			
		if(content.getType() == AudioBook.TYPENAME){ //check the type of given content
			for(AudioBook a : audiobooks){   //check if audiobook is already in the audiobooks arraylist  
				if(a.equals(content)){
					throw new AudioContentExistsException("Audiobook "+content.getTitle()+" already downloaded");  //if the audiobook is in the audiobooks arraylist then setting the errormsg and returning false
				}
			}
			audiobooks.add((AudioBook)content);  // adding the content to audiobooks arraylist by typecasting it to Audiobook and returning true
		}
	}	
	// Printing Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)				//loop through each song and print out their indexes starting at 1 followed by their info
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Printing Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for(int i =0; i<audiobooks.size();i++){						//loop through each audiobook and print out their indexes starting at 1. followed by their info
			int index = i+1;
			System.out.print(index+". ");
			audiobooks.get(i).printInfo();
			System.out.println();
		}
	}
	
  // Printing Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		
	}
	
  // Printing the name of all playlists in the playlists array list

	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
    	{
        int index = i + 1;
        System.out.println("" + index + ". " + playlists.get(i).getTitle());
		System.out.println();
    	}
	}
	
  // Printing the name of all artists. 
	public void listAllArtists()
	{
		
		ArrayList<String> allArtists = new ArrayList<String>();			//create new arraylist containing all the artists

		for(Song curr_song: songs){											//this loops  through each song and adds the artist to new arraylist if not in it yet
			if(!allArtists.contains(curr_song.getArtist())){
				allArtists.add(curr_song.getArtist());
			}
		}
		for(int i=0;i<allArtists.size();i++){								//prints the names with indexes
			System.out.println((i+1)+". "+allArtists.get(i));
		}
	}

	// Deleting a song from the library (i.e. the songs list) 
	
	public void deleteSong(int index)					//becomes void 
	{
		if(index < 0) {
			throw new InvalidIndexException("Invalid index");			//invalid index 
		}
		Song songToRemove = songs.get(index-1); // get the song to be removed
   
		if (songToRemove == null) {															// if song doesn't exist
        throw new DoesNotExistException("Song does not exist");
    }
	songs.remove(songs.get(index-1));												//this will remove a song from the songlist

	for(int i = 0; i < playlists.size(); i++) {
        Playlist currentPlaylist = playlists.get(i);
        if	(currentPlaylist.getContent().contains(songToRemove)){
			currentPlaylist.getContent().remove(songToRemove);
		}
	}														
}	
	
 /* 	for(int i =0;i<playlists.size();i++)
		{
			for(int j=0;j<playlists.get(i).getContent().size();j++)						//this will loop through playlist then loop through the contents of each playlist
			{
				if(playlists.get(i).getContent().get(j).equals(songs.get(index-1)))		//checks if a song equals the song being removed, then remove the song from playlists
				{
					playlists.get(i).getContent().remove(songs.get(index-1));
				} 
			}
		}
	}
	*/
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());								//sorts song list by year

	}
 
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song song1, Song song2) {
			if (song1.getYear() < song2.getYear()) return -1;									//comparator interface for sorting songs by year
			if (song1.getYear() > song2.getYear()) return  1;
			return 0;
		}
	}

	// Sorting songs by length
	public void sortSongsByLength()
	{
	
	 Collections.sort(songs, new SongLengthComparator());							//sorts song list by length

	}
  

	
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2) {
			if (s1.getLength() < s2.getLength()) return -1;								//comparator interface for sorting songs by length
			if (s1.getLength() > s2.getLength()) return  1;
			return 0;
		}
	}
	
	
	public void sortSongsByName()
	{
		Collections.sort(songs);														//sorts song by name

	}

	/*
	 * Playing Content
	 */
	
	// Plays song from songs list
	public void playSong(int index)					//becomes void 
	{
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentException("Song not found");
		}
		else
		{
		songs.get(index-1).play();
		}
		
	}
	
	// Plays a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)	//becomes void 
	{
		if(index < 1 || index > audiobooks.size())
		{
			throw new AudioContentException("Audiobook not found");							//audio content not found exception
		}
		else
		{
			if (chapter < 1 || chapter > audiobooks.get(index-1).getNumberOfChapters())
			{
				throw new AudioContentException("Audiobook not found");				//audio content not found exception
			}
			else
			{
				audiobooks.get(index-1).selectChapter(chapter);
				audiobooks.get(index-1).play();
			}
		}
		
	}
	
	// Printing the chapter titles (Table Of Contents) of an audiobook
	
	public void printAudioBookTOC(int index) //becomes void 
	{
		if (index < 1 || index > audiobooks.size())
		{
			throw new AudioContentException("AudioBook not found");				//audio content not found exception
		}
		audiobooks.get(index-1).printTOC();

	}
	
  /*
   * Playlist Related Methods
   */
	
	// Making a new playlist and add to playlists array list
	
	public void makePlaylist(String title)				//becomes void 
	{
		Playlist playlist = new Playlist(title);
		if(!playlists.contains(playlist))
		{
			playlists.add(playlist);
		}
		else
		{
			throw new PlaylistAlreadyExistsException("Playlist " + title + " Already Exists");			//playlist already exists exception
		}
	
	}
	
	// Printing list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)				//becomes void 
	{
		Playlist playlist = new Playlist(title);
		if(playlists.contains(playlist))
		{
			int index = playlists.indexOf(playlist);
			playlists.get(index).playAll();
		}
		else
		{
			throw new AudioContentException("Playlist not found"); //audio content not found exception
		}
	
	}
	
	// Plays all content in a playlist
	public void playPlaylist(String playlistTitle)					//becomes void 
	{
		Playlist playlist = new Playlist(playlistTitle);
		if(playlists.contains(playlist))
		{
			int index = playlists.indexOf(playlist);
			playlists.get(index).printContents();
		}
		else
		{
			throw new AudioContentException("Playlist not found"); //audio content not found exception
		}
			
	}
	
	// Plays a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)				//becomes void 
	{
		boolean playlistFound = false;												//added

		for(int i =0;i<playlists.size();i++){
			if(playlists.get(i).getTitle().equals(playlistTitle)){			//this will loop through the playlists to find one that matches the passed play list title 
				playlistFound = true;											//added
				System.out.println(playlistTitle);							//then it will print out the title and then play the content at the index given
				playlists.get(i).getContent().get(indexInPL-1).play();
				break;														//added
			}	
		}
		if(!playlistFound) {																	//added
			throw new AudioContentException("Playlist not found");		//audio content not found exception
		}
	}
	
	public void addContentToPlaylist(String type, int index, String playlistTitle)			//becomes void 
	{
		for(int i=0; i < playlists.size(); i++){
			
			if(playlists.get(i).getTitle().equals(playlistTitle)){				//this will loop through the playlists to find the playlist same as the passed pltitle
			
				if(type.equalsIgnoreCase(Song.TYPENAME)){						//this checks if the givevn type is a song, if it is one,  it will add it to playlists
					
					playlists.get(i).addContent(songs.get(index-1));
					
					System.out.println("Song Added");
				}
				else if(type.equalsIgnoreCase(AudioBook.TYPENAME)){				//this checks if type is an audiobook, if so add it to playlists
					
					playlists.get(i).addContent(audiobooks.get(index-1));
				
					System.out.println("AudioBook Added");
				}
				 else {
					throw new AudioContentException("Failed to add to playlist");		//audio content was failed to be added
				}
			}
			
		} 
	}

  // Deleting a song/audiobook/podcast from a playlist with the given title
	
	public void delContentFromPlaylist(int index, String title)							//becomes void 
	{
		Playlist playlist = new Playlist(title);
		if(playlists.contains(playlist))
		{
			int ind = playlists.indexOf(playlist);
			if (playlists.get(ind).contains(ind)) playlists.get(ind).deleteContent(index);
		 	else
			{
				throw new AudioContentException("Content not found");				//if content is not found
			}
		}
		else
		{
		throw new AudioContentException("Playlist not found");										//if the playlist is not found 
		}
		
	}
}
	//exception classes 
class AudioContentException extends RuntimeException
{
	public AudioContentException() {}
	public AudioContentException(String message)
        {
                 super(message);
         } 
}

class AudioContentExistsException extends RuntimeException
{
	public AudioContentExistsException() {}
	public AudioContentExistsException(String message)
        {
                 super(message);
         } 
}

class DoesNotExistException extends RuntimeException
{
	public DoesNotExistException() {} 
	public DoesNotExistException(String message)
        {
                 super(message);
         } 
}

class InvalidIndexException extends RuntimeException
{
	public InvalidIndexException() {}
  
	public InvalidIndexException(String message)
        {
                 super(message);
         } 
}

class PlaylistAlreadyExistsException extends RuntimeException
{
	public PlaylistAlreadyExistsException() {}

	public PlaylistAlreadyExistsException(String message)
	 {
	super(message);
 } 
}
