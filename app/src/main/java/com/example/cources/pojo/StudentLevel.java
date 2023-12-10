package com.example.cources.pojo;

public class StudentLevel {

   private String progressLevel;
    private double score;

    public StudentLevel() {
    }

    public StudentLevel(String progressLevel) {
        this.progressLevel = progressLevel;
    }
    public StudentLevel(double score) {
        this.progressLevel = progressLevel;
    }

    public String getProgressLevel() {
        return progressLevel;
    }

    public void setProgressLevel(String progressLevel) {
        this.progressLevel = progressLevel;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
