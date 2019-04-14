import java.util.ArrayList;
import java.io.IOException;


public class FPtest{
    public static void main(String[] args) throws IOException {
		FPGrowth fpg = new FPGrowth();
        fpg.threshold = Double.parseDouble(args[1]);
		ArrayList<ArrayList<String>> ds = fpg.readFile(args[0], " ");
		fpg.FPAlgo(ds, null);
	}
}
