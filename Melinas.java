
import java.io.*;
import java.util.*;

/*
Concept; Given an amount of change (derived from money given-owed), find how many different ways it can be represented with given coin values
This may be one of the hardest problems I've done to date
My first idea was tabulation going up in different change values, but this led to repeat coin representations in different orders
This methods of going up in the  coin values eliminates those repeats, adding to each table value ~once per coin
Also took me a while to realize table[0] should be 1 (and not 0)
It is still suprising how much this problem coould be boiled down to
*/

 public class Melinas {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("Melinas.dat"));

        while (sc.hasNextLine()) {
            //Making use of the input (I multiply everything by 100 to make it integers)
            int money = (int) (sc.nextDouble()*100 - (double)sc.nextDouble()*100);
            sc.nextLine();
            String n = sc.nextLine();
            String[] nl = n.split(" ");
            int[] dems = new int[nl.length];
            for (int i = 0; i < dems.length; i++) {
                dems[i] = (int) (Double.parseDouble(nl[i]) * 100.0);
            }
            
            //Table for how many ways a change amount can be made
            int[] table = new int[money + 1];

            //0 can only be represented in 1 way (which is no coins)
            table[0] = 1;

            //For each currency, analyze all the values from its value to max value
            //Adding the ways the remainder from the coin value to the desired value for each coin value will total the total ways the change can be made
            //This works since tabulation will allways find those smaller difference values first 
            for (int d:  dems) {
                for (int leftover = d; leftover <= money; leftover++) {
                    table[leftover] += table[leftover-d];
                }
            }

           System.out.println(table[money]);
        
        }
    }
}

/* 
Example Input:
25 24.78
.01 .05 .10 .25 1
40 39.15
.01 .05 .10 .25 1
50.26 41.95
.01 .05 .10 .25 1 2 5 10

Example Output:
9
163
332365
*/


