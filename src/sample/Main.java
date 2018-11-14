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
import java.util.Random;

public class Main extends Application{

    private int score = 0;
    private Label scoreLabel;

    private Pane root;
    private Stage stage;
    private Scene scene;
    private String sceneIndicator;
    private Snake snake = new Snake();
    private javafx.scene.control.Button closeButton;
    private AnimationTimer timer;

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
            sceneIndicator = "MainMenu";
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
        ballList.clear();
        blockList.clear();
        destroyBlockList.clear();
        magnetList.clear();
        shieldList.clear();
        wallList.clear();
        score = 0;
        t = 0;
        snake.setLength(1);

        root = new Pane();
        root.setPrefSize(500, 900);
        root.setStyle("-fx-background-color: #7851A9; -fx-font-family: \"Courier New\";");

        // Score
        scoreLabel = new Label(Integer.toString(score));
        scoreLabel.setFont(new Font("Courier New", 20));
        scoreLabel.layoutXProperty().bind(root.widthProperty().subtract(scoreLabel.widthProperty()));
        scoreLabel.setTextFill(Color.WHITE);

        Label label = new Label("Score: ");
        label.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).subtract(label.widthProperty()));
        label.setFont(new Font("Courier New", 20));
        label.setTextFill(Color.WHITE);

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

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (sceneIndicator.equals("Game")) {
                    update();
                }
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
            sceneIndicator = "Game";
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
            sceneIndicator = "MainMenu";
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
        sceneIndicator = "StartScreen";

        this.stage = stage;

        this.stage.setScene(scene);

        this.stage.show();

    }
    private void update() {
        t += 0.016;



        // Move snake
        scene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case LEFT:{
                    for (Wall w:wallList){
                        if(snake.getSnakeBody().get(0).getTranslateX()-20==w.getBody().getTranslateX()&&
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
                        if(snake.getSnakeBody().get(0).getTranslateX()+20==w.getBody().getTranslateX()&&
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

        // Move objects and check if they collide with snake
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
                blockList.remove(b);
            }
            if(b.getBody().getTranslateY()==snake.getSnakeBody().get(0).getTranslateY()-b.getBody().getHeight() &&
                    snake.getSnakeBody().get(0).getTranslateX()>=b.getBody().getTranslateX()&&
                    snake.getSnakeBody().get(0).getTranslateX()<=b.getBody().getTranslateX()+b.getBody().getWidth()){

                if (b.getValue() >= snake.getLength() && b.isAlive()) {
                    scene = new Scene(gameOverPageContent());
                    sceneIndicator = "GameOver";
                    stage.setScene(scene);
                    stage.show();
                    timer.stop();
                }
                else {
                    score += b.getValue();
                    scoreLabel.setText(Integer.toString(score));
//                    root.getChildren().add(scoreLabel);
                    snake.setLength(snake.getLength() - b.getValue());
                    blockList.remove(b);
                }

                b.setAlive(false);
                b.getBody().setVisible(false);
                blockList.remove(b);
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
                destroyBlockList.remove(db);
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
                    score += b.getValue();
                    scoreLabel.setText(Integer.toString(score));
                    b.setAlive(false);
                    b.getBody().setVisible(false);
                }

                blockList.clear();
            }
        }

        for (Magnet m: magnetList
        ) {
            m.getBody().setTranslateY(m.getBody().getTranslateY() + 0.5);
            if(m.getBody().getTranslateY()==snake.getSnakeBody().get(0).getTranslateY()-(snake.getSnakeBody().get(0).getRadius()+m.getBody().getRadius())&&
                    snake.getSnakeBody().get(0).getTranslateX()>=m.getBody().getTranslateX()-(snake.getSnakeBody().get(0).getRadius()+m.getBody().getRadius())&&
                    snake.getSnakeBody().get(0).getTranslateX()<=m.getBody().getTranslateX()+(snake.getSnakeBody().get(0).getRadius()+m.getBody().getRadius())){
                m.getBody().setRadius(0);
                m.setAlive(false);
                magnetList.remove(m);
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
                s.setAlive(false);
                shieldList.remove(s);
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


        // Spawn objects
        Random random = new Random();

        // Ball
        int ballProb = random.nextInt(999) + 1;

        if (ballProb <= 2) {
            Ball ball = new Ball();
            ball.getBody().setTranslateX(random.nextInt(440) + 30);
            ball.getBody().setTranslateY(random.nextInt(400) + 30);
            ballList.add(ball);
            root.getChildren().add(ball.getBody());
        }

        if (t >= 20) {
            t = 0;


            // Blocks
            boolean block1Placed = false;
            boolean block2Placed = false;
            boolean block3Placed = false;
            boolean block4Placed = false;

            int block1Prob = random.nextInt(99) + 1;

            if (block1Prob <= 80) {
                block1Placed = true;
                Block block1 = new Block();
                block1.getBody().setTranslateX(0);
                block1.getBody().setTranslateY(50);
                blockList.add(block1);
                root.getChildren().add(block1.getBody());
            }

            int block2Prob = random.nextInt(99) + 1;

            if (block2Prob <= 80) {
                block2Placed = true;
                Block block2 = new Block();
                block2.getBody().setTranslateX(100);
                block2.getBody().setTranslateY(50);
                blockList.add(block2);
                root.getChildren().add(block2.getBody());

                int wallProb = random.nextInt(99) + 1;

                if (wallProb <=80) {
                    Wall wall = new Wall();
                    wall.getBody().setTranslateX(100);
                    wall.getBody().setTranslateY(50);
                    wallList.add(wall);
                    root.getChildren().add(wall.getBody());
                }
            }

            int block3Prob = random.nextInt(99) + 1;

            if (block3Prob <= 80) {
                block3Placed = true;
                Block block3 = new Block();
                block3.getBody().setTranslateX(200);
                block3.getBody().setTranslateY(50);
                blockList.add(block3);
                root.getChildren().add(block3.getBody());

                int wallProb = random.nextInt(99) + 1;

                if (wallProb <=80) {
                    Wall wall = new Wall();
                    wall.getBody().setTranslateX(200);
                    wall.getBody().setTranslateY(50);
                    wallList.add(wall);
                    root.getChildren().add(wall.getBody());
                }
            }

            int block4Prob = random.nextInt(99) + 1;

            if (block4Prob <= 80) {
                block4Placed = true;
                Block block4 = new Block();
                block4.getBody().setTranslateX(300);
                block4.getBody().setTranslateY(50);
                blockList.add(block4);
                root.getChildren().add(block4.getBody());

                int wallProb = random.nextInt(99) + 1;

                if (wallProb <=80) {
                    Wall wall = new Wall();
                    wall.getBody().setTranslateX(300);
                    wall.getBody().setTranslateY(50);
                    wallList.add(wall);
                    root.getChildren().add(wall.getBody());
                }
            }

            int block5Prob = random.nextInt(99) + 1;

            if (block5Prob <= 80) {
                Block block5;
                if (block1Placed && block2Placed && block3Placed && block4Placed) {
                    block5 = new Block(snake.getLength());
                }
                else {
                    block5 = new Block();
                }
                block5.getBody().setTranslateX(400);
                block5.getBody().setTranslateY(50);
                blockList.add(block5);
                root.getChildren().add(block5.getBody());
            }

            // Destroy Block

            int dblockProb = random.nextInt(99) + 1;

            if (dblockProb <= 30) {
                DestroyBlock dblock = new DestroyBlock();
                dblock.getBody().setTranslateX(random.nextInt(500-30) + 30);
                dblock.getBody().setTranslateY(350);
                destroyBlockList.add(dblock);
                root.getChildren().add(dblock.getBody());
            }

            // Magnet

            int magnetProb = random.nextInt(99) + 1;

            if (magnetProb <= 30) {
                Magnet magnet = new Magnet();
                magnet.getBody().setTranslateX(random.nextInt(500-10) + 10);
                magnet.getBody().setTranslateY(300);
                magnetList.add(magnet);
                root.getChildren().add(magnet.getBody());
            }

            // Shield

            int shieldProb = random.nextInt(99) + 1;

            if (shieldProb <= 30) {
                Shield shield = new Shield();
                shield.getBody().setTranslateX(50);
                shield.getBody().setTranslateY(200);
                shieldList.add(shield);
                root.getChildren().add(shield.getBody());
            }

//            for (Ball b: ballList
//            ) {
//                if(b.isAlive())
//                    root.getChildren().add(b.getBody());
//            }
//
//            for (Block b: blockList
//            ) {
//                if(b.isAlive())
//                    root.getChildren().add(b.getBody());
//            }
//
//            for (DestroyBlock db: destroyBlockList
//            ) {
//                if(db.isAlive())
//                    root.getChildren().add(db.getBody());
//            }
//
//            for (Magnet m: magnetList
//            ) {
//                if(m.isAlive())
//                    root.getChildren().add(m.getBody());
//            }
//
//            for (Shield s: shieldList
//            ) {
//                if(s.isAlive())
//                    root.getChildren().add(s.getBody());
//            }
//
//            for (Wall w: wallList
//            ) {
//                root.getChildren().add(w.getBody());
//            }
        }

    }

    class StartBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            createGameContent();
            scene = new Scene(createGameContent());
            sceneIndicator = "Game";
            stage.setScene(scene);
            stage.show();

//            scene.setOnKeyPressed(event -> {
//                switch(event.getCode()) {
//                    case LEFT: snake.moveLeft(); break;
//                    case RIGHT: snake.moveRight(); break;
//                }
//            });
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
            sceneIndicator = "LeaderBoard";
            stage.setScene(scene);
            stage.show();
        }
    }

    class MainMenuBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            createMainMenuContent();
            scene = new Scene(createMainMenuContent());
            sceneIndicator = "MainMenu";
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
