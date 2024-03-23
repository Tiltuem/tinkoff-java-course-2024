package edu.java.util.updateChecker;


import edu.java.model.Link;
import java.util.Optional;

public interface UpdateChecker {

    Optional<String> checkUpdates(Link link);

    boolean isAppropriateLink(Link link);
}
