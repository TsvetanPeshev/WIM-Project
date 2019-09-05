package models;

import models.contracts.Board;

public class BoardImpl extends MemberBoardBaseImpl implements Board {
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 10;

    public BoardImpl(String name) {
        super(name);
    }

    @Override
    protected int getNameMinLength(){
        return MIN_LENGTH;
    }

    @Override
    protected int getNameMaxLength(){
        return MAX_LENGTH;
    }
}
