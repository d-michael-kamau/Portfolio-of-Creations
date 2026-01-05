//"MAIN CODE"
/*
Context; A Simple terminal-based game made in java by Dalan Kamau and David Tran
The game is about traversing around a randomized 2d grid with randomized black hole spaces
Your body fills up the grid as you move, the goal being to just make as many moves as you can
There is also a unit test at the end of the document, that would be in another file for testing, as per project requirments
*/


import java.lang.*;
import java.util.*;

public class Centipede {

    public static String DataDisplay(int data) {
        String[] Pixels = {" ", "o", "@", "?"}; //Editable for Open space, Centipede body, BlackHoles, and Error displays 

        if (data == 0) {
            return Pixels[0];
        } else if (data == 1) { 
            return Pixels[1];
        } else if (data == 2)  {
            return Pixels[2];
        }
        return Pixels[3];
    }

    public static void PrintMap(int x, int y, int[][] map){
        for (int px = -1; px <= map.length ; px++){
            for (int py = -1; py <= map[1].length; py++) {
                try {
                    if (px == x && py == y) {
                        System.out.print("O");
                    } else {
                        System.out.print(DataDisplay(map[px][py]));
                    }
                } catch (Exception e) {             //BEYOND THE BARRIER
                    System.out.print("░");
                }
            }
            System.out.println("");
        }
    }

    public static void main(String[] args){
        int mines = 20;
        int[][] map = new int[10][20];

        //BIT DATA
        //0-1 If its a black hole
        //0-1 If its been filled (by the centipede)

        //add the blackholes
        for (int x = 0; x < map.length; x++) {
            for(int y=0; y< map[x].length; y++){
                if (Math.random() < 0.25 && mines > 0) {
                    map[x][y] ^= 2;
                } else {
                    map[x][y] ^= 0;
                }
            }
        }

        //Choosing the players start + Showing base map
        int PX = map.length/2;
        int PY = map[0].length/2;
        map[PX][PY] = 1;
        System.out.println("""
                
░█████╗░███████╗███╗░░██╗████████╗██╗██████╗░███████╗██████╗░███████╗
██╔══██╗██╔════╝████╗░██║╚══██╔══╝██║██╔══██╗██╔════╝██╔══██╗██╔════╝
██║░░╚═╝█████╗░░██╔██╗██║░░░██║░░░██║██████╔╝█████╗░░██║░░██║█████╗░░
██║░░██╗██╔══╝░░██║╚████║░░░██║░░░██║██╔═══╝░██╔══╝░░██║░░██║██╔══╝░░
╚█████╔╝███████╗██║░╚███║░░░██║░░░██║██║░░░░░███████╗██████╔╝███████╗
░╚════╝░╚══════╝╚═╝░░╚══╝░░░╚═╝░░░╚═╝╚═╝░░░░░╚══════╝╚═════╝░╚══════╝
                """);
        System.out.println("WELCOME to CENTIPEDE.");
        System.out.println("Objective; Move around the space-time, avoiding blackholes. You body will extend as you move around");
        System.out.println("Weave through the galaxy until you are stuck.");
        System.out.println("Your score will be determined based on how much of the map you can fill");
        PrintMap(PX, PY, map);

        int score = 0;
        Boolean cont = true;

        while (cont == true) {
            int[][] moves = {{0, -1}, {0,1},{-1,0},{1,0}};
           
            //Checking if the game is over
            boolean canmove = false;
            for (int[] m : moves) {
                try {
                    if (map[PX + m[0]][PY + m[1]] == 0) {
                    canmove = true;
                }
                } catch (Exception e) {
                }  
            }
            if (canmove == false) {
                cont = false;
                break;
            }
            
            System.out.print("Next Move (w,a,s,d);");
            Scanner input = new Scanner(System.in);
            String nextmove = input.nextLine();
            boolean valid = true;
            //Inputting player movement
            int[] mov = {0,0};
            switch (nextmove) {
                case "w":
                    mov = moves[2];
                    break;
                case "a":
                     mov = moves[0];
                    break;
                case "s":
                    mov = moves[3];
                    break;
                case "d":
                    mov = moves[1];
                    break;    
                default:
                    valid = false;
            }

            if (valid == false) {
                System.out.println("Move Invalid..");
                continue;
            }

            //Seeing if the movement is valid and moving the player
            assert cont == false : "The bug continues despite its fault, perhaps a 3d demension has be introduced?";
            try {
                if (map[PX + mov[0]][PY + mov[1]] != 0){
                    cont = false;
                    break;
                } else {
                    map[PX + mov[0]][PY + mov[1]] ^= 1;
                    PX += mov[0];
                    PY += mov[1];
                    score += 1;
                    PrintMap(PX, PY, map);
                }

            } catch (Exception e) {
                System.out.println("INVALID SPACE TIME TRAVERSAL!");
                cont = false;
                    break;
            }
             System.out.println("══════════════════════");
        }

        assert score > -1 : "The score has.. hit an asteriod";

        System.out.println("GAME OVER! Score; " + score);
    }
}
    


//"UNIT TEST"



import java.io.IOException;

public class UnitTest {

    public static void main(String[] args) throws IOException{
        TestDisplaySystem();

    }

    public static void TestDisplaySystem(){
        Centipede g = new Centipede();
        

        String Test0 = g.DataDisplay(0);
        String Test1 = g.DataDisplay(1);
        String Test2 = g.DataDisplay(2);
        String TestM = g.DataDisplay(3);
        String TestX = g.DataDisplay(Integer.MAX_VALUE + 1);

        assert Test0 == " " : "Normal Space Error";
        assert Test1 == "o" : "Centipede Body Display Error";
        assert Test2 == "@" : "Black Hole Display Error";
        assert TestM == "?" : "Centipede Vortex Collision Error";
        assert TestX == "?" : "Centipede Hyper-Space Error";
        
    }   
}
