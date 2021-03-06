import java.io.*;
import java.util.*;

public class Project3
{
    public static void main(String[] args) throws FileNotFoundException
    {
	String[] cityData = readInData();
	int[] cityNum = getCityNum(cityData);
	String[] cityCode = getCityCode(cityData);	// parallel data arrays for each city info
	String[] cityName = getCityName(cityData);	
	int[] population = getPopulation(cityData);
	int[] elevation = getElevation(cityData);
	int[] road = getRoadData();
	printPrompt(cityNum, cityCode, cityName, population, elevation, road);
    }

    public static void printPrompt(int[] cityNum, String[] cityCode, String[] cityName, int[] population, int[] elevation, int[] road) throws FileNotFoundException
    {
	Scanner kb = new Scanner(System.in);
	char command = 'H';
	String[] data;
	System.out.println("Q Query the information by entering the city code.");
	System.out.println("D Find the minimum distance between two cities.");
	System.out.println("I Insert a road by entering two city codes and distance.");
	System.out.println("R Remove an existing road by entering two city codes.");
	System.out.println("H Display this message.");
	System.out.println("E Exit.");
	while(command != 'E') {
	    System.out.print("Command: ");
	    command = kb.nextLine().charAt(0);
	    if(command == 'Q') {
		System.out.print("City Code: ");
		data = kb.nextLine().split(" ");
		getCityInfo(data[0], cityNum, cityCode, cityName, population, elevation);
	    }
	    else if(command == 'D') {
		System.out.print("City Codes: ");
		data = kb.nextLine().split(" ");
		getRoute(data, cityCode, cityName, road);
	    }
	    else if(command == 'I') {		
		System.out.print("City codes and distance: ");
		data = kb.nextLine().split(" ");				
		road = insertRoad(data, road, cityName, cityCode);	
	    }
	    else if(command == 'R') {
		System.out.print("City codes: ");
		data = kb.nextLine().split(" ");	
		road = removeRoad(data, road, cityName, cityCode);
	    }
	    else if(command == 'H') {
		System.out.println("Q Query the information by entering the city code.");
		System.out.println("D Find the minimum distance between two cities.");
		System.out.println("I Insert a road by entering two city codes and distance.");
		System.out.println("R Remove an existing road by entering two city codes.");
		System.out.println("H Display this message.");
		System.out.println("E Exit.");
	    }		
	}
    }
	
    public static int[] removeRoad(String[] data, int[] road, String[] cityName, String[] cityCode)
    {
	int source = 0, target = 0, index = -1, j = 0;
	for(int i = 0; i < 20; i++) {
	    if(cityCode[i].compareTo(data[0]) == 0)  // find the source and target
		source = i;
	    if(cityCode[i].compareTo(data[1]) == 0)
		target = i;
	}  
	for(int i = 0; i < road.length / 3; i++) {
	    if(road[3*i] == source && road[3*i+1] == target) {
		index = i;    // get the source in the road[]
		break;
	    }
	}
	if(index == -1)   // means not found
	    System.out.println("The road from " + cityName[source] + " to " + cityName[target] + " does not exist.");
	else {
	    int[] newRoad = new int[road.length - 3];
	    for(int i = 0; i < index; i++)   // 2 parts, 0 --> index
		newRoad[i] = road[i];
	    j = index;
	    while(j < newRoad.length) {     // index --> .length
		newRoad[j] = road[index + 3];
		j++;
		index++;
	    }
	    System.out.println("You have removed a road from " + cityName[source] + " to " + cityName[target]);
	    return newRoad;	
	}
	return road;  // return itself if nothing changes
    }

    public static int[] insertRoad(String[] data, int[] road, String[] cityName, String[] cityCode)
    {
	int source = 0, target = 0;
	for(int i = 0; i < 20; i++) {   // find source and target
	    if(cityCode[i].compareTo(data[0]) == 0)
		source = i;
	    if(cityCode[i].compareTo(data[1]) == 0)
		target = i;
	}
	int[] newRoad = new int[road.length + 3];
	newRoad[road.length] = source;
	newRoad[road.length + 1] = target;     // add 3 slots for new road
	newRoad[road.length + 2] = Integer.valueOf(data[2]);
	System.out.println("You have inserted a road from " + cityName[source] + " to " + cityName[target] + " with a distance of " + data[2]);
	return newRoad;
    }
	
    public static void getRoute(String[] data, String[] cityCode, String[] cityName, int[] road) throws FileNotFoundException
    {
	int source = 0, target = 0;	
	for(int i = 0; i < cityCode.length; i++) {
	    if(data[0].compareTo(cityCode[i]) == 0)  // find source and target
		source = i;
	    if(data[1].compareTo(cityCode[i]) == 0)
		target = i;
	}
	DirectedGraph<String> graph = getGraph(cityCode, road);  // create a graph base on the road[] and city code
	Dijkstra<String> dij = new Dijkstra<String>(graph); // create a Dij alg to find the shortest path
	int[] route = dij.getMinRoute(source, target);
	int distance = dij.getMinDistance(source, target);
	System.out.print("The minimum distance between " + cityName[source] + " and " + cityName[target] + " is " + distance + " through the route: ");
	for(int i = 0; i < route.length; i++)
	    System.out.print(cityName[route[i]] + ", ");
	System.out.println();
    }
    
