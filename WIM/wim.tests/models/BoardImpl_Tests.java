package models;

import core.contracts.MemberBoardFactory;
import core.factories.MemberBoardFactoryImpl;
import models.contracts.Board;
import org.junit.Before;
import org.junit.Test;

public class BoardImpl_Tests {
    private MemberBoardFactory memberBoardFactory;

    @Before
    public void before(){
        memberBoardFactory = new MemberBoardFactoryImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenBoardNameIsLessThanMinLength(){
        Board board = memberBoardFactory.createBoard("B");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenBoardNameIsGreaterThanMinLength(){
        Board board = memberBoardFactory.createBoard("TooLongName");
    }

}
