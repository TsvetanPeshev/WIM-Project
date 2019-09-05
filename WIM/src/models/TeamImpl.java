package models;

import models.common.Validator;
import models.contracts.Board;
import models.contracts.Member;
import models.contracts.Team;

import java.util.LinkedHashMap;
import java.util.Map;

import static commands.CommandConstants.BOARD_EXISTS_ERROR_MESSAGE;
import static commands.CommandConstants.MEMBER_EXISTS_ERROR_MESSAGE;


public  class TeamImpl extends ActivityBase implements Team {
    private static final String TEAM_NAME_HEADER = "TeamName";

    private String name;
    private Map<String, Member> members;
    private Map<String, Board> boards;


    public TeamImpl(String name) {
        setName(name);
        members = new LinkedHashMap<>();
        boards = new LinkedHashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        Validator.checkForEmptyValue(name, TEAM_NAME_HEADER);
        this.name = name;
    }

    @Override
    public void addMember(Member member){
        if (members.containsKey(name)) {
            throw new IllegalArgumentException(String.format(
                    MEMBER_EXISTS_ERROR_MESSAGE,
                    member.getName()));
        }
        members.put(member.getName(), member);
    }

    @Override
    public void addBoard(Board board){
       if(boards.containsKey(name)){
           throw new IllegalArgumentException(String.format(
                   BOARD_EXISTS_ERROR_MESSAGE,
                   name));
       }

       boards.put(board.getName(), board);
    }

    @Override
    public Map<String, Board> getBoards(){
        return new LinkedHashMap<>(boards);
    }

    @Override
    public Map<String, Member> getMembers() { return new LinkedHashMap<String, Member>(members); }

}
