package test.data;
import data.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task("Task1", "Description1", Status.NEW);
        task1.setId(1);
        Task task2 = new Task("Task2", "Description2", Status.DONE);
        task2.setId(1);

        assertEquals(task1, task2, "Задачи с одинаковым ID должны быть равны");
    }

    @Test
    void taskFieldsShouldRemainUnchangedAfterAddingToManager() {
        Task task = new Task("Task", "Description", Status.NEW);
        Task copy = new Task(task.getTitle(), task.getDescription(), task.getStatus());
        copy.setId(task.getId());

        assertEquals(task.getTitle(), copy.getTitle(), "Название задачи должно оставаться неизменным");
        assertEquals(task.getDescription(), copy.getDescription(), "Описание задачи должно оставаться неизменным");
        assertEquals(task.getStatus(), copy.getStatus(), "Статус задачи должен оставаться неизменным");
        assertEquals(task.getId(), copy.getId(), "ID задачи должен оставаться неизменным");
    }
}