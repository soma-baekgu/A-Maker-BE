package com.backgu.amaker.api.event.service

import com.backgu.amaker.api.event.dto.ReactionCommentCreateDto
import com.backgu.amaker.api.event.dto.ReactionCommentDto
import com.backgu.amaker.api.event.dto.ReactionOptionWithCommentDto
import com.backgu.amaker.api.event.dto.ReplyCommentCreateDto
import com.backgu.amaker.api.event.dto.ReplyCommentDto
import com.backgu.amaker.api.event.dto.ReplyCommentWithUserDto
import com.backgu.amaker.api.event.dto.TaskCommentCreateDto
import com.backgu.amaker.api.event.dto.TaskCommentDto
import com.backgu.amaker.api.event.dto.TaskCommentWithUserDto
import com.backgu.amaker.application.chat.event.FinishedCountUpdateEvent
import com.backgu.amaker.application.event.service.EventAssignedUserService
import com.backgu.amaker.application.event.service.ReactionCommentService
import com.backgu.amaker.application.event.service.ReactionEventService
import com.backgu.amaker.application.event.service.ReactionOptionService
import com.backgu.amaker.application.event.service.ReplyCommentService
import com.backgu.amaker.application.event.service.ReplyEventService
import com.backgu.amaker.application.event.service.TaskCommentService
import com.backgu.amaker.application.event.service.TaskEventService
import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.application.workspace.WorkspaceUserService
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.event.ReactionComment
import com.backgu.amaker.domain.user.User
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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
    private val taskCommentService: TaskCommentService,
    private val reactionCommentService: ReactionCommentService,
    private val reactionOptionService: ReactionOptionService,
    private val workspaceUserService: WorkspaceUserService,
    private val eventPublisher: ApplicationEventPublisher,
    private val taskEventService: TaskEventService,
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
    fun createTaskComment(
        userId: String,
        eventId: Long,
        taskCommentCreateDto: TaskCommentCreateDto,
    ): TaskCommentDto {
        val user = userService.getById(userId)
        val event = taskEventService.getById(eventId)

        val eventAssignedUser = eventAssignedUserService.getByUserAndEvent(user, event)

        val taskComment = taskCommentService.save(event.addTaskComment(user, taskCommentCreateDto.path))

        eventAssignedUserService.save(eventAssignedUser.updateIsFinished(true))

        eventPublisher.publishEvent(FinishedCountUpdateEvent.of(chatId = event.id))

        return TaskCommentDto.of(taskComment)
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
                    ?.also { it.toggleOptionId(reactionCommentCreateDto.optionId) } ?: event.createReactionComment(
                    userId,
                    reactionCommentCreateDto.optionId,
                ),
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

    fun findTaskComments(
        userId: String,
        eventId: Long,
        pageable: PageRequest,
    ): Page<TaskCommentWithUserDto> {
        workspaceUserService.validByUserIdAndChatIdInWorkspace(userId, eventId)

        val taskComments = taskCommentService.findAllByEventId(eventId, pageable)

        val userMap = userService.findAllByUserIdsToMap(taskComments.map { it.userId }.toList())

        return taskComments.map {
            TaskCommentWithUserDto.of(
                it,
                userMap[it.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND),
            )
        }
    }

    fun findReactionComment(
        userId: String,
        eventId: Long,
    ): List<ReactionOptionWithCommentDto> {
        workspaceUserService.validByUserIdAndChatIdInWorkspace(userId, eventId)
        val reactionOptions = reactionOptionService.getAllByEventId(eventId)
        val reactionCommentGroupByOption: Map<Long, List<ReactionComment>> =
            reactionCommentService.findAllByEventIdGroupByReactionOptions(eventId)
        val userMap: Map<String, User> =
            userService.findAllByUserIdsToMap(
                reactionCommentGroupByOption.flatMap {
                    it.value.map { it.userId }.toList()
                },
            )

        return reactionOptions.map {
            ReactionOptionWithCommentDto.of(
                it,
                reactionCommentGroupByOption[it.id]
                    ?: emptyList(),
                userMap,
            )
        }
    }
}
