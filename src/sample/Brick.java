package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Brick {
    //locationHolder
    protected int x = 0;
    protected int y = 0;
    protected static int width = 3;
    protected static int height = 3;
    protected Controller controller;
    protected GameBoard gameBoard;
    protected int rotationCounter = 0;
    protected Color color;
    protected Boolean[][] BrickType = new Boolean[height][width];

    public Brick(GameBoard gameBoard , Controller controller){
        this.controller = controller;
        this.gameBoard = gameBoard;

    }
    public Brick(GameBoard gameBoard , int x,int y , Controller controller){
        this.controller = controller;
        this.x = x;
        this.y = y;
        this.gameBoard = gameBoard;
    }
    public void move(Tetris.Direction direction){

        switch (direction){
            case DOWN:
                if(Controller.downWatchman()) {
                    y += GameBoard.cellSize;
                    Controller.moveCurrentBrick(direction);
                } else gameBoard.nextCycle();
                break;
            case LEFT:
                if(Controller.leftBound()){
                x -=GameBoard.cellSize;
                Controller.moveCurrentBrick(direction);
                }
                break;
            case RIGHT:
                if(Controller.rightBound()) {
                    x += GameBoard.cellSize;
                    Controller.moveCurrentBrick(direction);
                }
                break;
        }

    }

//    public boolean canMove(Tetris.Direction direction){
//        switch (direction){
//            case DOWN:
//                return !gameBoard.getBoard()[y+1][x];
//            case LEFT:
//                return !gameBoard.getBoard()[y][x-1];
//            case RIGHT:
//                return !gameBoard.getBoard()[y][x+1];
//        }
//    }

    public void paint(){
        List<Rectangle> blocks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(BrickType[i][j]){
                    Rectangle temp = new Rectangle(GameBoard.cellSize-1,GameBoard.cellSize-1);
                    temp.setFill(color);
                    if(i == 0 && j ==0){
                       temp.setLayoutX(x-GameBoard.cellSize);
                       temp.setLayoutY(y-GameBoard.cellSize);
                       blocks.add(temp);
                    }
                    if(i == 0 && j == 1){
                        temp.setLayoutX(x);
                        temp.setLayoutY(y-GameBoard.cellSize);
                        blocks.add(temp);
                    }
                    if(i == 0 && j == 2){
                        temp.setLayoutX(x+GameBoard.cellSize);
                        temp.setLayoutY(y-GameBoard.cellSize);
                        blocks.add(temp);
                    }
                    if(i == 1 && j == 0){
                        temp.setLayoutX(x-GameBoard.cellSize);
                        temp.setLayoutY(y);
                        blocks.add(temp);
                    }
                    if(i == 1 && j==1){
                        temp.setLayoutX(x);
                        temp.setLayoutY(y);
                        blocks.add(temp);
                    }
                    if(i == 1 && j==2){
                        temp.setLayoutX(x+GameBoard.cellSize);
                        temp.setLayoutY(y);
                        blocks.add(temp);
                    }
                    if(i == 2 && j == 0){
                        temp.setLayoutX(x - GameBoard.cellSize);
                        temp.setLayoutY(y + GameBoard.cellSize);
                        blocks.add(temp);
                    }
                    if(i == 2 && j==1){
                        temp.setLayoutX(x);
                        temp.setLayoutY(y + GameBoard.cellSize);
                        blocks.add(temp);
                    }
                    if(i == 2 && j==2){
                        temp.setLayoutX(x+GameBoard.cellSize);
                        temp.setLayoutY(y + GameBoard.cellSize);
                        blocks.add(temp);
                    }

                }
            }
        }
        Controller.painter(blocks,this);
    }

    public void freeze(){
        Controller.recordBrick();
    }

    public boolean rotate(){
        boolean result = false;
        switch (rotationCounter){
            case 0:
                result = firstR();
                break;
            case 1:
                result = secondR();
                break;
            case 2:
                result = thirdR();
                break;
            case 3:
                result = fourthR();
                break;
            case 4:
                rotationCounter = 0;
                result = firstR();
                break;

        }
       if(result) {
           Controller.clearBrick();
           paint();
       }
        return result;
    }
    abstract public boolean firstR();
    abstract public boolean secondR();
    abstract public boolean thirdR();
    abstract public boolean fourthR();

    // getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Brick.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Brick.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean[][] getBrickType() {
        return BrickType;
    }

    public void setBrickType(Boolean[][] brickType) {
        BrickType = brickType;
    }



}
class TBrick extends Brick{


