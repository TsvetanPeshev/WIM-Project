package commands;

import core.contracts.*;

public class Facility {

    private MemberBoardRepository memberBoardRepository;
    private MemberBoardFactory memberBoardFactory;
    private TeamFactory teamFactory;
    private TeamRepository teamRepository;
    private WorkitemRepository workitemRepository;
    private WorkitemFactory workItemFactory;
    private CommentsFactory commentsFactory;

    public Facility(MemberBoardFactory memberBoardFactory,
                    MemberBoardRepository memberBoardRepository,
                    TeamFactory teamFactory,
                    TeamRepository teamRepository,
                    WorkitemFactory workItemFactory,
                    WorkitemRepository workitemRepository,
                    CommentsFactory commentsFactory)
    {
        this.memberBoardFactory=memberBoardFactory;
        this.memberBoardRepository=memberBoardRepository;
        this.teamFactory=teamFactory;
        this.teamRepository=teamRepository;
        this.workitemRepository=workitemRepository;
        this.workItemFactory=workItemFactory;
        this.commentsFactory = commentsFactory;
    }

    public MemberBoardRepository getMemberBoardRepository() {
        return memberBoardRepository;
    }

    public MemberBoardFactory getMemberBoardFactory() {
        return memberBoardFactory;
    }

    public TeamFactory getTeamFactory() {
        return teamFactory;
    }

    public TeamRepository getTeamRepository() {
        return teamRepository;
    }

    public WorkitemRepository getWorkitemRepository() {
        return workitemRepository;
    }

    public CommentsFactory getCommentsFactory(){
        return commentsFactory;
    }

    public WorkitemFactory getWorkItemFactory() {
        return workItemFactory;
    }
}
