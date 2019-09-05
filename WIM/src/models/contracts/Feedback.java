package models.contracts;

public interface Feedback extends Workitem {
    int getRating();
    void setRating(int rating);
}
