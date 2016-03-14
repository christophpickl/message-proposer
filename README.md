# message-proposer
intellij plugin to propose a commit message prefix based on assigned issues


# TODOs

* ++ load Tasks from JIRA
* + own preferences ? or re-use configured task list server already existing in intellij
* - move dropdown from "before commit" panel to "commit message" panel, right next to two existing icons
* - usability: adaptive list of tasks (most recent at top)
* -- store password encrypted
* -- custom commit message template like "%id - " or "%id [%user] "
* --- FUTURE: provide extension point to extend list of supported issue systems
* --- FUTURE: list of task provider, instead of one, fixed JIRA server
* --- load strings from properties file (support german as well)
