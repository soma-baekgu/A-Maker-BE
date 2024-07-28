package com.backgu.amaker.api.workspace.event

import com.backgu.amaker.application.notification.mail.event.EmailEvent
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.infra.mail.constants.EmailConstants

class WorkspaceInvitedEvent(
    invitedUser: User,
    workspace: Workspace,
) : EmailEvent(
        email = invitedUser.email,
        emailConstants = EmailConstants.WORKSPACE_INVITED,
        title = EmailConstants.WORKSPACE_INVITED.title,
        content = String.format(EmailConstants.WORKSPACE_INVITED.content, invitedUser.name, workspace.name),
    )
