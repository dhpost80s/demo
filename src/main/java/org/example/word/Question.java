package org.example.word;

public class Question {

    private int id;

    private String text;

    private String page;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
