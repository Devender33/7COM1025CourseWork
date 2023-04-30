package main.modal;

public class Review {
    private String review;
    private int rating;
    private Lesson lesson;

    public Review(String description, int rating, Lesson lesson) {
        this.review = description;
        this.rating = rating;
        this.lesson = lesson;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public Lesson getLesson() {
        return lesson;
    }
}
