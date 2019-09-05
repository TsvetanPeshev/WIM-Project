package core.providers;

import core.contracts.Writer;

public class CommandWriterImpl implements Writer{

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public void writeLine(String message) {
        System.out.println(message);

    }
}
