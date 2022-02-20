package de.jplag;

import static de.jplag.options.Verbosity.LONG;

import java.util.ArrayList;
import java.util.List;

import de.jplag.options.JPlagOptions;
import de.jplag.options.Verbosity;

/**
 * Error collector class that collects errors but also allows printing the collected errors.
 * @author Timur Saglam
 */
public class ErrorCollector implements ErrorConsumer { // TODO TS should be eventually replaced with a true logger/logging manager

    private final List<String> collectedErrors; // List of errors that occurred during the execution of the errorConsumer.
    private final JPlagOptions options;
    private String currentSubmissionName;

    public ErrorCollector(JPlagOptions options) {
        this.options = options;
        collectedErrors = new ArrayList<>();
        currentSubmissionName = "<Unknown submission>";
    }

    @Override
    public void addError(String errorMessage) {
        collectedErrors.add("[" + currentSubmissionName + "] " + errorMessage);
        print(null, "\t" + errorMessage);
    }

    @Override
    public void print(String message, String longMessage) {
        if (message == null && longMessage == null) {
            throw new IllegalArgumentException("At least one message parameter needs to be non-null!");
        }
        Verbosity verbosity = options.getVerbosity();
        if (message != null) {
            System.out.println(message);
        }
        if (longMessage != null && verbosity == LONG) {
            System.out.println(longMessage);
        }
    }

    /**
     * Print all collected errors messages in a list-like fashion.
     */
    public void printCollectedErrors() {
        StringBuilder errorReport = new StringBuilder();
        System.out.println("The following errors occured: ");
        for (String message : collectedErrors) {
            errorReport.append(message);
            errorReport.append('\n');
        }

        System.out.println(errorReport);
    }

    /**
     * Updates the name of the currently processed submission.
     * @param currentSubmissionName is the name.
     */
    public void setCurrentSubmissionName(String currentSubmissionName) {
        this.currentSubmissionName = currentSubmissionName;
    }

    /**
     * @return true if there is at least one error.
     */
    public boolean hasErrors() {
        return !collectedErrors.isEmpty();
    }
}
