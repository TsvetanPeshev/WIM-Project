package core.factories;

import core.contracts.MemberBoardFactory;
import models.BoardImpl;
import models.MemberImpl;
import models.contracts.Board;
import models.contracts.Member;

public class MemberBoardFactoryImpl implements MemberBoardFactory {

    @Override
    public Member createMember(String name){
        return new MemberImpl(name);
    }

    @Override
    public Board createBoard(String name){
        return new BoardImpl(name);
    }
}
