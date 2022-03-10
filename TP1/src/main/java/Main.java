import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {


        int [][] startingState={
                {1,5,2},
                {4,0,3},
                {7,8,6}
        };


        BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt"));

        writer.write("------------ BPA ------------\n");
        Solver.bpa(startingState, writer);
        writer.write("\n------------ BPP ------------\n");
        Solver.bpp(startingState, writer);
        writer.write("\n------------ BPPV ------------\n");
        Solver.bppv(startingState,20, writer);
        writer.close();

    }




}
