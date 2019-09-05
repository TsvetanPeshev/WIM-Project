package models;

import models.contracts.Story;
import models.enums.PriorityType;
import models.enums.Size;
import models.enums.StatusType;

import static commands.CommandConstants.INCORRECT_SIZE_TYPE_ERROR_MESSAGE;

public class StoryImpl extends BugStoryBaseImpl implements Story{
    private Size storySize;

    public StoryImpl(String title, String description, PriorityType priority, Size storySize, StatusType status) {
        super(title, description, status, priority);
        this.storySize = storySize;
    }

    @Override
    public Size getStorySize(){
        return storySize;
    }

    public void setStorySize(Size storySize){
        if(!storySize.equals(Size.LARGE) && !storySize.equals(Size.MEDIUM) && !storySize.equals(Size.SMALL)){
            throw new IllegalArgumentException(INCORRECT_SIZE_TYPE_ERROR_MESSAGE);
        }
        this.storySize=storySize;
    }

    @Override
    public String toString(int id, String typeOfWorkitem){
        StringBuilder result = new StringBuilder(super.toString(id, typeOfWorkitem));
        result.append(String.format("Size: %S%n", this.getStorySize().toString()));

        return result.toString();
    }
}
