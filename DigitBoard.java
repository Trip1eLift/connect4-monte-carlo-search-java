public class DigitBoard {
    private int gameBoard[][];
    private int step;
    private boolean redTurn;
    private LastMove lastMove;
    public DigitBoard()
    {
        gameBoard = new int[6][7];
        step = 0;
        redTurn = true;
        lastMove = new LastMove();

        //setGame is for testing
        //setGame();
    }

    private void setGame()
    {
        //Input board here
        step = 9;
        redTurn = false;
        gameBoard[5][3] = 1;
        gameBoard[4][4] = 1;
        gameBoard[3][3] = 1;
        gameBoard[2][3] = 1;
        gameBoard[5][5] = 1;
        gameBoard[4][3] = -1;
        gameBoard[5][4] = -1;
        gameBoard[3][4] = -1;
        gameBoard[4][5] = -1;
        lastMove.set(2,3,1);
    }

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


    public DigitBoard clone()
    {
        DigitBoard temp = new DigitBoard();
        int tempGameBoard[][] = new int[6][7];
        boolean tempRedTurn;
        int tempStep;
        LastMove tempLastMove = new LastMove();

        for(int i = 0; i < 6; i++)
        {
            for(int j = 0; j < 7; j++)
            {
                tempGameBoard[i][j] = gameBoard[i][j];
            }
        }
        tempRedTurn = redTurn;
        tempStep = step;
        tempLastMove.column = lastMove.column;
        tempLastMove.row = lastMove.row;
        tempLastMove.color = lastMove.color;

        temp.setGameBoard(tempGameBoard);
        temp.setLastMove(tempLastMove);
        temp.setRetTurn(tempRedTurn);
        temp.setStep(tempStep);

        return temp;
    }

    public int[][] getGameBoard()
    {
        return gameBoard;
    }

    public void setGameBoard(int[][] board)
    {
        gameBoard = board;
    }

    public boolean getRedTurn()
    {
        return redTurn;
    }

    public int getStep()
    {
        return step;
    }

    public LastMove getLastMove()
    {
        return lastMove;
    }

    public void setRetTurn(boolean redTurn)
    {
        this.redTurn = redTurn;
    }

    public void setStep(int step)
    {
        this.step = step;
    }

    public void setLastMove(LastMove lastMove)
    {
        this.lastMove = lastMove;
    }
}
