package model;

public class Sample {
    int id;
    int ex_id;
    String sample_input;
    String sample_output;
    String explanation;

    public Sample(int ex_id, String sample_input, String sample_output, String explanation) {
        this.id = id;
        this.ex_id = ex_id;
        this.sample_input = sample_input;
        this.sample_output = sample_output;
        this.explanation = explanation;
    }

    public Sample() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEx_id() {
        return ex_id;
    }

    public void setEx_id(int ex_id) {
        this.ex_id = ex_id;
    }

    public String getSample_input() {
        return sample_input;
    }

    public void setSample_input(String sample_input) {
        this.sample_input = sample_input;
    }

    public String getSample_output() {
        return sample_output;
    }

    public void setSample_output(String sample_output) {
        this.sample_output = sample_output;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