    public TBrick(GameBoard gameBoard, Controller controller) {
        super(gameBoard, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        Arrays.fill(BrickType[2],true);
        BrickType[1][1] = true;
    }

    public TBrick(GameBoard gameBoard, int x, int y, Controller controller) {
        super(gameBoard, x, y, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        Arrays.fill(BrickType[2],true);
        BrickType[1][1] = true;
        color = Color.HOTPINK;
    }


    public boolean firstR(){
        if(gameBoard.isFilled(x - GameBoard.cellSize, y) && gameBoard.isFilled(x - GameBoard.cellSize, y - GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][0] = true;
            BrickType[1][0] = true;
            BrickType[2][0] = true;
            BrickType[1][1] = true;
            return true;
        }
        return false;
    }
    public boolean secondR(){
        if(gameBoard.isFilled(x, y - GameBoard.cellSize) && gameBoard.isFilled(x + GameBoard.cellSize, y - GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            Arrays.fill(BrickType[0],true);
            BrickType[1][1] = true;
            return true;
        }
        return false;

    }
    public boolean thirdR(){
        if(gameBoard.isFilled(x + GameBoard.cellSize, y) && gameBoard.isFilled(x + GameBoard.cellSize, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][2] = true;
            BrickType[1][2] = true;
            BrickType[2][2] = true;
            BrickType[1][1] = true;
            return true;
        }
        return false;
    }
    public boolean fourthR(){
        if(gameBoard.isFilled(x, y + GameBoard.cellSize) && gameBoard.isFilled(x - GameBoard.cellSize, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            Arrays.fill(BrickType[2],true);
            BrickType[1][1] = true;
            return true;
        }
        return false;
    }

}
class OBrick extends Brick{

    public OBrick(GameBoard gameBoard, Controller controller) {
        super(gameBoard, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[1][0] = true;
        BrickType[1][1] = true;
        BrickType[2][0] = true;
        BrickType[2][1] = true;

    }

    public OBrick(GameBoard gameBoard, int x, int y, Controller controller) {
        super(gameBoard, x, y, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[1][0] = true;
        BrickType[1][1] = true;
        BrickType[2][0] = true;
        BrickType[2][1] = true;
        color = Color.DARKCYAN;

    }


    @Override
    public boolean rotate() {
        return true;
    }

    @Override
    public boolean firstR() {
        return false;
    }

    @Override
    public boolean secondR() {
        return false;
    }

    @Override
    public boolean thirdR() {
        return false;
    }

    @Override
    public boolean fourthR() {
        return false;
    }
}
class LBrick extends Brick{


    public LBrick(GameBoard gameBoard, Controller controller) {
        super(gameBoard, controller);

        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[0][0] = true;
        BrickType[1][0] = true;
        BrickType[2][0] = true;
        BrickType[2][1] = true;
    }

    public LBrick(GameBoard gameBoard, int x, int y, Controller controller) {
        super(gameBoard, x, y, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[0][0] = true;
        BrickType[1][0] = true;
        BrickType[2][0] = true;
        BrickType[2][1] = true;
        color = Color.ORANGE;
    }



    @Override
    public boolean firstR() {
        if(gameBoard.isFilled(x, y - GameBoard.cellSize) && gameBoard.isFilled(x + GameBoard.cellSize, y - GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            Arrays.fill(BrickType[0],true);
            BrickType[1][0] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean secondR() {
        if(gameBoard.isFilled(x + GameBoard.cellSize, y) && gameBoard.isFilled(x + GameBoard.cellSize, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][2] = true;
            BrickType[1][2] = true;
            BrickType[2][2] = true;
            BrickType[0][1] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean thirdR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y + GameBoard.cellSize) && gameBoard.isFilled(x, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            Arrays.fill(BrickType[2],true);
            BrickType[1][2] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean fourthR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y) && gameBoard.isFilled(x - GameBoard.cellSize, y - GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][0] = true;
            BrickType[1][0] = true;
            BrickType[2][0] = true;
            BrickType[2][1] = true;
            return true;
        }
        return false;
    }
}
class IBrick extends Brick{


    public IBrick(GameBoard gameBoard, Controller controller) {
        super(gameBoard, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[0][1] = true;
        BrickType[1][1] = true;
        BrickType[2][1] = true;
    }

    public IBrick(GameBoard gameBoard, int x, int y, Controller controller) {
        super(gameBoard, x, y, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[0][1] = true;
        BrickType[1][1] = true;
        BrickType[2][1] = true;
        color = Color.BLUEVIOLET;
    }


    @Override
    public boolean firstR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y) && gameBoard.isFilled(x + GameBoard.cellSize, y)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            Arrays.fill(BrickType[1],true);
            return true;
        }
        return false;
    }

    @Override
    public boolean secondR() {
        if(gameBoard.isFilled(x, y - GameBoard.cellSize) && gameBoard.isFilled(x, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][1] = true;
            BrickType[1][1] = true;
            BrickType[2][1] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean thirdR() {
        return firstR();
    }

    @Override
    public boolean fourthR() {
        return secondR();
    }
}
class RLBrick extends Brick{


    public RLBrick(GameBoard gameBoard, Controller controller) {
        super(gameBoard, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[0][2] = true;
        BrickType[1][2] = true;
        BrickType[2][2] = true;
        BrickType[2][1] = true;
    }

    public RLBrick(GameBoard gameBoard, int x, int y, Controller controller) {
        super(gameBoard, x, y, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[0][2] = true;
        BrickType[1][2] = true;
        BrickType[2][2] = true;
        BrickType[2][1] = true;
        color = Color.GREENYELLOW;
    }


    @Override
    public boolean firstR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y + GameBoard.cellSize) && gameBoard.isFilled(x - GameBoard.cellSize, y)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[1][0] = true;
            Arrays.fill(BrickType[2],true);
            return true;
        }
        return false;
    }

    @Override
    public boolean secondR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y - GameBoard.cellSize) && gameBoard.isFilled(x, y - GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][0] = true;
            BrickType[0][1] = true;
            BrickType[1][0] = true;
            BrickType[2][0] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean thirdR() {
        if(gameBoard.isFilled(x + GameBoard.cellSize, y - GameBoard.cellSize) && gameBoard.isFilled(x + GameBoard.cellSize, y)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            Arrays.fill(BrickType[0],true);
            BrickType[1][2] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean fourthR() {
        if(gameBoard.isFilled(x + GameBoard.cellSize, y + GameBoard.cellSize) && gameBoard.isFilled(x, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][2] = true;
            BrickType[1][2] = true;
            BrickType[2][2] = true;
            BrickType[2][1] = true;
            return true;
        }
        return false;
    }
}
class BrainlessOBrick extends Brick{


    public BrainlessOBrick(GameBoard gameBoard, Controller controller) {
        super(gameBoard, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[1][0] = true;
        BrickType[2][0] = true;
        BrickType[2][1] = true;
    }

    public BrainlessOBrick(GameBoard gameBoard, int x, int y, Controller controller) {
        super(gameBoard, x, y, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[1][0] = true;
        BrickType[2][0] = true;
        BrickType[2][1] = true;
        color = Color.GRAY;
    }

    @Override
    public boolean firstR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y - GameBoard.cellSize) && gameBoard.isFilled(x, y - GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][0] = true;
            BrickType[0][1] = true;
            BrickType[1][0] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean secondR() {
        if(gameBoard.isFilled(x + GameBoard.cellSize, y - GameBoard.cellSize) && gameBoard.isFilled(x + GameBoard.cellSize, y)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][2] = true;
            BrickType[0][1] = true;
            BrickType[1][2] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean thirdR() {
        if(gameBoard.isFilled(x + GameBoard.cellSize, y + GameBoard.cellSize) && gameBoard.isFilled(x, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[2][2] = true;
            BrickType[2][1] = true;
            BrickType[1][2] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean fourthR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y) && gameBoard.isFilled(x - GameBoard.cellSize, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[1][0] = true;
            BrickType[2][0] = true;
            BrickType[2][1] = true;
            return true;
        }
        return false;
    }
}
class UBrick extends Brick{


    public UBrick(GameBoard gameBoard, Controller controller) {
        super(gameBoard, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        Arrays.fill(BrickType[1],true);
        BrickType[2][0] = true;
        BrickType[2][2] = true;
    }

    public UBrick(GameBoard gameBoard, int x, int y, Controller controller) {
        super(gameBoard, x, y, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        Arrays.fill(BrickType[1],true);
        BrickType[2][0] = true;
        BrickType[2][2] = true;
        color = Color.PALETURQUOISE;
    }


    @Override
    public boolean firstR() {
        if(gameBoard.isFilled(x, y + GameBoard.cellSize) && gameBoard.isFilled(x, y - GameBoard.cellSize) && gameBoard.isFilled(x - GameBoard.cellSize, y - GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][1] = true;
            BrickType[1][1] = true;
            BrickType[2][1] = true;
            BrickType[0][0] = true;
            BrickType[2][0] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean secondR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y) && gameBoard.isFilled(x + GameBoard.cellSize, y) && gameBoard.isFilled(x + GameBoard.cellSize, y - GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            Arrays.fill(BrickType[1],true);
            BrickType[0][0] = true;
            BrickType[0][2] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean thirdR() {
        if(gameBoard.isFilled(x, y + GameBoard.cellSize) && gameBoard.isFilled(x, y - GameBoard.cellSize) && gameBoard.isFilled(x + GameBoard.cellSize, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][1] = true;
            BrickType[1][1] = true;
            BrickType[2][1] = true;
            BrickType[0][2] = true;
            BrickType[2][2] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean fourthR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y) && gameBoard.isFilled(x + GameBoard.cellSize, y) && gameBoard.isFilled(x - GameBoard.cellSize, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            Arrays.fill(BrickType[1],true);
            BrickType[2][0] = true;
            BrickType[2][2] = true;
            return true;
        }
        return false;
    }

}
class ZBrick extends Brick{


    public ZBrick(GameBoard gameBoard, Controller controller) {
        super(gameBoard, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[1][0] = true;
        BrickType[1][1] = true;
        BrickType[2][1] = true;
        BrickType[2][2] = true;
    }

    public ZBrick(GameBoard gameBoard, int x, int y, Controller controller) {
        super(gameBoard, x, y, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[1][0] = true;
        BrickType[1][1] = true;
        BrickType[2][1] = true;
        BrickType[2][2] = true;
        color = Color.MAROON;
    }


    @Override
    public boolean firstR() {
        if(gameBoard.isFilled(x, y - GameBoard.cellSize) && gameBoard.isFilled(x - GameBoard.cellSize, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][1] = true;
            BrickType[1][1] = true;
            BrickType[1][0] = true;
            BrickType[2][0] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean secondR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y - GameBoard.cellSize) && gameBoard.isFilled(x + GameBoard.cellSize, y)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][1] = true;
            BrickType[1][1] = true;
            BrickType[0][0] = true;
            BrickType[1][2] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean thirdR() {
        if(gameBoard.isFilled(x + GameBoard.cellSize, y - GameBoard.cellSize) && gameBoard.isFilled(x, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][2] = true;
            BrickType[1][1] = true;
            BrickType[2][1] = true;
            BrickType[1][2] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean fourthR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y) && gameBoard.isFilled(x + GameBoard.cellSize, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[1][0] = true;
            BrickType[1][1] = true;
            BrickType[2][1] = true;
            BrickType[2][2] = true;
            return true;
        }
        return false;
    }
}
class SBrick extends Brick{


    public SBrick(GameBoard gameBoard, Controller controller) {
        super(gameBoard, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[1][2] = true;
        BrickType[1][1] = true;
        BrickType[2][1] = true;
        BrickType[2][0] = true;
    }

    public SBrick(GameBoard gameBoard, int x, int y, Controller controller) {
        super(gameBoard, x, y, controller);
        for (Boolean[] a:BrickType) {
            Arrays.fill(a,false);
        }
        BrickType[1][2] = true;
        BrickType[1][1] = true;
        BrickType[2][1] = true;
        BrickType[2][0] = true;
        color = Color.DARKGREEN;
    }

    @Override
    public boolean firstR() {
        if(gameBoard.isFilled(x - GameBoard.cellSize, y) && gameBoard.isFilled(x - GameBoard.cellSize, y - GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][0] = true;
            BrickType[1][0] = true;
            BrickType[1][1] = true;
            BrickType[2][1] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean secondR() {
        if(gameBoard.isFilled(x, y - GameBoard.cellSize) && gameBoard.isFilled(x + GameBoard.cellSize, y - GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][1] = true;
            BrickType[0][2] = true;
            BrickType[1][1] = true;
            BrickType[1][0] = true;
            return true;
        }


        return false;
    }

    @Override
    public boolean thirdR() {
        if(gameBoard.isFilled(x + GameBoard.cellSize, y + GameBoard.cellSize) && gameBoard.isFilled(x + GameBoard.cellSize, y)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[0][1] = true;
            BrickType[1][1] = true;
            BrickType[1][2] = true;
            BrickType[2][2] = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean fourthR() {
        if(gameBoard.isFilled(x, y + GameBoard.cellSize) && gameBoard.isFilled(x - GameBoard.cellSize, y + GameBoard.cellSize)){
            rotationCounter++;
            for (Boolean[] a:BrickType) {
                Arrays.fill(a,false);
            }
            BrickType[1][2] = true;
            BrickType[1][1] = true;
            BrickType[2][1] = true;
            BrickType[2][0] = true;
            return true;
        }
        return false;
    }

}
