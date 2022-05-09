import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Statistics {

    public static void main(String[] args) throws IOException {
        Ej2Analisis();
    }

    public static void Ej2Analisis() throws IOException {
        int samples = 100;
        double error = 0;

        BufferedWriter writer = new BufferedWriter(new FileWriter("ej2_1.csv"));
        writer.write("type,error\n");

        for (int i = 0; i < samples; i++) {
            writer.write(String.format("Linear,%g",Main.Ej2Lineal(0.01, 10000)));
            writer.newLine();
        }

        for (int i = 0; i < samples; i++) {
            writer.write(String.format("NotLinear,%g",Main.Ej2NotLineal(0.01, 10000)));
            writer.newLine();
        }

        writer.close();
    }
}
