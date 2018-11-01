package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController {


    public void pressStartGame(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Snake Vs Block");

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }
}
