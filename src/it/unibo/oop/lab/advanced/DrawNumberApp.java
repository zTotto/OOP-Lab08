package it.unibo.oop.lab.advanced;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private static int min;
    private static int max = 100;
    private static int attempts = 10;
    private final DrawNumber model;
    private final List<DrawNumberView> view;

    /**
     * Constructor for the application, with multiple views.
     * 
     * @param view
     */
    public DrawNumberApp(final DrawNumberView... view) {
        loadFromConfig();
        this.model = new DrawNumberImpl(min, max, attempts);
        this.view = new LinkedList<>();
        Collections.addAll(this.view, view);
        for (final DrawNumberView v : this.view) {
            v.setObserver(this);
            v.start();
        }
    }

    @SuppressWarnings("PMD.CloseResource")
    /**
     * Loads configs from file.
     */
    private void loadFromConfig() {
        String line;
        final InputStream inStream = ClassLoader.getSystemResourceAsStream("config.yml");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        try {
            while ((line = reader.readLine()) != null) {
                final StringTokenizer tokenizer = new StringTokenizer(line);
                tokenizer.nextToken(": ");
                if (line.startsWith("min")) {
                    min = Integer.parseInt(tokenizer.nextToken());
                } else if (line.contains("max")) {
                    max = Integer.parseInt(tokenizer.nextToken());
                } else if (line.contains("attempts")) {
                    attempts = Integer.parseInt(tokenizer.nextToken());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Closing input stream
        try {
            inStream.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newAttempt(final int n) {
        for (final DrawNumberView v : this.view) {
            try {
                final DrawResult result = model.attempt(n);
                v.result(result);
            } catch (IllegalArgumentException e) {
                v.numberIncorrect();
            } catch (AttemptsLimitReachedException e) {
                v.limitsReached();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    @SuppressWarnings("PMD.DoNotTerminateVM")
    public void quit() {
        System.exit(0);
    }

    /**
     * @param args
     *                 ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp(
                new DrawNumberViewImpl(),
                //new DrawNumberViewImpl(),
                new LoggerView("output.txt"),
                new LoggerView(System.out)
                );
    }

}
