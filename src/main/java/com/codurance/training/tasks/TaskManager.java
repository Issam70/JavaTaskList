package com.codurance.training.tasks;

import java.io.PrintWriter;
import java.util.*;

public class TaskManager {

    private static final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private static final Map<Long, Boolean> tasksState = new TreeMap<>();
    private static long lastId = 0;

    public static void addProject(String name) {
        tasks.put(name, new ArrayList<Task>());
    }

    public static void addTask(String project, String description, PrintWriter out) {
        List<Task> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        Task task = new Task(nextId(), description);
        projectTasks.add(task);
        tasksState.put(task.id, false);
    }

    public static void setDone(String idString, boolean done, PrintWriter out) {
        int id = Integer.parseInt(idString);
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.id == id) {
                    tasksState.put(task.id, done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }

    public static void show(PrintWriter out) {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                out.printf("    [%c] %d: %s%n", (tasksState.get(task.id) ? 'x' : ' '), task.id, task.description);
            }
            out.println();
        }
    }

    private static Long nextId() {
        return ++lastId;
    }

}
