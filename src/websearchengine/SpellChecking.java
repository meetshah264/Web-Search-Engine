package websearchengine;

import java.io.*;
import java.util.*;

public class SpellChecking {

    private static ArrayList<String> words = new ArrayList<>();
    
    
     private static void getSimilarWords() throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        File allTextFiles = new File(currentDirectory+ "/text_pages");

        File[] texts = allTextFiles.listFiles();

        StringBuilder line = new StringBuilder();
        assert texts != null;
        for (File text : texts) {
            BufferedReader br = new BufferedReader(new FileReader(text));
            String string;
            while ((string = br.readLine()) != null) {
                line.append(string);
            }
            br.close();
        }
        String fullText = line.toString();
        StringTokenizer tokenizer = new StringTokenizer(fullText, "0123456789 ,`*$|~(){}_@><=+[]\\?;/&#-.!:\"'\n\t\r");
        while (tokenizer.hasMoreTokens()) {
            String tokens = tokenizer.nextToken().toLowerCase(Locale.ROOT);
            if (!words.contains(tokens)) {
                words.add(tokens);
            }
        }
    }
     
     public static String[] getTheWordsuggestion(String searchWord) throws IOException {
         getSimilarWords();
         HashMap<String, Integer> hashMap = new HashMap<>();
         String[] newWords = new String[10];
         for (String w : words) {
             int editDistance = EditDistance(searchWord, w);
             hashMap.put(w, editDistance);
         }
         Map<String, Integer> map = sortByValue(hashMap);

         int rank = 0;
         for (Map.Entry<String, Integer> en : map.entrySet()) {
             if (en.getValue() != 0) {
                 newWords[rank] = en.getKey();
                 rank++;
                 if (rank == 10){ break; }
             }
         }
         return newWords;
     }

    

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer> > listOfWords = new LinkedList<>(map.entrySet());

        listOfWords.sort(Map.Entry.comparingByValue());

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : listOfWords) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static int EditDistance(String word1, String word2) {
        int word1Length = word1.length();
        int word2Lenghth = word2.length();
        int[][] distance_Array = new int[word1Length + 1][word2Lenghth + 1];

        for (int i = 0; i <= word1Length; i++) {
        	distance_Array[i][0] = i;
        }
        for (int j = 0; j <= word2Lenghth; j++) {
        	distance_Array[0][j] = j;
        }

        for (int i = 0; i < word1Length; i++) {
            char char1 = word1.charAt(i);
            for (int j = 0; j < word2Lenghth; j++) {
                char char2 = word2.charAt(j);

                if (char1 == char2) {
                	distance_Array[i + 1][j + 1] = distance_Array[i][j];
                } else {
                    int value1 = distance_Array[i][j] + 1;
                    int value2 = distance_Array[i][j + 1] + 1;
                    int value3 = distance_Array[i + 1][j] + 1;

                    int min = Math.min(value1, value2);
                    min = Math.min(value3, min);
                    distance_Array[i + 1][j + 1] = min;
                }
            }
        }
        return distance_Array[word1Length][word2Lenghth];
    }

    public static void main(String[] args) throws IOException{
        String[] list = getTheWordsuggestion("a");
        System.out.println(list.length+""+ list[0]);
        if(list.length > 0) {
            if(list[0] != null) {
                for(String item: list){
                    System.out.println(item);
                }
            }else {
                System.out.println("Oops! No Suggestions found");
            }
        } else {
            System.out.println("Oops! No suggestions found");
        }
    }
}
