package models.contracts;

public interface MemberBoardBase extends Activity {

    String getName();

    void addWorkitem(long id, Workitem workitem);

    Workitem getWorkitem(Long id);
}
