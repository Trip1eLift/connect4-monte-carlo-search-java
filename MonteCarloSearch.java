/** MonteCarloSearch.java
 *
 * Description: Use Monte Carlo Method to find what is the best step.
 *
 * Date: 12/25/2018
 * Version: 1
 * @author: Joseph Chang
 */

import java.util.concurrent.ThreadLocalRandom;

public class MonteCarloSearch
{
    final private DigitBoard boardInfo;
    private int targetColor;

    public MonteCarloSearch(DigitBoard digitBoard)
    {
        boardInfo = new DigitBoard();
        boardInfo.setStep(digitBoard.getStep());
        boardInfo.setRetTurn(digitBoard.getRedTurn());
        boardInfo.setLastMove(digitBoard.getLastMove());
        boardInfo.setGameBoard(digitBoard.getGameBoard());

        if (boardInfo.getRedTurn())
            targetColor = 1;
        else
            targetColor = -1;
    }

    private int randomNum()
    {
        return ThreadLocalRandom.current().nextInt(0, 6 + 1);
    }

    public int Search()
    {
        final int searchRounds = 100000;
        Connect4AppOptimized tempGame;
        double score[] = new double[7];
        for (int j = 0; j < 7; j++)
        {
            score[j] = 0.0;
        }
        boolean cannotMove[] = new boolean[7];

        for (int j = 0; j < 7; j++)
        {
            tempGame = new Connect4AppOptimized(boardInfo.clone());
            int Step = boardInfo.getStep();

            int pieceLine[] = tempGame.getPieceLineClone();
            if (pieceLine[j] == -1)
            {
                cannotMove[j] = true;
            }
            else
            {
                for (int n = 0; n < searchRounds; n++)
                {
                    tempGame = new Connect4AppOptimized(boardInfo.clone());
                    tempGame.move(j);
                    boolean flag = true;
                    if(tempGame.win() == targetColor)
                    {
                        score[j] += 1.0;
                        flag = false;
                    }
                    else if(tempGame.ifDraw())
                    {
                        score[j] = 0.0;
                        break;
                    }

                    //Start monte carlo here
                    while (flag)
                    {
                        int count = 0;
                        while (tempGame.move(randomNum()) == false)
                        {
                            count++;
                            if (count > 50) //if fail to move in 20 times, skip
                            {
                                flag = false;
                                break;
                            }
                        }

                        if(flag == false)
                            break;

                        int win = tempGame.win();
                        if (win != 0)
                        {
                            if (win == targetColor)
                            {
                                //Positive score change
                                //double scale = tempGame.getDigitBoard().getStep() - Step + 1.0;
                                //score[j] = score[j] + (1.0/scale);
                                score[j]++;
                            }
                            else
                            {
                                //Negative score change
                                //double scale = tempGame.getDigitBoard().getStep() - Step + 1.0;
                                //score[j] = score[j] - (1.0/scale);
                                score[j]--;
                            }

                            flag = false;
                        }
                        else if (tempGame.ifDraw())
                            flag = false;

                    }
                }
            }

        }

        int maxIndex = 0;
        if (cannotMove[0] == true)
        {
            for (int j = 1; j < 7; j++)
            {
                if (cannotMove[j] == false)
                {
                    maxIndex = j;
                    break;
                }
            }
        }

        for (int j = maxIndex + 1; j < 7; j++)
        {
            if (score[j] > score[maxIndex] && cannotMove[j] == false)
                maxIndex = j;
        }

        printSearchResult(score, maxIndex, searchRounds);

        return maxIndex;
    }

    public void printSearchResult(double score[], int move, int searchRounds)
    {
        for (int j = 0; j < 7; j++)
        {
            double rate = score[j] / (double)searchRounds;
            System.out.printf("%.2f", rate);
            //System.out.printf("%.2f", score[j]);
            System.out.print(" ");
        }
        System.out.println();

        //Average
        double sum = score[0];
        for (int j = 1; j < 7; j++)
        {
            sum += score[j];
        }
        double ave = sum / (7.0 * searchRounds);
        if (targetColor == 1)
            System.out.print("Red's ");
        else
            System.out.print("Black's ");
        System.out.print("overall score: ");
        System.out.printf("%.3f", ave);
        System.out.println();

        System.out.println(targetColor + " move: " + move);
    }

    public static void main(String args[])
    {
        DigitBoard digitBoard = new DigitBoard();
        digitBoard.printBoard();

        MonteCarloSearch search = new MonteCarloSearch(digitBoard);

        search.Search();
    }
}
