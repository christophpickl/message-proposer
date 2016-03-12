package com.gmail.pickl.christoph.messageproposer.settings;

import com.intellij.compiler.options.ComparingUtils;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

// ui see: HttpProxySettingsUi
public class MessageProposerConfigurable implements Configurable {


    @NotNull
    private MessageProposerSettings.State mySettings;

    private JPanel myPanel;
    private JTextField myTxtJiraUrl;

    @Nls
    @Override
    public String getDisplayName() {
        return "Message Proposer";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        System.out.println("createComponent()");
        if (myPanel == null) {
            mySettings = MessageProposerSettings.getInstance();
            myPanel = new JPanel();
            myTxtJiraUrl = new JTextField(20);
            myTxtJiraUrl.setText(mySettings.jiraUrl);
            myPanel.add(myTxtJiraUrl);
        }
        return myPanel;
    }

    @Override
    public boolean isModified() {
        boolean isModified = false;
        isModified |= ComparingUtils.isModified(myTxtJiraUrl, mySettings.jiraUrl);
        return isModified;
    }

    @Override
    public void apply() throws ConfigurationException {
        System.out.println("apply: " + myTxtJiraUrl.getText());
        mySettings.jiraUrl = myTxtJiraUrl.getText();
    }

    @Override
    public void reset() {
        System.out.println("reset()");
        myTxtJiraUrl.setText(mySettings.jiraUrl);
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }


    @Override
    public void disposeUIResources() {
        // no-op
    }
}
