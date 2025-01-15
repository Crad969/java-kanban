package Service;

import Modul.Epic;
import Modul.Status;
import Modul.Subtask;
import Modul.Task;
import Service.TaskManager;

 public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Создание задач
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание 2", Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        // Создание эпиков и подзадач
        Epic epic1 = new Epic("Эпик 1", "Описание 1", Status.NEW);
        Epic epic2 = new Epic("Эпик 2", "Описание 2", Status.NEW);
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание 2", Status.DONE, epic1.getId());
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание 3", Status.IN_PROGRESS, epic2.getId());

        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);

        // Печать всех задач, эпиков , и подзадач
        System.out.println("Задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());

        // Обновление статуса
        task1.setStatus(Status.DONE);
        manager.updateTask(task1);
        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);


        // Удаление задачи и эпика
        manager.removeTaskById(task2.getId());
        manager.removeEpicById(epic1.getId());


    }
}

