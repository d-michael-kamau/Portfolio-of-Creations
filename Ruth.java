
import java.io.*;
import java.util.*;

/*
    Context; Given a list of height, figure out how many units of water could fill the divets
    This was my initial awnser to this problem, though I want to figure out a way to go from quadratic down to linear time
*/

public class Ruth {
    public static void main(String[] args)throws IOException{
        Scanner s = new Scanner(new File("ruth.dat"));

        while(s.hasNextLine()){            
            String a = s.nextLine();
            ArrayList<Integer> arr = new ArrayList<>();
            Scanner b = new Scanner(a);
            while(b.hasNextInt()){
                arr.add(b.nextInt());
            }
            
            //For each value, find the largest on one side, and largest on the other
            //Find the difference between the smallest of the 2 sides, and add to the total
            int total = 0;
            for(int i = 1; i<arr.size(); i++){
                
                int bestL = 0;
                int bestR = 0;

                for(int L = 0; L < i; L++) {
                    bestL = Math.max(bestL, arr.get(L));
                }
                for(int R = arr.size()-1; R >= i; R--) {
                    bestR = Math.max(bestR, arr.get(R));
                }

                if(arr.get(i)< bestR && arr.get(i)<bestL) {
                    total += Math.min(bestL, bestR) - arr.get(i);
                }
            }
           
            System.out.println(total);
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