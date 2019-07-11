import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Wan Hazmi WAN MOHD NOR
 * on 11/7/2019
 * @project SentimentAnalysis
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller myController = new Controller(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
