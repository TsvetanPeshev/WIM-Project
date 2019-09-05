package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import models.common.Validator;
import models.contracts.Member;

import java.util.List;

import static commands.CommandConstants.NO_MEMBERS_ERROR_MESSAGE;

public class ShowPersonsActivity implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;
    private Member member;

    private final MemberBoardRepository memberBoardRepository;

    public ShowPersonsActivity(MemberBoardRepository memberBoardRepository) {
        this.memberBoardRepository = memberBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        if (memberBoardRepository.getMembers().isEmpty()) {
            return NO_MEMBERS_ERROR_MESSAGE;
        }
        String memberName = parameters.get(0);
        member = memberBoardRepository.getMembers().get(memberName);
        StringBuilder sb = new StringBuilder(String.format("Member Activity:%n"));

        memberBoardRepository
                .getMembers()
                .keySet()
                .stream()
                .filter(m -> m.compareTo(memberName) == 0)
                .forEach(a -> sb.append(member.showActivity(memberName))
                        .append(System.lineSeparator()));

        return sb.toString();
    }
}
