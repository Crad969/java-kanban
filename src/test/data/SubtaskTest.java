package test.data;

import data.*;
import manager.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask subtask1 = new Subtask("Subtask1", "Description1", Status.NEW, 2);
        subtask1.setId(5);
        Subtask subtask2 = new Subtask("Subtask2", "Description2", Status.DONE, 2);
        subtask2.setId(5);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым ID должны быть равны");
    }

    @Test
    void subtaskShouldNotHaveItselfAsEpic() {
        Subtask subtask = new Subtask("Subtask", "Desc", Status.NEW, 5);
        subtask.setId(5);

        assertNotEquals(subtask.getEpicId(), subtask.getId(), "Subtask не может быть своим же Epic");
    }
}