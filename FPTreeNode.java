import java.util.ArrayList;
import java.util.List;

public class FPTreeNode{
    private String item;
	private FPTreeNode parentNode;
	private List<FPTreeNode> childNodes = new ArrayList<FPTreeNode>();
	private int counts;
	private FPTreeNode nextNode;

    public String getItem(){
	    return item;
	}

	public void setItem(String item){
	    this.item = item;
	}

    public FPTreeNode getParentNode(){
        return parentNode;
	}

    public void setParentNode(FPTreeNode parentNode){
        this.parentNode = parentNode;
    }

    public List<FPTreeNode> getChildNodes(){
        return childNodes;
    }

    public void setChildNodes(List<FPTreeNode> childNodes){
        this.childNodes = childNodes;
    }

    public int getCounts(){
        return counts;
    }

    public void increCounts(){
        this.counts = counts + 1;
    }

    public FPTreeNode getNextNode(){
        return nextNode;
    }

    public void setNextNode(FPTreeNode nextNode){
        this.nextNode = nextNode;
    }

    public void setCounts(int counts){
        this.counts = counts;
    }
}
