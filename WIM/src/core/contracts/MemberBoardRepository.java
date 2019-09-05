package core.contracts;

import models.contracts.Board;
import models.contracts.Member;

import java.util.Map;

public interface MemberBoardRepository{
    Map<String, Member> getMembers();
    Map<String, Board> getBoards();

    void addMember(String name, Member member);
    void addBoard(String name, Board board);
}
