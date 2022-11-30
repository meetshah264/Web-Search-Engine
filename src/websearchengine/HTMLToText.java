package websearchengine;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLToText {

  public static void Static_HTMLtoText() throws Exception {
    File file = new File("url_pages_fixed");
    String[] List_of_f1 = file.list();
    for (int i = 0; i < List_of_f1.length; i++) {
    	Html_To_Text_Converter(List_of_f1[i], "fixed pages"); //From url_pages_fixed, take html files and convert them to text
    }
  }

  public static void Dynamic_HTMLtoText(ArrayList<String> list_of_link)
    throws Exception {
    System.out.println("-------------------Crawled Links------------------- \n");
    int limit = 0;
    for (String str : list_of_link) {
      //System.out.println("Link1: "+s);
      limit++;
      Document linkdoc = Jsoup.connect(str).get();

      String string_of_html = linkdoc.html();
      String html_Path = "url_pages_crawled";
      File html_Folder = new File(html_Path);
      String regex = "[a-zA-Z0-9]+";
      Pattern pattern = Pattern.compile(regex);
      Matcher m = pattern.matcher(str);
      StringBuffer stringBuffer = new StringBuffer();
      while (m.find()) {
        stringBuffer.append(m.group(0));
      }
      String link_Adress = stringBuffer.substring(0);
      System.out.println("Link: " + link_Adress);

      PrintWriter pwout = new PrintWriter(html_Path + "\\" + link_Adress + ".html");
      pwout.println(string_of_html);
      pwout.close();

      if (limit == 20) {
        break;
      }
    }
    File myfile = new File("url_pages_crawled");
    String[] List_of_f2 = myfile.list();
    for (int i = 0; i < List_of_f2.length; i++) {
    	Html_To_Text_Converter(List_of_f2[i], "crawled pages"); //from W3C Web Pages,take HTML files and convert them to text
    }
  }

  public static void Html_To_Text_Converter(String file, String type)
    throws Exception {
    //System.out.println("File Name: "+file);
    String folder;
    if (type.equals("fixed pages")) {
      folder = "url_pages_fixed";
    } else {
      folder = "url_pages_crawled";
    }
    File f = new File(folder + "\\" + file);
    //Parse the file using JSoup
    Document document = Jsoup.parse(f, "UTF-8");
    //Convert the file to text
    String string = document.text();
    PrintWriter writer = new PrintWriter(
      "text_pages\\" + file.replaceAll(".html", ".txt")
    );
    writer.println(string);
    writer.close();
  }
}
