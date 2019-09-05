package models;

import models.common.Validator;
import models.contracts.Feedback;
import models.enums.StatusType;

public class FeedbackImpl extends WorkitemImpl implements Feedback {
    private static final int RATING_MIN_VALUE = 1;
    private static final int RATING_MAX_VALUE = 6;
    private static final String RATING_ERROR_MESSAGE = "Rating should be between %d and %d";

    private int rating;

    public FeedbackImpl(String title, String description, int rating, StatusType status) {
        super(title, description, status);
        setRating(rating);
    }

    @Override
    public int getRating() {
        return rating;
    }

   @Override
    public void setRating(int rating) {
        Validator.validateValueOfNumber(
                rating, RATING_MIN_VALUE, RATING_MAX_VALUE,RATING_ERROR_MESSAGE );
        this.rating = rating;
    }

    @Override
    public String toString(long id){
        StringBuilder result = new StringBuilder(super.toString(id));
        result.append(String.format("Rating: %d%n", this.getRating()));

        return result.toString();
    }
}
