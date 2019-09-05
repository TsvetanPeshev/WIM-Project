package models.contracts;

import models.enums.Size;

public interface Story extends BugStoryBase {
    Size getStorySize();
    void setStorySize(Size storySize);
}
