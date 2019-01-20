/** DigitConnect4App.java
 *
 * Description: The basic game engine of connect 4, can reset, play moves,
 *              check if the game is over, or print the board with 1, -1, 0.
 *
 * Date: 10/22/2018
 * Version: 3
 * @author: Joseph Chang
 */

import java.util.Scanner;

public class DigitConnect4App
{
    protected int gameBoard[][];
    protected boolean redTurn;
    protected int steps;
    protected LastMove lastmove;
    protected winningPath winpath;

    //no args constructor
    public DigitConnect4App()
    {
        gameBoard = new int[6][7]; //array will be initiate as 0
        //rule of gameBoard: 1 = red, 0 = empty, -1 = black

        lastmove = new LastMove();
        redTurn = true;
        steps = 0;
    }

    //reset the game's information, wipe out the board
    public void Reset()
    {
        gameBoard = new int[6][7];
        lastmove = new LastMove();
        redTurn = true;
        steps = 0;
    }

    //return true if the move is successful.
    //return false if the move is denied.
    public boolean move(int column)
    {
        if (column < 0||column > 6) //check if column out of range
            return false;

        if (gameBoard[0][column] != 0) //check if the column is full
            return false;

        for(int i = 5; i >= 0; i--)
        {
            if (gameBoard[i][column] == 0)
            {
                if (redTurn == true)
                {
                    gameBoard[i][column] = 1;
                    lastmove.set(i,column,1);
                    redTurn = false;
                }
                else
                {
                    gameBoard[i][column] = -1;
                    lastmove.set(i,column,-1);
                    redTurn = true;
                }
                break;
            }
        }
        steps++;

        return true;
        //return true means the move is complete
        //return false means the move is failed
    }

    //Accessors
    public winningPath getWinpath()
    {
        return winpath;
    }

    public LastMove getLastmove()
    {
        return lastmove;
    }

    public boolean getRedTurn()
    {
        return redTurn;
    }

    //Print the board in digits
    public void printBoard()
    {
        for (int i = 0; i <= 5; i++)
        {
            for (int j = 0; j <= 6; j++)
            {
                if (gameBoard[i][j] != -1)
                    System.out.print("  " + gameBoard[i][j]);
                else
                    System.out.print(" " + gameBoard[i][j]);
            }
            System.out.println();
        }
    }

