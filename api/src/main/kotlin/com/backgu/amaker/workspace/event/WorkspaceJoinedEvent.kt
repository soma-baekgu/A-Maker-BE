package com.backgu.amaker.workspace.event

import com.backgu.amaker.mail.constants.EmailConstants
import com.backgu.amaker.mail.event.EmailEvent
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.workspace.domain.Workspace

data class WorkspaceJoinedEvent(
    val joiner: User,
    private val workspace: Workspace,
) : EmailEvent {
    override val email = joiner.email
    override val emailConstants = EmailConstants.WORKSPACE_JOINED

    override fun emailModel(): Map<String, Any> =
        mapOf(
            "name" to joiner.name,
            "workspaceName" to workspace.name,
        )

    override fun title(): String = String.format(emailConstants.title, joiner.name)
}
