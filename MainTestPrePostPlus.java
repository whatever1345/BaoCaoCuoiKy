import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * Example of how to use PrePost+ algorithm from the source code.
 * @author Philippe Fournier-Viger, 2015
 */
public class MainTestPrePostPlus {

	public static void main(String [] args) throws IOException{

		String input = fileToPath(args[0]);
		String output = ".//output.txt";  // the path for saving the frequent itemsets found

		double minsup = Double.parseDouble(args[1]); // means a minsup of 2 transaction (we used a relative support)

		// Applying the algorithm
		PrePost prepost = new PrePost();
		// this line is to indicate that we want PrePost+ instead of PrePost
		prepost.setUsePrePostPlus(true);
		prepost.runAlgorithm(input, minsup, output);
		prepost.printStats();
	}

	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = MainTestPrePostPlus.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}
