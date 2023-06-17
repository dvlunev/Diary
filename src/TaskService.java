import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The service class contains methods for working with tasks
 */
public class TaskService {

    private static final Map<Integer, Task> TASK_SERVICE = new HashMap<>();
    private static final Map<Integer, Task> REMOVED_TASKS = new HashMap<>();

    private static int id = 1;
//    не очень понял по какой логике нужно генерировать, какой диапазон, показалось логичным сделать просто id с первого
//    private int id = (int) (Math.random() * 1001); - либо вот так, но с проверкой на повторы по Key в методе add


    private TaskService() {
    }

    /**
     * The method adds a new Task
     *
     * @param task
     */
    public static void add(Task task) {
        //        если будет рандом, то нужно id проверить на повтор:
//        while (taskService.containsKey(id)) {
//            id = (int) (Math.random() * 1001);
//        }
        if (TASK_SERVICE.containsValue(task)) {
            System.out.println("Задача " + task.getHeader() + " уже есть в ежедневнике");
        } else {
            TASK_SERVICE.put(id++, task);
            System.out.println();
            System.out.println("Задача " + TASK_SERVICE.get(id - 1).getHeader() + " с номером id " + (id - 1) +
                    " добавлена в ежедневник");
        }
    }

    /**
     * The method removes a task
     *
     * @param id
     */
    public static void remove(int id) {
        if (TASK_SERVICE.size() == 0) {
            System.out.println("В ежедневнике нет ни одной задачи");
            return;
        }
        if (TASK_SERVICE.containsKey(id)) {
            System.out.println();
            if (!REMOVED_TASKS.containsKey(id)) {
                System.out.println("Задача " + TASK_SERVICE.get(id).getHeader() + " с номером id " + id +
                        " помечена удаленной");
                TASK_SERVICE.get(id).setRemoved(true);
                REMOVED_TASKS.put(id, TASK_SERVICE.get(id));
//                TASK_SERVICE.remove(id);
            } else {
                System.out.println("Задача " + TASK_SERVICE.get(id).getHeader() + " с номером id " + id +
                        " уже помечена удаленной");
            }
        } else {
            System.out.println();
            System.out.println("В ежедневнике отсутсвует задача с id " + id);
        }
    }

