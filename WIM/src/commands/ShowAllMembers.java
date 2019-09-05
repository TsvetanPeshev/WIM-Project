package commands;

import commands.contracts.Command;
import core.contracts.MemberBoardRepository;
import models.common.Validator;

import java.util.List;

import static commands.CommandConstants.NO_MEMBERS_ERROR_MESSAGE;

public class ShowAllMembers implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 0;

    private MemberBoardRepository memberBoardRepository;

    public ShowAllMembers(MemberBoardRepository memberBoardRepository){
        this.memberBoardRepository = memberBoardRepository;
    }
    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        if(memberBoardRepository.getMembers().isEmpty()){
            return NO_MEMBERS_ERROR_MESSAGE;
        }

        StringBuilder sb = new StringBuilder(String.format("Members are:%n"));
        memberBoardRepository.getMembers()
                .values()
                .forEach(member -> sb.append(member.getName()).append(System.lineSeparator()));

        return sb.toString();
    }
}
