package models;

import core.contracts.WorkitemFactory;
import core.factories.WorkitemFactoryImpl;
import models.contracts.Feedback;
import org.junit.Before;
import org.junit.Test;

public class FeedbackImpl_Tests {
    WorkitemFactory workitemFactory;

    @Before
    public void before() {
        workitemFactory = new WorkitemFactoryImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenTitleIsLessThanMinLength() {
        Feedback feedback = workitemFactory.createFeedback(
                "Title",
                "This is bug description",
                5,
                "new");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenTitleIsMoreThanMinLength() {
        StringBuilder title = new StringBuilder();
        while (title.length() <= 50) {
            title.append(1);
        }
        Feedback feedback = workitemFactory.createFeedback(
                title.toString(),
                "This is bug description",
                5,
                "new");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenDescriptionIsLessThanMinLength() {
        Feedback feedback = workitemFactory.createFeedback(
                "Feedback title",
                "Too small",
                5,
                "new");

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenDescriptionIsMoreThanMaxLength() {
        StringBuilder description = new StringBuilder();

        while (description.length() <= 501) {
            description.append(0);
        }

        Feedback feedback = workitemFactory.createFeedback(
                "Feedback title",
                description.toString(),
                5,
                "new");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenRatingIsLessThanMinValue(){
        Feedback feedback = workitemFactory.createFeedback(
                "Feedback title",
                "The feedback description",
                0,
                "new");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenRatingIsMoreThanMaxValue(){
        Feedback feedback = workitemFactory.createFeedback(
                "Feedback title",
                "The feedback description",
                7,
                "new");
    }
}
