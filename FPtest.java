import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;


public class FPtest{
    public static void main(String[] args) throws IOException {
        String output = ".//output.txt";

		FPGrowth fpg = new FPGrowth();

        fpg.writer = new BufferedWriter(new FileWriter(output));

        fpg.threshold = Double.parseDouble(args[1]);
        fpg.startTimestamp = System.currentTimeMillis();
		ArrayList<ArrayList<String>> ds = fpg.readFile(args[0], " ");
		fpg.FPAlgo(ds, null);

        fpg.writer.close();

        fpg.endTimestamp = System.currentTimeMillis();

        fpg.printStats();
	}
}
