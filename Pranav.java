import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/*
    Concept; Both players take turns grabbing point values from each end of the list of integers
    The idea is to find the best score assuming both players play as ideally as possible
*/

public class Pranav{
    
     public static Map<String, int[]> memo = new HashMap<>();

    public static int[] Play(int curplr, ArrayList<Integer> map ) {
        //base case; theres only one point value up for grabs
        if (map.size() == 1) {
            return curplr == 0 ? new int[]{map.get(0),0} : new int[]{ 0,map.get(0)};
        }

        //Memocheck
         if (memo.containsKey(curplr + "," + map.toString())) {
                int[] choice =  memo.get(curplr + "," + map.toString());
                //I have to return new int[] because lines 33 and 40 sometimes effect the memo, due to mutability
                return new int[]{choice[0],choice[1]};
            }

        //Analyze choosing the first choice
        int S0 = map.get(0);
        ArrayList<Integer> map0 = new ArrayList<>(map);
        map0.remove(0);
        int[] result0 = Play((curplr+1)%2, map0);
        result0[curplr] += S0;

        //Analyzing the second choice
        int S1 = map.get(map.size()-1);
        ArrayList<Integer> map1 = new ArrayList<>(map);
        map1.remove(map.size()-1);
        int[] result1 = Play((curplr+1)%2, map1);
        result1[curplr] += S1;

        //Choosing best, adding to memo, retunring
        int[] bestoption = result0[curplr] > result1[curplr] ? result0 : result1;
        memo.put((curplr + "," + map.toString()), new int[]{bestoption[0],bestoption[1]});
        return bestoption;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("pranav.dat"));

        while(sc.hasNextLine()){
            //All of this just to go from string to int array :(
            String[] line = sc.nextLine().split(" ");
            ArrayList<Integer> game = new ArrayList<>();
            for (String l : line) {game.add(Integer.valueOf(l));}

            int[] score = Play(0, game);

            if (score[0] > score[1]) {
                System.out.println("Win: " + score[0]);
            } else if (score[0] < score[1]) {
                 System.out.println("Loss: " + score[0]);
            } else {
                System.out.println("Tie: " + score[0]);
            }
            memo.clear();
        }
    }

}

/*
Example Input:
10 7 3 5
10 10 10 10 10 10
8 15 3 7
10 10 10 12 10 10
10 10 -10 10 10 -10
10 -10 -10 10 10 -10
5 9 1 59 3 4 29 5 18 4 9 29 3 8
10 7 3 5 10 10 10 10 10 10 8 15 3 7 10 10 10 12 10 10 10 10 -10 10 10 -10 10 -10 -10 10 10 -10 5 9 1 59 3 4 29 5 18 4 9 29 3 8
10 -7 3 5 10 -10 10 10 -10 10 8 -15 3 -7 10 10 10 -12 10 10 -10 10 -10 -10 10 -10 10 -10 -10 10 10 -10 5 9 1 -59 3 -4 29 5 -18 4 9 -29 3 -8
34 34
34 35
1 1 1 1 1 9 1 1
6 8 4 9 5 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
212 17 94 78 212 93

Example Output:
Win: 15
Tie: 30
Win: 22
Win: 32
Tie: 10
Win: 10
Win: 118
Win: 221
Win: 96
Tie: 34
Win: 35
Win: 12
Win: 18
Tie: 11
Win: 518
*/