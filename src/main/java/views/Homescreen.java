package views;

import controller.Controller;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * @author Wan Hazmi WAN MOHD NOR
 * on 11/7/2019
 * @project SentimentAnalysis
 */
public class Homescreen {
    private Controller myController;

    public void setController(Controller myController) {
        this.myController = myController;
    }

    @FXML
    TextField screennameByUser;

    @FXML
    TextField keywordTweet;

    @FXML
    private ListView TweetsList;

    @FXML
    private TextArea TweetInfo;

    public static Homescreen createAndShow(Controller controller, Stage primaryStage) {
        URL location = Homescreen.class.getResource("/views/Homescreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root =null;
        try{
            root= (Parent) fxmlLoader.load();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        Homescreen view = fxmlLoader.getController();
        //Stage primaryStage = new Stage();
        primaryStage.setTitle("Tweet Extract - Home");
        primaryStage.setScene(new Scene(root,1500,600));
        primaryStage.show();
        view.setController(controller);

        return view;
    }

    /*
     * -------------------------------------------------------------
     * (1) Local Functions
     * -------------------------------------------------------------
     */

    /*
     * Function to search the sentiment of a tweet
     */
    public String searchSentiment(String tweet){
        String sentiment=null;

        String[] sentimentText = {" Very Negative", "Negative", "Neutral", "Positive", "Very Positive"};

        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation annotation = new Annotation(tweet);
        pipeline.annotate(annotation);
        for (CoreMap sentence : annotation.get( CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int score = RNNCoreAnnotations.getPredictedClass(tree);
            //System.out.println();
            sentiment = tweet + " : "+ sentimentText[score] + score;
        }

        return sentiment;
    }

    /*
     * Function to retrieve sentiment (on Hold)
     */

    /*
     * -------------------------------------------------------------
     * End (1)
     * -------------------------------------------------------------
     */

    /*
     * -------------------------------------------------------------
     * (2) ActionEvent
     * -------------------------------------------------------------
     */

    /*
     * Upon clicking #SearchTimeline button
     */
    public void searchTimeline(ActionEvent event) {
        this.TweetsList.setItems(myController.getUserTimeline(screennameByUser.getText()));

        //Sentiment upon clicking item
        TweetsList.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        // change the label text value to the newly selected item.
                        TweetInfo.setText(searchSentiment(newValue));
                        TweetInfo.setWrapText(true);
                    }
                });
    }

    /*
     * Upon clicking #MentionButton button
     */
    public void searchMentions(ActionEvent event) {
        this.TweetsList.setItems(myController.getAllMentions(screennameByUser.getText()));

        //Sentiment upon clicking item
        TweetsList.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        // change the label text value to the newly selected item.
                        TweetInfo.setText(searchSentiment(newValue));
                        TweetInfo.setWrapText(true);
                    }
                });
    }

    /*
     * Upon clicking #RepliesButton button
     */

    /*
     * Upon clicking #KeywordButton button
     */
    public void searchByKeyword(ActionEvent event) {
        this.TweetsList.setItems(myController.getByKeyword(keywordTweet.getText()));
        //Sentiment upon clicking item
        TweetsList.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        // change the label text value to the newly selected item.
                        TweetInfo.setText(searchSentiment(newValue));
                    }
                });
    }

    /*
     * Upon clicking #RepliesButton button
     */
    public void saveCurrentList(ActionEvent event) {
        // Saving
        directoryChooser();
        try (
                FileOutputStream fos = new FileOutputStream("testSA.csv");
                PrintWriter writer = new PrintWriter(fos);
        ) {
            for (int i = 0; i < TweetsList.getItems().size() - 1; i++) {
                writer.printf("%s\n", TweetsList.getItems().get(i));
            }
        } catch (IOException ioe) {
            System.out.printf("Problem saving: %s/n", "testSA.csv");
            ioe.printStackTrace();
        }
    }


    public void directoryChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose location To Save Report");
        File selectedFile = null;
        while(selectedFile== null){
            selectedFile = chooser.showSaveDialog(null);
        }

        File file = new File(String.valueOf(selectedFile));
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(int i = 0; i<TweetsList.getItems().size(); i++){
            outFile.println(TweetsList.getItems().get(i).toString());
        }
        outFile.close();
    }



    /*
     * -------------------------------------------------------------
     * End (2)
     * -------------------------------------------------------------
     */
}
