package com.backgu.amaker.api.workspace.event

import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.infra.mail.constants.EmailConstants
import com.backgu.amaker.infra.mail.event.EmailEvent

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
