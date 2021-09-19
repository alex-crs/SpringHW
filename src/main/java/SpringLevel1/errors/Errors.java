package SpringLevel1.errors;

import lombok.Data;

import java.util.*;

@Data
public class Errors {
    private int status;
    private List<String> messages;
    private Date timestamp;

    public Errors(int status, String... message) {
        this.status = status;
        this.messages = new ArrayList<>(Arrays.asList(message));
        this.timestamp = new Date();
    }

    public Errors(int status, Collection<String> messages) {
        this.status = status;
        this.messages = new ArrayList<>(messages);
        this.timestamp = new Date();
    }
}
