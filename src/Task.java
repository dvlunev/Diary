import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The abstract class describing a Task
 */
public abstract class Task {

    private String header;
    private String description;
    private final boolean isWork;
    private LocalDateTime deadLine;
    private boolean removed;

    public Task(String header,
                String description,
                boolean isWork,
                LocalDateTime deadLine) {
        setHeader(header);
        setDescription(description);
        this.isWork = isWork;
        setDeadLine(deadLine);
        this.removed = false;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        if (StringUtils.isNullOrEmptyOrBlank(header)) {
            throw new IllegalArgumentException("Поле Заголовок обязательно к заполнению");
        }
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (StringUtils.isNullOrEmptyOrBlank(description)) {
            throw new IllegalArgumentException("Поле Описание обязательно к заполнению");
        }
        this.description = description;
    }

    public boolean isWork() {
        return isWork;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
//        if (deadLine.toLocalDate().isBefore(LocalDate.now().plusDays(1))) {
//            throw new IllegalArgumentException("Дата задачи не может быть раньше или равна текущей");
//        } убрал чтобы потестрировать
        this.deadLine = deadLine;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public abstract String getRepeatability();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return isWork == task.isWork && Objects.equals(header, task.header) &&
                Objects.equals(description, task.description) && Objects.equals(deadLine, task.deadLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, description, isWork, deadLine);
    }


}
