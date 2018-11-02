package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.geom.Point2D;

public class Main extends Application{

    private Pane root;
    private Stage stage;
    private Snake snake;

    private Parent createMainMenuContent() {
        root = new Pane();
        root.setPrefSize(600, 900);
        root.setStyle("-fx-background-color: #7851A9;");

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

    private Parent createGameContent() {
        root = new Pane();
        root.setPrefSize(600, 900);
        root.setStyle("-fx-background-color: #7851A9;");

        // Drop down menu
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Options:","Start Again", "Exit to Main Menu");
        choiceBox.setValue("Options:");

        Button choiceConfirmBtn = new Button("Confirm");

        choiceConfirmBtn.setOnAction(e -> getChoice(choiceBox));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(choiceBox, choiceConfirmBtn);

        Rectangle topBar = new Rectangle(600, 120);
        Stop[] stops = new Stop[] { new Stop(0, Color.web("#190236")), new Stop(1, Color.web("#7851A9"))};
        LinearGradient lg1 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);

        topBar.setFill(lg1);

        topBar.setTranslateX(0);
        topBar.setTranslateY(0);

        // Game

        // Snake code
        snake = new Snake();

        // Setting snake head location
        snake.getSnakeBody().get(0).setTranslateX(300);
        snake.getSnakeBody().get(0).setTranslateY(450);

        // Ball
        Ball ball = new Ball();
        ball.getBody().setTranslateX(100);
        ball.getBody().setTranslateY(200);
        ball.setVelocity(new Point2D.Float(1,0));

        // Block
        Block block = new Block();
        block.getBody().setTranslateX(50);
        block.getBody().setTranslateY(400);

        // Destroy Block
        DestroyBlock dblock = new DestroyBlock();
        dblock.getBody().setTranslateX(100);
        dblock.getBody().setTranslateY(600);

        // Magnet
        Magnet magnet = new Magnet();
        magnet.getBody().setTranslateX(500);
        magnet.getBody().setTranslateY(200);

        // Shield
        Shield shield = new Shield();
        shield.getBody().setTranslateX(500);
        shield.getBody().setTranslateY(400);

        // Wall
        Wall wall = new Wall();
        wall.getBody().setTranslateX(500);
        wall.getBody().setTranslateY(600);

        root.getChildren().addAll(topBar, layout, snake.getSnakeBody().get(0), ball.getBody(), block.getBody(), dblock.getBody(), magnet.getBody(), shield.getBody(), wall.getBody());

        return root;
    }

    private void getChoice(ChoiceBox<String> choiceBox) {
        String option = choiceBox.getValue();
        if (option.equals("Start Again")) {
            createGameContent();
            stage.setScene(new Scene(createGameContent()));
            stage.show();
        }

        else if (option.equals("Exit to Main Menu")) {
            createMainMenuContent();
            stage.setScene(new Scene(createMainMenuContent()));
            stage.show();
        }
    }

    private Parent createLeaderBoardContent() {
        root = new Pane();
        root.setPrefSize(600, 900);
        root.setStyle("-fx-background-color: #7851A9;");

        Label label = new Label("Leader Board");
        label.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).divide(2));
        label.setTranslateY(100);

        Button mainMenuBtn = new Button("Main Menu");

        root.getChildren().addAll(label, mainMenuBtn);

        MainMenuBtnHandlerClass mainMenuBtnHandler = new MainMenuBtnHandlerClass();
        mainMenuBtn.setOnAction(mainMenuBtnHandler);

        return root;
    }

    @Override
    public void start(Stage stage){
        Scene scene = new Scene(createMainMenuContent());

        this.stage = stage;

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.A) {
                snake.moveLeft();
            } else if (e.getCode() == KeyCode.D) {
                snake.moveRight();
            }
        });

        this.stage.setScene(scene);

        this.stage.show();

    }

    class StartBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            createGameContent();
            stage.setScene(new Scene(createGameContent()));
            stage.show();
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
            createLeaderBoardContent();
            stage.setScene(new Scene(createLeaderBoardContent()));
            stage.show();
        }
    }

    class MainMenuBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            createMainMenuContent();
            stage.setScene(new Scene(createMainMenuContent()));
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}