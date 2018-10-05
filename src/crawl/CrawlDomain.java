package crawl;

import config.Config;
import connectSQLite.ActDomain;
import model.Domain;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CrawlDomain {
    public static void crawlDomain(){
        // get response when connect dashboard
        String res = SendMethod.getResponse(Config.DASHBOARD);
        // parse response to document
        Document document = Jsoup.parse(res);
        try {
            // get element by attribute value because all of Domain in Hackerrank have that attribute
            Elements domains = document.getElementsByAttributeValue("data-analytics","TrackLink");
            System.out.println(domains.size());
            List<Domain> domainList = new ArrayList<>();
            Domain domain;
            // With a element in domain get name and url
            for (int i = 0; i < domains.size(); i++){
                domain = new Domain(domains.get(i).text(),domains.get(i).attr("href").replaceAll("/domains/",""));
                domainList.add(domain);
            }
            System.out.println("Saving domain: " + domainList.size());
            // Save domain list to database
            Boolean result = ActDomain.insertDomainList(domainList);
            System.out.println("Save domain: " + result);
        }catch (Exception e){

        }
    }

}