    /**
     * The method gets list of the tasks for particular date
     *
     * @param checkDate
     */
    public static void getTasksForDate(LocalDate checkDate) {
        System.out.println();
        if (TASK_SERVICE.size() == 0) {
            System.out.println("В ежедневнике нет ни одной задачи");
            return;
        }
        int countOfTasks = 0;
        System.out.println("Список задач на " +
                checkDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy года, EEEE")) + ":");
        System.out.println();
        for (Map.Entry<Integer, Task> integerTaskEntry : TASK_SERVICE.entrySet()) {
            LocalDateTime nextLocalDateTime = null;
            if (integerTaskEntry.getValue() instanceof Repeatability) {
                nextLocalDateTime = ((Repeatability) (integerTaskEntry.getValue())).getNextDeadLine(checkDate);
            }

            if (!integerTaskEntry.getValue().isRemoved() && (integerTaskEntry.getValue().getDeadLine().toLocalDate().isEqual(checkDate) ||
                    nextLocalDateTime != null && nextLocalDateTime.toLocalDate().isEqual(checkDate))) {
                System.out.println("Задача id: " + integerTaskEntry.getKey());
                System.out.println("Заголовок: " + integerTaskEntry.getValue().getHeader());
                System.out.println("Описание: " + integerTaskEntry.getValue().getDescription());
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

    /**
     * The method gets the list of the removed tasks
     */
    public static void getRemoved() {
        System.out.println();
        System.out.println("Список удаленных задач:");
        System.out.println();
        if (REMOVED_TASKS.size() == 0) {
            System.out.println("Удаленных задач нет");
            return;
        }
        for (Map.Entry<Integer, Task> integerTaskEntry : REMOVED_TASKS.entrySet()) {
            System.out.println("Задача id: " + integerTaskEntry.getKey());
            System.out.println("Заголовок: " + integerTaskEntry.getValue().getHeader());
            System.out.println("Описание: " + integerTaskEntry.getValue().getDescription());
            System.out.println("Тип задачи: " + (integerTaskEntry.getValue().isWork() ? "Рабочая" : "Личная"));
            System.out.println("Срок выполнения: " +
                    integerTaskEntry.getValue().getDeadLine().format(DateTimeFormatter.ofPattern("dd.MM.yyyy в HH:mm")));
            System.out.println("Повторяемость: " + integerTaskEntry.getValue().getRepeatability());
        }
    }

    /**
     * The method edits a header of the task by id
     *
     * @param id        of a task
     * @param newHeader of a task
     */
    public static void editHeader(int id, String newHeader) {
        if (TASK_SERVICE.size() == 0) {
            System.out.println("В ежедневнике нет ни одной задачи");
            return;
        }
        if (TASK_SERVICE.containsKey(id)) {
            System.out.println();
            if (TASK_SERVICE.get(id).isRemoved()) {
                System.out.println("Задача " + TASK_SERVICE.get(id).getHeader() + " с номером id " + id +
                        " помечена удаленной и ее уже нельзя редактировать");
            } else {
                System.out.print("Задача " + TASK_SERVICE.get(id).getHeader() + " с номером id " + id +
                        " отредактирована, новое название задачи: ");
                TASK_SERVICE.get(id).setHeader(newHeader);
                System.out.println(TASK_SERVICE.get(id).getHeader());
            }
        } else {
            System.out.println();
            System.out.println("В ежедневнике отсутсвует задача с id " + id);
        }
    }

    /**
     * The method edits a description of the task by id
     *
     * @param id             of a task
     * @param newDescription of a task
     */
    public static void editDescription(int id, String newDescription) {
        if (TASK_SERVICE.size() == 0) {
            System.out.println("В ежедневнике нет ни одной задачи");
            return;
        }
        if (TASK_SERVICE.containsKey(id)) {
            System.out.println();
            if (TASK_SERVICE.get(id).isRemoved()) {
                System.out.println("Задача " + TASK_SERVICE.get(id).getDescription() + " с номером id " + id +
                        " помечена удаленной и ее уже нельзя редактировать");
            } else {
                System.out.print("Задача " + TASK_SERVICE.get(id).getDescription() + " с номером id " + id +
                        " отредактирована, новое описание задачи: ");
                TASK_SERVICE.get(id).setDescription(newDescription);
                System.out.println(TASK_SERVICE.get(id).getDescription());
            }
        } else {
            System.out.println();
            System.out.println("В ежедневнике отсутсвует задача с id " + id);
        }
    }

    /**
     * The method prints grouped tasks by dates
     */
    public static void printGroupedTasks() {
        if (TASK_SERVICE.size() == 0) {
            System.out.println("В ежедневнике нет ни одной задачи");
            return;
        }
        System.out.println();
        System.out.println("Список задач сгруппированных по датам:");
        System.out.println();
        Map<Integer, Task> groupedTasks = new LinkedHashMap<>();
        for (Map.Entry<Integer, Task> Task : TASK_SERVICE.entrySet()) {
            if (!Task.getValue().isRemoved()) {
                groupedTasks.put(Task.getKey(), Task.getValue());
            }
            for (Map.Entry<Integer, Task> Task1 : TASK_SERVICE.entrySet()) {
                if (!Task1.getValue().isRemoved() && Task.getValue().getDeadLine().toLocalDate().isEqual(Task1.getValue().getDeadLine().toLocalDate())) {
                    groupedTasks.put(Task1.getKey(), Task1.getValue());
                }
            }
        }
        LocalDateTime previousLocalDateTime = null;
        for (Map.Entry<Integer, Task> Task : groupedTasks.entrySet()) {
            if (previousLocalDateTime == null || !(previousLocalDateTime.toLocalDate().isEqual(Task.getValue().getDeadLine().toLocalDate()))) {
                System.out.println("Задачи на дату : " + Task.getValue().getDeadLine().toLocalDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy года, EEEE")));
            }
            System.out.println("Задача id: " + Task.getKey());
            System.out.println("Заголовок: " + Task.getValue().getHeader());
            System.out.println("Описание: " + Task.getValue().getDescription());
            System.out.println("Тип задачи: " + (Task.getValue().isWork() ? "Рабочая" : "Личная"));
            System.out.println("Повторяемость: " + Task.getValue().getRepeatability());
            previousLocalDateTime = Task.getValue().getDeadLine();
        }
    }
}