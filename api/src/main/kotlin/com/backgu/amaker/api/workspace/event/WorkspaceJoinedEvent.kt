package com.backgu.amaker.api.workspace.event

import com.backgu.amaker.application.notification.mail.event.EmailEvent
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace
import com.backgu.amaker.infra.mail.constants.EmailConstants

class WorkspaceJoinedEvent(
    joiner: User,
    workspace: Workspace,
) : EmailEvent(
        joiner.email,
        EmailConstants.WORKSPACE_JOINED,
        EmailConstants.WORKSPACE_JOINED.title,
        String.format(EmailConstants.WORKSPACE_JOINED.content, joiner.name, workspace.name),
    )
