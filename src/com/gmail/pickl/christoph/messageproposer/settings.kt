package com.gmail.pickl.christoph.messageproposer

import com.gmail.pickl.christoph.messageproposer.settings.MessageProposerSettings
import com.intellij.compiler.options.ComparingUtils
import com.intellij.openapi.components.*
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

public class SettingsView : Configurable {

    private var mySettings: SettingsVo? = null

    private var myPanel: JPanel = JPanel()
    private val myTxtJiraUrl = JTextField()
    private val myTxtUsername = JTextField()
    private val myTxtPassword = JPasswordField()
    private val myTxtMaxTasks = JTextField()
    private val myTxtCustomJql = JTextField()


    override fun createComponent(): JComponent? {
        println("SettingsView: createComponent()")
        if (myPanel != null) {
            return myPanel
        }
        mySettings = SettingsVoManager.getSettings()
        val layout = GridBagLayout()
        myPanel = JPanel(layout)
        val c = GridBagConstraints()
        layout.setConstraints(myPanel, c)

        c.gridy = 0
        c.gridx = 0
        c.fill = GridBagConstraints.HORIZONTAL
        c.weightx = 0.0
        c.gridwidth = 2
        c.insets = Insets(0, 0, 10, 0)
        myPanel.add(JLabel("<html>This is some <b>dummy</b> text. This is some dummy text. This is some dummy text. This is some dummy text. This is some dummy text. This is some dummy text. This is some dummy text.</html>"), c)
        c.gridy++

        c.gridwidth = 1
        addRow("JIRA URL", myTxtJiraUrl, "The JIRA web URL, e.g.: https://jira.atlassian.com/projects/DEMO/", c)
        addRow("Username", myTxtUsername, "The JIRA account's username for authentication.", c)
        addRow("Password", myTxtPassword, "The JIRA account's password for authentication.", c)
        addRow("Limit Tasks", myTxtMaxTasks, "Limit number of visible tasks.", c)
        addRow("Custom JQL", myTxtCustomJql, "Leave empty to use default query of: " + MessageProposerSettings.State.DEFAULT_JQL, c)

        c.gridx = 0
        c.gridwidth = 2
        c.weightx = 1.0
        c.weighty = 1.0
        c.fill = GridBagConstraints.BOTH
        myPanel.add(JLabel(" "), c)

        reset()
        return myPanel
    }

    private val INSET1 = Insets(0, 0, 4, 20)
    private val INSET2 = Insets(0, 0, 4, 0)
    private fun addRow(label: String, component: JComponent, tooltip: String, c: GridBagConstraints) {
        component.toolTipText = tooltip

        c.gridx = 0
        c.weightx = 0.0
        c.fill = GridBagConstraints.NONE
        c.anchor = GridBagConstraints.FIRST_LINE_START
        c.insets = INSET1
        myPanel.add(JLabel(label), c)

        c.gridx++
        c.weightx = 1.0
        c.fill = GridBagConstraints.HORIZONTAL
        c.anchor = GridBagConstraints.CENTER
        c.insets = INSET2
        myPanel.add(component, c)

        c.gridy++
    }

    override fun isModified(): Boolean {
        if (ComparingUtils.isModified(myTxtJiraUrl, mySettings.jiraUrl)) return true
        if (ComparingUtils.isModified(myTxtUsername, mySettings.username)) return true
        if (ComparingUtils.isModified(myTxtPassword, mySettings.password)) return true
        if (ComparingUtils.isModified(myTxtMaxTasks, mySettings.maxTasks)) return true
        if (ComparingUtils.isModified(myTxtCustomJql, mySettings.customJql)) return true
        return false
    }

    override fun apply() {
        validateUi()

        mySettings!!.jiraUrl = myTxtJiraUrl.text
        mySettings!!.username = myTxtUsername.text
        mySettings!!.password = String(myTxtPassword.password)
        mySettings!!.maxTasks = parseNonNegativeInt(myTxtMaxTasks.text)
        mySettings!!.customJql = myTxtCustomJql.text
        println("SettingsView: apply() =>" + mySettings)
    }

    @Throws(ConfigurationException::class)
    private fun validateUi() {
        parseNonNegativeInt(myTxtMaxTasks.text)
    }

    @Throws(ConfigurationException::class)
    private fun parseNonNegativeInt(input: String): Int {
        if (input.trim { it <= ' ' }.isEmpty()) {
            return MessageProposerSettings.State.DEFAULT_MAX_TASKS
        }
        val i: Int
        try {
            i = Integer.parseInt(input)
        } catch (e: NumberFormatException) {
            throw ConfigurationException("Invalid Number entered: '$input'!")
        }

        if (i <= 0) {
            throw ConfigurationException("Invalid Number entered: '$input'!")
        }
        return i
    }

    override fun reset() {
        println("SettingsView: reset()")
        myTxtJiraUrl.text = mySettings!!.jiraUrl
        myTxtUsername.text = mySettings!!.username
        myTxtPassword.text = mySettings!!.password
        myTxtMaxTasks.text = mySettings!!.maxTasks.toString()
        myTxtCustomJql.text = mySettings!!.customJql
    }

    override fun getDisplayName(): String? = "Message Proposer" // TODO load from resources

    override fun getHelpTopic(): String? = null

    override fun disposeUIResources() {
        // no-op
    }

}

@State(name = "SettingsVoManager",
        storages = arrayOf(@Storage(file = StoragePathMacros.APP_CONFIG + "/messageproperties.xml"))
)
public class SettingsVoManager : PersistentStateComponent<SettingsVo> {
    companion object {
        fun getSettings(): SettingsVo {
            return ServiceManager.getService(SettingsVoManager::class.java).state
        }
    }

    private var state = SettingsVo()

    override fun getState(): SettingsVo = state

    override fun loadState(state: SettingsVo) {
        this.state = state
    }

}

public class SettingsVo {
    companion object {
        val DEFAULT_MAX_TASKS = 20
        val DEFAULT_JQL = "assignee in (currentUser()) ORDER BY id ASC"
    }

    public var jiraUrl: String = ""
    public var username: String = ""
    public var password: String = ""
    public var maxTasks: Int = DEFAULT_MAX_TASKS
    public var customJql: String = DEFAULT_JQL
}
