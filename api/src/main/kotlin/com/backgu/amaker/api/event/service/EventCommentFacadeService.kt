package com.backgu.amaker.api.event.service

import com.backgu.amaker.api.common.exception.BusinessException
import com.backgu.amaker.api.common.exception.StatusCode
import com.backgu.amaker.api.event.dto.ReplyCommentCreateDto
import com.backgu.amaker.api.event.dto.ReplyCommentDto
import com.backgu.amaker.api.event.dto.ReplyCommentWithUserDto
import com.backgu.amaker.api.user.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventCommentFacadeService(
    private val userService: UserService,
    private val replyEventService: ReplyEventService,
    private val eventAssignedUserService: EventAssignedUserService,
    private val replyCommentService: ReplyCommentService,
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

        return ReplyCommentDto.of(replyComment)
    }

    fun findReplyComments(
        userId: String,
        eventId: Long,
        pageable: Pageable,
    ): Page<ReplyCommentWithUserDto> {
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
