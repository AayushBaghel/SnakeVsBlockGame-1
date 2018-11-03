package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application{

    private Pane root;
    private Stage stage;
    private Scene scene;
    private Snake snake = new Snake();
    private javafx.scene.control.Button closeButton;

    private List<Ball> ballList = new ArrayList<>();
    private List<Block> blockList = new ArrayList<>();
    private List<DestroyBlock> destroyBlockList = new ArrayList<>();
    private List<Magnet> magnetList = new ArrayList<>();
    private List<Shield> shieldList = new ArrayList<>();
    private List<Wall> wallList = new ArrayList<>();

    private double t = 0;

    private Parent createMainMenuContent() {
        root = new Pane();
        root.setPrefSize(500, 900);

        root.setStyle("-fx-background-color: #7851A9; -fx-font-family: \"Courier New\";");

        Label label = new Label("Snake Vs Block");
        label.setFont(new Font("Courier New", 50));
        label.setTextFill(Color.WHITE);
        label.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).divide(2));
        label.setTranslateY(100);

        Button exitBtn = new Button("Exit");
        exitBtn.setStyle("-fx-background-color: red; -fx-text-fill: white");
        exitBtn.setPrefSize(60, 25);
        exitBtn.layoutXProperty().bind(root.widthProperty().subtract(exitBtn.widthProperty()).divide(2));
        exitBtn.setTranslateX(210);
        exitBtn.setTranslateY(10);

        Button startBtn = new Button("Start Game");
        startBtn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white; -fx-font-size: 40px;");
        startBtn.setPrefSize(400, 100);

        startBtn.layoutXProperty().bind(root.widthProperty().subtract(startBtn.widthProperty()).divide(2));
        startBtn.setTranslateY(300);

        Button resumeBtn = new Button("Resume Game");
        resumeBtn.layoutXProperty().bind(root.widthProperty().subtract(resumeBtn.widthProperty()).divide(2));
        resumeBtn.setTranslateY(500);
        resumeBtn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white; -fx-font-size: 40px;");
        resumeBtn.setPrefSize(400, 100);

        Button leaderBoardBtn = new Button("Leader board");
        leaderBoardBtn.layoutXProperty().bind(root.widthProperty().subtract(leaderBoardBtn.widthProperty()).divide(2));
        leaderBoardBtn.setTranslateY(700);
        leaderBoardBtn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white; -fx-font-size: 40px;");
        leaderBoardBtn.setPrefSize(400, 100);

        root.getChildren().addAll(label, startBtn, resumeBtn, leaderBoardBtn, exitBtn);

        exitBtn.setOnAction(e -> Platform.exit());

        StartBtnHandlerClass startHandler = new StartBtnHandlerClass();
        startBtn.setOnAction(startHandler);

        ResumeBtnHandlerClass resumeHandler = new ResumeBtnHandlerClass();
        resumeBtn.setOnAction(resumeHandler);

        LeaderBoardBtnHandlerClass LeaderBoardHandler = new LeaderBoardBtnHandlerClass();
        leaderBoardBtn.setOnAction(LeaderBoardHandler);

        return root;
    }

    // Exit Button

    class ExitBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
    }

    // Game Contents

    private Parent createGameContent() {
        root = new Pane();
        root.setPrefSize(500, 900);
        root.setStyle("-fx-background-color: #7851A9; -fx-font-family: \"Courier New\";");

        // Drop down menu
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");
        choiceBox.getItems().addAll("Options:","Start Again", "Exit to Main Menu");
        choiceBox.setValue("Options:");

        Button choiceConfirmBtn = new Button("Confirm");
        choiceConfirmBtn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");

        choiceConfirmBtn.setOnAction(e -> getChoice(choiceBox));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(choiceBox, choiceConfirmBtn);

        Rectangle topBar = new Rectangle(500, 120);
        Stop[] stops = new Stop[] { new Stop(0, Color.web("#190236")), new Stop(1, Color.web("#7851A9"))};
        LinearGradient lg1 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);

        topBar.setFill(lg1);

        topBar.setTranslateX(0);
        topBar.setTranslateY(0);

        // Game

        // Snake code
        snake = new Snake();

        // Setting snake head location
        snake.getSnakeBody().get(0).setTranslateX(250);
        snake.getSnakeBody().get(0).setTranslateY(450);

        /*
        Code that should be here:
        Spawn blocks etc. with certain speed downwards

        blocks - spawn 5 in a single row, each has a 0.8 prob of spawning. If blocks reach certain
        point (y point is block size away from bottom of screen, spawn new block row. When y point
        is equal to bottom of screen, remove the blocks that are out of bounds.
               - Also spawn a wall below each block by a certain probability

        Tokens - spawn with certain probability

        If the snake head's position is out of bounds, game ends
         */

        // Ball
        Ball ball = new Ball();
        ball.getBody().setTranslateX(100);
        ball.getBody().setTranslateY(200);
        ball.setVelocity(new Point2D.Float(1,0));
        ballList.add(ball);

        // Block
        Block block = new Block();
        block.getBody().setTranslateX(50);
        block.getBody().setTranslateY(400);
        blockList.add(block);

        // Destroy Block
        DestroyBlock dblock = new DestroyBlock();
        dblock.getBody().setTranslateX(100);
        dblock.getBody().setTranslateY(600);
        destroyBlockList.add(dblock);

        // Magnet
        Magnet magnet = new Magnet();
        magnet.getBody().setTranslateX(400);
        magnet.getBody().setTranslateY(200);
        magnetList.add(magnet);

        // Shield
        Shield shield = new Shield();
        shield.getBody().setTranslateX(400);
        shield.getBody().setTranslateY(400);
        shieldList.add(shield);

        // Wall
        Wall wall = new Wall();
        wall.getBody().setTranslateX(400);
        wall.getBody().setTranslateY(600);
        wallList.add(wall);

        for (Ball b: ballList
        ) {
            root.getChildren().add(b.getBody());
        }

        for (Block b: blockList
        ) {
            root.getChildren().add(b.getBody());
        }

        for (DestroyBlock db: destroyBlockList
        ) {
            root.getChildren().add(db.getBody());
        }

        for (Magnet m: magnetList
        ) {
            root.getChildren().add(m.getBody());
        }

        for (Shield s: shieldList
        ) {
            root.getChildren().add(s.getBody());
        }

        for (Wall w: wallList
        ) {
            root.getChildren().add(w.getBody());
        }

        root.getChildren().addAll(topBar, layout, snake.getSnakeBody().get(0));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        return root;
    }

    private void getChoice(ChoiceBox<String> choiceBox) {
        String option = choiceBox.getValue();
        if (option.equals("Start Again")) {
            ballList.clear();
            blockList.clear();
            destroyBlockList.clear();
            magnetList.clear();
            shieldList.clear();
            wallList.clear();
            createGameContent();
            scene = new Scene(createGameContent());
            stage.setScene(scene);
            stage.show();

            scene.setOnKeyPressed(event -> {
                switch(event.getCode()) {
                    case LEFT: snake.moveLeft(); break;
                    case RIGHT: snake.moveRight(); break;
                }
            });
        }

        else if (option.equals("Exit to Main Menu")) {
            ballList.clear();
            blockList.clear();
            destroyBlockList.clear();
            magnetList.clear();
            shieldList.clear();
            wallList.clear();
            createMainMenuContent();
            scene = new Scene(createMainMenuContent());
            stage.setScene(scene);
            stage.show();
        }
    }

    private Parent createLeaderBoardContent() {
        root = new Pane();
        root.setPrefSize(500, 900);
        root.setStyle("-fx-background-color: #7851A9; -fx-font-family: \"Courier New\";");

        Label label = new Label("Leader Board");
        label.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).divide(2));
        label.setTranslateY(100);
        label.setFont(new Font("Courier New", 40));
        label.setTextFill(Color.WHITE);

        Button mainMenuBtn = new Button("<- Main Menu");
        mainMenuBtn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");
        mainMenuBtn.setTranslateX(10);
        mainMenuBtn.setTranslateY(10);

        root.getChildren().addAll(label, mainMenuBtn);

        MainMenuBtnHandlerClass mainMenuBtnHandler = new MainMenuBtnHandlerClass();
        mainMenuBtn.setOnAction(mainMenuBtnHandler);

        return root;
    }

    // Game Over Page to display the final score.

    private Parent gameOverPageContent() {
        root = new Pane();
        root.setPrefSize(500, 900);
        root.setStyle("-fx-background-color: #7851A9; -fx-font-family: \"Courier New\";");

        Label label = new Label("G A M E\nO V E R");
        label.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).divide(2));
        label.setTranslateY(100);
        label.setFont(new Font("Courier New", 72));
        label.setTextFill(Color.RED);

        Label label2 = new Label("Your Score = "+snake.getLength());
        label2.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).divide(2));
        label2.setTranslateY(500);
        label2.setFont(new Font("Courier New", 40));
        label2.setTextFill(Color.WHITE);

        Button mainMenuBtn = new Button("<- Main Menu");
        mainMenuBtn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");
        mainMenuBtn.setTranslateX(10);
        mainMenuBtn.setTranslateY(10);

        root.getChildren().addAll(label, label2, mainMenuBtn);

        MainMenuBtnHandlerClass mainMenuBtnHandler = new MainMenuBtnHandlerClass();
        mainMenuBtn.setOnAction(mainMenuBtnHandler);

        return root;
    }

    @Override
    public void start(Stage stage){
        scene = new Scene(createMainMenuContent());

        this.stage = stage;

        this.stage.setScene(scene);

        this.stage.show();

    }

    private void update() {
        t += 0.016;

        scene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case LEFT: snake.moveLeft(); break;
                case RIGHT: snake.moveRight(); break;
            }
        });

        for (Ball b: ballList
        ) {
            b.getBody().setTranslateY(b.getBody().getTranslateY() + 0.5);
        }

        for (Block b: blockList
        ) {
            b.getBody().setTranslateY(b.getBody().getTranslateY() + 0.5);
        }

        for (DestroyBlock db: destroyBlockList
        ) {
            db.getBody().setTranslateY(db.getBody().getTranslateY() + 0.5);
        }

        for (Magnet m: magnetList
        ) {
            m.getBody().setTranslateY(m.getBody().getTranslateY() + 0.5);
        }

        for (Shield s: shieldList
        ) {
            s.getBody().setTranslateY(s.getBody().getTranslateY() + 0.5);
        }

        for (Wall w: wallList
        ) {
            w.getBody().setTranslateY(w.getBody().getTranslateY() + 0.5);
        }

    }

    class StartBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            createGameContent();
            scene = new Scene(createGameContent());
            stage.setScene(scene);
            stage.show();

            scene.setOnKeyPressed(event -> {
                switch(event.getCode()) {
                    case LEFT: snake.moveLeft(); break;
                    case RIGHT: snake.moveRight(); break;
                }
            });
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
            scene = new Scene(createLeaderBoardContent());
            stage.setScene(scene);
            stage.show();
        }
    }

    class MainMenuBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            createMainMenuContent();
            scene = new Scene(createMainMenuContent());
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
