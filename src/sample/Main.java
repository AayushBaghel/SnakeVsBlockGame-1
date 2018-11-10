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

    private int score = 0;

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

    private Parent startScreenContent(){
        root = new Pane();
        root.setPrefSize(500,900);

        root.setStyle("-fx-background-color: #FF007F; -fx-font-family: \"Courier New\";");

        Label label = new Label("Snake\n v/s\nBlock");
        label.setFont(new Font("Courier New Bold", 86));
        label.setTextFill(Color.GHOSTWHITE);
        label.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).divide(2));
        label.setTranslateY(100);

        Label label2 = new Label("Tap to Start");
        label2.setFont(new Font("Courier New", 40));
        label2.setTextFill(Color.BLACK);
        label2.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).divide(2));
        label2.setTranslateY(600);

        root.getChildren().addAll(label,label2);

        root.setOnMouseClicked(event -> {
            createMainMenuContent();
            scene = new Scene(createMainMenuContent());
            stage.setScene(scene);
            stage.show();
        });

        return root;
    }

    private Parent createMainMenuContent() {
        root = new Pane();
        root.setPrefSize(500, 900);

        root.setStyle("-fx-background-color: #7851A9; -fx-font-family: \"Courier New\";");

        Label label = new Label("Snake Vs Block");
        label.setFont(new Font("Courier New", 50));
        label.setTextFill(Color.WHITE);
        label.setStyle("\"-fx-font-weight: bold\"");
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

    // Game Contents

    private Parent createGameContent() {
        root = new Pane();
        root.setPrefSize(500, 900);
        root.setStyle("-fx-background-color: #7851A9; -fx-font-family: \"Courier New\";");

        // Score
        Label label = new Label(Integer.toString(score));
        label.setFont(new Font("Courier New", 20));
        label.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()));
        label.setTextFill(Color.WHITE);

        Label scoreLabel = new Label("Score: ");
        scoreLabel.layoutXProperty().bind(root.widthProperty().subtract(scoreLabel.widthProperty()).subtract(label.widthProperty()));
        scoreLabel.setFont(new Font("Courier New", 20));
        scoreLabel.setTextFill(Color.WHITE);

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
        ball.getBody().setTranslateX(150);
        ball.getBody().setTranslateY(300);
        ballList.add(ball);

        // Block
        Block block1 = new Block();
        block1.getBody().setTranslateX(0);
        block1.getBody().setTranslateY(50);
        blockList.add(block1);

        Block block2 = new Block();
        block2.getBody().setTranslateX(100);
        block2.getBody().setTranslateY(50);
        blockList.add(block2);

        Block block3 = new Block();
        block3.getBody().setTranslateX(200);
        block3.getBody().setTranslateY(50);
        blockList.add(block3);

        Block block4 = new Block();
        block4.getBody().setTranslateX(300);
        block4.getBody().setTranslateY(50);
        blockList.add(block4);

        Block block5 = new Block();
        block5.getBody().setTranslateX(400);
        block5.getBody().setTranslateY(50);
        blockList.add(block5);

        // Destroy Block
        DestroyBlock dblock = new DestroyBlock();
        dblock.getBody().setTranslateX(350);
        dblock.getBody().setTranslateY(350);
        destroyBlockList.add(dblock);

        // Magnet
        Magnet magnet = new Magnet();
        magnet.getBody().setTranslateX(450);
        magnet.getBody().setTranslateY(300);
        magnetList.add(magnet);

        // Shield
        Shield shield = new Shield();
        shield.getBody().setTranslateX(50);
        shield.getBody().setTranslateY(200);
        shieldList.add(shield);

        // Wall
        Wall wall = new Wall();
        wall.getBody().setTranslateX(200);
        wall.getBody().setTranslateY(50);
        wallList.add(wall);

        Wall wall2 = new Wall();
        wall2.getBody().setTranslateX(400);
        wall2.getBody().setTranslateY(50);
        wallList.add(wall2);

        for (Ball b: ballList
        ) {
            if(b.isAlive())
                root.getChildren().add(b.getBody());
        }

        for (Block b: blockList
        ) {
            if(b.isAlive())
                root.getChildren().add(b.getBody());
        }

        for (DestroyBlock db: destroyBlockList
        ) {
            if(db.isAlive())
                root.getChildren().add(db.getBody());
        }

        for (Magnet m: magnetList
        ) {
            if(m.isAlive())
                root.getChildren().add(m.getBody());
        }

        for (Shield s: shieldList
        ) {
            if(s.isAlive())
                root.getChildren().add(s.getBody());
        }

        for (Wall w: wallList
        ) {
            root.getChildren().add(w.getBody());
        }

        root.getChildren().addAll(topBar, layout, snake.getSnakeBody().get(0), label, scoreLabel);

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

        Label label2 = new Label("Your Score = "+score);
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
        scene = new Scene(startScreenContent());

        this.stage = stage;

        this.stage.setScene(scene);

        this.stage.show();

    }
    private void update() {
        t += 0.016;
        scene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case LEFT:{
                    for (Wall w:wallList){
                        if(snake.getSnakeBody().get(0).getTranslateX()-25==w.getBody().getTranslateX()&&
                                w.getBody().getTranslateY()>=snake.getSnakeBody().get(0).getTranslateY()-w.getBody().getHeight() &&
                                w.getBody().getTranslateY()<=snake.getSnakeBody().get(0).getTranslateY()){
                            return;
                        }
                    }
                    snake.moveLeft();
                }
                break;
                case RIGHT:{
                    for (Wall w:wallList){
                        if(snake.getSnakeBody().get(0).getTranslateX()+25==w.getBody().getTranslateX()&&
                                w.getBody().getTranslateY()>=snake.getSnakeBody().get(0).getTranslateY()-w.getBody().getHeight() &&
                                w.getBody().getTranslateY()<=snake.getSnakeBody().get(0).getTranslateY()){
                            return;
                        }
                    }
                    snake.moveRight();
                }
                break;
            }
        });

        for (Ball b: ballList
        ) {
            if(b.isAlive()){
                b.getBody().setTranslateY(b.getBody().getTranslateY() + 0.5);
            }
            else{
                b.getBody().setVisible(false);
            }
            if(b.getBody().getTranslateY()==snake.getSnakeBody().get(0).getTranslateY()-b.getBody().getHeight() &&
                    snake.getSnakeBody().get(0).getTranslateX()>=b.getBody().getTranslateX()&&
                    snake.getSnakeBody().get(0).getTranslateX()<=b.getBody().getTranslateX()+b.getBody().getWidth()){

                b.setAlive(false);
                b.getBody().setVisible(false);

                snake.setLength(snake.getLength() + b.getValue());
                ballList.remove(b);
            }


        }
        for (Block b: blockList
        ) {
            if(b.isAlive()){
                b.getBody().setTranslateY(b.getBody().getTranslateY() + 0.5);
            }
            else{
                b.getBody().setVisible(false);
            }
            if(b.getBody().getTranslateY()==snake.getSnakeBody().get(0).getTranslateY()-b.getBody().getHeight() &&
                    snake.getSnakeBody().get(0).getTranslateX()>=b.getBody().getTranslateX()&&
                    snake.getSnakeBody().get(0).getTranslateX()<=b.getBody().getTranslateX()+b.getBody().getWidth()){

                b.setAlive(false);
                b.getBody().setVisible(false);

                if (b.getValue() >= snake.getLength()) {
                    scene = new Scene(gameOverPageContent());
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    score += b.getValue();
                    snake.setLength(snake.getLength() - b.getValue());
                    blockList.remove(b);
                }
            }
        }

        for (DestroyBlock db: destroyBlockList
        ) {
            if(db.isAlive()){
                db.getBody().setTranslateY(db.getBody().getTranslateY() + 0.5);
            }
            else{
                db.getBody().setHeight(0);
                db.getBody().setWidth(0);
            }
            if(db.getBody().getTranslateY()==snake.getSnakeBody().get(0).getTranslateY()-db.getBody().getHeight()&&
                    snake.getSnakeBody().get(0).getTranslateX()>=db.getBody().getTranslateX()&&
                    snake.getSnakeBody().get(0).getTranslateX()<=db.getBody().getTranslateX()+db.getBody().getWidth()){

                db.setAlive(false);
                db.getBody().setHeight(0);
                db.getBody().setWidth(0);
                destroyBlockList.remove(db);

                for (Block b: blockList
                     ) {
                    b.setAlive(false);
                    b.getBody().setVisible(false);
                    score += b.getValue();
                    blockList.remove(b);
                }
            }
        }

        for (Magnet m: magnetList
        ) {
            m.getBody().setTranslateY(m.getBody().getTranslateY() + 0.5);
            if(m.getBody().getTranslateY()==snake.getSnakeBody().get(0).getTranslateY()-(snake.getSnakeBody().get(0).getRadius()+m.getBody().getRadius())&&
                    snake.getSnakeBody().get(0).getTranslateX()>=m.getBody().getTranslateX()-(snake.getSnakeBody().get(0).getRadius()+m.getBody().getRadius())&&
                    snake.getSnakeBody().get(0).getTranslateX()<=m.getBody().getTranslateX()+(snake.getSnakeBody().get(0).getRadius()+m.getBody().getRadius())){
                m.getBody().setRadius(0);
            }
        }

        for (Shield s: shieldList
        ) {
            s.getBody().setTranslateY(s.getBody().getTranslateY() + 0.5);
            if(s.getBody().getTranslateY()==snake.getSnakeBody().get(0).getTranslateY()-s.getBody().getHeight()&&
                    snake.getSnakeBody().get(0).getTranslateX()>=s.getBody().getTranslateX() &&
                    snake.getSnakeBody().get(0).getTranslateX()<=s.getBody().getTranslateX()+s.getBody().getWidth()){
                s.getBody().setWidth(0);
                s.getBody().setHeight(0);
            }
        }

        for (Wall w: wallList
        ) {
            if(w.getBody().getTranslateY()+w.getBody().getHeight()+snake.getSnakeBody().get(0).getRadius()==snake.getSnakeBody().get(0).getTranslateY()&&
                    snake.getSnakeBody().get(0).getTranslateX()==w.getBody().getTranslateX()){
                snake.getSnakeBody().get(0).setTranslateX(snake.getSnakeBody().get(0).getTranslateX()+25);
            }
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
