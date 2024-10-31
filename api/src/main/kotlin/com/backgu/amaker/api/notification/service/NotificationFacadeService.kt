package com.backgu.amaker.api.notification.service

import com.backgu.amaker.api.notification.dto.EventNotificationDto
import com.backgu.amaker.application.event.service.EventAssignedUserService
import com.backgu.amaker.application.event.service.ReactionCommentService
import com.backgu.amaker.application.event.service.ReactionEventService
import com.backgu.amaker.application.event.service.ReactionOptionService
import com.backgu.amaker.application.event.service.ReplyCommentService
import com.backgu.amaker.application.event.service.ReplyEventService
import com.backgu.amaker.application.notification.service.NotificationQueryService
import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.application.workspace.WorkspaceUserService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NotificationFacadeService(
    private val userService: UserService,
    private val replyEventService: ReplyEventService,
    private val reactionEventService: ReactionEventService,
    private val eventAssignedUserService: EventAssignedUserService,
    private val replyCommentService: ReplyCommentService,
    private val reactionCommentService: ReactionCommentService,
    private val reactionOptionService: ReactionOptionService,
    private val workspaceUserService: WorkspaceUserService,
    private val notificationQueryService: NotificationQueryService,
    private val eventPublisher: ApplicationEventPublisher,
) {
    fun getNotifications(
        userId: String,
        workspaceId: Long,
        pageable: Pageable,
    ): Page<EventNotificationDto> {
        workspaceUserService.validateUserInWorkspace(userId, workspaceId)

        return notificationQueryService.getNotification(userId, workspaceId, pageable).map { EventNotificationDto.from(it) }
    }
}
