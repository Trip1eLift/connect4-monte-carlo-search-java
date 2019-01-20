/** LastMove.java
 *
 * Description: Designed to power DigitConnect4App. It stores winning coordinates of the game.
 *
 * Date: 10/20/2018
 * @author: Joseph Chang
 */

import java.util.ArrayList;

//Mainly used as struct data type, it store the winning coordinates of the game.
public class winningPath
{
    private ArrayList<TheData> fourData;

    //No args constructor
    public winningPath()
    {
        fourData = new ArrayList<TheData>();
    }

    //Ints constructor
    //Being used in board.java
    public winningPath(int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3)
    {
        fourData = new ArrayList<TheData>();
        fourData.add(new TheData(x0, y0));
        fourData.add(new TheData(x1, y1));
        fourData.add(new TheData(x2, y2));
        fourData.add(new TheData(x3, y3));
    }

    //Accessor
    public TheData get(int i)
    {
        return fourData.get(i);
    }

    //Print out the coordinates
    public void Print()
    {
        for (int i = 0; i < 4; i++)
        {
            System.out.print("( " + get(i).X + ", " + get(i).Y + ") ");
        }
    }

    //Return the winning coordinates as string array
    public String[] toStringArr()
    {
        String[] keys = new String[4];

        for (int i = 0; i < 4; i++)
        {
            keys[i] = Integer.toString(get(i).X);
            keys[i] += Integer.toString(get(i).Y);
        }

        return keys;
    }

    //Another class used as struct data type
    //Work as Nodes to store the coordinates
    //Note: X means row, Y means column. I know it's confusing, but I don't want to change.
    public class TheData
    {
        public int X; //row or I
        public int Y; //column or J

        public TheData(int x, int y)
        {
            X = x;
            Y = y;
        }
    }
}
