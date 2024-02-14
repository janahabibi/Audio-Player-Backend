import java.util.ArrayList;

/* Jana Habibi
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks, Podcasts) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	/*
	 * Printing the information of each audio content object (song, audiobook, podcast)
	 */
	public void printContents()
	{
		for(int i=0;i<contents.size();i++){			//this loops through contents then prints out indexes followed by content's info
			int index = i + 1;
		 	System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	// Playing all the AudioContent in the contents list
	public void playAll()
	{
		for (int i = 0; i < contents.size(); i++) {
			contents.get(i).play();
			System.out.println();
		}

	}
	
	// Playing the specific AudioContent from the contents array list.

	public void play(int index)
	{
		System.out.println();
		contents.get(index -1).play();
	}
	
	public boolean contains(int index)
	{
		return index >= 1 && index <= contents.size();
	}
	
	
	public boolean equals(Object other)
	{
		Playlist oth = (Playlist) other; //casting
		if(this.title.equals(oth.title)) return true; 
		return false; 

	}
	
	public void deleteContent(int index)
	{
		if(!contains(index)) return;
		contents.remove(index-1);
	
	
	}
}
