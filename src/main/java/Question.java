
public class Question {
    private int id;
    private String name;
    private String topic;
    private int difficulty;

    public Question(int id, String name, String topic, int difficulty) {
        this.id = id;
        this.name = name;
        this.topic = topic;
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Question["
                + "id=" + id
                + ", name=" + name
                + ", topic=" + topic
                + ", difficulty=" + difficulty
                + ']';
    }

    public Question() {
    }

    public Question(String name, String topic, Integer difficulty) {
        this.name = name;
        this.topic = topic;
        this.difficulty = difficulty;
    }

    public int getId() {
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
