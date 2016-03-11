package com.gmail.pickl.christoph.messageproposer;

import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.vcs.changes.LocalChangeList;
import com.intellij.openapi.vcs.changes.ui.EditChangelistSupport;
import com.intellij.ui.EditorTextField;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MyEditChangelistSupport implements EditChangelistSupport {

    private EditorTextField textField;

    public MyEditChangelistSupport() {
        System.out.println("new MyEditChangelistSupport() !!!!!");

//        Extensions.getExtensions(EditChangelistSupport.EP_NAME, project)
    }

    @Override
    public void installSearch(EditorTextField textField, EditorTextField comment) {
        // both parameters reference to the same field i guess...
        System.out.println("#installSearch()");
        this.textField = textField;
    }

    @Override
    public Consumer<LocalChangeList> addControls(JPanel bottomPanel, @Nullable LocalChangeList initial) {
        return new Consumer<LocalChangeList>() {
            @Override
            public void consume(LocalChangeList list) {
                System.out.println("#addControls() ... list.getComment(): " + list.getComment());
            }
        };
    }

    @Override
    public void changelistCreated(LocalChangeList list) {
        System.out.println("#changelistCreated() ... list.getComment(): " + list.getComment());
    }
}
