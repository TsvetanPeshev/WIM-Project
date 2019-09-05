package core.providers;

import core.contracts.Reader;

import java.util.Scanner;

public class CommandReaderImpl implements Reader {
    private final Scanner scanner;

    public CommandReaderImpl() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
