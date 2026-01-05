
import java.io.*;
import java.util.*;

/*
    Context; Given a list of range x,y value find how many prime numbers lie between x and y
    This problem made me realize finding prime numbers can be rather hard, the solution I found works backwards, finding non-primes and excluding them
*/
public class Dante {
    public static void main(String[] args)throws IOException {
        Scanner s = new Scanner(new File("dante.dat"));

        while(s.hasNextLine()){
            int a = s.nextInt();
            int b = s.nextInt();

            //Making an array of all the numbers from 0-b
            int[] arr = new int[b+1];
            for(int i = 0;i<=b;i++){
                arr[i] = i;
            }

            //For 2-b, eliminate all multiples of those numbers (indicating non primes)
            for(int c = 2; c <= b-1;c++){
                for(int d = 2*c; d < b; d += c){
                    arr[d] = -1;
                }
            }
            //for (int e : arr) {System.out.print(e + " ");}
            //System.out.println();
            //For every number a-b that wasnt crossed out (with -1), add to the totalprimes
            int totalprimes = 0;
            for(int i=a+1;i<b;i++){
                if(arr[i] != -1){
                    totalprimes++;
                }
            }
            System.out.println(totalprimes);
        }
    }
}

/*
Example Input:
4 15
10 14
8 11
1 100000
100 1000
1000 10000
10000 100000
19 475
537 5384
4858 37574
123 234
345 456
567 789
890 12345
473 56375

Example Output:
4
2
0
9592
143
1061
8363
83
610
3330
21
19
35
1320
5623
*/