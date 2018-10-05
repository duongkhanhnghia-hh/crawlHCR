package crawl;

import config.Config;
import connectSQLite.ActEx;
import connectSQLite.ActSample;
import connectSQLite.ConnectSQLite;
import model.Exercise;
import model.Sample;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CrawlExDetail {
    static ConnectSQLite connection = new ConnectSQLite();

    public static void crawlExDetail(int number){
        System.out.println("Crawling Ex Detail...");
        // get max id in sample table
        int exDetailed = ActSample.getMaxExID();
        // get list exercisr from max id above to max + number of exercise will crawl
        System.out.println(ActEx.getExNotDetail().size());
        List<Exercise> exerciseList = ActEx.getExNotDetail();
//        List<Exercise> exerciseList = ActEx.getListExFromTo(exDetailed+1, exDetailed+number+1);
        // create new exercise list and sample list to once save 50/100/... exercise, sample
        List<Exercise> exerciseList_tmp = new ArrayList<>();
        List<Sample> sampleList = new ArrayList<>();
        // get size of exercise list and save will minus
        number = exerciseList.size();

        //with a exercise will be crawled detail, add list and save list to database
        for (int i = 0; i < exerciseList.size(); i++) {
            // get this exercise
            Exercise exercise = exerciseList.get(i);
            // get response and parse it to document
            String res = SendMethod.getResponse(exercise.getLink_hcr());
            Document document = Jsoup.parse(res);

           // if this url fail get response by JSON url
            if (document.getElementsByClass("challenge_problem_statement_body").size() == 0){
                res = SendMethod.getResponse2(exercise.getSlug());
                document  = Jsoup.parse(res);
            }

            // get problem by className
            try {
                Element problem = document.getElementsByClass("challenge_problem_statement_body").get(0);
                String problem_str = problem.toString().replaceAll(" class=\".*\"|id=\".*\"", "");;
                exercise.setProblem(problem_str);
            }catch (Exception e){
                exercise.setProblem("");
            }

            // get input format by className
            try {
                Element input_format = document.getElementsByClass("challenge_input_format_body").get(0);
                String input_format_str = input_format.toString().replaceAll(" class=\".*\"|id=\".*\"", "");
                exercise.setInput_format(input_format_str);
            } catch (Exception e){
                exercise.setInput_format("");
            }

            // get constraints by className
            try {
                Element constraints = document.getElementsByClass("challenge_constraints_body").get(0);
                String constraints_str = constraints.toString().replaceAll(" class=\".*\"|id=\".*\"", "");
                exercise.setConstraints(constraints_str);
            } catch (Exception e){
                exercise.setConstraints("");
            }

            // get output format by className
            try {
                Element output_format = document.getElementsByClass("challenge_output_format_body").get(0);
                String output_format_str = output_format.toString().replaceAll(" class=\".*\"|id=\".*\"", "");
                exercise.setOuput_format(output_format_str);
            } catch (Exception e){
                exercise.setOuput_format("");
            }

            // get list sample inputs, sample output, explanation
            Elements sample_inputs = document.getElementsByClass("challenge_sample_input_body");;
            Elements sample_outputs = document.getElementsByClass("challenge_sample_output_body");
            Elements explanation = document.getElementsByClass("challenge_explanation_body");

            // with a input, out put, explanation add into new sample then add to list
            for (int j = 0; j < sample_inputs.size(); j++){
                String sample_input_str = "";
                String sample_output_str = "";
                String explanation_str = "";
                try {
                    sample_input_str = sample_inputs.get(j).toString().replaceAll(" class=\".*\"|id=\".*\"", "");
                    sample_output_str = sample_outputs.get(j).toString().replaceAll(" class=\".*\"|id=\".*\"", "");
                    explanation_str = explanation.get(j).toString().replaceAll(" class=\".*\"|id=\".*\"", "");
                }catch (Exception e){
                }
                Sample sample = new Sample(exercise.getId(), sample_input_str, sample_output_str, explanation_str);
                sampleList.add(sample);
            }

            // add exercise to list
            exerciseList_tmp.add(exercise);

            // if size of list = number once save or total of exercise < number once save then save list exercise
            // and sample to database and print notìication
            if ((number >= Config.NUM_EX_Detail_SAVE_TMP && i%Config.NUM_EX_Detail_SAVE_TMP ==Config.NUM_EX_Detail_SAVE_TMP-1)
                    ||number < Config.NUM_EX_Detail_SAVE_TMP){
                System.out.println("Saving " + (i+1) + " problem detail");
                Boolean result = ActEx.insertExDetail(exerciseList_tmp);
                System.out.println("Saved " + (i+1) + " problem detail: " + result);
                exerciseList_tmp = new ArrayList<>();
                System.out.println("Saving " + sampleList.size() + " sample");
                Boolean result_sample = ActSample.insertListSample(sampleList);
                System.out.println("Saved " + sampleList.size() + " sample: " + result_sample);
                number-=Config.NUM_EX_Detail_SAVE_TMP;
                sampleList = new ArrayList<>();
            }
        }
    }

}
