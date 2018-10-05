package model;

import java.util.List;

public class Domain {
    private String name;
    private String url;
    private List<Exercise> exerciseList;

    public Domain(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public Domain(String name, String url, List<Exercise> exerciseList) {

        this.name = name;
        this.url = url;
        this.exerciseList = exerciseList;
    }
}
