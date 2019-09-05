package commands;

import commands.contracts.Command;
import core.MemberBoardRepositoryImpl;
import core.contracts.MemberBoardFactory;
import core.contracts.MemberBoardRepository;
import core.factories.MemberBoardFactoryImpl;
import models.common.Validator;
import models.contracts.Member;

import java.util.List;

import static commands.CommandConstants.*;

public class CreateMember implements Command {
    private static final int CORRECT_NUMBER_OF_ARGUMENTS = 1;

    private final MemberBoardRepository memberBoardRepository;
    private final MemberBoardFactory memberBoardFactory;

    public  CreateMember(MemberBoardFactory memberBoardFactory, MemberBoardRepository memberBoardRepository) {
        this.memberBoardFactory = memberBoardFactory;
        this.memberBoardRepository = memberBoardRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Validator.checkForCorrectNumberOfArguments(
                parameters.size(),
                CORRECT_NUMBER_OF_ARGUMENTS);

        String memberName = parameters.get(0);
        return createMember(memberName);
    }

    private String createMember(String name) {


        if (memberBoardRepository.getMembers().containsKey(name)) {
            return String.format(MEMBER_EXISTS_ERROR_MESSAGE, name);
        }

        Member member = memberBoardFactory.createMember(name);
        memberBoardRepository.addMember(name, member);

        member.addActivity(String.format(MEMBER_CREATED_SUCCESS_MESSAGE, name));
        return String.format(MEMBER_CREATED_SUCCESS_MESSAGE, name);
    }
}
