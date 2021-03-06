public class DirectedGraph<T extends Comparable<? super T>>
{
    private boolean[][] edge;
    private int[][] weight;
    private T[] label;


    public DirectedGraph(int numV)
    {
	edge = new boolean[numV][numV];        // default constructor, set all edges to false, all weight to infinity and a empty array of labels with a length of the parameter
	@SuppressWarnings("unchecked")
	T[] tmp = (T[]) new Comparable[numV];
	label = tmp;
	weight = new int[numV][numV];
	for(int i = 0; i < numV; i++)
	    for(int j = 0; j < numV; j++)
		if(i == j)
		    weight[i][j] = 0;
		else
		    weight[i][j] = Integer.MAX_VALUE;
    }

    public int getSize()
    {
	return label.length;
    }
    
    public boolean isEdge(int source, int target)
    {
	return edge[source][target];
    }
    
    public int getWeight(int source, int target)
    {
	return weight[source][target];
    }

    public T getLabel(int vertex)
    {
	return label[vertex];
    }

    public int locateLabel(T targetLabel)
    {
	for(int i = 0; i < label.length; i++) 
	    if(label[i].compareTo(targetLabel) == 0)   // get the label index, that's why the label is a Comparable[]
		return i;
	return -1;
    }
    
    public void addLabel(int source, T labelData)
    {
	label[source] = labelData;
    }

    public void addEdge(int source, int target, int weightData)
    {
	edge[source][target] = true;  
	weight[source][target] = weightData;
    }

    public void removeEdge(int source, int target)
    {
	edge[source][target] = false;  
	weight[source][target] = Integer.MAX_VALUE;   // reset to default distance, infinity means no edge
    }       

    public boolean[][] getEdge()
    {
	return edge;
    }
    
    public void addVertex(T vertex)    // ignore this part, it's not used in this project
    {
	boolean[][] newEdge = new boolean[edge.length + 1][edge.length + 1];
	for(int i = 0; i < edge.length; i++)
	    for(int j = 0; j < edge.length; j++)
		newEdge[i][j] = edge[i][j];
	edge = newEdge;
	@SuppressWarnings("unchecked")
	T[] newLabel = (T[]) new Object[label.length + 1];
	for(int i = 0; i < label.length; i++)
	    newLabel[i] = label[i];
	newLabel[newLabel.length - 1] = vertex;
	label = newLabel;	 
    }
}