package it.unibo.oop.lab.advanced;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * A custom view that log what happens in the specified file.
 *
 */
public class LoggerView implements DrawNumberView {

    private final PrintStream stream;

    /**
     * Starts a logger in the specified path.
     * @param path
     * @throws FileNotFoundException 
     */
    public LoggerView(final String path) throws FileNotFoundException {
        this.stream = new PrintStream(new FileOutputStream(new File(path)));
    }

    /**
     * Starts a logger from a print stream.
     * @param out
     */
    public LoggerView(final PrintStream out) {
        this.stream = out;
    }

    @Override
    public void setObserver(final DrawNumberViewObserver observer) {
    }

    @Override
    public void start() {
    }

    /**
     * Write on file that the written number is wrong.
     */
    @Override
    public void numberIncorrect() {
        stream.println("Incorrect Number.. try again");
    }

    /**
     * 
     */
    @Override
    public void result(final DrawResult res) {
        stream.println(res.getDescription());
    }

    /**
     * Write on file that the attempts limit has been reached.
     */
    @Override
    public void limitsReached() {
        stream.println("You lost");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void displayError(final String message) {
        stream.println("An error occurred, " + message);
    }

}
