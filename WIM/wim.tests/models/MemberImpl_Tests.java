package models;

import core.contracts.MemberBoardFactory;
import core.factories.MemberBoardFactoryImpl;
import models.contracts.Member;
import org.junit.Before;
import org.junit.Test;

public class MemberImpl_Tests {
    MemberBoardFactory memberBoardFactory;

    @Before
    public void before(){
        memberBoardFactory = new MemberBoardFactoryImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenMemberNameIsLessThanMinLength(){
        Member member = memberBoardFactory.createMember("El");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenMemberNameIsGreaterThanMinLength(){
        Member member = memberBoardFactory.createMember("KonstantinBorisov");
    }
}
