package com.gmail.pickl.christoph.messageproposer;

import com.gmail.pickl.christoph.messageproposer.settings.MessageProposerConfigurable;
import com.gmail.pickl.christoph.messageproposer.settings.MessageProposerSettings;
import com.google.common.base.Strings;
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

    private static final boolean FAKE = true;

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
        System.out.println("MediatorService: reloadData()");
        if (FAKE) {
            data = Arrays.asList(
                     new GenericTask("PRESTO-1337", "As a user I want to feed the dog.", null)
                    ,new GenericTask("REL-42", "As a client I want to feed the cat.", null)
            );
            return;
        }

        JiraRepository repo = new JiraRepository();
        repo.setApiType(JiraRemoteApi.ApiType.REST_2_0);
//        repo.setJiraVersion();

        MessageProposerSettings.State settings = MessageProposerSettings.getInstance();
        repo.setUrl(settings.jiraUrl);//"https://jira.atlassian.com/projects/DEMO/");
//        repo.setLoginAnonymously(true);
        repo.setUsername(settings.username);
        repo.setPassword(settings.password);

        String jql = settings.customJql;// "project = DEMO AND reporter in (currentUser()) ORDER BY id ASC";
        if (Strings.isNullOrEmpty(settings.customJql)) {
            jql = MessageProposerSettings.State.DEFAULT_JQL;
//            jql = "project = DEMO AND assignee in (EMPTY) ORDER BY id ASC";
        }
        try {
            System.out.println("MediatorService: requesting JIRA: " + settings.jiraUrl);
            System.out.println("  JQL: '" + jql + "' (max tasks: " + settings.maxTasks + ")");
            JiraRestApi2 rest = new JiraRestApi2(repo);
            data = rest.findTasks(jql, settings.maxTasks);
        } catch (Exception e) {
            throw new RuntimeException("Ups,requesting JIRA failed :)", e);
        }
    }

    public List<Task> getData() {
        if (data == null) {
            reloadData();
        }
        return new ArrayList<>(data);
    }
}
