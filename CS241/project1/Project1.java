import java.util.Scanner;

public class Project1
{
    public static void main(String[] args)
    {
	Scanner kb = new Scanner(System.in);
	System.out.print("please enter the initial sequence of values (Seperate by space): ");
	String[] tmp = kb.nextLine().split(" ");
	Integer[] values = new Integer[tmp.length];   //Read in a line of values as a string and convert it
	for(int i = 0; i < tmp.length; i++)
	    values[i] = Integer.valueOf(tmp[i]);
	BinaryNode<Integer> BST = buildBST(values, 1, null, null);   // build the BST with given values in order
	printOrders(BST);    		//print pre, in, post orders
	showOptions(kb, BST, values);       // prompt options for users
    }

    public static BinaryNode<Integer> buildBST(Integer[] values, int i, BinaryNode<Integer> BST, BinaryNode<Integer> rootNode)   // keep a pointer to root for future use
    {				
	BinaryNode<Integer> root = new BinaryNode<Integer>(values[0]);    // root entry created	
	if(BST == null)	   // set BST as the root if it is the first entry
	{	    
	    BST = root;        	 
	    rootNode = BST;
	}
	if(i < values.length)  // recursion control as i < array length
	{
	    BinaryNode<Integer> newNode = new BinaryNode<Integer>(values[i]);  // new entry node created
	    if(BST.hasLeftChild() && values[i] < BST.getData())  // go down through left since it has a left child. Keep the index i because the entry is not added yet
		buildBST(values, i, BST.getLeftChild(), rootNode);
	    else if(BST.hasRightChild() && values[i] > BST.getData())   // go down through right since it has a right child. Keep the index i because the entry is not added yet
		buildBST(values, i, BST.getRightChild(), rootNode);        
	    else if(values[i] > BST.getData() && !BST.hasRightChild())
	    {
		BST.setRightChild(newNode);	// reach the available node to add as the right child
		buildBST(values, i + 1, rootNode, rootNode);  // increase the index after addition, and back to the root
	    }		
	    else if(values[i] < BST.getData() && !BST.hasLeftChild())
	    {
		BST.setLeftChild(newNode);	// reach the available node to add as the left child
		buildBST(values, i + 1, rootNode, rootNode);  // increase the index after addition, and back to the root
	    }	   
	}
	return BST;
    }

    public static void printOrders(BinaryNode<Integer> BST)
    {
	System.out.print("Pre-order: ");
	preTraverse(BST);
	System.out.println();
	System.out.print("In-order: ");
	inTraverse(BST);         
	System.out.println();
	System.out.print("Post-order: ");
	postTraverse(BST);
	System.out.println();
    }

    public static void preTraverse(BinaryNode<Integer> BST)
    {
	if(BST != null)
	{
	    System.out.print(BST.getData() + " ");
	    preTraverse(BST.getLeftChild());
	    preTraverse(BST.getRightChild());
	}
    }

    public static void inTraverse(BinaryNode<Integer> BST)
    {
	if(BST != null)
	{	   
	    inTraverse(BST.getLeftChild());
	    System.out.print(BST.getData() + " ");
	    inTraverse(BST.getRightChild());
	}
    }
 
    public static void postTraverse(BinaryNode<Integer> BST)
    {
	if(BST != null)
	{	    
	    postTraverse(BST.getLeftChild());
	    postTraverse(BST.getRightChild());
	    System.out.print(BST.getData() + " ");
	}
    }

