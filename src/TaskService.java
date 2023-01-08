import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TaskService {

    private static final Map<Integer, Task> TASK_SERVICE = new HashMap<>();

    private static int id = 1;
//    не очень понял по какой логике нужно генерировать, какой диапазон, показалось логичным сделать просто id с первого
//    private int id = (int) (Math.random() * 1001); - либо вот так, но с проверкой на повторы по Key в методе add


    private TaskService() {
    }

    public static void add(Task task) {
        //        если будет рандом, то нужно id проверить на повтор:
//        while (taskService.containsKey(id)) {
//            id = (int) (Math.random() * 1001);
//        }
        if (TASK_SERVICE.containsValue(task)) {
            System.out.println("Задача " + task.getHeader() + " уже есть в ежедневнике");
        } else {
            TASK_SERVICE.put(id++,task);
            System.out.println();
            System.out.println("Задача " + TASK_SERVICE.get(id - 1).getHeader() + " с номером id " + (id - 1) +
                    " добавлена в ежедневник");
        }
    }

    public static void remove(int id) {
        if (TASK_SERVICE.containsKey(id)) {
            System.out.println();
            System.out.println("Задача " + TASK_SERVICE.get(id).getHeader() + " с номером id " + id +
                    " удалена из ежедневника");
            TASK_SERVICE.remove(id);
        } else {
            System.out.println();
            System.out.println("В ежедневнике отсутсвует задача с id " + id);
        }
    }

    public static void getTasksForDate(LocalDate checkDate) {
        System.out.println();
        int countOfTasks = 0;
        System.out.println("Список задач на " +
                checkDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy года, EEEE")) + ":");
        System.out.println();
        for (Map.Entry<Integer, Task> integerTaskEntry : TASK_SERVICE.entrySet()) {
            LocalDateTime nextLocalDateTime = null;

            if (integerTaskEntry.getValue() instanceof Repeatability) {
                nextLocalDateTime = ((Repeatability) (integerTaskEntry.getValue())).getNextDeadLine(checkDate);
            }

            if (integerTaskEntry.getValue().getDeadLine().toLocalDate().isEqual(checkDate) ||
                    nextLocalDateTime != null && nextLocalDateTime.toLocalDate().isEqual(checkDate)) {
                System.out.println("Задача id: " + integerTaskEntry.getKey());
                System.out.println("Заголовок: " + integerTaskEntry.getValue().getHeader());
                System.out.println("Описание: " + integerTaskEntry.getValue().getHeader());
                System.out.println("Тип задачи: " + (integerTaskEntry.getValue().isWork() ? "Рабочая" : "Личная"));
                System.out.println("Срок выполнения: " + ((nextLocalDateTime == null) ?
                        integerTaskEntry.getValue().getDeadLine().format(DateTimeFormatter.ofPattern("dd.MM.yyyy в HH:mm")) :
                        nextLocalDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy в HH:mm"))));
                System.out.println("Повторяемость: " + integerTaskEntry.getValue().getRepeatability());
                countOfTasks++;
            }
        }
        if (countOfTasks == 0) {
            System.out.println("Задач нет, отдыхаем");
        }
    }

    public static int getId() {
        return id;
    }
}
