package com.backgu.amaker.api.event.service

import com.backgu.amaker.api.event.dto.ReactionCommentCreateDto
import com.backgu.amaker.api.event.dto.ReactionCommentDto
import com.backgu.amaker.api.event.dto.ReplyCommentCreateDto
import com.backgu.amaker.api.event.dto.ReplyCommentDto
import com.backgu.amaker.api.event.dto.ReplyCommentWithUserDto
import com.backgu.amaker.application.chat.event.FinishedCountUpdateEvent
import com.backgu.amaker.application.event.service.EventAssignedUserService
import com.backgu.amaker.application.event.service.ReactionCommentService
import com.backgu.amaker.application.event.service.ReactionEventService
import com.backgu.amaker.application.event.service.ReactionOptionService
import com.backgu.amaker.application.event.service.ReplyCommentService
import com.backgu.amaker.application.event.service.ReplyEventService
import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.application.workspace.WorkspaceUserService
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventCommentFacadeService(
    private val userService: UserService,
    private val replyEventService: ReplyEventService,
    private val reactionEventService: ReactionEventService,
    private val eventAssignedUserService: EventAssignedUserService,
    private val replyCommentService: ReplyCommentService,
    private val reactionCommentService: ReactionCommentService,
    private val reactionOptionService: ReactionOptionService,
    private val workspaceUserService: WorkspaceUserService,
    private val eventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun createReplyComment(
        userId: String,
        eventId: Long,
        replyCommentCreateDto: ReplyCommentCreateDto,
    ): ReplyCommentDto {
        val user = userService.getById(userId)
        val event = replyEventService.getById(eventId)

        val eventAssignedUser = eventAssignedUserService.getByUserAndEvent(user, event)

        val replyComment = replyCommentService.save(event.addReplyComment(user, replyCommentCreateDto.content))

        eventAssignedUserService.save(eventAssignedUser.updateIsFinished(true))

        eventPublisher.publishEvent(FinishedCountUpdateEvent.of(chatId = event.id))

        return ReplyCommentDto.of(replyComment)
    }

    @Transactional
    fun createReactionComment(
        userId: String,
        eventId: Long,
        reactionCommentCreateDto: ReactionCommentCreateDto,
    ): ReactionCommentDto {
        val user = userService.getById(userId)
        val event = reactionEventService.getById(eventId)

        val eventAssignedUser = eventAssignedUserService.getByUserAndEvent(user, event)

        reactionOptionService.validateOptionInEvent(reactionCommentCreateDto.optionId, eventId)

        val reactionComment =
            reactionCommentService.save(
                reactionCommentService
                    .findByEventIdAndUserId(event, user)
                    ?.also { it.toggleOptionId(reactionCommentCreateDto.optionId) }
                    ?: event.createReactionComment(userId, reactionCommentCreateDto.optionId),
            )

        eventAssignedUserService.save(eventAssignedUser.updateIsFinished(true))

        eventPublisher.publishEvent(FinishedCountUpdateEvent.of(chatId = event.id))

        return ReactionCommentDto.of(reactionComment)
    }

    fun findReplyComments(
        userId: String,
        eventId: Long,
        pageable: Pageable,
    ): Page<ReplyCommentWithUserDto> {
        workspaceUserService.validByUserIdAndChatIdInWorkspace(userId, eventId)

        val replyComments = replyCommentService.findAllByEventId(eventId, pageable)

        val userMap = userService.findAllByUserIdsToMap(replyComments.map { it.userId }.toList())

        return replyComments.map {
            ReplyCommentWithUserDto.of(
                it,
                userMap[it.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND),
            )
        }
    }
}
