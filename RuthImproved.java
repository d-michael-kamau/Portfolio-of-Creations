import java.io.*;
import java.util.*;

/*
    Context; Given a list of height, figure out how many units of water could fill the divets
    I revisited this concept, inspired by sorting algorithms that use pointers
    There was lots of white board work, but eventually I figured out a better solution that works linearlly
    Also help from my computer science teacher!
 */

public class RuthImproved {

    public static int CalcWaterSpace(ArrayList<Integer> arr) {
        //Added a base case for when the water can never be held (hence why this is all in its own method)
        int n = arr.size();
        if (n <= 2) return 0;

        //Pointers, with max values
        int left = 0;
        int right = n - 1;
        int lMax = arr.get(left);
        int rMax = arr.get(right);
        int total = 0;

        //Keep moving pointers in until they overlap
        while (left < right) {
            //Find the smaller pointer, move it in
            //If the in movment got larger, make it the new max for the side
            //Add the total water if the move inwards was smaller based on the max of that side
            if (arr.get(left) < arr.get(right)) {
                left++;
                lMax = Math.max(lMax, arr.get(left));
                total += Math.max(0, lMax - arr.get(left));
            } else {
                right--;
                rMax = Math.max(rMax, arr.get(right));
                total += Math.max(0, rMax - arr.get(right));
            }
        }

        return total;
    }

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(new File("ruth.dat"));

        while (s.hasNextLine()) {
            String line = s.nextLine();
            ArrayList<Integer> arr = new ArrayList<>();
            Scanner b = new Scanner(line);
            while (b.hasNextInt()) {
                arr.add(b.nextInt());
            }

            System.out.println(CalcWaterSpace(arr));
        }
    }
}

/*
Example Input:
3 0 2 0 4
2 1 5 3 1 0 4 1 4
13 14 9 10 6 9 14 7 6 14 2 14 11 19 11 3 8 12 17 6 4 11 9 10 4 3 9 7  
8 8 15 5 17 19 13 17 15 0 10 6 4 6 5 16 17 12 3 8 16 0 11 9 2 6 0 3 4 
8 16 3 15 1 3 11 15 1 16 18 16 16 18 0 3 11 11 13 5 13 11 14 5 10 14 12 
16 15 10 8 2 16 10 7 0 2 19 2 6 3 1 3 11 8 2 5 10 0 16 2 1 10 14 5 16 4 19 8 14
14 1 0 7 19 17 3 18 19 3 3 4

Example Output:
7
12
110
133
125
322
55
*/