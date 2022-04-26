package DataTypes;

public class Answer {
    private String info;
    private boolean correct;

    public Answer() {
    }

    public Answer(String info, boolean correct) {
        this.info = info;
        this.correct = correct;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
