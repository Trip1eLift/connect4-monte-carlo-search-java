/** Board.java
 *
 * Description: The graphic App of connect 4 program powered by DigitConnect4App
 *
 * Date: 10/22/2018
 * Version: 3
 * @author: Joseph Chang
 */

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

public class Board extends Application
{
    //Graphical objects and alignment objects
    protected ArrayList<Circle> Red;
    protected ArrayList<Circle> Black;
    protected ArrayList<Line> vertical;
    protected ArrayList<Line> horizon;
    protected Group root;
    protected Group BigRoot;
    protected Label result;
    protected Button reset;
    protected VBox vBox;
    protected HBox hBox;
    protected Scene scene;
    protected int mouseX = 0;
    protected int mouseY = 0;
    //protected Label mouseLocation;


    //Game info works in background
    protected DigitConnect4App gameApp;
    protected boolean fallingAnimation = false;
    protected HashMap<String, Circle> GameMap = new HashMap();

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        gameApp = new DigitConnect4App(); //Create the digital version of connect 4 work in background
        buildBoard(); //buildBoard will initiate most graphic stuff

        scene = new Scene(BigRoot,1230,900); //create the scene

        scene.setOnMouseMoved(new setOnMouse()); //Tracing the movement of mouse
        scene.setOnMouseClicked(new setOnMouseClicked()); //Tracing the click of mouse.
                                                          // If the mouse click in the right area, the move will be played.
        reset.setOnAction(new resetButton()); //The reset button. If the button be hit, the game will restart.

        primaryStage.setTitle("Connect4");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //When the mouse has been click, the methods will be called.
    //If the mouse clicked in the right position, the play will be move.
    //The play may still be denied by the gameApp, it depends on if the board is full.
    class setOnMouseClicked implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(MouseEvent event)
        {
            boolean legitMove = false;
            mouseX = (int)event.getX();
            mouseY = (int)event.getY();

            if (0 < event.getY() && event.getY() <= 140 &&!fallingAnimation)
            {
                if (130 <= event.getX() && event.getX() < 230)
                {
                    if (gameApp.move(0))
                        legitMove = true;
                }
                else if (230 <= event.getX() && event.getX() < 330)
                {
                    if (gameApp.move(1))
                        legitMove = true;
                }
                else if (330 <= event.getX() && event.getX() < 430)
                {
                    if (gameApp.move(2))
                        legitMove = true;
                }
                else if (430 <= event.getX() && event.getX() < 530)
                {
                    if (gameApp.move(3))
                        legitMove = true;
                }
                else if (530 <= event.getX() && event.getX() < 630)
                {
                    if (gameApp.move(4))
                        legitMove = true;
                }
                else if (630 <= event.getX() && event.getX() < 730)
                {
                    if (gameApp.move(5))
                        legitMove = true;
                }
                else if (730 <= event.getX() && event.getX() < 830)
                {
                    if (gameApp.move(6))
                        legitMove = true;
                }

            }

