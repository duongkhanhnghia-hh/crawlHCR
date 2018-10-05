package model;

import java.util.List;

public class Exercise {
    private int id;
    private String name;
    private String level;
    private double max_score;
    private double success_rate;
    private String link_hcr;
    private String slug;
    private String problem;
    private String input_format;
    private String constraints;
    private String ouput_format;
    private String start_code;
    private List<Sample> sampleList;
    private List<User_Ex> user_exList;
    private String domain_name;


    public Exercise() {
    }

    public Exercise(String name, String level, double max_score, double success_rate, String link_hcr, String slug, String domain_name) {
        this.name = name;
        this.level = level;
        this.max_score = max_score;
        this.success_rate = success_rate;
        this.link_hcr = link_hcr;
        this.slug = slug;
        this.domain_name = domain_name;
    }

    public Exercise(int id, String slug, String link_hcr){
        this.id = id;
        this.slug = slug;
        this.link_hcr = link_hcr;
    }

    public Exercise(int id, String name, String level, double max_score, double success_rate, String problem, String input_format, String constraints, String ouput_format) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.max_score = max_score;
        this.success_rate = success_rate;
        this.problem = problem;
        this.input_format = input_format;
        this.constraints = constraints;
        this.ouput_format = ouput_format;
    }

    public Exercise(int id, String name, String level, double max_score, double success_rate, String problem, String input_format, String constraints, String ouput_format, String domain_name) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.max_score = max_score;
        this.success_rate = success_rate;
        this.problem = problem;
        this.input_format = input_format;
        this.constraints = constraints;
        this.ouput_format = ouput_format;
        this.domain_name = domain_name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getMax_score() {
        return max_score;
    }

    public void setMax_score(double max_score) {
        this.max_score = max_score;
    }

    public double getSuccess_rate() {
        return success_rate;
    }

    public void setSuccess_rate(double success_rate) {
        this.success_rate = success_rate;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getStart_code() {
        return start_code;
    }

    public void setStart_code(String start_code) {
        this.start_code = start_code;
    }

    public String getLink_hcr() {
        return link_hcr;
    }

    public void setLink_hcr(String link_hcr) {
        this.link_hcr = link_hcr;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInput_format() {
        return input_format;
    }

    public void setInput_format(String input_format) {
        this.input_format = input_format;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public String getOuput_format() {
        return ouput_format;
    }

    public void setOuput_format(String ouput_format) {
        this.ouput_format = ouput_format;
    }

    public List<Sample> getSampleList() {
        return sampleList;
    }

    public void setSampleList(List<Sample> sampleList) {
        this.sampleList = sampleList;
    }

    public List<User_Ex> getUser_exList() {
        return user_exList;
    }

    public void setUser_exList(List<User_Ex> user_exList) {
        this.user_exList = user_exList;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

}