    public static void showOptions(Scanner kb, BinaryNode<Integer> BST, Integer[] values)
    {
	String[] tmp;
	char userChoice = 'H';
	int userInput = 0;
	boolean given = false;    // boolean var to control userInput
	boolean done = false;   // boolean var to control deletion
	do
	{
	    if(userChoice == 'E')
		break;    // end immediately
	    System.out.println("I Insert a value");
	    System.out.println("D Delete a value");
	    System.out.println("P Find predecessor");
	    System.out.println("S Find successor");
	    System.out.println("E Exit the program");
	    System.out.println("H Display this message");
	    System.out.println("Command? ");
	    tmp = kb.nextLine().split(" ");	 
	    userChoice = tmp[0].charAt(0);   // same trick as the beginning
	    if(tmp.length > 1)
	    {
		userInput = Integer.valueOf(tmp[1]);
		given = true;          // > 1 means user gives both option and input value
	    }
	    if(userChoice == 'I')
	    {	       	
		if(!given)
		{
		    System.out.print("No input value given, please give a value: ");
		    userInput = kb.nextInt();		    
		}
		if(checkDuplicates(values, userInput))   //No duplicates in the BST 
		    System.out.println(userInput + " already exists, ignore"); 
		else
		{
		    values = updateArrayInsert(values, userInput);
		    insertValue(BST, userInput);
		    System.out.print("In-order: ");
		    inTraverse(BST);
		    System.out.println();	
		    given = false;		// reset boolean control for new input
		}
	    } 
	    if(userChoice == 'D')
	    {
		if(!given)
		{
		    System.out.print("No input value given, please give a value: ");
		    userInput = kb.nextInt();
		    given = true;
		}		
		if(!deleteValue(BST, userInput, values))		
		    System.out.println(userInput + "does not exist!");
		else
		{		    
		    values = updateArrayDelete(values, userInput);
		    System.out.print("In-order: "); 
		    inTraverse(BST);	
		    System.out.println();
		    given = false;		    
		}
	    }
	    if(userChoice == 'P')
		findPredecessor(BST, userInput, values);
	    if(userChoice == 'S')
		findSuccessor(BST, userInput, values);		
	} while(userChoice != 'E');
	System.out.println("Thank you for using my program!");
    }

    public static boolean checkDuplicates(Integer[] values, int userInput)
    {
	for(int i = 0; i < values.length; i++)
	{
	    if(values[i] == userInput)
		return true;	 	    
	}		   
	return false;
    }
    
    public static Integer[] updateArrayInsert(Integer[] values, int newValue)
    {
	//keep use values[] for future use of Predeccessor and Successor  ***	
	Integer[] tmp = new Integer[values.length + 1];
	for(int i = 0; i < values.length; i++)
	    tmp[i] = values[i];
	tmp[values.length] = newValue;
	values = tmp;
	return values;
    }

    public static void insertValue(BinaryNode<Integer> BST, int newValue)  
    {
	BinaryNode<Integer> newNode = new BinaryNode<Integer>(newValue); 
	recursiveInsertion(BST, newNode);   
	//BST = buildBST(values, 1, null, null);	  This can be possible as well, but more complex
    }

    public static void recursiveInsertion(BinaryNode<Integer> BST, BinaryNode<Integer> newNode)
    {	// almost same as buildBST();
	if(newNode.getData() < BST.getData() && BST.hasLeftChild())
	    recursiveInsertion(BST.getLeftChild(), newNode);
	else if(newNode.getData() < BST.getData() && !BST.hasLeftChild())
	    BST.setLeftChild(newNode);		
	else if(newNode.getData() > BST.getData() && BST.hasRightChild())
	    recursiveInsertion(BST.getRightChild(), newNode);
	else if(newNode.getData() > BST.getData() && !BST.hasRightChild())
	    BST.setRightChild(newNode);	
    }

    public static Integer[] updateArrayDelete(Integer[] values, int oldValue)
    {
	Integer[] tmp = new Integer[values.length - 1];
	for(int i = 0; i < values.length; i++)
	{
	    if(values[i] == oldValue)
	    {
		values[i] = null;	
		if(i != values.length - 1)
		    values[i] = values[values.length - 1];
		break;
	    }
	}    // keep values updated, for use of Prede and Succe
	for(int i = 0; i < tmp.length; i++)
	    tmp[i] = values[i];	
	values = tmp;	
	return values;	
    }
    
    public static boolean deleteValue(BinaryNode<Integer> BST, int oldValue, Integer[] values)
    {
	boolean found = false;
	for(int i = 0; i < values.length; i++)
	{
	    if(values[i] == oldValue)
		found = true;		
	}
	if(found == false)
	  return false;	
	return recursiveDeletion(BST, BST, oldValue);   // true if deleted
    }

