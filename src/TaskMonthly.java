import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskMonthly extends Task implements Repeatability {

    private final String repeatability = "ежемесячная";

    public TaskMonthly(String header, String description, boolean isWork, LocalDateTime deadLine) {
        super(header, description, isWork, deadLine);
    }

    public String getRepeatability() {
        return repeatability;
    }

    @Override
    public LocalDateTime getNextDeadLine(LocalDate checkDate) {
        LocalDateTime nextLocalDateTime = this.getDeadLine();
        while (nextLocalDateTime.toLocalDate().isBefore(checkDate)) {
            nextLocalDateTime = nextLocalDateTime.plusMonths(1);
        }
        return nextLocalDateTime;
    }
}