package model;

public class User_Ex {
    public int user_id;
    public int ex_id;
    public double score;
    public int rank;
    private User user;

    public User_Ex(int user_id, int ex_id, double score, int rank) {
        this.user_id = user_id;
        this.ex_id = ex_id;
        this.score = score;
        this.rank = rank;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEx_id() {
        return ex_id;
    }

    public void setEx_id(int ex_id) {
        this.ex_id = ex_id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
