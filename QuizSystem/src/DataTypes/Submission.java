package DataTypes;

public class Submission {
    private String submittedBy;
    private String course;
    private String quiz;
    private String score;

    public Submission(String submittedBy, String course, String quiz, String score) {
        this.submittedBy = submittedBy;
        this.course = course;
        this.quiz = quiz;
        this.score = score;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public String getCourse() {
        return course;
    }

    public String getQuiz() {
        return quiz;
    }

    public String getScore() {
        return score;
    }
}
