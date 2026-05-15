package model.reviews;

public class Review {

    private String author;
    private String content;
    private int score;

    public Review(String author, String content, int score) {
        this.author = author;
        this.content = content;
        this.score = score;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return author + " (" + score + "/10): " + content;
    }
}



