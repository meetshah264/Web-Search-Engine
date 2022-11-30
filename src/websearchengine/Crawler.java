package websearchengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

  private static ArrayList<String> listOfLinks = new ArrayList<>();

  public String[] get_URLList() {
    String[] url_List = listOfLinks.toArray(new String[listOfLinks.size()]);
    return url_List;
  }

  public static void getLinks(String url) throws Exception {
    Document document;
    try {
      document = Jsoup.connect(url).get();
      Elements links = document.select("a[href]");
      for (Element link1 : links) {
        String str = link1.attr("abs:href");
        String regex =
          "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
        	listOfLinks.add(m.group(0));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    HTMLToText hToT = new HTMLToText();
    hToT.Dynamic_HTMLtoText(listOfLinks);
  }
}
