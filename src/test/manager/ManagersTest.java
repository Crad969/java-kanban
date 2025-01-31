package test.manager;
import manager.*;
import data.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ManagersTest {

    @Test
    void shouldReturnInitializedTaskAndHistoryManager() {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(taskManager, "Экземпляр TaskManager не должен быть null");
        assertNotNull(historyManager, "Экземпляр HistoryManager не должен быть null");
    }

    @Test
    void shouldReturnNewInstanceForEachGetDefaultCall() {
        TaskManager firstTaskManager = Managers.getDefault();
        TaskManager secondTaskManager = Managers.getDefault();

        assertNotSame(firstTaskManager, secondTaskManager,
                "Каждый вызов getDefault должен возвращать новый экземпляр TaskManager");
    }

    @Test
    void shouldReturnNewInstanceForEachGetDefaultHistoryCall() {
        HistoryManager firstHistoryManager = Managers.getDefaultHistory();
        HistoryManager secondHistoryManager = Managers.getDefaultHistory();

        assertNotSame(firstHistoryManager, secondHistoryManager,
                "Каждый вызов getDefaultHistory должен возвращать новый экземпляр HistoryManager");
    }

    @Test
    void shouldStoreAndRetrieveTasksCorrectly() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test Task", "Description", Status.NEW);
        taskManager.addTask(task);

        List<Task> tasks = taskManager.getAllTasks();
        assertEquals(1, tasks.size(), "Диспетчер задач должен корректно хранить задачи");
        assertEquals(task, tasks.get(0), "Диспетчер задач должен вернуть правильную задачу");
    }

    @Test
    void shouldStoreAndRetrieveTaskHistoryCorrectly() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test Task", "Description", Status.NEW);
        taskManager.addTask(task);
        taskManager.getTask(task.getId()); // Добавляем в историю

        List<Task> history = taskManager.getHistory();
        assertNotNull(history, "История просмотров не должна быть null");
        assertEquals(1, history.size(), "История должна корректно хранить задачи");
        assertEquals(task, history.get(0), "История должна возвращать правильную задачу");
    }
}