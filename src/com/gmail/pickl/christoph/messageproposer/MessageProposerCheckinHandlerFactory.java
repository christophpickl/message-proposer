package com.gmail.pickl.christoph.messageproposer;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.VcsConfiguration;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import com.intellij.ui.NonFocusableCheckBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MessageProposerCheckinHandlerFactory extends CheckinHandlerFactory {

    @NotNull
    @Override
    public CheckinHandler createHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext commitContext) {
        return new MessageProposerCheckinHandler(panel.getProject(), panel);
    }

    public static class MessageProposerCheckinHandler extends CheckinHandler {

        private static final Logger LOG = Logger.getInstance("#com.gmail.pickl.christoph.messageproposer.MessageProposerCheckinHandler");

        private final Project myProject;
        private final CheckinProjectPanel myPanel;

        private final ComboBox taskList = new ComboBox();

        public MessageProposerCheckinHandler(Project project, CheckinProjectPanel panel) {
            myProject = project;
            myPanel = panel;
        }

        private java.util.List<String> loadProposedTaskIds() {
            return Arrays.asList("PRESTO-1337", "REL-42");
        }

        private static final String OPT_SELECT_TASK = "-Select Task ID-";
        private static final String OPT_REFRESH = "-Refresh-";

        private void onRefresh() {
            System.out.println("onRefresh()");
        }

        private void onSelectTask(String taskId) {
            System.out.println("onSelectTask(taskId='" + taskId + "')");
        }

        @Override
        @Nullable
        public RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
            // VcsBundle.message("checkbox.checkin.options.optimize.imports")

            taskList.addItem(OPT_SELECT_TASK);
            for (String taskId : loadProposedTaskIds()) {
                taskList.addItem(taskId);
            }
            taskList.addItem(OPT_REFRESH);

            taskList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LOG.debug("action Performed on TaskList combo box");
                    String taskId = (String) taskList.getSelectedItem();
                    System.out.println("taskList.getSelectedItem()==> " + taskId);

                    if (taskId.equals(OPT_SELECT_TASK)) {
                        return; // do nothing
                    }
                    if (taskId.equals(OPT_REFRESH)) {
                        onRefresh();
                        return;
                    }
                    onSelectTask(taskId);
                }
            });

//            CheckinHandlerUtil.disableWhenDumb(myProject, optimizeBox, "Impossible until indices are up-to-date");
            return new RefreshableOnComponent() {
                @Override
                public JComponent getComponent() {
                    final JPanel panel = new JPanel(new GridLayout(1, 0));
                    panel.add(taskList);
                    return panel;
                }

                @Override
                public void refresh() {
                }

                @Override
                public void saveState() {
//                    getSettings().OPTIMIZE_IMPORTS_BEFORE_PROJECT_COMMIT = optimizeBox.isSelected();
                }

                @Override
                public void restoreState() {
//                    optimizeBox.setSelected(getSettings().OPTIMIZE_IMPORTS_BEFORE_PROJECT_COMMIT);
                }
            };
        }


        protected VcsConfiguration getSettings() {
            return VcsConfiguration.getInstance(myProject);
        }


    }


    /*
Please try to define a CheckinHandler with getBeforeCheckinConfigurationPanel or getAfterCheckinConfigurationPanel.
You may also override CheckinEnvironment#createAdditionalOptionsPanel, but it would require you to implement your own CheckinEnvironment.
     */
}
