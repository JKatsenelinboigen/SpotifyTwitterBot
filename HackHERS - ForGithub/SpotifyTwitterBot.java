import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class SpotifyTwitterBot {

    private static final int MAX_CHARACTERS = 280;// twitter charactert limit is 280

    // if the string is longer than 280 characters we break it up into multiple
    // tweets
    public static void generateTweetsFromString(String longTweet) {
        
        int maxTweets = 10;
        String currentTweet = "";
       

        int cLine = 0;
       
        // if tweet is 280 characters or less, send it out
        if (longTweet.length() <= MAX_CHARACTERS)
            sendTweet(longTweet);
        else {
            //split tweet into individual lines
            String[] lines = longTweet.split("\n");
            cLine = 0;

            //for every tweet, fill it up to the max number of characters while maintaining lines, once character limit is reached go onto the next tweet
            for (int i = 0; i < maxTweets; i++) {

                while ((currentTweet + "\n" + lines[cLine]).length() <= MAX_CHARACTERS) {
                    currentTweet += "\n" + lines[cLine];
                    cLine++;
                    if(cLine >= lines.length) break;
                }
                //sends the tweet and clears the string for the next one in case of multiple tweets 
                sendTweet(currentTweet);
                currentTweet = "";
                if(cLine >= lines.length) break;
            }


        }

    }

    //uses twitter4j API to send out tweet to account thats is authenticated by the configuration builder
    private static void sendTweet(String tweet) {

        ConfigurationBuilder cb = getCB();

        // create twitter objects
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        // post a tweet
        try {
            Status status = twitter.updateStatus(tweet);
            System.out.println("Successfully updated the status to [" + status.getText() + "].");
        } catch (Exception e) {
        }

    }

    // load twitter4j with the secret tokens from the twitter api
    // OBFUSCATE THIS BEFORE UPLOADING TO GITHUB JESUS CHRIST
    private static ConfigurationBuilder getCB() {

        // get these values from the twitter developer portal
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("***************************")
                .setOAuthConsumerSecret("***************************")
                .setOAuthAccessToken("***************************-***************************")
                .setOAuthAccessTokenSecret("***************************");

        return cb;
    }

}
