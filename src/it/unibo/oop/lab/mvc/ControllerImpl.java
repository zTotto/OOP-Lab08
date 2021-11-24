package it.unibo.oop.lab.mvc;

import java.util.LinkedList;
import java.util.List;
/**
 * 
 *
 */
public class ControllerImpl implements Controller {

    private String current;
    private final List<String> printedStrings = new LinkedList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNextPrint(final String nextString) {
        if (nextString != null) {
            this.current = nextString;
        } else {
            System.out.println("Can't accept a null value");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNext() {
        return this.current;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getPrinted() {
        return new LinkedList<String>(printedStrings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printCurrent() {
        if (this.current == null) {
            throw new IllegalStateException("Next string is uninitialized");
        }
        System.out.println(this.current);
        printedStrings.add(this.current);
    }

}
