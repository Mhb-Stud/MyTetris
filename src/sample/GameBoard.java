package sample;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameBoard {
    public static final int rows = 20;
    public static final int column = 15;
    public static final int cellSize = 30;
    public static final int windowWidth = cellSize*column;
    public static final int windowHeight = cellSize*rows;
    private Controller controller;
    private Brick currentBrick;
    private int lvl;
    private int lvlChangeCounter = 0;
    private Tetris tetris;
    private Boolean[][] Board = new Boolean[rows][column];
    enum State{
        START,STOP;
    }
    private State currentState = State.START;

    public GameBoard(int lvl ,Controller controller , Tetris tetris){
        this.lvl = lvl;
        this.controller = controller;
        this.tetris = tetris;
        for (Boolean[] a:Board) {
            Arrays.fill(a,false);
        }
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }
    public void start() throws InterruptedException {
        brickGenerator();
        //currentBrick = new IBrick(this,cellSize*(column/2),0,controller);
        currentBrick.paint();
        while(currentState == State.START){
            Thread.sleep(1000/lvl);
            if(tetris.HaltStatus == Tetris.Halt.FALSE) moveDown();
        }
    }
    public void nextCycle(){
        currentBrick.freeze();
        for (Boolean element:Board[0]) {
            if(element) stop();
        }
        shouldRemove();
        brickGenerator();
        //currentBrick = new IBrick(this,cellSize*(column/2),0,controller);
        currentBrick.paint();
    }
    public void shouldRemove(){
        int counter = 0;
        List<Integer> targetRows = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column; j++) {
                if(Board[i][j]) counter++;
            }
            if(counter == column) targetRows.add(i);
            counter = 0;
        }
        List<Boolean[]> tempBoard = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            tempBoard.add(Board[i]);
        }
        for (int k = 0; k < targetRows.size(); k++) {
            label:
             for (int i = rows-1; i >=0; i--) {
                for (int j = 0; j < column; j++) {
                    if(tempBoard.get(i)[j]) counter++;
                }
                if(counter == column) {
                    tempBoard.remove(i);
                    Boolean[] empty = new Boolean[15];
                    Arrays.fill(empty,false);
                    tempBoard.add(0,empty);
                    counter = 0;
                    break label;
                }
                 counter = 0;
            }
        }
        for (int i = 0; i < rows; i++) {
            Board[i] = tempBoard.get(i);
        }
        switch (targetRows.size()){
            case 1:
                tetris.score += 100*lvl;
                break;
            case 2:
                tetris.score += 300*lvl;
                break;
            case 3:
                // tetris :) !!!!
                tetris.score += 1200*lvl;
                break;
        }
        if(targetRows.size()>3) tetris.score += 1200*lvl;
        lvlChangeCounter +=targetRows.size();
        if(lvlChangeCounter>=8) {
            lvl++;
            lvlChangeCounter = 0;
        }
        Controller.rowRemover(targetRows);
    }
    public void stop(){
        if(tetris.getHart() > 0) {
            tetris.setHart(tetris.getHart() -1);
            clearBoard();
        }
        else{
            currentState = State.STOP;
            Controller.gameOver();
        }
    }
    public void pause(){
        if(tetris.HaltStatus == Tetris.Halt.FALSE){
            tetris.HaltStatus = Tetris.Halt.TRUE;
        } else {
            Controller.clearBrick();
            brickGenerator();
            currentBrick.paint();
            tetris.HaltStatus = Tetris.Halt.FALSE;
        }
    }
    public void moveDown(){
        currentBrick.move(Tetris.Direction.DOWN);
    }
    public void move(Tetris.Direction Direct){
        currentBrick.move(Direct);
    }
    public boolean isFilled(int x , int y){
        if(x < 0 || y <= 0 || y>=windowHeight-1 || x>= windowWidth-1) return false;
        return !Board[((y+GameBoard.cellSize/2)/GameBoard.cellSize )][((x+GameBoard.cellSize/2 )/GameBoard.cellSize)];
    }
    public void fill(int x , int y){


    }
    public void clearBoard(){
        for (Boolean[] i:Board) {
            Arrays.fill(i,false);
        }
        Controller.boardClean();
    }
    public void brickGenerator(){
        double rand = Math.random()*100;
        currentBrick = new TBrick(this,cellSize*(column/2),0,controller);
        if (rand < 10){
            currentBrick = new TBrick(this,cellSize*(column/2),0,controller);
        } else if(rand <20){
            currentBrick = new LBrick(this,cellSize*(column/2),0,controller);
        }else if(rand<30){
            currentBrick = new RLBrick(this,cellSize*(column/2),0,controller);
        }else if(rand < 40){
            currentBrick = new UBrick(this,cellSize*(column/2),0,controller);
        }else if(rand < 50){
            currentBrick = new BrainlessOBrick(this,cellSize*(column/2),0,controller);
        }else if(rand < 60){
            currentBrick = new OBrick(this,cellSize*(column/2),0,controller);
        } else if(rand < 70){
            currentBrick = new ZBrick(this,cellSize*(column/2),0,controller);
        }else if(rand < 80){
            currentBrick = new SBrick(this,cellSize*(column/2),0,controller);
        }else if(rand <100){
            currentBrick = new IBrick(this,cellSize*(column/2),0,controller);
        }
    }


    // get and set
    public static int getRows() {
        return rows;
    }

    public static int getColumn() {
        return column;
    }

    public static int getCellSize() {
        return cellSize;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Brick getCurrentBrick() {
        return currentBrick;
    }

    public void setCurrentBrick(Brick currentBrick) {
        this.currentBrick = currentBrick;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public Tetris getTetris() {
        return tetris;
    }

    public void setTetris(Tetris tetris) {
        this.tetris = tetris;
    }

    public Boolean[][] getBoard() {
        return Board;
    }

    public void setBoard(Boolean[][] board) {
        Board = board;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
