package com.backgu.amaker.domain.notifiacation.workspace

import com.backgu.amaker.domain.notifiacation.UserNotification
import com.backgu.amaker.domain.notifiacation.method.TemplateEmailNotificationMethod
import com.backgu.amaker.domain.notifiacation.type.UserNotificationType.WORKSPACE_INVITED
import com.backgu.amaker.domain.user.User
import com.backgu.amaker.domain.workspace.Workspace

class WorkspaceInvited private constructor(
    user: User,
    method: TemplateEmailNotificationMethod,
) : UserNotification(
        user.id,
        method,
    ) {
    companion object {
        private fun buildDetailMessage(
            workspace: Workspace,
            user: User,
        ): String =
            "${user.name}님이 ${workspace.name}에 초대되었습니다.\n " +
                "가입하시려면 링크를 클릭하세요. " +
                "https://www.a-maker.co.kr/invite/${workspace.id}"

        fun of(
            workspace: Workspace,
            user: User,
        ): WorkspaceInvited =
            WorkspaceInvited(
                user = user,
                TemplateEmailNotificationMethod(
                    WORKSPACE_INVITED.title,
                    String.format(WORKSPACE_INVITED.message, workspace.name),
                    buildDetailMessage(workspace, user),
                    "workspace-invite",
                ),
            )
    }
}
