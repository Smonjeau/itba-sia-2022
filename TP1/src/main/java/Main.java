import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;


public class Main {



    public static void main(String[] args) throws IOException {





        Properties props = new Properties();
        props.load(Main.class.getResourceAsStream("sia.properties"));

        int [][] startingState={
                {1,5,2},
                {4,0,3},
                {7,8,6}
        };


        BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt"));



        switch(props.getProperty("alg").toUpperCase()){
            case "BPA":
                writer.write("------------ BPA ------------\n");
                Solver.bpa(startingState, writer);
                break;
            case "BPP":
                writer.write("\n------------ BPP ------------\n");
                Solver.bpp(startingState, writer);
                break;
            default:
                throw new IllegalArgumentException("<alg> property has to be a valid option");
        }





        writer.close();

     //   Solver.localHeuristic(new PuzzleState(startingState),writer,new Manhattan());


    }




}
