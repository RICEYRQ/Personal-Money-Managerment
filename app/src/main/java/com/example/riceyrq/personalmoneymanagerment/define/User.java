package com.example.riceyrq.personalmoneymanagerment.define;

public class User {
    private String userName;
    private String userPas;
    private String pasQuestion;
    private String pasAnswer;

    public String getUserPas() {
        return userPas;
    }

    public void setUserPas(String userPas) {
        this.userPas = userPas;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasQuestion() {
        return pasQuestion;
    }

    public void setPasQuestion(String pasQuestion) {
        this.pasQuestion = pasQuestion;
    }

    public String getPasAnswer() {
        return pasAnswer;
    }

    public void setPasAnswer(String pasAnswer) {
        this.pasAnswer = pasAnswer;
    }

}