package models;

import core.contracts.WorkitemFactory;
import core.factories.WorkitemFactoryImpl;
import models.contracts.Story;
import org.junit.Before;
import org.junit.Test;

public class StoryImpl_Tests {
    WorkitemFactory workitemFactory;

    @Before
    public void before(){
        workitemFactory = new WorkitemFactoryImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenTitleIsLessThanMinLength(){
        Story story = workitemFactory.createStory(
                "title",
                "Description of the story",
                "low",
                "small",
                "NotDone");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenTitleIsGreaterThanMaxLength(){
        StringBuilder title = new StringBuilder();

        while(title.length() <= 50){
            title.append(1);
        }
        Story story = workitemFactory.createStory(
                title.toString(),
                "Description of the story",
                "low",
                "small",
                "NotDone");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenDescriptionIsLessThanMinLength() {
       Story story = workitemFactory.createStory(
               "My story title",
                "too small",
               "low",
               "small",
               "NotDone");
    }
    @Test(expected = IllegalArgumentException.class)
    public void throwWhenDescriptionIsGreaterThanMaxLength() {
        StringBuilder description = new StringBuilder();

        while(description.length() <= 500){
            description.append(0);
        }

        Story story = workitemFactory.createStory(
                "My story title",
                description.toString(),
                "low",
                "small",
                "NotDone");
    }
}
