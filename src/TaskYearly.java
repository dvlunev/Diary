import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The class that describes an annual task and containing the implementation of the interface {@link Repeatability}
 */
public class TaskYearly extends Task implements Repeatability {

    private final String repeatability = "ежегодная";

    public TaskYearly(String header, String description, boolean isWork, LocalDateTime deadLine) {
        super(header, description, isWork, deadLine);
    }

    public String getRepeatability() {
        return repeatability;
    }

    @Override
    public LocalDateTime getNextDeadLine(LocalDate checkDate) {
        LocalDateTime nextLocalDateTime = this.getDeadLine();
        while (nextLocalDateTime.toLocalDate().isBefore(checkDate)) {
            nextLocalDateTime = nextLocalDateTime.plusYears(1);
        }
        return nextLocalDateTime;
    }
}
