import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
                TaskSingle taskSingle = new TaskSingle(
                "Однократная задача 1",
                "Описание однократной задачи 1",
                true,
                LocalDateTime.of(2023, 2, 6, 10, 1)
        );
        TaskDaily taskDaily = new TaskDaily(
                "Ежедневная задача 1",
                "Описание ежедневной задачи 1",
                false,
                LocalDateTime.of(2023, 1, 7, 11, 2)
        );
        TaskWeekly taskWeekly = new TaskWeekly(
                "Еженедельная задача 1",
                "Описание еженедельной задачи 1",
                true,
                LocalDateTime.of(2023, 2, 6, 12, 3)
        );
//        TaskMonthly taskMonthly = new TaskMonthly(
//                "Ежемесячная задача 1",
//                "Описание ежемесячной задачи 1",
//                false,
//                LocalDateTime.of(2022, 12, 6, 13, 4)
//        );
//        TaskYearly taskYearly = new TaskYearly(
//                "Ежегодная задача 1",
//                "Описание ежегодной задачи 1",
//                true,
//                LocalDateTime.of(2023, 2, 6, 14, 5)
//        );
//
        TaskService.add(taskSingle);
        TaskService.add(taskDaily);
        TaskService.add(taskWeekly);
//        taskService.add(taskMonthly);
//        taskService.add(taskYearly);
//        taskService.getTasksForDate(LocalDate.of(2023, 1, 5));

        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                System.out.println();
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            removeTask(scanner);
                            break;
                        case 3:
                            editTask(scanner);
                            break;
                        case 4:
                            getTaskForDay(scanner);
                            break;
                        case 5:
                            TaskService.printGroupedTasks();
                            break;
                        case 6:
                            TaskService.getRemoved();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }


    private static void inputTask(Scanner scanner) {
        scanner.useDelimiter("\n");
        System.out.print("Введите название задачи: ");
        String taskName = scanner.next();

        System.out.print("Введите описание задачи: ");
        String description = scanner.next();

        Boolean isWork = null;
        while (isWork == null) {
            System.out.println("Выберете тип задачи:");
            System.out.print(
                    """
                          1. Рабочая
                          2. Личная
                          """
                );
            if (scanner.hasNextInt()) {
                int work = scanner.nextInt();
                switch (work) {
                    case 1:
                        isWork = true;
                        break;
                    case 2:
                        isWork = false;
                        break;
                }
            } else {
                scanner.next();
                System.out.println("Выберите пункт меню из списка!");
            }
        }

        Pattern pattern = Pattern.compile("\\d{1,2}\\.\\d{1,2}\\.\\d{4} \\d{2}:\\d{2}");
        LocalDateTime deadLine = null;
        while (deadLine == null) {
            System.out.print("Введите дату и время выполнения задачи в формате ДД.ММ.ГГГГ ЧЧ.ММ: ");
            if (scanner.hasNext(pattern) && (deadLine = parseLocalDateTime(scanner.next(pattern))) != null) {

            } else {
                scanner.next();
            }
        }
        Integer repeatability = null;
        while (repeatability == null) {
            System.out.println("Выберете признак повторяемости:");
            System.out.print(
                    """
                          1. Однократная
                          2. Ежедневная
                          3. Еженедельная
                          4. Ежемесячная
                          5. Ежегодная
                          """
            );
            if (scanner.hasNextInt()) {
                repeatability = scanner.nextInt();
                switch (repeatability) {
                    case 1:
                        TaskService.add(new TaskSingle(taskName,description,isWork,deadLine));
                        break;
                    case 2:
                        TaskService.add(new TaskDaily(taskName,description,isWork,deadLine));
                        break;
                    case 3:
                        TaskService.add(new TaskWeekly(taskName,description,isWork,deadLine));
                        break;
                    case 4:
                        TaskService.add(new TaskMonthly(taskName,description,isWork,deadLine));
                        break;
                    case 5:
                        TaskService.add(new TaskYearly(taskName,description,isWork,deadLine));
                        break;
                }
            } else {
                scanner.next();
                System.out.println("Выберите пункт меню из списка!");
            }
        }
    }

    public static void removeTask(Scanner scanner) {
        scanner.useDelimiter("\n");
        System.out.print("Введите id задачи для удаления: ");
        int id = 0;
        while (id == 0) {
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                TaskService.remove(id);
            } else {
                scanner.next();
                System.out.print("Введите целочисленное значение id: ");
            }
        }
    }

    public static void editTask(Scanner scanner) {
        scanner.useDelimiter("\n");
        System.out.print("Введите id задачи для редактирования: ");
        int id = 0;
        while (id == 0) {
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
            } else {
                scanner.next();
                System.out.print("Введите целочисленное значение id: ");
            }
        }

        int item = 0;
        while (!(item == 1 || item == 2)) {
            System.out.println("Выберете свойство задачи для редактирования:");
            System.out.print(
                    """
                            1. Заголовок
                            2. Описание
                            """
            );
            if (scanner.hasNextInt()) {
                item = scanner.nextInt();
            } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
            }
        }

        if (item == 1) {
            System.out.print("Введите новый заголовок задачи: ");
            String newHeader = scanner.next();
            TaskService.editHeader(id, newHeader);
        }
        if (item == 2) {
            System.out.print("Введите новое описание задачи: ");
            String newDescription = scanner.next();
            TaskService.editDescription(id,newDescription);
        }
    }

    public static void getTaskForDay(Scanner scanner) {
        scanner.useDelimiter("\n");
        System.out.print("Введите дату на которую необходимо получить задачи в формате ДД.ММ.ГГГГ: ");
        Pattern pattern = Pattern.compile("\\d{1,2}\\.\\d{1,2}\\.\\d{4}");
        LocalDate deadLine = null;
        while (deadLine == null) {
            if (scanner.hasNext(pattern) && ((deadLine = parseLocalDate(scanner.next(pattern))) != null))  {
                TaskService.getTasksForDate(deadLine);
            } else {
                scanner.next();
                System.out.print("Введите дату на которую необходимо получить задачи в формате ДД.ММ.ГГГГ: ");
            }
        }
    }

    private static LocalDateTime parseLocalDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm"));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static LocalDate parseLocalDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyy"));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void printMenu() {
        System.out.println(
                """
                        1. Добавить задачу
                        2. Удалить задачу
                        3. Редактировать задачу
                        4. Получить задачи на указанный день
                        5. Получить список задач, сгруппированных по датам
                        6. Получить список удаленных задач
                        0. Выход
                        """
        );
    }
}