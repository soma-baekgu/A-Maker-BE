package com.backgu.amaker.workspace.event

import com.backgu.amaker.mail.constants.EmailConstants
import com.backgu.amaker.mail.event.EmailEvent
import com.backgu.amaker.user.domain.User
import com.backgu.amaker.workspace.domain.Workspace

class WorkspaceInvitedEvent(
    private val invitedUser: User,
    private val workspace: Workspace,
) : EmailEvent {
    override val email: String = invitedUser.email
    override val emailConstants: EmailConstants = EmailConstants.WORKSPACE_INVITED

    override fun emailModel(): Map<String, Any> =
        mapOf(
            "name" to invitedUser.name,
            "workspaceName" to workspace.name,
            "workspaceId" to workspace.id,
        )

    override fun title(): String = String.format(emailConstants.title, invitedUser.name)
}
