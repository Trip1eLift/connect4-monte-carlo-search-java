/** LastMove.java
 *
 * Description: Designed to power DigitConnect4App. It stores the last move of the game.
 *
 * Date: 10/20/2018
 * @author: Joseph Chang
 */

//Mainly used as a struct data type, it store the last movement of the game
public class LastMove
{
    //It is mainly used as C++'s struct, so I set the variable to be public
    public int row;
    public int column;
    public int color; //Red: 1, Black: -1

    public void set(int Row, int Column, int Color)
    {
        row = Row;
        column = Column;
        color = Color;
    }

    //Print out the last move coordinate
    public void Print()
    {
        System.out.print("( " + row + ", " + column + "), Color: " + color);
    }

    //Return the last moving coordinates as string
    public String toStringKey()
    {
        String key = new String(Integer.toString(row));
        key += Integer.toString(column);

        return key;
    }
}
