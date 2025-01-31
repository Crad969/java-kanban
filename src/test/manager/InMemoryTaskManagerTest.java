package test.manager;

import manager.*;
import data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    void taskManagerShouldAddAndRetrieveTasksById() {
        Task task = new Task("Task", "Description", Status.NEW);
        taskManager.addTask(task);

        Task foundTask = taskManager.getTask(task.getId());
        assertNotNull(foundTask, "Задача должна находиться по ID");
        assertEquals(task, foundTask, "Задача в менеджере должна совпадать с добавленной");
    }

    @Test
    void tasksWithGeneratedAndManualIdsShouldNotConflict() {
        Task task1 = new Task("Task1", "Description", Status.NEW);
        task1.setId(1);
        taskManager.addTask(task1);

        Task task2 = new Task("Task2", "Description", Status.DONE);
        taskManager.addTask(task2);

        assertNotEquals(task1.getId(), task2.getId(), "ID задач должны быть уникальными");
    }

    @Test
    void epicShouldNotBeAddedAsItsOwnSubtask() {
        Epic epic = new Epic("Epic", "Description", Status.NEW);
        epic.setId(4);

        epic.getSubtaskIds().add(4);

        assertFalse(epic.getSubtaskIds().contains(4), "Epic не должен содержать себя в списке подзадач");
    }
}