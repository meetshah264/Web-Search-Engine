package websearchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class SearchWord {

  private final int R; //the Radix
  private static int[] right; //array for skipping bad character

  //Constructor for accepting a search as input.
  public SearchWord(String patternString) {
    this.R = 10000;

    //rightmost position occurrence of x in the search pattern
    right = new int[R];
    for (int x = 0; x < R; x++) 
      right[x] = -1;
    for (int x = 0; x < patternString.length(); x++) 
      right[patternString.charAt(x)] = x;
  }

  public static Hashtable<String, Integer> SearchWord_frequency(String word)
    throws IOException {
    Hashtable<String, Integer> listOfFiles = new Hashtable<String, Integer>();
    File fileTextData = new File("./text_pages");
    File[] arrayFiles = fileTextData.listFiles();
    int totalFiles = 0;

    for (int x = 0; x < arrayFiles.length; x++) {
      String text = readFile(arrayFiles[x].getPath());

      int frequencyOfWord = searchWord(text, word, arrayFiles[x].getName());
     
      if (frequencyOfWord != 0) {
        listOfFiles.put(arrayFiles[x].getName(), frequencyOfWord);
        totalFiles++;
      }
    }
    if (totalFiles < 0) {
      System.out.println("\n" + word + "\" could not be find.");
    } 
    else {
    	System.out.println("\n" + word + " found in total " + totalFiles + " files");
    }
    return listOfFiles;
  }

  public static String readFile(String fileName) throws IOException {
    BufferedReader bufferReader = new BufferedReader(new FileReader(fileName));
    try {
      StringBuilder strBuilder = new StringBuilder();
      String readLine = bufferReader.readLine();

      while (readLine != null) {
    	  strBuilder.append(readLine);
    	  strBuilder.append("\n");
    	  readLine = bufferReader.readLine();
      }
      return strBuilder.toString();
    } 
    finally {
    	bufferReader.close();
    }
  }

  public static int search(String pattern, String text) {
    int patternLength = pattern.length();
    int textLength = text.length();
    int skip;
    for (int x = 0; x <= textLength - patternLength; x += skip) {
      skip = 0;
      for (int y = patternLength - 1; y >= 0; y--) {
        if (pattern.charAt(y) != text.charAt(x + y)) {
          skip = Math.max(1, y - right[text.charAt(x + y)]);
          break;
        }
      }
      if (skip == 0) return x; //search word found
    }
    return textLength; //search word not found
  }

  public static int searchWord(String data, String word, String fileName) {
    int counter = 0;

    int offset = 0;
    SearchWord searchWord = new SearchWord(word);

    for (int locationOfWord = 0; locationOfWord <= data.length(); locationOfWord += offset + word.length()) {
      offset = searchWord.search(word, data.substring(locationOfWord));
      if ((offset + locationOfWord) < data.length()) {
        counter++;
      }
    }
    return counter;
  }
}
