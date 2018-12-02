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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

/**
 * The Main class is the heart of the program. Both the GUI and the non-GUI aspects of the game are managed here. All the pages are
 * created here and all the interactions between the user and game as well as between the game objects and other game objects
 * are handled of here.
 *
 * <h1>Snake Vs. Block Game</h1>
 * This is a 2D game made using JavaFX. You play as snake. The objective is to collect coins and break blocks to increase
 * your score. If you encounter a block, you loose as much in length as you gain in points, so you need to collect balls
 * to keep your length up. If your length reaches zero, you die.
 *
 * The game includes four tokens (power-ups):-
 * 1. Shield - Gives you invincibility from damage from blocks for a few seconds.
 * 2. Magnet - Automatically catches nearby balls and coins for you for a few seconds.
 * 3. Destroy block - Destroys all on-screen blocks and adds their values to your score.
 *
 * The game also includes walls which are impenetrable and immovable objects.
 *
 * @author Jaspreet Singh Marwah
 * @author Aayush Baghel
 * @since 2018-11-01
 *
 */

public class Main extends Application {

    /**
     * Saves the current state of the leader board in an external file.
     * @param LeaderBoard A list that contains the leader board.
     * @throws IOException
     */
    public static void serializeLeaderBoard(List<LeaderBoard> LeaderBoard) throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream( new FileOutputStream("LeaderBoardOut.txt"));
            out.writeObject(LeaderBoard);
        } finally {
            out.close();
        }
    }

    /**
     * Saves the current score in an external file.
     * @param Score The current score.
     * @throws IOException
     */
    public static void serializeScore(int Score) throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream( new FileOutputStream("ScoreOut.txt"));
            out.writeObject(Score);
        } finally {
            out.close();
        }
    }

    /**
     * Saves the current length of the snake in an external file.
     * @param Length The current length of the snake.
     * @throws IOException
     */
    public static void serializeLength(int Length) throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream( new FileOutputStream("LengthOut.txt"));
            out.writeObject(Length);
        } finally {
            out.close();
        }
    }

    /**
     * Retrieves the last saved state of the leader board.
     * @return The last saved state of the leader board.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<LeaderBoard> deserialzeLeaderBoard() throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("LeaderBoardOut.txt"));
            return (List<LeaderBoard>) in.readObject();
        } finally {
            in.close();
        }
    }

    /**
     * Retrieves the last saved state of the score.
     * @return The last saved state of the score.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static int deserializeScore() throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("ScoreOut.txt"));
            return (int) in.readObject();
        } finally {
            in.close();
        }
    }

    /**
     * Retrieves the last saved state of the snake length.
     * @return The last saved state of the snake length.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static int deserialzeLength() throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("LengthOut.txt"));
            return (int) in.readObject();
        } finally {
            in.close();
        }
    }

    /**
     * The current score of the player
     */
    private int score = 0;

    /**
     * A label used to store and display the current score
     */
    private Label scoreLabel;

    /**
     * A label used to store and display the current length of the snake
     */
    private Label lengthLabel;

    /**
     * The pane used throughout the program
     */
    private Pane root;

    /**
     * The stage used throughout the program
     */
    private Stage stage;

    /**
     * The scene used throughout the program
     */
    private Scene scene;

    /**
     * A variable used to indicate what the current scene is
     */
    private String sceneIndicator;

    /**
     * The snake object
     */
    private Snake snake = new Snake();

    /**
     * A timer that is used to continuously call the update() function while the game is running.
     */
    private AnimationTimer timer;

    /**
     * A list containing all the balls present in the game.
     */
    private List<Ball> ballList = new ArrayList<>();

    /**
     * A list containing all the coins present in the game.
     */
    private List<Coin> coinList = new ArrayList<>();

    /**
     * A list containing all the blocks present in the game.
     */
    private List<Block> blockList = new ArrayList<>();

    /**
     * A list containing all the destroy blocks present in the game.
     */
    private List<DestroyBlock> destroyBlockList = new ArrayList<>();

    /**
     * A list containing all the magnets present in the game.
     */
    private List<Magnet> magnetList = new ArrayList<>();

    /**
     * A list containing all the shields present in the game.
     */
    private List<Shield> shieldList = new ArrayList<>();

    /**
     * A list containing all the walls present in the game.
     */
    private List<Wall> wallList = new ArrayList<>();

    /**
     * A list containing all leader board entries.
     */
    private List<LeaderBoard> LeaderBoard = new ArrayList<>();

    /**
     * A variable used as a timer to keep track of time in the update function.
     */
    private double t = 0;

    /**
     * A variable used to check if the snake has encountered a block of value greater than 5.
     */
    private boolean blockEncountered = false;

    /**
     * A variable used as a timer to see how long its been since we encountered a block of value greater than 5 (so that
     * we can stop the snake accordingly for the correct duration of time).
     */
    private double tBlock = 0;

    /**
     * A variable used to check if the snake has encountered a shield.
     */
    private boolean shieldEncountered = false;

    /**
     * A variable used as a timer to see how long its been since we encountered a shield (so that we can activate the
     * shield power-up accordingly for the correct duration of time).
     */
    private double tShield = 0;

    /**
     * A variable used to check if the snake has encountered a magnet.
     */
    private boolean magnetEncountered = false;

    /**
     * A variable used as a timer to see how long its been since we encountered a magnet (so that we can activate the
     * magnet power-up accordingly for the correct duration of time).
     */
    private double tMagnet = 0;

    /**
     * Audio clip to store the sound made when we click on a button.
     */
    private static final AudioClip buttonClick = new AudioClip(Main.class.getResource("/buttonselect.wav").toString());

    /**
     * Audio clip to store the sound made when we encounter a destroy block.
     */
    private static final AudioClip destroyBlockTaken = new AudioClip(Main.class.getResource("/destroyblock.wav").toString());

    /**
     * Audio clip to store the sound made when we encounter a shield.
     */
    private static final AudioClip shieldTaken = new AudioClip(Main.class.getResource("/shield.mp3").toString());

    /**
     * Audio clip to store the sound made when we encounter a coin.
     */
    private static final AudioClip coinTaken = new AudioClip(Main.class.getResource("/coin.wav").toString());

    /**
     * Audio clip to store the sound made when we encounter a magnet.
     */
    private static final AudioClip magnetTaken = new AudioClip(Main.class.getResource("/magnet.wav").toString());

    /**
     * Audio clip to store the sound made when our snake dies.
     */
    private static final AudioClip gameover = new AudioClip(Main.class.getResource("/gameover.wav").toString());

    /**
     * Audio clip to store the sound made when we start the game.
     */
    private static final AudioClip intro = new AudioClip(Main.class.getResource("/intro.wav").toString());

    /**
     * Audio clip to store the sound made when we click on the drop down menu inside the game.
     */
    private static final AudioClip pauseClicked = new AudioClip(Main.class.getResource("/pause.wav").toString());

    /**
     * Audio clip to store the sound made when we break a block.
     */
    private static final AudioClip blockTaken = new AudioClip(Main.class.getResource("/block.wav").toString());

    /**
     * Audio clip to store the sound made when we open the LeaderBoard Page.
     */
    private static final AudioClip leaderboardSound = new AudioClip(Main.class.getResource("/leaderboard.mp3").toString());

    /**
     * Audio clip to store the sound made when we Start the start the game window.
     */
    private static final AudioClip Crabrave = new AudioClip(Main.class.getResource("/Crab Rave.mp3").toString());

    /**
     * A function used to make an entry into the leader board if the score is good enough.
     * @throws IOException
     */
    private void InsertIntoLeaderBoard() throws IOException {
        LeaderBoard playerInfo = new LeaderBoard(score);

        if (LeaderBoard.isEmpty()) {
            LeaderBoard.add(playerInfo);
        }
        else if (LeaderBoard.size() == 15){
            for (int i = 0; i < LeaderBoard.size(); i++) {
                if (LeaderBoard.get(i).getScore() > score) continue;

                else
                    LeaderBoard.add(i, playerInfo);
                LeaderBoard.remove(LeaderBoard.get(14));
                serializeLeaderBoard(LeaderBoard);
                return;
            }
        }

        else {
            for (int i = 0; i < LeaderBoard.size(); i++) {
                if (i == LeaderBoard.size() - 1)
                    LeaderBoard.add(i+1, playerInfo);
                else
                if (LeaderBoard.get(i).getScore() > score) continue;

                else
                    LeaderBoard.add(i, playerInfo);
                serializeLeaderBoard(LeaderBoard);
                return;
            }
        }
        serializeLeaderBoard(LeaderBoard);
    }

    /**
     * A function to create the start screen.
     * @return The pane used throughout the game.
     */
    private Parent startScreenContent(){
        root = new Pane();
        root.setPrefSize(500,900);

        root.setStyle("-fx-background-color: #FF007F; -fx-font-family: \"Courier New\";");

        Main.Crabrave.setCycleCount(MediaPlayer.INDEFINITE);
        Main.Crabrave.play();
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

    /**
     * A function to create the main menu where you can choose to play the game, resume the previous game, or access the
     * leader board.
     * @return The pane used throughout the game.
     */
    private Parent createMainMenuContent() {
        root = new Pane();
        root.setPrefSize(500, 900);

        Main.leaderboardSound.stop();
        Main.Crabrave.stop();
        Main.intro.stop();
        Main.intro.setCycleCount(MediaPlayer.INDEFINITE);
        Main.intro.play();

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

        try {
            LeaderBoard = deserialzeLeaderBoard();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return root;
    }

    /**
     * A function to create the setup for the main game.
     * @return The pane used throughout the game.
     * @throws IOException
     */
    private Parent createGameContent() throws IOException {
        ballList.clear();
        blockList.clear();
        destroyBlockList.clear();
        magnetList.clear();
        shieldList.clear();
        wallList.clear();
        score = 0;
        t = 0;
        snake.setLength(3);

        serializeLength(snake.getLength());

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

        // Snake length
        lengthLabel = new Label(Integer.toString(snake.getLength()));
        lengthLabel.setFont(new Font("Courier New", 20));
        lengthLabel.layoutXProperty().bind(root.widthProperty().subtract(lengthLabel.widthProperty()));
        lengthLabel.setTranslateY(scoreLabel.getTranslateY()+20);
        lengthLabel.setTextFill(Color.WHITE);

        Label label1 = new Label("Length: ");
        label1.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).subtract(label.widthProperty()));
        label1.setTranslateY(label.getTranslateY()+20);
        label1.setFont(new Font("Courier New", 20));
        label1.setTextFill(Color.WHITE);

        // Drop down menu
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");
        choiceBox.getItems().addAll("Options:","Start Again", "Exit to Main Menu");
        choiceBox.setValue("Options:");

        Button choiceConfirmBtn = new Button("Confirm");
        choiceConfirmBtn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");

        choiceConfirmBtn.setOnAction(e -> {
            try {
                getChoice(choiceBox);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

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

        for(int i=0;i<3;i++){
            snake.getSnakeBody().get(i).setTranslateX(250);
            snake.getSnakeBody().get(i).setTranslateY(450+(i*40));
        }
        snake.getLengthText().setTranslateX(snake.getSnakeBody().get(0).getTranslateX()-5);
        snake.getLengthText().setTranslateY(snake.getSnakeBody().get(0).getTranslateY()-5);

        // Ball
        Ball ball = new Ball();
        ball.getBody().setTranslateX(150);
        ball.getBody().setTranslateY(300);
        ballList.add(ball);

        // Coin
        Coin coin = new Coin();
        coin.getBody().setTranslateX(250);
        coin.getBody().setTranslateY(300);
        coinList.add(coin);

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
        wall.getBody().setTranslateY(150);
        wallList.add(wall);

        Wall wall2 = new Wall();
        wall2.getBody().setTranslateX(400);
        wall2.getBody().setTranslateY(150);
        wallList.add(wall2);

        for (Circle c: snake.getSnakeBody()
        ) {
            root.getChildren().add(c);
        }

        for (Ball b: ballList
        ) {
            if(b.isAlive())
                root.getChildren().add(b.getBody());
        }

        for (Coin c: coinList
        ) {
            if(c.isAlive())
                root.getChildren().add(c.getBody());
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

        root.getChildren().addAll(topBar, layout, label, label1, scoreLabel, snake.getSnakePane(), lengthLabel);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (sceneIndicator.equals("Game")) {
                    try {
                        update();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.start();

        return root;
    }

    /**
     * A function to create the setup for the main game when the player wants to resume their previous game.
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Parent createResumeGameContent() {
        ballList.clear();
        blockList.clear();
        destroyBlockList.clear();
        magnetList.clear();
        shieldList.clear();
        wallList.clear();
        try {
            score = deserializeScore();
        } catch (IOException e) {
            score = 0;
        } catch (ClassNotFoundException e) {
            score = 0;
        }

//        t = 0;

        try {
            snake.setLength(deserialzeLength());
        } catch (IOException e) {
            snake.setLength(3);
        } catch (ClassNotFoundException e) {
            snake.setLength(3);
        }

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

        // Snake length
        lengthLabel = new Label(Integer.toString(snake.getLength()));
        lengthLabel.setFont(new Font("Courier New", 20));
        lengthLabel.layoutXProperty().bind(root.widthProperty().subtract(lengthLabel.widthProperty()));
        lengthLabel.setTranslateY(scoreLabel.getTranslateY()+20);
        lengthLabel.setTextFill(Color.WHITE);

        Label label1 = new Label("Length: ");
        label1.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).subtract(label.widthProperty()));
        label1.setTranslateY(label.getTranslateY()+20);
        label1.setFont(new Font("Courier New", 20));
        label1.setTextFill(Color.WHITE);

        // Drop down menu
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");
        choiceBox.getItems().addAll("Options:","Start Again", "Exit to Main Menu");
        choiceBox.setValue("Options:");

        Button choiceConfirmBtn = new Button("Confirm");
        choiceConfirmBtn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");

        choiceConfirmBtn.setOnAction(e -> {
            try {
                getChoice(choiceBox);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

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

        int x = snake.getLength();
        // Snake code
        snake = new Snake(x);

        // Setting snake body position
        for(int i=0;i<snake.getLength();i++){
            snake.getSnakeBody().get(i).setTranslateX(250);
            snake.getSnakeBody().get(i).setTranslateY(450+(i*40));
        }
        snake.getLengthText().setTranslateX(snake.getSnakeBody().get(0).getTranslateX()-5);
        snake.getLengthText().setTranslateY(snake.getSnakeBody().get(0).getTranslateY()-5);

        // Ball
        Ball ball = new Ball();
        ball.getBody().setTranslateX(150);
        ball.getBody().setTranslateY(300);
        ballList.add(ball);

        // Coin
        Coin coin = new Coin();
        coin.getBody().setTranslateX(250);
        coin.getBody().setTranslateY(300);
        coinList.add(coin);

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
        wall.getBody().setTranslateY(150);
        wallList.add(wall);

        Wall wall2 = new Wall();
        wall2.getBody().setTranslateX(400);
        wall2.getBody().setTranslateY(150);
        wallList.add(wall2);

        for (Circle c: snake.getSnakeBody()
        ) {
            root.getChildren().add(c);
        }

        for (Ball b: ballList
        ) {
            if(b.isAlive())
                root.getChildren().add(b.getBody());
        }

        for (Coin c: coinList
        ) {
            if(c.isAlive())
                root.getChildren().add(c.getBody());
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

        root.getChildren().addAll(topBar, layout, label, label1, scoreLabel, snake.getSnakePane(), lengthLabel);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (sceneIndicator.equals("ResumeGame")) {
                    try {
                        update();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.start();

        return root;
    }

    /**
     * A function to handle whatever option is chosen from the drop down menu in the game.
     * @param choiceBox The drop down menu.
     * @throws IOException
     */
    private void getChoice(ChoiceBox<String> choiceBox) throws IOException {
        Main.pauseClicked.play();
        String option = choiceBox.getValue();
        if (option.equals("Start Again")) {
            Main.pauseClicked.play();
            coinList.clear();
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
            Main.pauseClicked.play();
            ballList.clear();
            coinList.clear();
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

    /**
     * A function to create the leader board page where you can see the top 15 scores.
     * @return The pane used throughout the game.
     */
    private Parent createLeaderBoardContent() {
        root = new Pane();
        root.setPrefSize(500, 900);
        root.setStyle("-fx-background-color: #7851A9; -fx-font-family: \"Courier New\";");

        Main.leaderboardSound.setCycleCount(MediaPlayer.INDEFINITE);
        Main.leaderboardSound.play();
        Label label = new Label("Leader Board");
        label.layoutXProperty().bind(root.widthProperty().subtract(label.widthProperty()).divide(2));
        label.setTranslateY(100);
        label.setFont(new Font("Courier New", 50));
        label.setStyle("-fx-font-weight: bold");
        label.setTextFill(Color.MISTYROSE);

        Button mainMenuBtn = new Button("<- Main Menu");
        mainMenuBtn.setStyle("-fx-background-color: palevioletred; -fx-text-fill: white;");
        mainMenuBtn.setTranslateX(10);
        mainMenuBtn.setTranslateY(10);

        Label label1 = new Label("S. No.    Score   Date & Time");
        label1.layoutXProperty().bind(root.widthProperty().subtract(label1.widthProperty()).divide(2));
        label1.setTranslateY(200);
        label1.setFont(new Font("Courier New", 20));
        label1.setTextFill(Color.WHITE);

        root.getChildren().addAll(label, label1, mainMenuBtn);

        for (int i=0; i < LeaderBoard.size(); i++) {
            Label label2 = new Label(i+1 + "          " + LeaderBoard.get(i).getScore() + "      " + LeaderBoard.get(i).getDate());
            label2.layoutXProperty().bind(root.widthProperty().subtract(label1.widthProperty()).divide(2));
            label2.setTranslateY(250 + 20*i);
            label2.setFont(new Font("Courier New", 20));
            label2.setTextFill(Color.WHITE);
            root.getChildren().add(label2);
        }

        MainMenuBtnHandlerClass mainMenuBtnHandler = new MainMenuBtnHandlerClass();
        mainMenuBtn.setOnAction(mainMenuBtnHandler);

        return root;
    }

    /**
     * A function to display the game over page where the final score is shown.
     * @return The pane used throughout the game.
     * @throws IOException
     */
    private Parent gameOverPageContent() throws IOException {
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

        score = 0;
        snake.setLength(3);

        serializeLength(snake.getLength());
        serializeScore(score);

        MainMenuBtnHandlerClass mainMenuBtnHandler = new MainMenuBtnHandlerClass();
        mainMenuBtn.setOnAction(mainMenuBtnHandler);

        return root;
    }

    /**
     * The first function to execute in this JavaFX program.
     * @param stage The stage variable  that is used throughout the program.
     */
    @Override
    public void start(Stage stage) {
        scene = new Scene(startScreenContent());
        sceneIndicator = "StartScreen";

        this.stage = stage;

        this.stage.setScene(scene);

        this.stage.show();

    }

    /**
     * The function that checks and updates the status of each game object.
     * @throws ConcurrentModificationException
     * @throws IOException
     */
    private void update() throws ConcurrentModificationException, IOException {
        t += 0.016;

        if (blockEncountered) {
            tBlock += 0.016;
            if (tBlock > 5) {
                tBlock = 0;
                blockEncountered = false;
            }
        }

        if (shieldEncountered) {
            tShield += 0.016;
            if (tShield > 40) {
                tShield = 0;
                shieldEncountered = false;
            }
        }

        if (magnetEncountered) {
            tMagnet += 0.016;
            if (tMagnet > 40) {
                tMagnet = 0;
                magnetEncountered = false;
            }
        }

        // Move snake
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

        int snekLen = snake.getLength();
        double extraSpeed = snekLen/5.0;

        if (extraSpeed > 1.5) {
            extraSpeed = 1.5;
        }
        else if (extraSpeed >= 0.5) {
            extraSpeed = 0.5;
        }
        else {
            extraSpeed = 0;
        }

        // Move objects and check if they collide with snake
        for (Ball b: ballList
        ) {
            if(b.getBody().getTranslateY() > 899) {
                ballList.remove(b);
            }

            if(b.isAlive()){
                if (!blockEncountered)
                    b.getBody().setTranslateY(b.getBody().getTranslateY() + 0.5 + extraSpeed); //*(1+snekLen/5)
            }
            else{
                b.getBody().setVisible(false);
            }
            int powerup = 0;
            if (magnetEncountered) {
                powerup = 70;
            }
            if(b.getBody().getTranslateY()>=snake.getSnakeBody().get(0).getTranslateY()-snake.getSnakeBody().get(0).getRadius()-b.getBody().getHeight()-powerup &&
                    b.getBody().getTranslateY()<=snake.getSnakeBody().get(0).getTranslateY()+snake.getSnakeBody().get(0).getRadius()+b.getBody().getHeight()+powerup &&
                    snake.getSnakeBody().get(0).getTranslateX()+powerup>=b.getBody().getTranslateX()&&
                    snake.getSnakeBody().get(0).getTranslateX()<=b.getBody().getTranslateX()+b.getBody().getWidth()+powerup)  {
                b.setAlive(false);
                b.getBody().setVisible(false);

                snake.setLength(snake.getLength() + b.getValue());
                serializeLength(snake.getLength());
                for (int i=snake.getSnakeBody().size();i<snake.getLength();i++){
                    snake.getSnakeBody().add(new Circle(20, Paint.valueOf("BLUE")));
                    snake.getSnakeBody().get(i).setTranslateX(snake.getSnakeBody().get(0).getTranslateX());
                    snake.getSnakeBody().get(i).setTranslateY(450+(40*i));
                    root.getChildren().add(snake.getSnakeBody().get(i));
                }
                lengthLabel.setText(Integer.toString(snake.getLength()));
                snake.updateLengthText();
                ballList.remove(b);
            }


        }

        for (Coin c: coinList
        ) {
            if(c.getBody().getTranslateY() > 899) {
                coinList.remove(c);
            }

            if(c.isAlive()){
                if (!blockEncountered)
                    c.getBody().setTranslateY(c.getBody().getTranslateY() + 0.5 + extraSpeed); //*(1+snekLen/5)
            }
            else{
                c.getBody().setVisible(false);
            }
            int powerup = 0;
            if (magnetEncountered) {
                powerup = 70;
            }
            if(c.getBody().getTranslateY()>=snake.getSnakeBody().get(0).getTranslateY()-snake.getSnakeBody().get(0).getRadius()-c.getBody().getHeight()-powerup &&
                    c.getBody().getTranslateY()<=snake.getSnakeBody().get(0).getTranslateY()+snake.getSnakeBody().get(0).getRadius()+c.getBody().getHeight()+powerup &&
                    snake.getSnakeBody().get(0).getTranslateX()+powerup>=c.getBody().getTranslateX()&&
                    snake.getSnakeBody().get(0).getTranslateX()<=c.getBody().getTranslateX()+c.getBody().getWidth()+powerup) {
                Main.coinTaken.play();
                c.setAlive(false);
                c.getBody().setVisible(false);
                score+=c.getValue();
                serializeScore(score);
                scoreLabel.setText(Integer.toString(score));
                coinList.remove(c);
            }


        }

        for (Block b: blockList
        ) {
            if(b.getBody().getTranslateY() > 899) {
                blockList.remove(b);
            }
            if(b.isAlive()){
                if (!blockEncountered)
                    b.getBody().setTranslateY(b.getBody().getTranslateY() + 0.5 + extraSpeed); //*(1+snekLen/5)
            }
            else{
                b.getBody().setVisible(false);
                blockList.remove(b);
            }
            if(b.getBody().getTranslateY()==snake.getSnakeBody().get(0).getTranslateY()-b.getBody().getHeight()-snake.getSnakeBody().get(0).getRadius() &&
                    snake.getSnakeBody().get(0).getTranslateX()>=b.getBody().getTranslateX()&&
                    snake.getSnakeBody().get(0).getTranslateX()<=b.getBody().getTranslateX()+b.getBody().getWidth()){
                Main.blockTaken.play();
                if (!shieldEncountered) {
                    if (b.getValue() >= snake.getLength() && b.isAlive()) {
                        Main.intro.stop();
                        Main.shieldTaken.stop();
                        Main.magnetTaken.stop();
                        Main.destroyBlockTaken.stop();
                        Main.gameover.play();
                        snake.getLengthText().setVisible(false);
                        score+=snake.getLength();
                        InsertIntoLeaderBoard();
                        scene = new Scene(gameOverPageContent());
                        sceneIndicator = "GameOver";
                        stage.setScene(scene);
                        stage.show();
                        timer.stop();
                    }
                    else {
                        if (b.getValue() > 5)
                            blockEncountered = true;
                        score += b.getValue();
                        serializeScore(score);
                        scoreLabel.setText(Integer.toString(score));
                        snake.setLength(snake.getLength() - b.getValue());
                        serializeLength(snake.getLength());
                        blockList.remove(b);
                        System.out.println("Snake Length = "+snake.getLength());
                        double locX = snake.getSnakeBody().get(0).getTranslateX();
                        double locY = snake.getSnakeBody().get(0).getTranslateY();
                        for(int i=0;i<snake.getSnakeBody().size();i++){
                            snake.getSnakeBody().get(i).setVisible(false);
//                        snake.getSnakeBody().remove(snake.getSnakeBody().get(i));
                        }

//                    b.getBody().setVisible(false);
                        snake.getSnakeBody().clear();
                        snake.getSnakeBody().add(new Circle(20, Paint.valueOf("BLUE")));
                        snake.getSnakeBody().get(0).setTranslateX(locX);
                        snake.getSnakeBody().get(0).setTranslateY(locY);
//                    snake.getLengthText().setTranslateX(snake.getSnakeBody().get(0).getTranslateX());
//                    snake.getLengthText().setTranslateY(snake.getSnakeBody().get(0).getTranslateY());
                        root.getChildren().add(snake.getSnakeBody().get(0));
                        for (int i=snake.getSnakeBody().size();i<snake.getLength();i++){
                            snake.getSnakeBody().add(new Circle(20, Paint.valueOf("BLUE")));
                            snake.getSnakeBody().get(i).setTranslateX(snake.getSnakeBody().get(0).getTranslateX());
                            snake.getSnakeBody().get(i).setTranslateY(450+(40*i));
                            root.getChildren().add(snake.getSnakeBody().get(i));
                        }
                        lengthLabel.setText(Integer.toString(snake.getLength()));
                        snake.updateLengthText();
                    }
                    b.setAlive(false);
                    b.getBody().setVisible(false);
                    ImageView img = new ImageView(Main.class.getResource("/ex2.png").toString());
                    img.setFitHeight(100);
                    img.setFitWidth(100);
                    img.setTranslateX(b.getBody().getTranslateX());
                    img.setTranslateY(b.getBody().getTranslateY());
                    root.getChildren().add(img);
                    Timer animTimer = new Timer();
                    animTimer.scheduleAtFixedRate(new TimerTask() {
                        int i=0;
                        @Override
                        public void run() {
                            if(i<10){
                                img.setFitHeight(img.getFitHeight()+10);
                                img.setFitWidth(img.getFitWidth()+10);
                                img.setTranslateX(img.getTranslateX()-5);
                                img.setTranslateY(img.getTranslateY()-5);
                            }
                            else{
                                img.setVisible(false);
                                root.getChildren().remove(img);
                                this.cancel();
                            }
                            i++;
                        }
                    }, 15,25);
                    blockList.remove(b);
                }
                else {
                    score += b.getValue();
                    serializeScore(score);
                    scoreLabel.setText(Integer.toString(score));
                    blockList.remove(b);
                    b.setAlive(false);
                    b.getBody().setVisible(false);
                }

            }
        }

        for (DestroyBlock db: destroyBlockList
        ) {
            if(db.getBody().getTranslateY() > 899) {
                destroyBlockList.remove(db);
            }
            if(db.isAlive()){
                if (!blockEncountered)
                    db.getBody().setTranslateY(db.getBody().getTranslateY() + 0.5 + extraSpeed); //*(1+snekLen/5)
            }
            else{
                db.getBody().setVisible(false);
                destroyBlockList.remove(db);
            }
            if(db.getBody().getTranslateY()>=snake.getSnakeBody().get(0).getTranslateY()-db.getBody().getHeight()&&
                    db.getBody().getTranslateY()<=snake.getSnakeBody().get(0).getTranslateY()+db.getBody().getHeight()&&
                    snake.getSnakeBody().get(0).getTranslateX()>=db.getBody().getTranslateX()&&
                    snake.getSnakeBody().get(0).getTranslateX()<=db.getBody().getTranslateX()+db.getBody().getWidth()){
                Main.destroyBlockTaken.play();
                db.getBody().setVisible(false);
                db.setAlive(false);
                destroyBlockList.remove(db);

                for (Block b: blockList
                ) {
                    score += b.getValue();
                    serializeScore(score);
                    scoreLabel.setText(Integer.toString(score));
                    b.setAlive(false);
                    b.getBody().setVisible(false);
                }

                blockList.clear();
            }
        }

        for (Magnet m: magnetList
        ) {
            if(m.getBody().getTranslateY() > 899) {
                m.getBody().setVisible(false);
                magnetList.remove(m);
            }
            if (!blockEncountered)
                m.getBody().setTranslateY(m.getBody().getTranslateY() + 0.5 + extraSpeed); //*(1+snekLen/5)
            if(m.getBody().getTranslateY()>=snake.getSnakeBody().get(0).getTranslateY()-(snake.getSnakeBody().get(0).getRadius()+m.getBody().getHeight())&&
                    m.getBody().getTranslateY()<=snake.getSnakeBody().get(0).getTranslateY()-(snake.getSnakeBody().get(0).getRadius()-m.getBody().getHeight())&&
                    snake.getSnakeBody().get(0).getTranslateX()>=m.getBody().getTranslateX()-(snake.getSnakeBody().get(0).getRadius()+m.getBody().getWidth())&&
                    snake.getSnakeBody().get(0).getTranslateX()<=m.getBody().getTranslateX()+(snake.getSnakeBody().get(0).getRadius()+m.getBody().getWidth())){
                Main.magnetTaken.play();
                magnetEncountered = true;
                tMagnet = 0;
                m.getBody().setVisible(false);
                m.setAlive(false);
                magnetList.remove(m);
            }
        }

        for (Shield s: shieldList
        ) {
            if(s.getBody().getTranslateY() > 899) {
                shieldList.remove(s);
            }
            if (!blockEncountered)
                s.getBody().setTranslateY(s.getBody().getTranslateY() + 0.5 +  + extraSpeed); //*(1+snekLen/5)
            if(s.getBody().getTranslateY()>=snake.getSnakeBody().get(0).getTranslateY()-snake.getSnakeBody().get(0).getRadius()-s.getBody().getHeight()&&
                    s.getBody().getTranslateY()<=snake.getSnakeBody().get(0).getTranslateY()+snake.getSnakeBody().get(0).getRadius()+s.getBody().getHeight()&&
                    snake.getSnakeBody().get(0).getTranslateX()>=s.getBody().getTranslateX() &&
                    snake.getSnakeBody().get(0).getTranslateX()<=s.getBody().getTranslateX()+s.getBody().getWidth()){
                Main.shieldTaken.play();
                shieldEncountered = true;
                tShield = 0;
                s.getBody().setVisible(false);
                s.setAlive(false);
                shieldList.remove(s);
            }
        }

        for (Wall w: wallList
        ) {
            if(w.getBody().getTranslateY() > 899) {
                wallList.remove(w);
            }

            if(w.getBody().getTranslateY()+w.getBody().getHeight()+snake.getSnakeBody().get(0).getRadius()==snake.getSnakeBody().get(0).getTranslateY()&&
                    snake.getSnakeBody().get(0).getTranslateX()==w.getBody().getTranslateX()){
                for (int i=0;i<snake.getSnakeBody().size();i++){
                    snake.getSnakeBody().get(i).setTranslateX(snake.getSnakeBody().get(i).getTranslateX()+25);
                }
            }
            if (!blockEncountered)
                w.getBody().setTranslateY(w.getBody().getTranslateY() + 0.5 + extraSpeed); //*(1+snekLen/5)
        }


        // Spawn objects
        Random random = new Random();

        // Ball
        int ballProb = random.nextInt(999) + 1;

        if (ballProb <= 2) {
            Ball ball = new Ball();
            ball.getBody().setTranslateX(random.nextInt(440) + 30);
            ball.getBody().setTranslateY(75);
            ballList.add(ball);
            root.getChildren().add(ball.getBody());
        }

        int coinProb = random.nextInt(999) + 1;

        if (coinProb <= 2) {
            Coin coin = new Coin();
            coin.getBody().setTranslateX(random.nextInt(440) + 30);
            coin.getBody().setTranslateY(75);
            coinList.add(coin);
            root.getChildren().add(coin.getBody());
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
                    wall.getBody().setTranslateY(150);
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
                    wall.getBody().setTranslateY(150);
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
                    wall.getBody().setTranslateY(150);
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
        }
    }

    /**
     * This class handles the event in which the start button is pressed.
     */
    class StartBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e){
            Main.buttonClick.play();
            try {
                createGameContent();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                scene = new Scene(createGameContent());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            sceneIndicator = "Game";
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This class handles the event in which the resume button is pressed.
     */
    class ResumeBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Main.buttonClick.play();
            createResumeGameContent();
            scene = new Scene(createResumeGameContent());
            sceneIndicator = "ResumeGame";
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This class handles the event in which the leader board button is pressed.
     */
    class LeaderBoardBtnHandlerClass implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Main.buttonClick.play();
            createLeaderBoardContent();
            scene = new Scene(createLeaderBoardContent());
            sceneIndicator = "LeaderBoard";
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This class handles the event in which the main menu button is pressed.
     */
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
