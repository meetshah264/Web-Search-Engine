package websearchengine;

import java.io.File;
import java.util.Hashtable;
import java.util.Scanner;

public class index {

  private static Scanner scan = new Scanner(System.in);

  public static void main(String args[]) throws Exception {
    // delete all the files from url_pages_crawled at initial level
    deleteFiles("./text_pages");
    deleteFiles("./url_pages_crawled");

    System.out.println("---------------------Web Search Engine-------------------\n");

    System.out.println("Choose any one of the way to search:");
    System.out.println("\nWant to search by word, type word \n   OR   \nWant to search by URL, type url \n");
    String searchChoice = scan.next();
    if (searchChoice.equals("url")) {
      System.out.println("Please enter the URL that you want to search for: \n");
      String input_URL = scan.next();
      String formed_URL = "https://" + input_URL + "/";

      //for removing whitespaces
      input_URL = input_URL.trim();
    
      //call for crawler
      Crawler craw = new Crawler();
      craw.getLinks(formed_URL);
    } 
    else {
      //HTML to Text Converion
      HTMLToText htmlToText = new HTMLToText();
      HTMLToText.Static_HTMLtoText();
    }
    while (true) {
      System.out.println("\n\nEnter '0' to exit OR Enter any number to continue: ");

      if (scan.nextInt() == 0) {
        System.out.println("\nThank You! You exited successfully.\n");
        break;
      }

      System.out.println("Enter a word you wold like to search: ");
      String searchWord = scan.next();


      // For searching a word, we are using BoyerMoore Algorithm
      SearchWord sw = new SearchWord(searchWord);
      Hashtable<String, Integer> File_List = sw.SearchWord_frequency(searchWord);
      if (File_List.isEmpty()) {
      //For getting similar suggestions related to word, edit distance concept is used
        System.out.println("Sorry! No matches found as per your serach, \n\nSome suggestions for you: ");
        String[] list = SpellChecking.getTheWordsuggestion(searchWord);
        if(list.length > 0) {
            if(list[0] != null) {
                for(String item: list){
                    System.out.println(item);
                }
            }else {
                System.out.println("Sorry! No suggestions for you.");
            }
        } else {
            System.out.println("Sorry! No suggestions for you.");
        }
      }
      else {
    	// Algorithm for sorting pages
        System.out.println("List after Sorting:");
        SortPages sortPages = new SortPages();
        sortPages.rankWebPages(File_List, File_List.size());

        //After process delete all crawled and converted files
        deleteFiles("./text_pages");	
        deleteFiles("./url_pages_crawled");

        System.out.println("\nThank You! You exited successfully.");
        break;
      }
    }   
  }

  //Delete process
  private static void deleteFiles(String filePath) {
    File files = new File(filePath);
    File[] ArrayFiles = files.listFiles();

    for (int x = 0; x < ArrayFiles.length; x++) {
      ArrayFiles[x].delete();
    }
  }
}
