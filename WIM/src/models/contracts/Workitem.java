package models.contracts;


import models.enums.StatusType;
import models.contracts.Comment;
import java.util.ArrayList;
import java.util.List;

public interface Workitem extends Activity {
    String getTitle();

    String getDescription();

    StatusType getStatus();

    List<String> getHistory();

    void addHistory(String history);

    void setStatus(StatusType status);

    String toString(long id);

    void addComment(Comment comment);

    ArrayList<Comment> getComments();

}
