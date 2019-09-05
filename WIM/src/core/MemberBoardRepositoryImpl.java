package core;

import core.contracts.MemberBoardRepository;
import models.contracts.Board;
import models.contracts.Member;

import java.util.LinkedHashMap;
import java.util.Map;

public class MemberBoardRepositoryImpl implements MemberBoardRepository {
    private Map<String, Member> members;
    private Map<String, Board> boards;

    public MemberBoardRepositoryImpl() {
        members = new LinkedHashMap<>();
        boards = new LinkedHashMap<>();
    }

    @Override
    public Map<String, Member> getMembers() {
        return new LinkedHashMap<>(members);
    }

    @Override
    public void addMember(String name, Member member) {
        members.put(name, member);
    }

    @Override
    public Map<String, Board> getBoards() {
        return new LinkedHashMap<>(boards);

    }

    @Override
    public void addBoard(String name, Board board) {
        this.boards.put(name, board);
    }
}
