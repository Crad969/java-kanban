package service;

import modul.Epic;
import modul.Subtask;
import modul.Task;
import modul.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int idCounter = 1; // Счетчик для генерации уникальных ID

    // Приватный метод генерации уникального ID
    private int generateId() {
        return idCounter++;
    }

    // Методы управления задачами
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void clearTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void addTask(Task task) {
        if (task != null) {
            task.setId(generateId());
            tasks.put(task.getId(), task);
        }
    }

    public void updateTask(Task task) {
        if (task == null || !tasks.containsKey(task.getId())) {
            System.out.println("Ошибка: Задача с ID " + (task != null ? task.getId() : "null") + " не найдена.");
            return;
        }
        tasks.put(task.getId(), task);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    // Методы управления эпиками
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void clearEpics() {
        epics.clear();
        subtasks.clear(); // Удаляем все подзадачи, так как они принадлежат эпикам
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void addEpic(Epic epic) {
        if (epic != null) {
            epic.setId(generateId());
            epics.put(epic.getId(), epic);
        }
    }

    public void updateEpic(Epic epic) {
        if (epic == null || !epics.containsKey(epic.getId())) {
            System.out.println("Ошибка: Эпик с ID " + (epic != null ? epic.getId() : "null") + " не найден.");
            return;
        }

        Epic existingEpic = epics.get(epic.getId());
        existingEpic.setTitle(epic.getTitle());
        existingEpic.setDescription(epic.getDescription());
    }

    public void removeEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.remove(id);
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }
    }

    // Методы управления подзадачами
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void clearSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear(); // Очищаем список подзадач у эпиков
            updateEpicStatus(epic); // Обновляем статус эпиков
        }
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public void addSubtask(Subtask subtask) {
        if (subtask != null && epics.containsKey(subtask.getEpicId())) {
            subtask.setId(generateId());
            subtasks.put(subtask.getId(), subtask);
            epics.get(subtask.getEpicId()).getSubtaskIds().add(subtask.getId());
            updateEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtask == null || !subtasks.containsKey(subtask.getId())) {
            System.out.println("Ошибка: Подзадача с ID " + (subtask != null ? subtask.getId() : "null") + " не найдена.");
            return;
        }

        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epics.get(subtask.getEpicId()));
    }

    public void removeSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSubtaskIds().remove(Integer.valueOf(id));
                updateEpicStatus(epic);
            }
        }
    }

    public List<Subtask> getSubtasksByEpic(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return new ArrayList<>();
        }

        List<Subtask> epicSubtasks = new ArrayList<>();
        for (int subtaskId : epic.getSubtaskIds()) {
            epicSubtasks.add(subtasks.get(subtaskId));
        }
        return epicSubtasks;
    }

    // Метод обновления статуса эпика
    private void updateEpicStatus(Epic epic) {
        if (epic == null || epic.getSubtaskIds().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allDone = true;
        boolean allNew = true;

        for (int subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask == null) continue;

            if (subtask.getStatus() != Status.DONE) {
                allDone = false;
            }
            if (subtask.getStatus() != Status.NEW) {
                allNew = false;
            }
        }

        if (allNew) {
            epic.setStatus(Status.NEW);
        } else if (allDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}