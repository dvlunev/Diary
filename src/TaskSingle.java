import java.time.LocalDateTime;

public class TaskSingle extends Task {

    private final String repeatability = "однократная";

    public TaskSingle(String header, String description, boolean isWork, LocalDateTime deadLine) {
        super(header, description, isWork, deadLine);
    }

    @Override
    public String getRepeatability() {
        return repeatability;
    }
}
