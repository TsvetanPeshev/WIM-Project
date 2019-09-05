package models.contracts;

import java.util.Map;

public interface Team extends Activity{
    String getName();

    void addMember(Member member);

    Map<String, Board> getBoards();

    void addBoard(Board board);

    Map<String, Member> getMembers();
}
