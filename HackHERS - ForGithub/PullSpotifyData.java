import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;


public class PullSpotifyData 
{
    
    private static final int TOP_N_SONGS = 5;

    //returns a formatted tweet containing the top N songs on the spotify daily charts
    public static String getTweetAsString()
    {

        Song[] list = convertToSong(csvToStringArray(TOP_N_SONGS), TOP_N_SONGS);

        String tweet = "";
        
        for(int i = 0; i < TOP_N_SONGS; i++)
        {         
                 tweet = tweet + list[i] + "\n";
        }

        return tweet;
    }



    public static String[] csvToStringArray(int topNSongs) 
    {   
        String[] fileString = null;
        try{
        URL url = new URL("https://spotifycharts.com/regional/global/daily/latest/download");
        BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
        
        fileString = new String[topNSongs];
        String s;
        int index = 0;
            while ((s = read.readLine()) != null && index < topNSongs + 2)
            {
                if (index > 1)
                {
                    fileString[index - 2] = s;
                   
                }
                index++;
            }
            read.close();
        } 
        catch(Exception e)
        {
                System.out.println("Error");
        }
        return fileString;
    }
    public static Song[] convertToSong(String[] fileString, int topNSongs)
    {
        Song[] songList = new Song[topNSongs];

        String[] cLine = new String[5]; //5 attributes per song
        
        for (int i = 0; i < topNSongs; i++)
        {
            songList[i] = new Song(); //create new song

            //split csv file into 5 values
            cLine = splitLineIntoCSV(fileString[i]); 
            //instantiate song values      
            songList[i].setPosition(Integer.parseInt(cLine[0]));
            songList[i].setSongName(cLine[1].substring(0, cLine[1].length()));
            songList[i].setArtist(cLine[2].substring(0, cLine[2].length()));
            songList[i].setStreams(Integer.parseInt(cLine[3]));
            songList[i].setSpotifyLink(cLine[4]);
        } 
        return songList;
    }

    public static String[] splitLineIntoCSV(String line)//needs a custom split function to account for comma values within a field
    {
        //position, name, artist, streams, link
       String[] values = new String[5];
        int index = line.indexOf(",");
        values[0] = line.substring(0, (index));
       // System.out.println(values[0]);
        if (line.charAt(index + 1) == '\"') //name has a quote
        {
            values[1] = line.substring(index + 2, line.indexOf('\"', index + 3)); //substring from beginning of name to before the quote
            index = line.indexOf('\"', index + 2)+1; //sets new index to the index of the comma
        }
        else //name has no quote- read until comma
        {
            values[1] = line.substring(index + 1, line.indexOf(',', index + 2));
            index = line.indexOf(',', index + 1);
        }
        //System.out.println(values[1]);
         if (line.charAt(index + 1) == '\"') //artist has a quote
        {
            values[2] = line.substring(index + 2, line.indexOf('\"', index + 3)); //substring from beginning of artist to before the quote
            index = line.indexOf('\"', index + 2)+1; //sets new index to the index of the comma
        }
        else //artist has no quote- read until comma
        {
            values[2] = line.substring(index + 1, line.indexOf(',', index + 2));
            index = line.indexOf(',', index + 1);
        }
        //System.out.println(values[2]);
        values[3] = line.substring(index + 1, line.indexOf(',', index + 2));
        //System.out.println(values[3]);
        index = line.indexOf(',', index + 2);
        values[4] = line.substring(index + 1);
        //System.out.println(values[4]);
        
        return values;
    }

}


class Song{
    private int position;
    private String songName;
    private String artist;
    private int streams;
    private String spotifyLink;

    public int getPosition() {
    	return this.position;
    }
    public void setPosition(int position) {
    	this.position = position;
    }

    public String getSongName() {
    	return this.songName;
    }
    public void setSongName(String songName) {
    	this.songName = songName;
    }

    public String getArtist() {
    	return this.artist;
    }
    public void setArtist(String artist) {
    	this.artist = artist;
    }

    public int getStreams() {
    	return this.streams;
    }
    public void setStreams(int streams) {
    	this.streams = streams;
    }


    public String getSpotifyLink() {
    	return this.spotifyLink;
    }
    public void setSpotifyLink(String spotifyLink) {
    	this.spotifyLink = spotifyLink;
    }

    public Song(){
        this.songName = "Default Song Title";
        this.artist = "Default Artist";
        this.streams = -1;
        this.spotifyLink = "spotify.com";
    }
    public Song(int position, String songName, String artist, int streams, String spotifyLink){

        this.position = position;
        this.songName = songName;
        this.artist = artist;
        this.streams = streams;
        this.spotifyLink = spotifyLink;
    }

    public String toString(){
        return (getPosition() + ". \"" + getSongName()+ "\" by " + getArtist() + 
        " has "+ new DecimalFormat("#,###").format(getStreams()) + " streams ").trim();// + "\nSpotify Link: " + getSpotifyLink();
    }
}
