package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private Pane root;

    private Parent createMainMenuContent() {
        root = new Pane();
        root.setPrefSize(600, 900);

        Label label = new Label("Snake Vs Block");
        label.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).divide(2));
        label.setTranslateY(100);

        Button startBtn = new Button("Start Game");
        startBtn.layoutXProperty().bind(root.widthProperty().subtract(startBtn.widthProperty()).divide(2));
        startBtn.setTranslateY(300);

        Button resumeBtn = new Button("Resume Game");
        resumeBtn.layoutXProperty().bind(root.widthProperty().subtract(resumeBtn.widthProperty()).divide(2));
        resumeBtn.setTranslateY(500);

        Button leaderBoardBtn = new Button("Leader board");
        leaderBoardBtn.layoutXProperty().bind(root.widthProperty().subtract(leaderBoardBtn.widthProperty()).divide(2));
        leaderBoardBtn.setTranslateY(700);

        root.getChildren().addAll(label, startBtn, resumeBtn, leaderBoardBtn);

        StartBtnHandlerClass startHandler = new StartBtnHandlerClass();
        startBtn.setOnAction(startHandler);

        ResumeBtnHandlerClass resumeHandler = new ResumeBtnHandlerClass();
        resumeBtn.setOnAction(resumeHandler);

        LeaderBoardBtnHandlerClass LeaderBoardHandler = new LeaderBoardBtnHandlerClass();
        leaderBoardBtn.setOnAction(LeaderBoardHandler);

        return root;
    }

    @Override
    public void start(Stage stage) throws Exception{


        stage.setScene(new Scene(createMainMenuContent()));
        stage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}

    class StartBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            System.out.println("Start button clicked");
        }
    }

    class ResumeBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            System.out.println("Resume button clicked");
        }
    }

    class LeaderBoardBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            System.out.println("Leader board button clicked");
        }
    }