            //When the move has been played in background.
            if (legitMove) //Back ground is done.
            {
                //Calling animation
                fallingAnimation = true;
                fallingAni(event.getY());

                //print information on terminal
                gameApp.printBoard();
                gameApp.getLastmove().Print();
                System.out.println();
                System.out.println("-----------------------------");

                //Checking win
                int win = gameApp.win();
                if (win == 0)
                {
                    if (gameApp.ifDraw())
                    {
                        result.setText("It's a draw!");
                        result.setScaleX(5);
                        result.setScaleY(5);
                        result.setTextFill(Color.GREEN);
                    }
                    else
                    {
                        fallingAnimation = false;
                        buildPiece();
                        if (gameApp.getRedTurn())
                        {
                            result.setText("Red's turn");
                            result.setTextFill(Color.RED);
                        }
                        else
                        {
                            result.setText("Black's turn");
                            result.setTextFill(Color.BLACK);
                        }
                    }
                }
                else if (win == 1)
                {
                    result.setText("Red wins!!!");
                    result.setScaleX(5);
                    result.setScaleY(5);
                    result.setTextFill(Color.RED);
                    //calling winning animation
                    winningAni(true);
                    //print information on terminal
                    gameApp.getWinpath().Print();
                    System.out.println();
                }
                else
                {
                    result.setText("Black wins!!!");
                    result.setScaleX(5);
                    result.setScaleY(5);
                    result.setTextFill(Color.BLACK);
                    //calling winning animation
                    winningAni(false);
                    //print information on terminal
                    gameApp.getWinpath().Print();
                    System.out.println();
                }


            }

        }
    }

    //Being called when there is any movement on the mouse in the scene
    //It will trace the mouse to let the piece follow the mouse
    class setOnMouse implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(javafx.scene.input.MouseEvent event) {
            //mouseLocation.setText("X: " + event.getX() + "\nY: " + event.getY());

            if (!(902 < event.getX() && event.getX() < 1182 && 262 < event.getY() && event.getY() < 462))
            {
                if(!fallingAnimation && gameApp.getRedTurn())
                {
                    Red.get(Red.size()-1).setCenterX(event.getX()-130);
                    Red.get(Red.size()-1).setCenterY(event.getY()-140);
                }
                else if(!fallingAnimation)
                {
                    Black.get(Black.size()-1).setCenterX(event.getX()-130);
                    Black.get(Black.size()-1).setCenterY(event.getY()-140);
                }
            }
        }
    }

    //Being called when the user hits reset.
    //call method Reset to wipe out the info in background.
    //Remove and clear all the piece
    class resetButton implements  EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            gameApp.Reset();
            System.out.println("Reset the game board.");
            fallingAnimation = true;

            //Every single piece can be reach in three data structure:
            // Group BigRoot, HashMap gameMap, ArrayList Red or Black
            //Inorder to disconnect the piece, all three connection must be cut.
            for (int i = 0; i < Red.size(); i++)
            {
                BigRoot.getChildren().remove(Red.get(i));
            }
            for (int i = 0; i < Black.size(); i++)
            {
                BigRoot.getChildren().remove(Black.get(i));
            }
            GameMap.clear();
            Red.clear();
            Black.clear();

            //Build a new piece and start the game
            buildPiece();

            result.setText("Red's turn");
            result.setScaleX(3);
            result.setScaleY(3);
            result.setTextFill(Color.RED);

            fallingAnimation = false;
        }
    }

    //Being called when one side defeat the other side
    //It will highlight the winning pieces
    //The piece Node can be reach using winningPath and GameMap
    protected void winningAni(boolean redWin)
    {
        ArrayList<Circle> winCircle = new ArrayList<Circle>();
        String winKey[] = gameApp.getWinpath().toStringArr();
        for (int i = 0; i < 4; i++)
        {
            winCircle.add(GameMap.get(winKey[i]));
        }

        if (redWin == true)
        {
            for (int i = 0; i < winCircle.size(); i++)
            {
                winCircle.get(i).setFill(Color.ORANGE);
                winCircle.get(i).setStrokeWidth(4);
            }
        }
        else
        {
            for (int i = 0; i < winCircle.size(); i++)
            {
                winCircle.get(i).setFill(Color.DARKGREY);
                winCircle.get(i).setStroke(Color.BLACK);
                winCircle.get(i).setStrokeWidth(4);
            }
        }
    }

    //Being call when the play has been move
    //It will drop the piece at the height where the click is pressed
    // but drop at the width in the middle of lines
    protected void fallingAni(double Ystart)
    {
        LastMove lastmove = gameApp.getLastmove();
        int bounceY = 66 + 100 * lastmove.row;
        int x = 180 + 100 * lastmove.column;
        Circle cir;
        if (lastmove.color == 1)
            cir = Red.get(Red.size()-1);
        else
            cir = Black.get(Black.size()-1);

        int Y = (int)Ystart;

        cir.toBack();
        cir.setCenterX(x-130+2);
        cir.setCenterY(Y-140);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(((double)bounceY-Ystart)/566.0*1.5+0.5));
        transition.setToY(bounceY-Y+140);
        transition.setToX(0);
        transition.setNode(cir);
        transition.play();

        //Throw info into hashMap to connect cir with coordinates
        GameMap.put(gameApp.getLastmove().toStringKey(), cir);
    }

    //Being call when the game need a new piece
    //For example, when the game start, restart,
    // or the play has been move but the game is not over yet.
    protected void buildPiece()
    {
        Circle circle = new Circle(50);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        circle.setCenterX(mouseX-130);
        circle.setCenterY(mouseY-140);
        circle.toFront();

        if(gameApp.getRedTurn())
        {
            circle.setFill(Color.RED);
            Red.add(circle);
            BigRoot.getChildren().add(Red.get(Red.size()-1));
        }
        else
        {
            circle.setFill(Color.BLACK);
            Black.add(circle);
            BigRoot.getChildren().add(Black.get(Black.size()-1));
        }


    }

    //Will only be call once during the entire compile time
    //Being call when the game is started
    //To initiate the graphical board
    protected void buildBoard()
    {
        vertical = new ArrayList<Line>();
        horizon = new ArrayList<Line>();

        Red = new ArrayList<Circle>();
        Black = new ArrayList<Circle>();

        root = new Group();
        BigRoot = new Group();

        for (int i = 0; i < 8; i++)
        {
            Line line = new Line();
            line.setStartX(100+100*i);
            line.setEndX(line.getStartX());

            line.setStartY(200-15);
            line.setEndY(line.getStartY()+600+15);

            line.setStroke(Color.BLUE);
            line.setStrokeWidth(3);
            line.toBack();

            vertical.add(line);
            root.getChildren().add(vertical.get(i));
        }

        for (int i = 0; i < 7; i++)
        {
            Line line = new Line();
            line.setStartX(100);
            line.setEndX(line.getStartX()+700);

            line.setStartY(200+100*i);
            line.setEndY(line.getStartY());

            line.setStroke(Color.BLUE);
            line.setStrokeWidth(3);
            line.toBack();

            horizon.add(line);
            root.getChildren().add(horizon.get(i));
        }

        reset = new Button("Reset");
        reset.setScaleX(3);
        reset.setScaleY(3);
        reset.toBack();

        result = new Label("Red's turn");
        result.setScaleX(3);
        result.setScaleY(3);
        result.setTextFill(Color.RED);
        result.toBack();
        //mouseLocation = new Label();

        //Alignment
        //vBox = new VBox(150,reset,result, mouseLocation);
        vBox = new VBox(150,reset,result);
        vBox.setAlignment(Pos.CENTER);
        hBox = new HBox(175,root, vBox);
        hBox.setAlignment(Pos.CENTER);
        BigRoot.getChildren().add(hBox);
        BigRoot.setLayoutX(130);
        BigRoot.setLayoutY(140);

        buildPiece();
    }

    public static void main(String args[]){ launch(args); }


}
