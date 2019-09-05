package core.contracts;

import commands.Facility;
import commands.contracts.Command;

public interface CommandFactory{
     Command createCommand(String commandTypeAsString, Facility facility);
}
