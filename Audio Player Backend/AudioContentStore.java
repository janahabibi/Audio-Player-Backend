import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
// Jana Habibi
// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		private Map<String, Integer> titleMap;
   		private Map<String, ArrayList<Integer>> artistMap;
    	private Map<Song.Genre, ArrayList<Integer>> genreMap;
		
		public AudioContentStore()
		{
			contents = new ArrayList<AudioContent>();
			try{
			contents.addAll(readFile());
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
			//maps
			// map for title 
			titleMap = new HashMap<String, Integer>();
		
			for(int i = 0; i < contents.size(); i++){
				titleMap.put(contents.get(i).getTitle(), i+1);
			}
			//map for artist
			artistMap = new HashMap<String, ArrayList<Integer>>();
			for(int i =0; i < contents.size(); i++){
				if(contents.get(i).getType().equals("SONG")){
					Song x = (Song)contents.get(i);
					if(artistMap.containsKey(x.getArtist())){
						artistMap.get(x.getArtist()).add(i+1);	
					}
					else{
						ArrayList<Integer> idex = new ArrayList<Integer>();						
						idex.add(i+1);
						artistMap.put(x.getArtist(),idex);
					}

				}
					
				
				if(contents.get(i).getType().equals("AUDIOBOOK")){
					AudioBook book = (AudioBook)contents.get(i);
					if(artistMap.containsKey(book.getAuthor())){
						artistMap.get(book.getAuthor()).add(i+1);	
					}
					else {
						ArrayList<Integer> idex = new ArrayList<Integer>();						
						idex.add(i+1);
						artistMap.put(book.getAuthor(),idex);
					}

				}
			}
			//map for genre
			genreMap = new HashMap<Song.Genre, ArrayList<Integer>>();							
			for(int i=0;i<contents.size();i++){
				ArrayList<Integer> number = new ArrayList<Integer>();
				if(contents.get(i).getType().equalsIgnoreCase("SONG")){
					Song song = (Song)contents.get(i);
				for(Song.Genre x: Song.Genre.values()){
					if(song.getGenre() == x){
						if(genreMap.containsKey(x)){
							number = genreMap.get(x);
							number.add(i);
						}else{
							number.add(i);
							genreMap.put(x, number);
						}
					}
				}
			}
		}	

		}

		
		// get the content from store.txt
		private ArrayList<AudioContent> readFile() throws IOException 
		{
			ArrayList<AudioContent> contents = new ArrayList<AudioContent>();
			File file = new File("store.txt");
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
			// the different lines speficied in the store file, using a scanner to rechieve it
				String type = scan.nextLine();
				String id = scan.nextLine();
				String title = scan.nextLine();
				int year = scan.nextInt();
				int length = scan.nextInt();
				scan.nextLine();
				String artistAuthor = scan.nextLine();
				String composerNarrator = scan.nextLine();
	
				if (type.equals("SONG")) 											// if the type is a song
				{
		
					String genre = scan.nextLine();
					int numLyrics = scan.nextInt();							//gets the number of lyrics
					scan.nextLine();
					String lyrics = "";
					
				for(int i = 0; i < numLyrics; i++)
				{
					lyrics += scan.nextLine() + "\n";
				}
				System.out.println("Loading SONG");
				contents.add(new Song(title, year, id, type, lyrics, length , artistAuthor, composerNarrator, Song.Genre.valueOf(genre), lyrics));			//add to contents arraylist
				}	
				
				if( type.equals("AUDIOBOOK"))						//if the type is audiobook 
				{
					
					int numChapter = scan.nextInt();
					scan.nextLine();
					String audioFile = "";

					ArrayList<String> chapterTitle =  new ArrayList<String>();				//creating an arraylist to hold the chapter titles
					ArrayList<String> chapter = new ArrayList<String>();					//creating an arraylist to hold the actual chapter
					for(int i = 0; i < numChapter; i++)
					{
						String x = scan.nextLine();
						chapterTitle.add(x);
					}
					for(int i =0; i < numChapter; i++)			
					{
						int numLines = scan.nextInt();
						scan.nextLine();
						for(int j = 0; j < numLines; j++)
						{
							chapter.add(scan.nextLine() + "\n");
						}
					}
					System.out.println("Loading AUDIOBOOK");

					contents.add(new AudioBook(title, year, id, type, audioFile, length, artistAuthor, composerNarrator, chapterTitle, chapter));			//add to contents arraylist
					
				}

			}
		scan.close();		//close the scanner
		return contents;
		
		}
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		//get content based on content name
		public void getContent(String title) 
		{
			if(titleMap.get(title)==null){
				throw new AudioContentException("No matches for " + title);			//exception if title is not found
			}
			System.out.print(titleMap.get(title) + ". ");
			contents.get(titleMap.get(title)-1).printInfo(); 
		}

		//get content based on artist name
		public void getContentA(String artistAuthor)
		 {  
			if(artistMap.get(artistAuthor)==null){
				throw new AudioContentException("No matches for " + artistAuthor);				//exception if artist/author is not found
			}
			for(int i = 0; i< artistMap.get(artistAuthor).size();i++){
				System.out.print(artistMap.get(artistAuthor).get(i) + ". ");
				contents.get(artistMap.get(artistAuthor).get(i)-1).printInfo();
				System.out.println();
			}
		}

		 //get content based on genre
		public void getContentG(String genre) 
		{
			if(genreMap.get(genre)==null){
				throw new AudioContentException("No matches for " + genre);						//exception if genre is not found
			}
			
			for(int i = 0; i < genreMap.get(genre).size();i++){
				System.out.print(genreMap.get(genre).get(i) + ". ");
				contents.get(genreMap.get(genre).get(i)-1).printInfo();
				System.out.println();
			}
		}	
		
		
	
	public ArrayList<Integer> searchP(String x){
		ArrayList<Integer> indexMatch = new ArrayList<Integer>();
		//takes a string x as input and returns an ArrayList of integers representing the indexes of the elements in an ArrayList called contents that contain the string x
//loops through each element of contents and checks if it is of type SONG or AUDIOBOOK
	for(int i=0 ; i<contents.size();i++){
			if(contents.get(i).getType().equalsIgnoreCase("SONG")){
				Song song = (Song)contents.get(i);
				// checks if the string x is found in any of the attributes of the Song object
				if(song.getArtist().contains(x) || song.getAudioFile().contains(x) || song.getTitle().contains(x) || song.getComposer().contains(x)){
					indexMatch.add(i);
				}
			}
			// checks if x is found in the audio file or author attributes
			if(contents.get(i).getType().equalsIgnoreCase("AUDIOBOOK")){
				AudioBook audioBook = (AudioBook)contents.get(i);

				if(audioBook.getAudioFile().contains(x) || audioBook.getAuthor().contains(x)){
					indexMatch.add(i);
				}else{
					for(int j=0 ; j < audioBook.getChapterTitles().size(); j++){
						if(audioBook.getChapterTitles().get(j).contains(x) || audioBook.getChapters().get(j).contains(x)){
							indexMatch.add(i);
						}
					}
				}
			}
		}
		//returns the indexMatch ArrayList that contains the indexes of all elements in contents that have a match with the string x
		return indexMatch;
	}
	// searching artist with map 
	public ArrayList<Integer> searchA(String artist){
		ArrayList<Integer> artists = new ArrayList<>();
		if(artistMap.containsKey(artist)){
			for(String key : artistMap.keySet()){
				if(key.equals(artist)){
					artists = artistMap.get(key);
				}
			}
		} else
		{
			throw new AudioContentException("No matches for "+ artist);
		}
		return artists;
	}
	//searching genre with map
	public ArrayList<Integer> searchG(Song.Genre genre){
		ArrayList<Integer> songsGenre = new ArrayList<>();
		if(genreMap.containsKey(genre)){
			for(Song.Genre key : genreMap.keySet()){
				if(key == genre){
					songsGenre = genreMap.get(key);
				}
			}
		}
		else
		{
			throw new AudioContentException("No matches for "+ genre);
		}
		return songsGenre;
	}
	// getting artist content
	public ArrayList<Integer> getArtistCont(String artist)
	{
		ArrayList<Integer> content = new ArrayList<Integer>();
		if(artistMap.get(artist) == null)
		{
			throw new AudioContentException("No matches for " + artist);
		}
		for(int i = 0; i < artistMap.get(artist).size(); i++)
		{
			content.add(artistMap.get(artist).get(i));
		}
		return content;
	}
	
	
	
	private ArrayList<String> makeHPChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("The Riddle House");
			titles.add("The Scar");
			titles.add("The Invitation");
			titles.add("Back to The Burrow");
			return titles;
		}
		
		private ArrayList<String> makeHPChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("In which we learn of the mysterious murders\r\n"
					+ " in the Riddle House fifty years ago, \r\n"
					+ "how Frank Bryce was accused but released for lack of evidence, \r\n"
					+ "and how the Riddle House fell into disrepair. ");
			chapters.add("In which Harry awakens from a bad dream, \r\n"
					+ "his scar burning, we recap Harry's previous adventures, \r\n"
					+ "and he writes a letter to his godfather.");
			chapters.add("In which Dudley and the rest of the Dursleys are on a diet,\r\n"
					+ " and the Dursleys get letter from Mrs. Weasley inviting Harry to stay\r\n"
					+ " with her family and attend the World Quidditch Cup finals.");
			chapters.add("In which Harry awaits the arrival of the Weasleys, \r\n"
					+ "who come by Floo Powder and get trapped in the blocked-off fireplace\r\n"
					+ ", blast it open, send Fred and George after Harry's trunk,\r\n"
					+ " then Floo back to the Burrow. Just as Harry is about to leave, \r\n"
					+ "Dudley eats a magical toffee dropped by Fred and grows a huge purple tongue. ");
			return chapters;
		}
		
		private ArrayList<String> makeMDChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Loomings.");
			titles.add("The Carpet-Bag.");
			titles.add("The Spouter-Inn.");
			return titles;
		}
		private ArrayList<String> makeMDChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("Call me Ishmael. Some years ago never mind how long precisely having little\r\n"
					+ " or no money in my purse, and nothing particular to interest me on shore,\r\n"
					+ " I thought I would sail about a little and see the watery part of the world.");
			chapters.add("stuffed a shirt or two into my old carpet-bag, tucked it under my arm, \r\n"
					+ "and started for Cape Horn and the Pacific. Quitting the good city of old Manhatto, \r\n"
					+ "I duly arrived in New Bedford. It was a Saturday night in December.");
			chapters.add("Entering that gable-ended Spouter-Inn, you found yourself in a wide, \r\n"
					+ "low, straggling entry with old-fashioned wainscots, \r\n"
					+ "reminding one of the bulwarks of some condemned old craft.");
			return chapters;
		}
		
		private ArrayList<String> makeSHChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Prologue");
			titles.add("Chapter 1");
			titles.add("Chapter 2");
			titles.add("Chapter 3");
			return titles;
		}
		
		private ArrayList<String> makeSHChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("The gale tore at him and he felt its bite deep within\r\n"
					+ "and he knew that if they did not make landfall in three days they would all be dead");
			chapters.add("Blackthorne was suddenly awake. For a moment he thought he was dreaming\r\n"
					+ "because he was ashore and the room unbelieveable");
			chapters.add("The daimyo, Kasigi Yabu, Lord of Izu, wants to know who you are,\r\n"
					+ "where you come from, how ou got here, and what acts of piracy you have committed.");
			chapters.add("Yabu lay in the hot bath, more content, more confident than he had ever been in his life.");
			return chapters;
		}
		
		
	}
