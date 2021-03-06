// Junda Lou
// CS240
// 10.18.2016

// everything similar to the previos ones, so less comments
import java.util.Random;
public class ArrayMultiset<T> implements MultisetInterface<T>
{
    private T[] Multiset;
    private int NumberOfEntries;
    private static final int DEFAULT_CAPACITY = 25;    

    public ArrayMultiset()
    {
	this(DEFAULT_CAPACITY);
    }
    
    public ArrayMultiset(int capacity)
    {
	    T[] tmpSet = (T[]) new Object[capacity];
	    Multiset = tmpSet;
	    NumberOfEntries = 0;
    }
    
    public int getCurrentSize()
    {
	for(int i = 0; i < Multiset.length; i++)
	{
	    if(Multiset[i] != null)
		NumberOfEntries++;
	}
	return NumberOfEntries;
    }

    public boolean isEmpty()
    {
        int i = 0;
	while(Multiset[i] == null)
	    i++;
	if(i == Multiset.length)
	    return true;
	else
	    return false;
    }

    public boolean add(T newEntry)
    {
        int i = 0;
	if(isFull())
	    return false;
	while(Multiset[i] != null)
	    i++;
	Multiset[i] = newEntry;
	return true;
    }
	
    public boolean remove(T anEntry)
    {
	int i = 0;
	if(!contains(anEntry))
	    return false;
	if(isEmpty())
	    return false;
	while(Multiset[i] != anEntry)
	    i++;
	Multiset[i] = Multiset[getCurrentSize()];
	Multiset[getCurrentSize()] = null;
	return true;
    }

    public T remove()
    {
        T result = null;
	if(isEmpty())
	    return result;
	Random unspecifiedEntry = new Random(getCurrentSize());
	int entry = unspecifiedEntry.nextInt();
	result = Multiset[entry];
	Multiset[entry] = Multiset[getCurrentSize()];
	Multiset[getCurrentSize()] = null;
	return result;
    }

    public void clear()
    {
	for(int i = 0; i < Multiset.length; i++)
	    Multiset[i] = null;
    }
	
    public int getFrequencyOf(T anEntry)
    {
        int frequency = 0;
	for(int i = 0; i < Multiset.length; i++)
	{    
	    if(Multiset[i].equals(anEntry))
		frequency++;
	}
	return frequency;
    }

    public boolean contains(T anEntry)
    {
	for(int i = 0; i < Multiset.length; i++)
	{    
	    if(Multiset[i].equals(anEntry))
		return true;
	}
	return false;
    }

    public T[] toArray()
    {
	if(isEmpty())
	{
	    T[] resultArray = Multiset;
	    return resultArray;
	}
	T[] resultArray = (T[])new Object[getCurrentSize()];	    
	for(int i = 0; i < getCurrentSize(); i++)
	{
	    resultArray[i] = Multiset[i];
	}
	return resultArray;
    }

    public boolean isFull()
    {
	return getCurrentSize() >= Multiset.length;
    }
}