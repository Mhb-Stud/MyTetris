package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Tetris extends Application{
    private GameBoard myGameBoard;
    private Controller controller;
    public Halt HaltStatus = Halt.STARTED;
    public static int lvl = SettingsManager.readFromFile();
    public int score = 0;
    private int hart = 3;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Tetris.class.getResource("mainMenu.fxml"));
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        myGameBoard = new GameBoard(lvl,controller,this);
        controller.setMyGameBoard(myGameBoard);
        Controller.setTetris(this);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 750, 750));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public int getHart() {
        return hart;
    }

    public void setHart(int hart) {
        this.hart = hart;
    }

    public enum Direction{
        LEFT,RIGHT,DOWN;
    }
    public enum Halt{
        TRUE,FALSE,STARTED;
    }

    public GameBoard getMyGameBoard() {
        return myGameBoard;
    }

    public void setMyGameBoard(GameBoard myGameBoard) {
        this.myGameBoard = myGameBoard;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
    public void start() throws InterruptedException {
        myGameBoard.start();
    }
    public void stop(){
        myGameBoard.stop();
    }
    public void moveDown(){
        myGameBoard.moveDown();
    }
    public void move(Direction direction){
        myGameBoard.move(direction);
    }
}
