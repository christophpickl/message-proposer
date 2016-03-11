package com.gmail.pickl.christoph.messageproposer;

import com.intellij.tasks.Task;
import com.intellij.tasks.generic.GenericTask;
import com.intellij.tasks.jira.JiraRemoteApi;
import com.intellij.tasks.jira.JiraRepository;
import com.intellij.tasks.jira.rest.JiraRestTask;
import com.intellij.tasks.jira.rest.api2.JiraRestApi2;
import com.intellij.ui.EditorTextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MediatorService {

    public static final int MAX_TASKS = 20;
    private EditorTextField commitField;

    private List<Task> data;

    public void taskSelected(Task task) {
        if (commitField == null) {
            throw new RuntimeException("This should never happen! commit message text field has not been set!");
        }
        commitField.setText(task.getId() + " ");
    }

    public void registerCommitMessageField(EditorTextField commitField) {
        this.commitField = commitField;
    }

    public void reloadData() {
        JiraRepository repo = new JiraRepository();
        repo.setApiType(JiraRemoteApi.ApiType.REST_2_0);
//        repo.setJiraVersion();
        repo.setUrl("https://jira.atlassian.com/projects/DEMO/");

        repo.setLoginAnonymously(true);
//        repo.setUsername("");
//        repo.setPassword("");
        System.out.println("Requesting JIRA!");
        JiraRestApi2 rest = new JiraRestApi2(repo);
//        String jql = "project = DEMO AND assignee in (EMPTY) ORDER BY id ASC";
        String jql = "project = DEMO AND reporter in (currentUser()) ORDER BY id ASC";
        try {
//            data = Arrays.asList(new GenericTask("id", "sum", null));
            data = rest.findTasks(jql, MAX_TASKS);
        } catch (Exception e) {
            throw new RuntimeException("Ups :)", e);
        }
    }

    public List<Task> getData() {
        if (data == null) {
            reloadData();
        }
        return new ArrayList<>(data);
    }
}
