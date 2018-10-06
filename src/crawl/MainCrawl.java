package crawl;

import connectSQLite.ActDomain;
import model.Domain;

import java.util.List;

public class MainCrawl {
    public static void main(String[] args) {
        // crawl all domain
        CrawlDomain.crawlDomain();

        // get all domain
        List<Domain> domainList = ActDomain.getAllDomain();

        // crawl exercise with domain
        for (int i = 0; i < domainList.size(); i++){
            CrawlExList.getExercise(domainList.get(i));
        }

        // crawl number detail of exercise
        CrawlExDetail.crawlExDetail();

        //crawl leader for exercise
        CrawlLeaderboard.saveAllLeader();
    }
}
