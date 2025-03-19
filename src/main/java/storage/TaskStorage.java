package storage;

import service.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TaskStorage {
    private final Map<String, Set<String>> userTasks = new HashMap<>();

    public Set<String> getTasks(String username) {
        return userTasks.getOrDefault(username, new HashSet<>());
    }

    public void addTask(String username, String task) {
        userTasks.computeIfAbsent(username, k -> new HashSet<>()).add(task);
        Logger.log("Task added for user: " + username + " Task: " + task);
    }

    public void removeTask(String username, String task) {
        Set<String> tasks = userTasks.get(username);
        if (tasks != null && tasks.remove(task)) {
            Logger.log("Task removed for user: " + username + " Task: " + task);
        } else {
            Logger.log("Task not found for user: " + username + " Task: " + task);
        }
    }
}