import java.util.ArrayList;

/* Jana Habibi
 * An AudioBook is a type of AudioContent.
 * It is a recording made available on the internet of a book being read aloud by a narrator
 * 
 */
public class AudioBook extends AudioContent
{
	public static final String TYPENAME =	"AUDIOBOOK";
	
	private String author; 
	private String narrator;
	private ArrayList<String> chapterTitles;
	private ArrayList<String> chapters;
	private int currentChapter = 0;

	
	public AudioBook(String title, int year, String id, String type, String audioFile, int length,
									String author, String narrator, ArrayList<String> chapterTitles, ArrayList<String> chapters)
	{
		
		super(title, year, id, type, audioFile, length);
		this.author = author;
		this.narrator = narrator; 
		this.chapterTitles = chapterTitles;
		this.chapters = chapters;
		// Making use of the constructor in the super class AudioContent. 
	
	}
	
	public String getType()
	{
		return TYPENAME;
	}

  // Printing information about the audiobook. 
	public void printInfo()
	{
		super.printInfo(); // superclass print info
		System.out.println("Author: " + this.author + " Narrated by: " + this.narrator);
		
	}
	
  // Playing the audiobook by setting the audioFile to the current chapter title (from chapterTitles array list) 
	// followed by the current chapter (from chapters array list)
	
	public void play()
	{
		setAudioFile(chapterTitles.get(currentChapter) + ".\n"  + chapters.get(currentChapter));
		super.play(); // super class play from the audio content class
	}
	
	// Printing the table of contents of the book - i.e. the list of chapter titles
	
	public void printTOC()
	{
		for(int i = 0; i < chapterTitles.size(); i++)
		{
			System.out.println("Chapter " +(i+1)+ ". " + chapterTitles.get(i) + "\n");
		}
	}

	// Selecting a specific chapter to play 
	public void selectChapter(int chapter)
	{
		if (chapter >= 1 && chapter <= chapters.size())
		{
			currentChapter = chapter - 1;
		}
	}
	

	public boolean equals(Object other)
	{
		AudioBook oth = (AudioBook) other; // casting
		if(super.equals(oth) && this.author.equals(oth.author) && this.narrator.equals(oth.narrator)) return true;
		return false;
	}
	
	public int getNumberOfChapters()
	{
		return chapters.size();
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getNarrator()
	{
		return narrator;
	}

	public void setNarrator(String narrator)
	{
		this.narrator = narrator;
	}

	public ArrayList<String> getChapterTitles()
	{
		return chapterTitles;
	}

	public void setChapterTitles(ArrayList<String> chapterTitles)
	{
		this.chapterTitles = chapterTitles;
	}

	public ArrayList<String> getChapters()
	{
		return chapters;
	}

	public void setChapters(ArrayList<String> chapters)
	{
		this.chapters = chapters;
	}

}
