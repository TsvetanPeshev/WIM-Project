package core.contracts;

import models.contracts.Board;
import models.contracts.Member;

public interface MemberBoardFactory {

    Member createMember(String name);

    Board createBoard(String name);
}
