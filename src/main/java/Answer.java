


public class Answer {

    private Integer id;
    private String name;
    private Boolean correct;
    private Question question;


    public Answer() {
    }

    public Answer(Integer id, String name, Boolean correct, Question question) {
        this.id = id;
        this.name = name;
        this.correct = correct;
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}


