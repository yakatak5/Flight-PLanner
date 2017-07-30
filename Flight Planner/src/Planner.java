import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;



public class Planner {
	public static int find(LinkedList<LinkedList<FlightData>> list, String city){
		boolean found = false;
		for (int i = 0; i < list.size(); i++){
			if(list.get(i).getFirst().getCity().equals(city)){
				return i;
			}//if the city already has a list, add another destination
		}
		return -1;
		
	}
	public static void resetFound(LinkedList<LinkedList<FlightData>> list){
		for (int i = 0; i < list.size(); i++){
			for(int j = 0; j < list.get(i).size(); j++){
				list.get(i).get(j).setFound(false);
			}
		}
	}//after each search, reset
	
	public static void main(String args[]){
		int argc = args.length;
		if (argc != 3){
			System.out.println("Usage: ./<exe name> <input file> <requests file> <output file>");
			System.exit(1);
		}//improper file input
		
		LinkedList<LinkedList<FlightData>> list = new LinkedList<LinkedList<FlightData>>(); //adjacency list
		
		try {
			PrintStream out = new PrintStream(new FileOutputStream(args[2]));
			System.setOut(out); //output to file
			File file = new File(args[0]);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line; //holds lines in file
			String [] pipes; //holds split info
			boolean flag = true;;
			
			while ((line = bufferedReader.readLine()) != null) {
				if (flag){
					flag = false;
				}//skip first line
				else{
				pipes = line.split("\\|");
				boolean found = false;
				for (int i = 0; i < list.size() && !found; i++){
					if(list.get(i).getFirst().getCity().equals(pipes[0])){
						list.get(i).add(new FlightData(pipes[1],Integer.valueOf(pipes[2]), Integer.valueOf(pipes[3])));
						found = true;
					}//if the city already has a list, add another destination
				}
				if (!found){
					list.add(new LinkedList<FlightData>());
					list.getLast().addFirst(new FlightData(pipes[0], 0, 0));
					list.getLast().add(new FlightData(pipes[1], Integer.valueOf(pipes[2]), Integer.valueOf(pipes[3])));
				}//if the city wasn't found in the list, make a new list for this city
				}//after skipping first line
			}//reading lines in file
			
			//get paths from source city
			
			String source;
			String destination;
			flag = true;
			File request = new File(args[1]);
			FileReader fileReader2 = new FileReader(request);
			BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
			while ((line = bufferedReader2.readLine()) != null){
				if (flag){
					flag = false;
				}//skip first line
				else{
					pipes = line.split("\\|");
					source = pipes[0];
					int index = find(list, source);
					if (index == -1){
						System.out.println("Sorry, there are no flights from " + pipes[0]);
					}//if no source city
					else{
						ArrayList<Path> paths = new ArrayList<Path>();
						LinkedList<FlightData> sourceList = list.get(index);
						for ( int j = 1; j < sourceList.size(); j++){
							FlightData city = sourceList.get(j);
							Path og = new Path(sourceList.getFirst(), sourceList.get(j));
							paths.add(og); //add single dest paths
							while(((index =find(list, city.getCity()) )!= -1  )){
								if (list.get(index).getFirst().isFound()){
									break;
								}//if looping back to previously found city
								LinkedList<FlightData> destList = list.get(index);
								list.get(index).getFirst().setFound(true);
								for ( int k = 1; k < destList.size(); k++){
									if (destList.get(k).getCity().equals(sourceList.getFirst().getCity()))
										continue;
									else{
									city = destList.get(k);
									paths.add(new Path(og, destList.get(k)));
									}
								}//add new path and search new city
							}//add all paths with more than 2 cities
							
						}//fill all paths from source city
						LinkedList<Path> search = new LinkedList<Path>();
						for (int i = 0; i < paths.size(); i++){
							if ((paths.get(i).getCities().peek().getCity()).equals(pipes[1])){
								search.add(paths.get(i));
							}
						}//add all paths that start at source and reach destination
						
						int place = 0;
						int shortest = 10000;
						if (pipes[2].equals("T")){
							System.out.println("Flight: "+ pipes[0] + " to " + pipes[1] + " (Time)");
							if (search.size() == 0)//no flights
								System.out.println("Sorry, there are no flights from " + pipes[0] + " to " + pipes[1] );
							int count = 0;
							while (count <= 3 && search.size() > 0){
								shortest = 1000;
								if (search.size() == 1){
									search.get(0).print();
									break;
								}//output only path
								else{
								for (int l = 0; l < search.size(); l++){
									if ((search.get(l).getTime()) <= shortest){
										place = l;
										shortest = search.get(l).getTime();
									}//get the shortest time in list
								}
								search.get(place).print();
								search.remove(place);
								count++;
								}//get shortest path and remove from list
							}
						}//if looking for shortest time
						if (pipes[2].equals("C")){
							System.out.println("Flight: "+ pipes[0] + " to " + pipes[1] + " (Cost)");
							if (search.size() == 0)//no flights to dest
								System.out.println("Sorry, there are no flights from " + pipes[0] + " to " + pipes[1] );
						{
							int count = 0;
							
							while (count <= 3 && search.size() > 0){
								shortest = 1000;
								if (search.size() == 1){
									search.get(0).print();
									break;
								}//output only flight
								else{
								for (int l = 0; l < search.size(); l++){
									if ((search.get(l).getCost()) <= shortest){
										place = l;
										shortest = search.get(l).getCost();
									}//get the shortest time in list
								}
								search.get(place).print();
								search.remove(place);
								count++;
								}//get shortest path and remove from list
							}
						}//if looking for shortest time
							
						}
						System.out.println();
						resetFound(list);
						}//if there are flights
					}
				}
			
			}catch(IOException e){System.out.println("failed");}
		
	}
}


