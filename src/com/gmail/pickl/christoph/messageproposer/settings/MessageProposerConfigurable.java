package com.gmail.pickl.christoph.messageproposer.settings;

import com.intellij.compiler.options.ComparingUtils;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

// ui see: HttpProxySettingsUi
public class MessageProposerConfigurable implements Configurable {


    @NotNull
    private MessageProposerSettings.State mySettings;

    private JPanel myPanel;
    private JTextField myTxtJiraUrl = new JTextField();
    private JTextField myTxtUsername = new JTextField();
    private JTextField myTxtPassword = new JTextField();
    private JTextField myTxtMaxTasks = new JTextField();
    private JTextField myTxtCustomJql = new JTextField();

    @Nls
    @Override
    public String getDisplayName() {
        return "Message Proposer";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        System.out.println("MessageProposerConfigurable: createComponent()");
        if (myPanel != null) {
            return myPanel;
        }
        mySettings = MessageProposerSettings.getInstance();
        GridBagLayout layout = new GridBagLayout();
        myPanel = new JPanel(layout);
        GridBagConstraints c = new GridBagConstraints();
        layout.setConstraints(myPanel, c);

        c.gridy = 0;
        addRow("JIRA URL", myTxtJiraUrl, "The JIRA web URL, e.g.: https://jira.atlassian.com/projects/DEMO/", c);
        addRow("Username", myTxtUsername, "The JIRA account's username for authentication.", c);
        addRow("Password", myTxtPassword, "The JIRA account's password for authentication.", c);
        addRow("Limit Tasks", myTxtMaxTasks, "Limit number of visible tasks.", c);
        addRow("Custom JQL", myTxtCustomJql, "Leave empty to use default query of: " + MessageProposerSettings.State.DEFAULT_JQL, c);


        c.gridx = 0;
        c.gridwidth = 2;
        c.weightx = c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        myPanel.add(new JLabel(" "), c);

        reset();
        return myPanel;
    }

    private static final Insets INSET1 = new Insets(0, 0, 4, 20);
    private static final Insets INSET2 = new Insets(0, 0, 4,  0);
    private void addRow(String label, JComponent component, String tooltip, GridBagConstraints c) {
        component.setToolTipText(tooltip);

        c.gridx = 0;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = INSET1;
        myPanel.add(new JLabel(label), c);

        c.gridx++;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = INSET2;
        myPanel.add(component, c);

        c.gridy++;
    }

    @Override
    public boolean isModified() {
        boolean isModified = false;
        isModified |= ComparingUtils.isModified(myTxtJiraUrl, mySettings.jiraUrl);
        isModified |= ComparingUtils.isModified(myTxtUsername, mySettings.username);
        isModified |= ComparingUtils.isModified(myTxtPassword, mySettings.password);
        isModified |= ComparingUtils.isModified(myTxtMaxTasks, mySettings.maxTasks);
        isModified |= ComparingUtils.isModified(myTxtCustomJql, mySettings.customJql);
        return isModified;
    }

    @Override
    public void apply() throws ConfigurationException {
        System.out.println("MessageProposerConfigurable: apply()");
        mySettings.jiraUrl = myTxtJiraUrl.getText();
        mySettings.username = myTxtUsername.getText();
        mySettings.password = myTxtPassword.getText();
        mySettings.maxTasks = parseNonNegativeInt(myTxtMaxTasks.getText());
        mySettings.customJql = myTxtCustomJql.getText();
    }

    private static int parseNonNegativeInt(String input) throws ConfigurationException {
        if (input.trim().isEmpty()) {
            return MessageProposerSettings.State.DEFAULT_MAX_TASKS;
        }
        int i;
        try {
            i = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Invalid Number entered: '" + input + "'!");
        }
        if (i <= 0) {
            throw new ConfigurationException("Invalid Number entered: '" + input + "'!");
        }
        return i;
    }

    @Override
    public void reset() {
        System.out.println("MessageProposerConfigurable: reset()");
        myTxtJiraUrl.setText(mySettings.jiraUrl);
        myTxtUsername.setText(mySettings.username);
        myTxtPassword.setText(mySettings.password);
        myTxtMaxTasks.setText(String.valueOf(mySettings.maxTasks));
        myTxtCustomJql.setText(mySettings.customJql);
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
