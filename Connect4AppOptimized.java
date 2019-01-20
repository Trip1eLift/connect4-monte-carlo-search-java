/** Connect4AppOptimized.java
 *
 * Description: Do what DigitConnect4App.java does, but in a more efficient way
 *
 * Date: 12/25/2018
 * Version: 1
 * @author: Joseph Chang
 */

import java.util.Scanner;

public class Connect4AppOptimized extends DigitConnect4App
{
    protected int pieceLine[];

    public Connect4AppOptimized()
    {
        super();
        pieceLine = new int[7];

        for(int i = 0; i < 7; i++)
        {
            pieceLine[i] = 5;
        }
    }

    public Connect4AppOptimized(DigitBoard initialBoard)
    {
        super();
        pieceLine = new int[7];

        gameBoard = initialBoard.getGameBoard();
        redTurn = initialBoard.getRedTurn();
        steps = initialBoard.getStep();
        lastmove = initialBoard.getLastMove();

        for (int j = 0; j < 7; j++)
        {
            pieceLine[j] = -1;
        }

        for(int j = 0; j < 7; j++)
        {
            for(int i = 5; i >= 0; i--)
            {
                if(gameBoard[i][j] == 0)
                {
                    pieceLine[j] = i;
                    break;
                }
            }
        }
    }

    public int[] getPieceLineClone()
    {
        int tempPieceLine[] = new int[7];
        for (int j = 0; j < 7; j++)
        {
            tempPieceLine[j] = pieceLine[j];
        }
        return tempPieceLine;
    }

    //return true if the move is successful.
    //return false if the move is denied.
    @Override
    public boolean move(int column)
    {
        //start here, using pieceLine[7]
        if(pieceLine[column] == -1)
            return false;
        else
        {
            int color;
            if(lastmove.color == 1)
                color = -1;
            else
                color = 1;

            gameBoard[pieceLine[column]][column] = color;
            lastmove.column = column;
            lastmove.row = pieceLine[column];
            lastmove.color = color;
            if (pieceLine[column] == 0)
                pieceLine[column] = -1;
            else
                pieceLine[column] = pieceLine[column] - 1;

            return true;
        }
    }

    //return 1 if red win
    //return -1 if black win
    //return 0 if no one win
    @Override
    public int win()
    {
        int count = 0;

        //check rows
        if (lastmove.row > 2)
        {}
        else if (gameBoard[lastmove.row+1][lastmove.column] == lastmove.color
            && gameBoard[lastmove.row+2][lastmove.column] == lastmove.color
            && gameBoard[lastmove.row+3][lastmove.column] == lastmove.color)
            return lastmove.color;

        //check column
        int j = lastmove.column - 3;
        int jMax = lastmove.column + 3;
        if (j < 0)
            j = 0;
        if (jMax > 6)
            jMax = 6;
        for (; j <= jMax; j++)
        {
            if(gameBoard[lastmove.row][j] == lastmove.color)
                count++;
            else
                count = 0;

            if(count == 4)
                return lastmove.color;
        }

        //Not sure if this part will be faster in computation
        //check left top to right bottom
        count = 0;
        for(int n = -3; n <= 3; n++)
        {
            if((lastmove.row + n) < 0 || (lastmove.column + n) < 0)
                continue;
            if((lastmove.row + n) > 5 || (lastmove.column + n) > 6)
                break;

            if(gameBoard[lastmove.row + n][lastmove.column + n] == lastmove.color)
                count++;
            else
                count = 0;

            if(count == 4)
                return lastmove.color;
        }

        //check right top to left bottom
        count = 0;
        for(int n = -3; n <= 3; n++)
        {
            if((lastmove.row + n) < 0 || (lastmove.column - n) > 6)
                continue;
            if((lastmove.row + n) > 5 || (lastmove.column - n) < 0)
                break;

            if(gameBoard[lastmove.row + n][lastmove.column - n] == lastmove.color)
                count++;
            else
                count = 0;

            if(count == 4)
                return lastmove.color;
        }

        return 0;
    }

    public void setBoard(int[][] board, int[] pieceline)
    {
        gameBoard = board;
        pieceLine = pieceline;
    }

    public void printPieceLine()
    {
        System.out.print("Piece line: ");
        for (int j = 0; j < 7; j++)
            System.out.print(pieceLine[j] + " ");
        System.out.println();
    }
    //TESTING
    public static void main(String args[])
    {
        DigitBoard initialBoard = new DigitBoard();
        Connect4AppOptimized game = new Connect4AppOptimized(initialBoard);
        game.printBoard();

        Scanner keyboard = new Scanner(System.in);
        int move;

        while(true)
        {
            if (game.getRedTurn())
                System.out.println("It is Red's turn, please enter a column from 0~6");
            else
                System.out.println("It is Black's turn, please enter a column from 0~6");

            do {
                move = keyboard.nextInt();
            } while(!game.move(move));

            game.printBoard();
            for (int j = 0; j < 7; j++)
            {
                System.out.print(game.getPieceLineClone()[j] + " ");
            }
            System.out.println();
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
