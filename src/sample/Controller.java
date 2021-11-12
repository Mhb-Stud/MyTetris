package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    public static GameBoard myGameBoard;
    public static Stage primaryStage;
    public static Scene mainGame;
    public static Pane storage;
    public static Pane mainPane;
    public static Button start;
    public static Tetris tetris;
    public static List<Rectangle> currentBrick;
    public static Thread mainThread;
    public static Brick currBlock;
    public static Label scoreLabel;
    public static Label lvlLabel;
    public static Label hartLabel;


    @FXML
    public void exitClicked(){
    System.exit(0);
}
@FXML
public void newGameClicked(ActionEvent event) throws IOException, InterruptedException {
//    construction
    mainPane = new Pane();
    storage = new Pane();
    storage.setPrefSize(450,600);
    storage.setLayoutX(30);
    storage.setLayoutY(30);
    mainPane.setStyle("-fx-background-color: GREY");
    storage.setStyle("-fx-background-color: white");
    mainPane.getChildren().add(storage);
    primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    primaryStage.setTitle("L E G O G A M E");
    // window elements !
    start = new Button("START");
    start.setStyle("-fx-background-color: lightcoral ; -fx-text-fill: black ; -fx-font-size: 22");
    start.setPrefSize(160,80);
    start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(tetris.HaltStatus == Tetris.Halt.STARTED) {
                tetris.HaltStatus = Tetris.Halt.FALSE;
                Runnable runnable = () -> {
                    try {
                        tetris.start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
                mainThread = new Thread(runnable);
                mainThread.start();
            } else {
                myGameBoard.pause();
            }
        }
    });
    start.setLayoutX(500.0);
    start.setLayoutY(510.0);
    scoreLabel = new Label("SCORE : "+tetris.score);
    scoreLabel.setStyle("-fx-background-color: lightblue ; -fx-text-fill: black ; -fx-font-size: 22");
    scoreLabel.setAlignment(Pos.CENTER);
    scoreLabel.setPrefSize(160,80);
    scoreLabel.setLayoutX(500.0);
    scoreLabel.setLayoutY(150.0);
    lvlLabel = new Label("LEVEL : "+myGameBoard.getLvl());
    lvlLabel.setStyle("-fx-background-color: lightblue ; -fx-text-fill: black ; -fx-font-size: 22");
    lvlLabel.setAlignment(Pos.CENTER);
    lvlLabel.setPrefSize(160,80);
    lvlLabel.setLayoutX(500.0);
    lvlLabel.setLayoutY(240.0);
    hartLabel = new Label("HART : "+ tetris.getHart());
    hartLabel.setStyle("-fx-background-color: lightblue ; -fx-text-fill: black ; -fx-font-size: 22");
    hartLabel.setAlignment(Pos.CENTER);
    hartLabel.setPrefSize(160,80);
    hartLabel.setLayoutX(500.0);
    hartLabel.setLayoutY(330.0);


    mainPane.getChildren().addAll(start,scoreLabel,lvlLabel,hartLabel);
    mainGame = new Scene(mainPane,700,650);
    mainGame.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            Runnable runnable = () -> {
                switch (event.getCode()){
                    case RIGHT:
                        currBlock.move(Tetris.Direction.RIGHT);
                        break;
                    case LEFT:
                        currBlock.move(Tetris.Direction.LEFT);
                        break;
                    case DOWN:
                        currBlock.move(Tetris.Direction.DOWN);
                        break;
                    case UP:
                        currBlock.rotate();
                        break;
                }
            };
            Platform.runLater(runnable);

        }
    });
    primaryStage.setScene(mainGame);
    primaryStage.show();
}
     public static void painter(List<Rectangle> blocks , Brick block){
         currentBrick = blocks;
         currBlock = block;
         for (int i = 0; i < blocks.size(); i++) {
           Rectangle tempRec = blocks.get(i);
             Platform.runLater(new Runnable() {
                 @Override
                 public void run() {
                     storage.getChildren().add(tempRec);
                     lvlLabel.setText("LEVEL : "+myGameBoard.getLvl());
                     hartLabel.setText("HART : "+tetris.getHart());
                 }
             });
         }
     }

    public void setMyGameBoard(sample.GameBoard gameBoard) {
        myGameBoard = gameBoard;
    }
    public static boolean leftBound(){
        for (Rectangle a:currentBrick) {
            if(a.getLayoutX() -GameBoard.cellSize < 0) return false;
            if(myGameBoard.getBoard()[(int)((a.getLayoutY()+GameBoard.cellSize/2)/GameBoard.cellSize )][(int)((a.getLayoutX()+GameBoard.cellSize/2)/GameBoard.cellSize-1)]) return false;
        }
        return true;
    }
    public static boolean rightBound(){
        for (Rectangle a:currentBrick) {
            if(a.getLayoutX() + GameBoard.cellSize >= GameBoard.windowWidth) return false;
            if(myGameBoard.getBoard()[(int)((a.getLayoutY()+GameBoard.cellSize/2)/GameBoard.cellSize)][(int)((a.getLayoutX()+GameBoard.cellSize/2)/GameBoard.cellSize +1)]) return false;
        }
        return true;
    }

    public static boolean downWatchman(){
        for (Rectangle a:currentBrick) {
            if(a.getLayoutY() + GameBoard.cellSize >= GameBoard.windowHeight) return false;
            if(myGameBoard.getBoard()[(int)((a.getLayoutY()+GameBoard.cellSize/2)/GameBoard.cellSize)+1][(int)((a.getLayoutX()+GameBoard.cellSize/2)/GameBoard.cellSize )]) return false;
        }
        return true;
    }
    public static void setTetris(Tetris tetris){
    Controller.tetris = tetris;
    }
    public static void moveCurrentBrick(Tetris.Direction direction){
    int step = GameBoard.cellSize;
    switch (direction){
        case DOWN:
            for (Rectangle a:currentBrick) {
                a.setLayoutY(a.getLayoutY() + step);
            }
            break;
        case RIGHT:
            for (Rectangle a:currentBrick) {
                a.setLayoutX(a.getLayoutX() + GameBoard.cellSize);
            }
            break;
        case LEFT:
            for (Rectangle a:currentBrick) {
                a.setLayoutX(a.getLayoutX() - GameBoard.cellSize);
            }
            break;
    }
    }
    public static void recordBrick(){
        for (Rectangle a:currentBrick) {
            myGameBoard.getBoard()[(int)((a.getLayoutY()+GameBoard.cellSize/2)/GameBoard.cellSize )][(int)((a.getLayoutX()+GameBoard.cellSize/2)/GameBoard.cellSize)] = true;
        }
    }
    public static void clearBrick(){
        for (Rectangle a:currentBrick) {
            storage.getChildren().remove(a);
        }
    }
   public static void rowRemover(List<Integer> rowsToDelete){
       for (Integer row:rowsToDelete) {
           List<Node> elements = new ArrayList<>(storage.getChildren());
           for (Node a:elements) {
               if(a instanceof Rectangle){
                   if(a.getLayoutY() + GameBoard.cellSize/2 <(row+1)*GameBoard.cellSize  && a.getLayoutY() + GameBoard.cellSize/2 >(row )*GameBoard.cellSize){
                       Platform.runLater( () -> {
                           storage.getChildren().remove(a);
                       });
                   }
               }
           }
           elements = new ArrayList<>(storage.getChildren());
           for (Node a:elements) {
               if(a instanceof Rectangle){
                   if(a.getLayoutY() + GameBoard.cellSize/2 <(row)*GameBoard.cellSize ){
                       Platform.runLater( ()-> {
                           a.setLayoutY(a.getLayoutY() + GameBoard.cellSize);
                       });
                   }
               }
           }

       }
       Platform.runLater( () ->{
       scoreLabel.setText("SCORE : "+tetris.score);
       });
    }
    public static void boardClean(){
    int counter = storage.getChildren().size();
        for (int i = 0; i < counter; i++) {
            Platform.runLater( () -> {
                storage.getChildren().remove(0);
            });
        }
    }
    public static void gameOver(){
        Label gameOver = new Label("GAME OVER");
        gameOver.setStyle("-fx-font-size: 50; -fx-text-fill: red");
        gameOver.setLayoutX(100);
        gameOver.setLayoutY(250);
        gameOver.setAlignment(Pos.CENTER);
        Platform.runLater( () -> {
            storage.getChildren().add(gameOver);
        });
        TextField playerName = new TextField();
        HBox main = new HBox(10);
        Button submit = new Button("SUBMIT");
        Label label = new Label("YOUR NAME :");
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ScoreManager.addPlayer(new Player(playerName.getText(),tetris.score));
                System.exit(0);
            }
        });
        main.getChildren().addAll(label,playerName,submit);
        Platform.runLater( () -> {
            primaryStage = (Stage) (gameOver).getScene().getWindow();
            primaryStage.setScene(new Scene(main, 300, 30));
        });
    }
    @FXML
    public void scoreTableSelected(ActionEvent event){
        try {
            VBox listOfPeople = new VBox();
            listOfPeople.setStyle("-fx-background-color: lightYellow");
            primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            int rankCounter = 1;
            List<Player> players = ScoreManager.readFromFile();
            HBox firstRow = new HBox();
            firstRow.setAlignment(Pos.CENTER);
            Label rankName = new Label("Rank");
            Label nameName = new Label("PlayerName");
            Label scoreName = new Label("Score");
            rankName.setStyle("-fx-border-color: black ; -fx-font-size: 16;-fx-border-radius: 2;-fx-background-color: skyBlue ; -fx-text-fill: white");
            nameName.setStyle("-fx-border-color: black ; -fx-font-size: 16;-fx-border-radius: 2;-fx-background-color: skyBlue ; -fx-text-fill: white");
            scoreName.setStyle("-fx-border-color: black ; -fx-font-size: 16;-fx-border-radius: 2;-fx-background-color: skyBlue ; -fx-text-fill: white");
            rankName.setAlignment(Pos.CENTER);
            nameName.setAlignment(Pos.CENTER);
            scoreName.setAlignment(Pos.CENTER);
            rankName.setPrefSize(60,20);
            nameName.setPrefSize(160,20);
            scoreName.setPrefSize(100,20);
            firstRow.getChildren().addAll(rankName,nameName,scoreName);
            listOfPeople.getChildren().add(firstRow);
            for (int i = 0; i < players.size(); i++) {
                HBox row = new HBox();
                row.setAlignment(Pos.CENTER);
                Label rank = new Label(Integer.toString(rankCounter++));
                Label name = new Label(players.get(i).getName());
                Label score = new Label(Integer.toString(players.get(i).getScore()));
                rank.setStyle("-fx-border-color: black ; -fx-font-size: 16;-fx-border-radius: 2 ; -fx-background-color: grey");
                name.setStyle("-fx-border-color: black ; -fx-font-size: 16;-fx-border-radius: 2; -fx-background-color: lightGreen");
                score.setStyle("-fx-border-color: black ; -fx-font-size: 16;-fx-border-radius: 2; -fx-background-color: orange");
                rank.setAlignment(Pos.CENTER);
                name.setAlignment(Pos.CENTER);
                score.setAlignment(Pos.CENTER);
                rank.setPrefSize(60,20);
                name.setPrefSize(160,20);
                score.setPrefSize(100,20);
                row.getChildren().addAll(rank,name,score);
                listOfPeople.getChildren().add(row);
            }
            mainGame = new Scene(listOfPeople,500,600);
            primaryStage.setScene(mainGame);
            primaryStage.setTitle("SCOREBOARD");
            primaryStage.show();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void settingsSelected(ActionEvent event){
        Pane options = new Pane();
        Label startLvl = new Label("STARTING LEVEL :");
        TextField myTextField = new TextField();
        Button saveChange = new Button("SAVE SETTINGS");
        Button clearScores = new Button("Clear Scores");
        clearScores.setAlignment(Pos.CENTER);
        saveChange.setAlignment(Pos.CENTER);
        clearScores.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ScoreManager.resetScoreBoard();
            }
        });
        saveChange.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SettingsManager.writeToFile(Integer.parseInt(myTextField.getText()));
                System.exit(0);
            }
        });
        options.getChildren().addAll(startLvl,myTextField,saveChange,clearScores);
        options.setStyle("-fx-background-color: lightYellow");
        //setting locations
        clearScores.setLayoutY(300);
        clearScores.setLayoutX(150);
        startLvl.setLayoutY(80);
        startLvl.setLayoutX(10);
        myTextField.setLayoutY(80);
        myTextField.setLayoutX(150);
        saveChange.setLayoutY(350);
        saveChange.setLayoutX(150);
        primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        mainGame = new Scene(options,300,400);
        primaryStage.setScene(mainGame);
        primaryStage.setTitle("SETTINGS");
        primaryStage.show();
    }
}
