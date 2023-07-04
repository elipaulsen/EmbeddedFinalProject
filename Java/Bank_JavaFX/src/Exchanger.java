import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * class that launches the javaFX GUI
 */
public class Exchanger extends Application {
    /**
     * start loads the fxml file and shows the display
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("USD/SWD Exchanger");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * starts the program
     */
    public static void main(String[] args) {
        launch(args);
    }
}
