// Jana Habibi

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;


// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		
		// Creating my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Processing keyboard actions
		while (scanner.hasNextLine())
		{ try {
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Downloading audiocontent (song/audiobook/podcast) from the store 
			
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int fromIndex = 0;
				int toIndex = 0;

				System.out.print("From store Content #: ");
				if (scanner.hasNextInt())
				{
					fromIndex = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt())
				{
					toIndex = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
			
				int range = toIndex-fromIndex;
				for(int i = 0; i <= range; i++){
				try {
						AudioContent content = store.getContent(fromIndex+i);
						mylibrary.download(content);
					
					
				} catch (AudioContentExistsException e) {
					
					System.out.println(e.getMessage());
				}
				catch (AudioContentException e)	{
					System.out.println(e.getMessage());
				}
			}
									
		}
		
		else if (action.equalsIgnoreCase("DOWNLOADA")) 				
		{
			//prompts the user to enter the name of an artist using the Scanner object
			System.out.println("Artist: ");
			String artist = scanner.nextLine();
			ArrayList<Integer> index = store.getArtistCont(artist);			// calls the getArtistCont() method of the AudioContentStore object called store, passing in the artist variable as an argument
			//loops through each index in the index ArrayList using a for loop
			for(int i = 0; i < index.size(); i++){
				try {
					AudioContent x = store.getContent(index.get(i));	//gets the audio content object located at the current index using the getContent() method of the store object
					mylibrary.download((x));							//calls the download() method of the mylibrary object, passing in the x object as an argument.
				}
				catch (Exception e){
					System.out.println(e.getMessage());						//If an exception occurs during the execution of the try block, the program catches it and prints the error message to the console
				}	
			}
		}

		else if (action.equalsIgnoreCase("DOWNLOADG")) {
		//prompts the user to enter the name of a genre using the Scanner object
			System.out.print("Genre: ");
					String genre = scanner.nextLine();

					ArrayList<Integer> list = store.searchG(Song.Genre.valueOf(genre));				/// calls the searchG() method of the AudioContentStore object called store, passing in the Genre value corresponding to the genre entered by the user as an argument
					//loops through each index in the list ArrayList using an enhanced for loop		
					for(int i : list){
						AudioContent content = store.getContent(i+1);					//gets the audio content object located at the current index plus one using the getContent() method of the store object
						try{															// audio content object is then stored in a variable called content
							 mylibrary.downloadArtistGenre(content);							//downloadArtistGenre() method of the mylibrary object, passing in the content object as an argument
						}catch(RuntimeException exception){ //exception
							System.out.println(exception.getMessage());				//If an exception occurs during the execution of the try block, the program catches it and prints the error message to the console
						}
					}
		}

		else if (action.equalsIgnoreCase("SEARCH")){ //search store for audio content with specified title, prints index and info
			Scanner scan  = new Scanner(System.in);
			System.out.print("Title: ");
			String title = scan.nextLine();
			try {
				store.getContent(title);							//calls the getContent() method of the AudioContentStore object called store, passing in the title variable as an argument
																		//program will print information about the content, including its index and other details
			} catch (AudioContentException e) { //exception
				// TODO: handle exception
				System.out.println(e.getMessage());										//if an exception occurs during the execution of the getContent() method, the program catches it and prints the error message to the console
			}
		}
			//similar to search
		else if (action.equalsIgnoreCase("SEARCHA")){ //search store for audio content with specified artist name, prints index/indices and info 
			Scanner scan =new Scanner(System.in);
			System.out.print("Artist: ");
			String artist = scan.nextLine();
			try {
				store.getContentA(artist);

			} catch (Exception e) { //exception
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}
	
		else if (action.equalsIgnoreCase("SEARCHG")){ //search store for audio content with specified genre , prints index/indices and info 
			{
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				String genre = scanner.nextLine();		
				ArrayList<Integer> index = store.searchG(Song.Genre.valueOf(genre));	//program calls the searchG() method of the AudioContentStore object called store, passing in the Song.Genre enumeration value corresponding to the specified genre name

				for(int i=0;i<index.size();i++){
					System.out.print(index.get(i)+1 + ". ");
					store.getContent(index.get(i)+1).printInfo();
					System.out.println("\n");
				}
		}
	}
	else if(action.equalsIgnoreCase("SEARCHP")){ //bonus search 

		System.out.print("Search for: ");
		String p = scanner.nextLine();

		ArrayList<Integer> index = store.searchP(p);			//this is creating a new ArrayList for index's

		for(int i=0;i<index.size();i++){
			System.out.print(index.get(i)+1 + ". ");
			store.getContent(index.get(i)+1).printInfo();
			System.out.println("\n");
		}
	}
			// Getting the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
		
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				System.out.print("Song Number: ");
				int index = scanner.nextInt();
				mylibrary.playSong(index);
				scanner.nextLine(); // gets rid of the extra >
			}
			
			// Printing the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				System.out.print("Audio Book Number: ");
				int index = 0;

				if(scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.printAudioBookTOC(index);
			}
			
			// Similar to playsong above except for audio book
			// In addition to the book index, reads the chapter 
			// number from the keyboard 
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				
				int bookNumber = scanner.nextInt();			
				int chapter = scanner.nextInt();
				
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					bookNumber = scanner.nextInt();
					scanner.nextLine();

					
				}
				System.out.print("Chapter: ");
				if(scanner.hasNextInt())
				{
					chapter = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.playAudioBook(bookNumber, chapter);
			}
	
			
			// Specifies a playlist title (string) 
			// Playing all the audio content (songs, audiobooks, podcasts) of the playlist 
		
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = "";

				if(scanner.hasNext())
				{
					name = scanner.nextLine();

				}
				mylibrary.playPlaylist(action);
				
				
				
			}
			// Specifies a playlist title (string) 
			// Reading the index of a song/audiobook/podcast in the playist from the keyboard 
			// Playing all the audio content 
	
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				
				
				System.out.print("Playlist Title: ");
				String name = "";									//this will get title from user
				if(scanner.hasNext()){
					name=scanner.nextLine();
				}

				System.out.print("Content Number: ");
				int content_number = 0;
				if(scanner.hasNextInt()){							//this gets index from user
					content_number = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.playPlaylist(name, content_number);		//this plays a specific content in playlist given title and index
			}
		
			// Deleting a song from the list of songs in mylibrary and any play lists it belongs to
			// Reading a song index from the keyboard
			
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				System.out.print("Library Song #: ");				
				int index = scanner.nextInt();
				scanner.nextLine();
				
		
				mylibrary.deleteSong(index);
				
			}
			// Reading a title string from the keyboard and make a playlist
		
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				System.out.print("Playlist Title: ");		//ask for playlist title
				String name = scanner.nextLine();
				
				
				mylibrary.makePlaylist(name);

					//runs makePlaylist, if it returns false print error msg
				
			}
			// Printing the content information (songs, audiobooks, podcasts) in the playlist
			// Reading a playlist title string from the keyboard
	
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				System.out.print("Playlist Title: ");						
				String title = "";
				if(scanner.hasNextLine()){
					title = scanner.nextLine();
				}
				mylibrary.printPlaylist(title);
			}
			// Adding content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Reading the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
	
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				System.out.print("Playlist Title: ");								//this will get the playlist title
				String title = "";
				if(scanner.hasNext()){
					title = scanner.nextLine();
				}
				System.out.println();
				String type = "";
				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");	//this will get the type of content
				if(scanner.hasNext()){
					type = scanner.nextLine();
				}
				System.out.println();
				int index = 0;
				System.out.print("Library Content #: ");							//this will get the index of the content to be added
				if(scanner.hasNextInt()){	
					index = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.addContentToPlaylist(type, index, title);					
			
			}
				
			

			// Deleting content from play list based on index from the playlist
			// Reading the playlist title string and the playlist index

			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine();
				System.out.print("Playlist Content #: ");
				int index = scanner.nextInt();
				scanner.nextLine();
				mylibrary.delContentFromPlaylist(index, name);
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}
		}
		
		catch (AudioContentException e){			
			System.out.println(e.getMessage());

		}
		 catch (DoesNotExistException e) {
			System.out.println(e.getMessage());
		 }

		 catch (InvalidIndexException e){			
			System.out.println(e.getMessage());

		}
		catch (PlaylistAlreadyExistsException  e){			
			System.out.println(e.getMessage());

		}
		 System.out.print("\n>");

		}
	}
}
