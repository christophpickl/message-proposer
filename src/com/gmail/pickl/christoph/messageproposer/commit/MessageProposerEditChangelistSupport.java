package com.gmail.pickl.christoph.messageproposer.commit;

import com.gmail.pickl.christoph.messageproposer.MediatorService;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.vcs.changes.LocalChangeList;
import com.intellij.openapi.vcs.changes.ui.EditChangelistSupport;
import com.intellij.ui.EditorTextField;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.Nullable;

import javax.print.attribute.standard.Media;
import javax.swing.*;

public class MessageProposerEditChangelistSupport implements EditChangelistSupport {

    public MessageProposerEditChangelistSupport() {
//        Extensions.getExtensions(EditChangelistSupport.EP_NAME, project)
    }

    @Override
    public void installSearch(EditorTextField textField, EditorTextField comment) {
        // both parameters reference to the same field i guess...
        System.out.println("MessageProposerEditChangelistSupport: installSearch()");
        MediatorService service = ServiceManager.getService(MediatorService.class);
        service.registerCommitMessageField(textField);
    }

    @Override
    public Consumer<LocalChangeList> addControls(JPanel bottomPanel, @Nullable LocalChangeList initial) {
        return new Consumer<LocalChangeList>() {
            @Override
            public void consume(LocalChangeList list) {
//                System.out.println("#addControls() ... list.getComment(): " + list.getComment());
            }
        };
    }

    @Override
    public void changelistCreated(LocalChangeList list) {
        System.out.println("MessageProposerEditChangelistSupport: changelistCreated() ... list.getComment(): " + list.getComment());
    }
}
