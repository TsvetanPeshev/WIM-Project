package models.contracts;

import java.util.List;

public interface Activity {
    void addActivity(String activity);
    public List<String> getActivityHistory();
    String showActivity(String name);
}
