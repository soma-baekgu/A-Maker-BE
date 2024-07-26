package com.backgu.amaker.api.workspace.event

import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.infra.mail.constants.EmailConstants
import com.backgu.amaker.infra.mail.event.EmailEvent

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
