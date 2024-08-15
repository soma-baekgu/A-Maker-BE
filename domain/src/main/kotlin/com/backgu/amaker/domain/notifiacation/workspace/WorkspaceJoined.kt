package com.backgu.amaker.domain.notifiacation.workspace

import com.backgu.amaker.domain.notifiacation.WorkspaceNotification
import com.backgu.amaker.domain.notifiacation.method.TemplateEmailNotificationMethod
import com.backgu.amaker.domain.notifiacation.type.WorkspaceNotificationType.WORKSPACE_JOINED
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace

class WorkspaceJoined(
    workspace: Workspace,
    method: TemplateEmailNotificationMethod,
) : WorkspaceNotification(
        workspaceId = workspace.id,
        method = method,
    ) {
    companion object {
        private fun buildDetailMessage(
            user: User,
            workspace: Workspace,
        ): String = "${user.name}님이 ${workspace.name}에 참여하였습니다."

        fun of(
            workspace: Workspace,
            user: User,
        ): WorkspaceJoined =
            WorkspaceJoined(
                workspace = workspace,
                method =
                    TemplateEmailNotificationMethod(
                        WORKSPACE_JOINED.title,
                        String.format(WORKSPACE_JOINED.message, workspace.name),
                        buildDetailMessage(user, workspace),
                        "workspace-join",
                    ),
            )
    }
}