    //return 1 if red win
    //return -1 if black win
    //return 0 if no one win
    public int win()
    {
        int count = 0;
        int lastColor = 0;

        //check rows
        for(int i = 5; i >= 0; i--)
        {
            for(int j = 0; j <= 6; j++)
            {
                if(j == 0) //at zero column
                {
                    lastColor = gameBoard[i][0];
                    count = 1;
                }
                else if(gameBoard[i][j] == lastColor && gameBoard[i][j] != 0) //when the color is same as the last column
                    count++;
                else                                  //when the color is different from the last column
                {
                    count = 1;
                    lastColor = gameBoard[i][j];
                }

                if (count >= 4 && lastColor != 0) //Four same color connected together
                {
                    winpath = new winningPath(i,j-3,i,j-2,i,j-1,i,j);
                    return lastColor;
                }
            }
        }

        //check columns
        for(int j = 0; j <= 6; j++)
        {
            for(int i = 5; i >= 0; i--)
            {
                if(i == 5) //at lowest row
                {
                    lastColor = gameBoard[5][j];
                    count = 1;
                }
                else if(gameBoard[i][j] == lastColor && gameBoard[i][j] != 0)
                    count++;
                else
                {
                    count = 1;
                    lastColor = gameBoard[i][j];
                }

                if (count >= 4 && lastColor != 0)
                {
                    winpath = new winningPath(i,j,i+1,j,i+2,j,i+3,j);
                    return lastColor;
                }
            }
        }

        //check diagonal from left top (First three)
        for (int i = 2; i >= 0; i--)
        {
            for (int j = 0; j <= 5; j++)
            {
                int inew = i + j;
                if (inew > 5)
                    break;

                if (j == 0)
                {
                    lastColor = gameBoard[inew][0];
                    count = 1;
                }
                else if(gameBoard[inew][j] == lastColor && gameBoard[inew][j] != 0)
                    count++;
                else
                {
                    count = 1;
                    lastColor = gameBoard[inew][j];
                }

                if (count >= 4 && lastColor != 0)
                {
                    winpath = new winningPath(inew-3,j-3,inew-2,j-2,inew-1,j-1,inew,j);
                    return lastColor;
                }
            }
        }
        //check diagonal from left top (last three)
        for (int j = 1; j <= 3; j++)
        {
            for (int i = 0; i <= 5; i++)
            {
                int jnew = i + j;
                if (jnew > 6)
                    break;

                if (i == 0)
                {
                    lastColor = gameBoard[0][jnew];
                    count = 1;
                }
                else if(gameBoard[i][jnew] == lastColor && gameBoard[i][jnew] != 0)
                    count++;
                else
                {
                    count = 1;
                    lastColor = gameBoard[i][jnew];
                }

                if (count >= 4 && lastColor != 0)
                {
                    winpath = new winningPath(i-3,jnew-3,i-2,jnew-2,i-1,jnew-1,i,jnew);
                    return lastColor;
                }
            }
        }

        //check diagonal from right top (first three)
        for (int i = 2; i >= 0; i--)
        {
            for(int j = 6; j >= 1; j--)
            {
                int inew = i + 6 - j;
                if (inew > 5)
                    break;

                if (j == 6)
                {
                    lastColor = gameBoard[inew][6];
                    count = 1;
                }
                else if (gameBoard[inew][j] == lastColor && gameBoard[inew][j] != 0)
                    count++;
                else
                {
                    count = 1;
                    lastColor = gameBoard[inew][j];
                }

                if (count >= 4 && lastColor != 0)
                {
                    winpath = new winningPath(inew-3,j+3,inew-2,j+2,inew-1,j+1,inew,j);
                    return lastColor;
                }
            }
        }
        //check diagonal from right top (last three)
        for (int j = 5; j >= 3; j--)
        {
            for (int i = 0; i <= 5; i++)
            {
                int jnew = j - i;
                if (jnew < 0)
                    break;

                if (i == 0)
                {
                    lastColor = gameBoard[i][jnew];
                    count = 1;
                }
                else if (gameBoard[i][jnew] == lastColor && gameBoard[i][jnew] != 0)
                    count++;
                else
                {
                    count = 1;
                    lastColor = gameBoard[i][jnew];
                }

                if(count >= 4 && lastColor != 0)
                {
                    winpath = new winningPath(i-3,jnew+3,i-2,jnew+2,i-1,jnew+1,i,jnew);
                    return lastColor;
                }
            }
        }


        return 0;
    }

    //Assume win method return 0
    //Why shouldn't I check again? too save computation time, of course.
    //return true if the game is draw
    //return false if the game is not draw
    public boolean ifDraw()
    {
        if(steps >= 42)
            return true;
        else
            return false;
    }

    public DigitBoard getDigitBoard()
    {
        DigitBoard digitBoard = new DigitBoard();
        digitBoard.setGameBoard(gameBoard);
        digitBoard.setLastMove(lastmove);
        digitBoard.setRetTurn(redTurn);
        digitBoard.setStep(steps);

        return digitBoard;
    }

    public void setDigitBoard(DigitBoard digitBoard)
    {
        gameBoard = digitBoard.getGameBoard();
        lastmove = digitBoard.getLastMove();
        redTurn = digitBoard.getRedTurn();
        steps = digitBoard.getStep();
    }

    //Testing
    //There is a digits version of connect 4 here. if you are interested, you can play the game here.
    public static void main(String args[])
    {
        DigitConnect4App game = new DigitConnect4App();
        game.printBoard();

        Scanner keyboard = new Scanner(System.in);
        int move;

        while(true)
        {
            if(game.getRedTurn())
                System.out.println("It is Red's turn, please enter a column from 0~6");
            else
                System.out.println("It is Black's turn, please enter a column from 0~6");

            do {
                move = keyboard.nextInt();
            } while(!game.move(move));

            game.printBoard();
            int gameStatus = game.win();
            if (gameStatus == 1)
            {
                System.out.println("Red win the game!");
                break;
            }
            else if (gameStatus == -1)
            {
                System.out.println("Black win the game!");
                break;
            }
            else if (gameStatus == 0)
            {
                if (game.ifDraw()) {
                    System.out.println("Draw, no one wins!");
                    break;
                }
            }
        }

        System.out.println();
        System.out.print("Last move: ");
        game.getLastmove().Print();
        System.out.println();
        System.out.print("Winning path: ");
        game.getWinpath().Print();
    }
}
