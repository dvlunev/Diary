import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The service class interface containing the method that gets the next deadline
 */
public interface Repeatability {

    LocalDateTime getNextDeadLine(LocalDate checkDate);
}
