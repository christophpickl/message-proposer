package com.gmail.pickl.christoph.messageproposer;

import com.intellij.compiler.options.ComparingUtils;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

// ui see: HttpProxySettingsUi
public class MessageProposerSettings implements Configurable {

    private JPanel myPanel;
    private JTextField myTxtJiraUrl;

    @Nls
    @Override
    public String getDisplayName() {
        return "Message Proposer";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return myPanel;
    }

    @Override
    public boolean isModified() {
        boolean isModified = false;
//        isModified |= ComparingUtils.isModified(myCbDebuggingInfo, myCompilerSettings.DEBUGGING_INFO);
        return isModified;
    }

    @Override
    public void apply() throws ConfigurationException {
        // myCompilerSettings.DEPRECATION =  myCbDeprecation.isSelected();
    }

    @Override
    public void reset() {
        // myCbDeprecation.setSelected(myCompilerSettings.DEPRECATION);
    }

    @Override
    public void disposeUIResources() {

    }
}
