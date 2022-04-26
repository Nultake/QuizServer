package DataTypes;

import java.util.ArrayList;

public class Quiz {
    private ArrayList<Question> questions;

    public Quiz() {
    }

    public Quiz(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
