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

import java.util.ArrayList;

public class BoardForMonte extends Board
{
    private Button computerPlay;

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
        computerPlay.setOnAction(new computerPlayButton());

        primaryStage.setTitle("Connect4");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
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

        computerPlay = new Button("Computer Play");
        computerPlay.setScaleX(2);
        computerPlay.setScaleY(2);
        computerPlay.toBack();

        result = new Label("Red's turn");
        result.setScaleX(3);
        result.setScaleY(3);
        result.setTextFill(Color.RED);
        result.toBack();
        //mouseLocation = new Label();

        //Alignment
        //vBox = new VBox(150,reset,result, mouseLocation);
        vBox = new VBox(150,computerPlay,reset,result);
        vBox.setAlignment(Pos.CENTER);
        hBox = new HBox(175,root, vBox);
        hBox.setAlignment(Pos.CENTER);
        BigRoot.getChildren().add(hBox);
        BigRoot.setLayoutX(130);
        BigRoot.setLayoutY(140);

        buildPiece();
    }

    //Being called when there is any movement on the mouse in the scene
    //It will trace the mouse to let the piece follow the mouse
    class setOnMouse implements EventHandler<MouseEvent>
    {
        @Override
        public void handle(javafx.scene.input.MouseEvent event) {
            //mouseLocation.setText("X: " + event.getX() + "\nY: " + event.getY());

            if (!(900 < event.getX()))
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

    class computerPlayButton implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            MonteCarloSearch search = new MonteCarloSearch(gameApp.getDigitBoard());
            int move = search.Search();

            if (gameApp.move(move) == false)
            {
                System.err.println("Error move by Monte Carlo Search!");
                gameApp.printBoard();
                DigitBoard tempDigitBoard = gameApp.getDigitBoard();
                Connect4AppOptimized tempGameApp = new Connect4AppOptimized(tempDigitBoard);
                tempGameApp.printPieceLine();
            }
            else
            {
                //Calling animation
                fallingAnimation = true;
                fallingAni(70);

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
                    System.out.println("-----------------------------");
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
                    System.out.println("-----------------------------");
                }
            }
        }
    }

    public static void main(String args[])
    { launch(); }
}