    public static void getCityInfo(String code, int[] cityNum, String[] cityCode, String[] cityName, int[] population, int[] elevation)
    {
	int index = 0;
	for(int i = 0; i < cityCode.length; i++)
	    if(code.compareTo(cityCode[i]) == 0)
		index = i;
	System.out.print(cityNum[index] + " ");
	System.out.print(cityCode[index] + " ");
	System.out.print(cityName[index] + " ");
	System.out.print(population[index] + " ");
	System.out.print(elevation[index] + " ");
	System.out.println();
    }

    public static int[] getRoadData() throws FileNotFoundException
    {
	int count = 0;
	Scanner sc = new Scanner(new File("road.dat"));
	while(sc.hasNext()) {	    
	    count++;
	    sc.nextInt();
	}
	int[] road = new int[count];	
	Scanner sc2 = new Scanner(new File("road.dat"));
	for(int i = 0; i < count; i++)
	    road[i] = sc2.nextInt();		      
	return road;
    }

    public static DirectedGraph<String> getGraph(String[] cityCode, int[] road)
    {
	DirectedGraph<String> graph = new DirectedGraph<String>(20);
	for(int i = 0; i < road.length / 3; i++)
	    graph.addEdge(road[3 * i] - 1, road[3 * i+1] - 1, road[3 * i+2]);
	for(int i = 0; i < 20; i++)
	    graph.addLabel(i, cityCode[i]);
	return graph;
    }

    public static String[] readInData() throws FileNotFoundException
    {
	Scanner sc_count = new Scanner(new File("city.dat"));	
	int count = 0;
	int i = 0, j = 0;
	while(sc_count.hasNext())
        {
	      sc_count.next();
	      count++;
	}
	Scanner sc_read = new Scanner(new File("city.dat"));
	String[] data = new String[count];
	for(i = 0; i < count; i++)        
	    data[i] = sc_read.next();			
	return data;
    }	
    
    public static int[] getCityNum(String[] data)
    {
	int[] cityNum = new int[20];
	for(int i = 0; i < 20; i++)
	    cityNum[i] = i + 1;
	return cityNum;
    }

    public static String[] getCityCode(String[] data)
    {
	int i = 0, j = 1;
	String[] cityCode = new String[20];
	while(i < data.length)
	{
	    try
	    {
		if(Integer.valueOf(data[i]) == j)
		{
		    cityCode[j-1] = data[i+1];	
		    j++;
		}
	    } catch(NumberFormatException e) {
	
	    }
	    i++;
	}
	return cityCode;
    }    
    
    public static int[] getNums(String[] data) 
    {
	int i = 0, j = 1;
	int[] nums = new int[20];
	while(i < data.length)
	{
	    try {
		if(Integer.valueOf(data[i]) == j)
		{
		    nums[j-1] = i;
		    j++;
		}
	    } catch(NumberFormatException e) {
	
	    }
	    i++;
	}
	return nums;       
    }
    
    public static String[] getCityName(String[] data)
    {	
	String[] cityName = new String[20];
	int[] nums = getNums(data);	
	for(int i = 0; i < 19; i++)	
	{	    
	    if(nums[i] + 5 == nums[i+1])
		cityName[i] = data[nums[i] + 2];
	    else
		cityName[i] = data[nums[i] + 2] + " " + data[nums[i] + 3];	    
	}
	cityName[19] = data[nums[19] + 2];
	return cityName;
    }
    
    public static int[] getPopulation(String[] data)
    {
	int[] population = new int[20];
	int[] nums = getNums(data);
	for(int i = 0; i < 19; i++)	
	{	    
	    if(nums[i] + 5 == nums[i+1])
		population[i] = Integer.valueOf(data[nums[i] + 3]);
	    else
		population[i] = Integer.valueOf(data[nums[i] + 4]);
	}
	population[19] = Integer.valueOf(data[nums[19] + 3]);
	return population;
    }

    public static int[] getElevation(String[] data)
    {
	int[] elevation = new int[20];
	int[] nums = getNums(data);
	for(int i = 0; i < 19; i++)	
	{	    
	    if(nums[i] + 5 == nums[i+1])
		elevation[i] = Integer.valueOf(data[nums[i] + 4]);
	    else
		elevation[i] = Integer.valueOf(data[nums[i] + 5]);
	}
	elevation[19] = Integer.valueOf(data[nums[19] + 4]);
	return elevation;
    }
   
}