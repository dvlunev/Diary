import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Repeatability {

    LocalDateTime getNextDeadLine(LocalDate checkDate);
}
