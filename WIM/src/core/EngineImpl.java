package core;

import commands.Facility;
import commands.contracts.Command;
import core.contracts.*;
import core.factories.*;
import core.providers.CommandParserImpl;
import core.providers.CommandReaderImpl;
import core.providers.CommandWriterImpl;
import models.common.Validator;

import java.util.List;

public class EngineImpl implements Engine {
    private static final String TERMINATION_COMMAND = "Exit";
    private static final String COMMAND_HEADER = "Command";

    private CommandFactory commandFactory;
    private CommandParser commandParser;
    private Reader reader;
    private Writer writer;

    private Facility facility;
    private MemberBoardFactory memberBoardFactory;
    private MemberBoardRepository memberBoardRepository;
    private WorkitemFactory workitemFactory;
    private WorkitemRepository workitemRepository;
    private TeamFactory teamFactory;
    private TeamRepository teamRepository;
    private CommentsFactory commentsFactory;

    public EngineImpl() {
        commandParser = new CommandParserImpl();
        writer = new CommandWriterImpl();
        reader = new CommandReaderImpl();
        commandFactory = new CommandFactoryImpl();
        initializeFaciilities();
    }

    public void start() {
        while (true) {
            try {
                String commandAsString = reader.readLine();
                if (commandAsString.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(commandAsString);
            } catch (Exception ex) {
                writer.writeLine(ex.toString());

            }
        }
    }

    private void processCommand(String commandAsString) {
        Validator.checkForEmptyValue(commandAsString, COMMAND_HEADER);
        String commandName = commandParser.parseCommand(commandAsString);
        Command command = commandFactory.createCommand(commandName, facility);
        List<String> parameters = commandParser.parseParameters(commandAsString);
        String executionResult = command.execute(parameters);
        writer.writeLine(executionResult);
    }

    private void initializeFaciilities() {
        this.teamFactory = new TeamFactoryImpl();
        this.teamRepository = new TeamRepositoryImpl();
        this.memberBoardFactory = new MemberBoardFactoryImpl();
        this.memberBoardRepository = new MemberBoardRepositoryImpl();
        this.workitemFactory = new WorkitemFactoryImpl();
        this.workitemRepository = new WorkitemRepositoryImpl();
        commentsFactory = new CommentsFactoryImpl();
        this.facility = new Facility(
                memberBoardFactory,
                memberBoardRepository,
                teamFactory,
                teamRepository,
                workitemFactory,
                workitemRepository,
                commentsFactory);

    }
}
