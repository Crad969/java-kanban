package Modul;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks = new ArrayList<>();

    public Epic(String title, String description, Status status) {
        super(title, description, Status.NEW);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public void updateStatus() {
        boolean hasInProgress = false;
        boolean hasDone = false;
        boolean hasNew = false;

        for (Subtask subtask : subtasks) {
            switch (subtask.getStatus()) {
                case Status.DONE:
                    hasDone = true;
                    break;
                case Status.IN_PROGRESS:
                    hasInProgress = true;
                    break;
                case Status.NEW:
                    hasNew = true;
                    break;
            }
            if (hasInProgress) {
                setStatus(Status.IN_PROGRESS);
                return;
            }
        }

        if (hasDone && !hasNew && !hasInProgress) {
            setStatus(Status.DONE);
        } else if (hasNew && !hasInProgress && !hasDone) {
            setStatus(Status.NEW);
        } else {
            setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public String toString() {
        return "Modul.Epic{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasks=" + subtasks + '}';
    }
}
