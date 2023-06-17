import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The class that describes a daily task and containing the implementation of the interface {@link Repeatability}
 */
public class TaskDaily extends Task implements Repeatability {

    private final String repeatability = "ежедневная";

    public TaskDaily(String header, String description, boolean isWork, LocalDateTime deadLine) {
        super(header, description, isWork, deadLine);
    }

    @Override
    public String getRepeatability() {
        return repeatability;
    }

    @Override
    public LocalDateTime getNextDeadLine(LocalDate checkDate) {
        LocalDateTime nextLocalDateTime = this.getDeadLine();
        while (nextLocalDateTime.toLocalDate().isBefore(checkDate)) {
            nextLocalDateTime = nextLocalDateTime.plusDays(1);
        }
        return nextLocalDateTime;
    }
}
