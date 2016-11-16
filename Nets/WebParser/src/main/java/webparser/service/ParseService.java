package webparser.service;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ParseService {

    public static Set<String> parseHtml(String html, String parentPath){
        Set<String> links = new HashSet<>();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("a");
        elements.forEach(element -> {
            String link = element.attr("href");
            parseLink(link, links, parentPath);
        });
        return links;
    }
    public static Set<String> parseHtml(String html){
        return parseHtml(html, null);
    }

    private static void parseLink(String link, Collection<String> collection,String parentPath){
        if (link.startsWith("/") && !link.startsWith("//") ){
            link = checkBugs(link);
            collection.add(link);
        }else {
            if (link.startsWith("../")){
                if (parentPath == null) {
                    collection.add(link.replace("../", ""));
                } else {
                    collection.add(parentPath + link.replace("../", "/"));
                }
            }else {
                if (!link.startsWith("http://") && !link.startsWith("https://") && !link.startsWith("#") && !link.startsWith("mailto:") && !link.startsWith("javascript:") && !link.startsWith("//") && !link.endsWith(".pdf")) {
                    link = checkBugs(link);
                    if (parentPath == null) {
                        collection.add("/" + link);
                    } else {
                        collection.add(parentPath.substring(0,parentPath.lastIndexOf("/")) +"/" + link);
                    }

                }
            }
        }
    }

    private static String checkBugs(String link){
        if (link.contains("\n")){
            link = link.substring(0, link.indexOf("\n") - 1) + link.substring(link.lastIndexOf("\n") + 1, link.length());
        }
        if (link.contains("#")){
            link = link.substring(0, link.indexOf("#"));
        }
        return link;
    }

}
