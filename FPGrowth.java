import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FPGrowth {
    //record time
    public long startTimestamp;
	public long endTimestamp;
    public int outputCount = 0;

    public double threshold;
	private double MIN_SUPPORT;
    private int numOfTrans;
	/**
	 *
	* @Title: itemSort
	* @Description: sort every line in itemSet according to itemMap
	* @param itemMap
	* @param itemSet
	* @return void
	* @throws
	 */
	public void itemSort(final Map<String, Integer> itemMap, ArrayList<ArrayList<String>> itemSet) {
		for(ArrayList<String> items : itemSet) {
			Collections.sort(items, new Comparator<String>() {
				@Override
				public int compare(String key1, String key2) {
					return itemMap.get(key2) - itemMap.get(key1);
				}
			});
		}
	}

	/**
	 *
	* @Title: buildHeadTable
	* @Description: build head table for FP tree
	* @param itemSet
	* @return ArrayList<FPTreeNode>
	* @throws
	 */
	public ArrayList<FPTreeNode> buildHeadTable(ArrayList<ArrayList<String>> itemSet) {
		ArrayList<FPTreeNode> head = new ArrayList<FPTreeNode>();

		Map<String, Integer> itemMap = new HashMap<String, Integer>();
		for(ArrayList<String> items : itemSet) {
			for(String item : items) {
				if(itemMap.get(item) == null) {
					itemMap.put(item, 1);
				} else {
					itemMap.put(item, itemMap.get(item) + 1);
				}
			}
		}

        MIN_SUPPORT = threshold * numOfTrans;
        MIN_SUPPORT = Math.ceil(MIN_SUPPORT);

		Iterator<String> ite = itemMap.keySet().iterator();
		String key;
		List<String> abandonSet = new ArrayList<String>();
		while(ite.hasNext()) {
			key = (String)ite.next();
			if(itemMap.get(key) <MIN_SUPPORT) {
				ite.remove();
				abandonSet.add(key);
			} else {
				FPTreeNode tn = new FPTreeNode();
				tn.increCounts();
				tn.setItem(key);
				tn.setCounts(itemMap.get(key));
				head.add(tn);
			}
		}

		for(ArrayList<String> items : itemSet) {
			items.removeAll(abandonSet);
		}

		itemSort(itemMap, itemSet);

		Collections.sort(head, new Comparator<FPTreeNode>() {
			@Override
			public int compare(FPTreeNode key1, FPTreeNode key2) {
				return key2.getCounts() - key1.getCounts();
			}
		});
		return head;
	}

	/**
	 *
	* @Title: findChildNode
	* @Description: find position for an item as build a FP tree
	* @param item
	* @param curNode
	* @return FPTreeNode
	* @throws
	 */
	public FPTreeNode findChildNode(String item, FPTreeNode curNode) {
		List<FPTreeNode> childs = curNode.getChildNodes();
		if(null != childs) {
			for(FPTreeNode tn : curNode.getChildNodes()) {
				if(tn.getItem().equals(item)) {
					return tn;
				}
			}
		}
		return null;
	}

	/**
	 *
	* @Title: addAdjNode
	* @Description: link the nodes with the same name to the head table
	* @param
	* @return void
	* @throws
	 */
	public void addAdjNode(FPTreeNode tn, ArrayList<FPTreeNode> head) {
		FPTreeNode curNode = null;
		for(FPTreeNode node : head) {
			if(node.getItem().equals(tn.getItem())) {
				curNode = node;
				while(null != curNode.getNextNode()) {
					curNode = curNode.getNextNode();
				}
				curNode.setNextNode(tn);
			}
		}
	}

	/**
	 *
	* @Title: buildFPTree
	* @Description: build FP tree
	* @param itemSet
	* @param head
	* @param @return
	* @return FPTreeNode
	* @throws
	 */
	public FPTreeNode buildFPTree(ArrayList<ArrayList<String>> itemSet, ArrayList<FPTreeNode> head) {
		FPTreeNode root = new FPTreeNode();
		FPTreeNode curNode = root;

		for(ArrayList<String> items : itemSet) {
			for(String item : items) {
				FPTreeNode tmp = findChildNode(item, curNode);
				if(null == tmp) {
					tmp = new FPTreeNode();
					tmp.setItem(item);
					tmp.setParentNode(curNode);
					curNode.getChildNodes().add(tmp);
					addAdjNode(tmp, head);
				}
				curNode = tmp;
				tmp.increCounts();
			}
			curNode = root;
		}
		return root;
	}

	/**
	 *
	* @Title: FPAlgo
	* @Description: TODO
	* @param itemSet
	* @param candidatePattern
	* @return void
	* @throws
	 */

    public BufferedWriter writer = null;

	public void FPAlgo(ArrayList<ArrayList<String>> itemSet, ArrayList<String> candidatePattern) throws IOException {
        MemoryLogger.getInstance().reset();

        StringBuilder buffer = new StringBuilder();

		// build head table
		ArrayList<FPTreeNode> head = buildHeadTable(itemSet);

		// build FP tree
		FPTreeNode root = buildFPTree(itemSet, head);

		// recursion exit
		if(root.getChildNodes().size() == 0) {
			return;
		}

		// print pattern
		if(null != candidatePattern) {
			for(FPTreeNode tn : head) {
				for(String s : candidatePattern) {
					System.out.print(s + " ");
                    buffer.append(s);
                    buffer.append(' ');
				}
				System.out.println(tn.getItem() + " : " + tn.getCounts());
                buffer.append(tn.getItem());
                buffer.append(" : ");
                buffer.append(tn.getCounts());
                buffer.append("\n");
                outputCount++;
			}
            writer.write(buffer.toString());
		}


		for(FPTreeNode hd : head) {
			ArrayList<String> pattern = new ArrayList<String>();
			pattern.add(hd.getItem());

			if(null != candidatePattern) {
				pattern.addAll(candidatePattern);
			}

			// find conditional pattern base
			ArrayList<ArrayList<String>> newItemSet = new ArrayList<ArrayList<String>>();
			FPTreeNode curNode = hd.getNextNode();

			while (curNode != null) {
                int counter = curNode.getCounts();
                ArrayList<String> parentNodes = new ArrayList<String>();
                FPTreeNode parent = curNode;

                // traverse all parent nodes of curNode and put them into parentNodes
                while ((parent = parent.getParentNode()).getItem() != null) {
                    parentNodes.add(parent.getItem());
                }
                while (counter-- > 0) {
                	newItemSet.add(parentNodes);
                }
                curNode = curNode.getNextNode();
            }

            // recursive process
			FPAlgo(newItemSet, pattern);

            MemoryLogger.getInstance().checkMemory();
		}
	}

	/**
	 *
	* @Title: readFile
	* @Description: Read a file and split it into a array list
	* @param path
	* @param @return
	* @param @throws IOException
	* @return ArrayList<ArrayList<String>>
	* @throws
	 */
	public ArrayList<ArrayList<String>> readFile(String path, String separator) throws IOException {
		File f = new File(path);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String str;
		ArrayList<ArrayList<String>> dataSet = new ArrayList<ArrayList<String>>();
		while((str = reader.readLine()) != null) {
			if(!"".equals(str)) {
				ArrayList<String> tmpList = new ArrayList<String>();
				String[] s = str.split(separator);
				for(int i = 0; i <s.length; i++) {
					tmpList.add(s[i]);
				}
				dataSet.add(tmpList);
			}
		}
        numOfTrans = dataSet.size();
		return dataSet;
	}

    public void printStats() {
		System.out.println("========== Frequent Pattern - STATS ============");
		System.out.println(" Minsup = " + threshold
				+ "\n Number of transactions: " + numOfTrans);
		System.out.println(" Number of frequent  itemsets: " + outputCount);
		System.out.println(" Total time ~: " + (endTimestamp - startTimestamp)
				+ " ms");
		System.out.println(" Max memory:"
				+ MemoryLogger.getInstance().getMaxMemory() + " MB");
		System.out.println("=====================================");
	}
}
