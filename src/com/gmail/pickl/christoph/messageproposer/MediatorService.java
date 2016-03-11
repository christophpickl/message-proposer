package com.gmail.pickl.christoph.messageproposer;

import com.intellij.ui.EditorTextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MediatorService {

    private EditorTextField commitField;

    private List<String> data = new LinkedList<>();
    {
        data.add("PRESTO-1337");
        data.add("REL-42");
    }

    public void taskSelected(String taskId) {
        if (commitField == null) {
            throw new RuntimeException("This should never happen! commit message text field has not been set!");
        }
        commitField.setText(taskId + " ");
    }

    public void registerCommitMessageField(EditorTextField commitField) {
        this.commitField = commitField;
    }

    int dataCounter = 0;
    public void reloadData() {
        // TODO load data from JIRA server
        data.add("new " + (++dataCounter));
    }

    public List<String> getData() {
        return new ArrayList<>(data);
    }
}
