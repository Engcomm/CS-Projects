// Junda Lou
// CS240
// 10.18.2016

import java.util.Random;
public class LinkedListSet<T> implements SetInterface<T>
{
    private Node firstNode;
    private int numberOfEntries;

    public LinkedListSet()
    {
	firstNode = null;
	numberOfEntries = 0;
    }

    public int getCurrentSize()
    {
	while (firstNode != null)
	{
	    numberOfEntries++;
	    firstNode = firstNode.next;
	}
	return numberOfEntries;
    }

    public boolean isEmpty()
    {
	if(firstNode == null)// the chain is not there if the first node is null
	    return true;
	return false;
    }

    public boolean add(T newEntry)
    {
	Node newNode = new Node(newEntry);
	newNode.next = firstNode;
	firstNode = newNode;      
	// start the chain by moving the original first node to the next  
	numberOfEntries++;
	return true;
    }

    private Node getReferenceTo(T anEntry)
    {
	boolean found = false;
	Node currentNode = firstNode;
	while(!found && (currentNode != null))  
	{
	    if(anEntry.equals(currentNode.data))
		found = true;         // quit if found
	    else
		currentNode = currentNode.next;
	    // quit if the currentNode is beyond the last one
	}
	return currentNode;
    }
	
    public boolean remove(T anEntry)
    {
	if(!contains(anEntry))
	    return false;
	if(isEmpty())
	    return false;
	Node nodeN = getReferenceTo(anEntry);
	if(nodeN != null)
	{
	    nodeN.data = firstNode.data;
	    firstNode = firstNode.next; // replace the first node and remove it
	    numberOfEntries--;
	}
	return true;
    }
    
    public T remove()
    {
        T removedEntry;
	Node nodeN = firstNode;
	Random removedEntryNumber = new Random(getCurrentSize());
	int entry = removedEntryNumber.nextInt();
	while(entry != 0)
	{
	    nodeN = nodeN.next;  
	    entry--;	    
	}
	removedEntry = nodeN.data;
	// get the entry to be removed with the index generated ramdomly
	if(nodeN != null)
	{
	    nodeN.data = firstNode.data;
	    firstNode = firstNode.next;
	    numberOfEntries--;
	}
	return removedEntry;
    }
    
    public void clear()
    {
	firstNode = null;  // unlink the chain by setting the first node to null
    }

    public boolean contains(T anEntry)
    {
	boolean found = false;
        Node currentNode = firstNode;
	while(!found && (currentNode != null))
	{
	    if(anEntry.equals(currentNode.data))
		found = true;
	    else
		currentNode = currentNode.next;
	}
	return found;
    }

    public T[] toArray()
    {
        int i = 0;
	if(isEmpty())
        {
	    T[] resultArray;
	    return resultArray;
	}
	T[] result = (T[]) new Object[getCurrentSize()];
	Node currentNode = firstNode;
	while((i < getCurrentSize()) && (currentNode != null))
	{
	    result[i] = currentNode.data;
	    i++;
	    currentNode = currentNode.next;
	}
	return result;
    }











    private class Node
    {
	private T data;
	private Node next;

	private Node(T dataPortion)
	{
	    this(dataPortion, null);
	}

	private Node(T dataPortion, Node nextNode)
	{
	    data = dataPortion;
	    next = nextNode;
	}
    }
}