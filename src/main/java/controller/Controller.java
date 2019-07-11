package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import views.Homescreen;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wan Hazmi WAN MOHD NOR
 * on 11/7/2019
 * @project SentimentAnalysis
 */
public class Controller {
    /*
     * CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN and ACCESS_TOKEN_SECRET can be obtained for free on Twitter API
     */
    private static final String CONSUMER_KEY = "CONSUMER_KEY here";
    private static final String CONSUMER_SECRET = "CONSUMER_SECRET here";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN here";
    private static final String ACCESS_TOKEN_SECRET = "ACCESS_TOKEN_SECRET here";

    private Stage stagePrincipal;


    private String screenname;
    private Twitter twitter;

    @FXML
    private ListView TimelineList;

    public Controller(Stage stage){
        this.stagePrincipal=stage;
        Homescreen.createAndShow(this, stage);
    }

    /*
     * -------------------------------------------------------------
     * (1) Local Functions
     * -------------------------------------------------------------
     */

    /*
     * Function to connect to Tweet
     */
    private static Twitter Connect() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());


        return tf.getInstance();
    }

    /*
     * -------------------------------------------------------------
     * End (1)
     * -------------------------------------------------------------
     */

    /*
     * -------------------------------------------------------------
     * (2) Functions used by views
     * -------------------------------------------------------------
     */

    /*
     * Function to show the timeline of the given tweethandle
     */
    public ObservableList<String> getUserTimeline(String screenname){
        List tweets = new ArrayList<>();
        try {
            List<Status> status = Connect().getUserTimeline("@"+screenname);
            for(Status st : status){
                tweets.add(st.getText());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return FXCollections.observableList(tweets);
    }

    /*
     * Function to show all mentions of the given tweethandle
     */
    public ObservableList<String> getAllMentions(String screenName){
        List mentionsByScreenName = new ArrayList<>();
        //Scanner sc = new Scanner(System.in);
        //System.out.print("Choose language (id for Malay/Indonesia , en for English : ");
        //String language = sc.nextLine();

        //System.out.println("\n------------"+screenName+"'s recent mentions in "+language+"------------\n");

        try {
            Query query = new Query("@"+ screenName
                    + " AND lang:en"
                    + " +exclude:retweets");
            QueryResult results;
            do {
                results = Connect().search(query);
                List<Status> tweets = results.getTweets();
                for (Status tweet : tweets){
                    mentionsByScreenName.add("@" + tweet.getUser().getScreenName() + " - " + tweet.getText() + " - ");
                }
            } while ((query = results.nextQuery()) != null);
            //System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
        }

        return FXCollections.observableList(mentionsByScreenName);
    }

    /*
     * Function to show all replies of the given tweethandle and tweet
     */
    public static ArrayList<Status> getReplies(String screenName, long tweetID, Twitter twitter) {
        ArrayList<Status> replies = new ArrayList<>();

        try {
            Query query = new Query("to:" + screenName + " since_id:" + tweetID);
            QueryResult results;
            do {
                results = twitter.search(query);
                List<Status> tweets = results.getTweets();

                for (Status tweet : tweets)
                    if (tweet.getInReplyToStatusId() == tweetID)
                        replies.add(tweet);
            } while ((query = results.nextQuery()) != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return replies;
    }

    public ObservableList getByKeyword(String keyword) {
        List<String> tweetsByKeywords = new ArrayList<>();

        try {
            Query query = new Query(keyword
                    + " +exclude:retweets");
            QueryResult results;
            do {
                results = Connect().search(query);
                List<Status> tweets = results.getTweets();
                for (Status tweet : tweets){
                    tweetsByKeywords.add("@" + tweet.getUser().getScreenName() + " - " + tweet.getText() + " - ");
                }
            } while ((query = results.nextQuery()) != null);
            //System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
        }

        return FXCollections.observableList(tweetsByKeywords);
    }

    /*
     * -------------------------------------------------------------
     * End (2)
     * -------------------------------------------------------------
     */


}
