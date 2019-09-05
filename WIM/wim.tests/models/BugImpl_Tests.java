package models;

import core.contracts.WorkitemFactory;
import core.factories.WorkitemFactoryImpl;
import models.contracts.Bug;
import org.junit.Before;
import org.junit.Test;

public class BugImpl_Tests {
    WorkitemFactory workitemFactory;

    @Before
    public void before(){
        workitemFactory = new WorkitemFactoryImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenTitleIsLessThanMinLength(){
        Bug bug = workitemFactory.createBug("Title",
                "This is bug description",
                "medium",
                "minor",
                "fixed");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenTitleIsGreaterThanMaxLength(){
        StringBuilder title = new StringBuilder();
        while(title.length() <= 50){
            title.append(1);
        }
        Bug bug = workitemFactory.createBug(title.toString(),
                "This is bug description",
                "medium",
                "minor",
                "fixed");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenDescriptionIsLessThanMinLength() {
        Bug bug = workitemFactory.createBug("My bug title",
                "too small",
                "medium",
                "minor",
                "fixed");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenDescriptionIsGreaterThanMaxLength() {
        StringBuilder description = new StringBuilder();

        while(description.length() <= 501){
            description.append(0);
        }

        Bug bug = workitemFactory.createBug("My bug title",
                description.toString(),
                "medium",
                "minor",
                "fixed");
    }

}
