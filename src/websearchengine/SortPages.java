package websearchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

public class SortPages {
	private static final int CUTOFF = 3;  // EDIT DISTANCE 
			
	// sorting using collection method
	public static void rankWebPages(Hashtable<?, Integer> files, int numberOfFiles) {

		// insert website files in ArrayList
		ArrayList<Map.Entry<?, Integer>> list_of_files = new ArrayList<Map.Entry<?, Integer>>(files.entrySet());
		
		// compare list of files and set in a descending order
		Comparator<Map.Entry<?, Integer>> compareByOccurence=
				(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2)->o1.getValue().compareTo(o2.getValue());
				
		// Ranking of list in descending Order 
		Collections.sort(list_of_files, compareByOccurence.reversed());		
				
	    System.out.println("\n------------------------------Ranked LIST OF WEBSITES------------------------------\n");
	    
		int serial_no=1;
		
		
		for(int i = 0 ; i < list_of_files.size() ; i++)
		{
			
			
			
			// if files are not null then this will print the websites according to the word accourance  IN THE SITES
			if(list_of_files.get(i).getKey()!= null) {
				
				System.out.println("(" + serial_no + ") " + list_of_files.get(i).getKey() + " --- OCUURANCE OF WORD IN THE WEBSITES -->> " + list_of_files.get(i).getValue());
				serial_no++;
				
			}
			
		}
	}   
}