    public static boolean recursiveDeletion(BinaryNode<Integer> BST, BinaryNode<Integer> prevNode, int oldValue)
    {       
	if(BST != null)
	{
	    if(oldValue < BST.getData())
	    {   // go down throught the path to find the value to be deleted
		// keep a pointer to the node before the node to be deleted in order to use the setL/RChild() method
		if(BST.getData() == prevNode.getData())
		    recursiveDeletion(BST.getLeftChild(), prevNode, oldValue);
		else
		{
		    if(prevNode.hasLeftChild() && BST.getData() == prevNode.getLeftChild().getData()) 
			recursiveDeletion(BST.getLeftChild(), prevNode.getLeftChild(), oldValue);
		    else
			recursiveDeletion(BST.getLeftChild(), prevNode.getRightChild(), oldValue);
		}			
	    }
	    else if(oldValue > BST.getData())
	    {
		if(BST.getData() == prevNode.getData())		
		    recursiveDeletion(BST.getRightChild(), prevNode, oldValue);
		else
		{
		    if(prevNode.hasLeftChild() && BST.getData() == prevNode.getLeftChild().getData())
			recursiveDeletion(BST.getRightChild(), prevNode.getLeftChild(), oldValue);
		    else
			recursiveDeletion(BST.getRightChild(), prevNode.getRightChild(), oldValue);
		}
	    }
	    else if(BST.isLeaf())    // case 1: delete it as a leaf
	    {
		if(prevNode.hasLeftChild() && BST.getData() == prevNode.getLeftChild().getData())  // check use setLeft or setRight
		    prevNode.setLeftChild(null);
		else
		    prevNode.setRightChild(null);
	    }
	    else if(BST.hasLeftChild() && !BST.hasRightChild())    // case 2: has one left child
	    {
		if(prevNode.hasLeftChild() && prevNode.getLeftChild().getData() == BST.getData())
		    prevNode.setLeftChild(BST.getLeftChild());
		else 
		    prevNode.setRightChild(BST.getLeftChild());		
	    }
	    else if(!BST.hasLeftChild() && BST.hasRightChild())   // has one right child
	    {
		if(prevNode.hasLeftChild() && prevNode.getLeftChild().getData() == BST.getData())
		    prevNode.setLeftChild(BST.getRightChild());
		else 
		    prevNode.setRightChild(BST.getRightChild());		
	    }
	    else   // case 3: delete it as it has two children
	    {		// set the node to be deleted as the rightmost node in its left subtree, and delete that "rightmost" node
	
		BST.setData(getRightMost(BST.getLeftChild()).getData());
		prevNode = BST.getLeftChild();
		while(prevNode.hasRightChild() && prevNode.getRightChild().getData() != BST.getData())
		    prevNode = prevNode.getRightChild();
		if(prevNode.getData() == BST.getLeftChild().getData() && !prevNode.hasRightChild())
		    BST.setLeftChild(null);		// maybe its left subtree root itself is the rightmost
		else
		    prevNode.setRightChild(null);		
	    }
	    return true;
	}	
	return false;
    }
    
    public static BinaryNode<Integer> getRightMost(BinaryNode<Integer> BST) 
    {
	BinaryNode<Integer> rightMost = BST;
	while(rightMost.hasRightChild())	
	    rightMost = rightMost.getRightChild();	    	
	return rightMost;
    }	

    public static void findPredecessor(BinaryNode<Integer> BST, int targetValue, Integer[] values)
    {
	// put the traverse into an array, making it easy to find the predeccessor as the previous index in the array
	Integer[] inTraverse = new Integer[values.length];	
	inOrderArray(BST, inTraverse);
	for(int i = 0; i < inTraverse.length; i++)
	{
	    if(inTraverse[i] == targetValue)
	    {
		if(i == 0)
		    System.out.println(targetValue + " does not have a predeccessor because it is the first value in the in-order traverse.");
		else
		    System.out.println("Predeccessor: " + inTraverse[i - 1]);
	    }	
	}			   				   
    }
   
    public static void inOrderArray(BinaryNode<Integer> BST, Integer[] inTraverse)
    {
	// similar as the print in-order method, just change the print to putIntoArray
	int i = 0;
	if(BST != null)
	{	    
	    inOrderArray(BST.getLeftChild(), inTraverse);
	    while(inTraverse[i] != null)
		i++;	      	    
	    inTraverse[i] = BST.getData();
	    inOrderArray(BST.getRightChild(), inTraverse);
	}
    }
   
    public static void findSuccessor(BinaryNode<Integer> BST, int targetValue, Integer[] values)
    {
	// put the traverse into an array, making it easy to find the successor as the previous index in the array
	Integer[] inTraverse = new Integer[values.length];	
	inOrderArray(BST, inTraverse);
	for(int i = 0; i < inTraverse.length; i++)
	{
	    if(inTraverse[i] == targetValue)
	    {
		if(i == inTraverse.length - 1)
		    System.out.println(targetValue + " does not have a sucessor because it is the last value in the in-order traverse.");
		else
		    System.out.println("successor: " + inTraverse[i + 1]);
	    }				   		
	}
    }
          
}