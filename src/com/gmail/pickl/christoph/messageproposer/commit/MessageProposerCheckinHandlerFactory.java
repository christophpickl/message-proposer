package com.gmail.pickl.christoph.messageproposer.commit;

import com.gmail.pickl.christoph.messageproposer.MediatorService;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.VcsConfiguration;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import com.intellij.tasks.Task;
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

        private static final Logger LOG = Logger.getInstance(MessageProposerCheckinHandler.class);

        private static final String OPT_SELECT_TASK = "-Select Task ID-";
        private static final String OPT_REFRESH = "-Refresh-";

        private final Project myProject;
        private final CheckinProjectPanel myPanel;
        private final ComboBox taskList = new ComboBox();

        public MessageProposerCheckinHandler(Project project, CheckinProjectPanel panel) {
            myProject = project;
            myPanel = panel;
        }


        private void onRefresh() {
            ServiceManager.getService(MediatorService.class).reloadData();
            resetTaskList();
        }

        private void onSelectTask(Task task) {
            ServiceManager.getService(MediatorService.class).taskSelected(task);
        }

        private void resetTaskList() {
            // VcsBundle.message("checkbox.checkin.options.optimize.imports")
            taskList.removeAllItems();

            taskList.addItem(OPT_SELECT_TASK);
            for (Task task : ServiceManager.getService(MediatorService.class).getData()) {
                taskList.addItem(task);
            }
            taskList.addItem(OPT_REFRESH);
            taskList.setSelectedIndex(0);
        }

        @Override
        @Nullable
        public RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
            resetTaskList();

            taskList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LOG.debug("action Performed on TaskList combo box");
                    Object selected = taskList.getSelectedItem();
                    if (selected instanceof String) {
                        String selectedString = (String) selected;
                        if (selectedString.equals(OPT_SELECT_TASK)) {
                            // do nothing
                        } else if (selectedString.equals(OPT_REFRESH)) {
                            onRefresh();
                        } else {
                            throw new RuntimeException("Unhandled string option: " + selectedString);
                        }
                    } else if (selected instanceof Task) {
                        onSelectTask((Task) selected);
                    } else {
                        throw new RuntimeException("Unhandled option: " + selected);
                    }
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

}
