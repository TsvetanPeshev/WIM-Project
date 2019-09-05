package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import core.contracts.TeamRepository;
import models.common.Validator;
import models.contracts.Member;
import models.contracts.Team;

import java.util.List;

import static commands.CommandConstants.*;

public class AddPersonToTeam implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 2;

    private TeamRepository teamRepository;
    private MemberBoardRepository memberBoardRepository;

    public AddPersonToTeam(TeamRepository teamRepository, MemberBoardRepository memberBoardRepository) {
        this.teamRepository = teamRepository;
        this.memberBoardRepository = memberBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(parameters.size(), CORRECT_NUMBER_OF_ARGUMENTS);

        if (memberBoardRepository.getMembers().isEmpty()) {
            return NO_MEMBERS_ERROR_MESSAGE;
        }

        String memberName = parameters.get(0);
        String teamName = parameters.get(1);

        return addPersonToTeam(memberName, teamName);
    }

    public String addPersonToTeam(String memberName, String teamName) {

        if (teamRepository.getTeams().isEmpty()) {
            return NO_TEAMS_ERROR_MESSAGE;
        }
        if (!memberBoardRepository.getMembers().containsKey(memberName)) {
            throw new IllegalArgumentException("No such a person");
        }

        Member member = memberBoardRepository.getMembers().get(memberName);

        Team team = teamRepository.getTeams().get(teamName);

        team.addMember(member);

        team.addActivity(
                String.format(MEMBER_ADDED_TO_TEAM_SUCCESS_MESSAGE, member.getName(), team.getName()));
        member.addActivity(
                String.format(MEMBER_ADDED_TO_TEAM_SUCCESS_MESSAGE, member.getName(), team.getName()));

        return String.format(MEMBER_ADDED_TO_TEAM_SUCCESS_MESSAGE, member.getName(), team.getName());

    }

}
