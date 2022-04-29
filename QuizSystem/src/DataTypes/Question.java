package DataTypes;

import java.util.ArrayList;

public class Question {
    private String info;
    private ArrayList<Answer> answers;

    public Question() {
    }

    public Question(String info, ArrayList<Answer> answers) {
        this.info = info;
        this.answers = answers;

    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public Answer getCorrectAnswer(){
        for (Answer x : answers ){
            if (x.isCorrect())
                return x;
        }
        return null;
    }
}
