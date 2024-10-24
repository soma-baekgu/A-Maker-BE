package com.backgu.amaker.api.event.service

import com.backgu.amaker.api.event.dto.EventWithUserAndChatRoomDto
import com.backgu.amaker.api.event.dto.ReactionEventCreateDto
import com.backgu.amaker.api.event.dto.ReactionEventDetailDto
import com.backgu.amaker.api.event.dto.ReactionEventDto
import com.backgu.amaker.api.event.dto.ReplyEventCreateDto
import com.backgu.amaker.api.event.dto.ReplyEventDetailDto
import com.backgu.amaker.api.event.dto.ReplyEventDto
import com.backgu.amaker.api.event.dto.TaskEventCreateDto
import com.backgu.amaker.api.event.dto.TaskEventDto
import com.backgu.amaker.api.user.dto.UserDto
import com.backgu.amaker.application.chat.event.EventChatSaveEvent
import com.backgu.amaker.application.chat.service.ChatRoomService
import com.backgu.amaker.application.chat.service.ChatRoomUserService
import com.backgu.amaker.application.chat.service.ChatService
import com.backgu.amaker.application.event.service.EventAssignedUserService
import com.backgu.amaker.application.event.service.EventService
import com.backgu.amaker.application.event.service.ReactionEventService
import com.backgu.amaker.application.event.service.ReactionOptionService
import com.backgu.amaker.application.event.service.ReplyEventService
import com.backgu.amaker.application.event.service.TaskEventService
import com.backgu.amaker.application.user.service.UserService
import com.backgu.amaker.application.workspace.WorkspaceUserService
import com.backgu.amaker.common.exception.BusinessException
import com.backgu.amaker.common.status.StatusCode
import com.backgu.amaker.domain.chat.Chat
import com.backgu.amaker.domain.chat.ChatType
import com.backgu.amaker.domain.event.Event
import com.backgu.amaker.domain.event.EventAssignedUser
import com.backgu.amaker.domain.event.EventStatus
import com.backgu.amaker.domain.user.User
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventFacadeService(
    private val userService: UserService,
    private val chatRoomService: ChatRoomService,
    private val chatRoomUserService: ChatRoomUserService,
    private val chatService: ChatService,
    private val replyEventService: ReplyEventService,
    private val taskEventService: TaskEventService,
    private val reactionEventService: ReactionEventService,
    private val reactionOptionService: ReactionOptionService,
    private val eventAssignedUserService: EventAssignedUserService,
    private val eventPublisher: ApplicationEventPublisher,
    private val eventService: EventService,
    private val workspaceUserService: WorkspaceUserService,
) {
    @Transactional
    fun getReplyEvent(
        userId: String,
        chatRoomId: Long,
        eventId: Long,
    ): ReplyEventDetailDto {
        val user = userService.getById(userId)
        val chatRoom = chatRoomService.getById(chatRoomId)
        chatRoomUserService.validateUserInChatRoom(user, chatRoom)

        val chat = chatService.getById(eventId)
        val eventAssignedUsers = eventAssignedUserService.findAllByEventId(eventId)
        val eventAssignedUserIds = eventAssignedUsers.map { it.userId }

        val users = userService.findAllByUserIdsToMap(eventAssignedUserIds.union(listOf(chat.userId)).toList())

        val replyEvent = replyEventService.getById(eventId)

        val (finishedUsers, waitingUsers) = eventAssignedUsers.partition { it.isFinished }

        return ReplyEventDetailDto.of(
            replyEvent = replyEvent,
            eventCreator = UserDto.of(users[chat.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND)),
            finishUser =
                finishedUsers.map {
                    UserDto.of(
                        users[it.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND),
                    )
                },
            waitingUser =
                waitingUsers.map {
                    UserDto.of(
                        users[it.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND),
                    )
                },
        )
    }

    @Transactional
    fun getReactionEvent(
        userId: String,
        chatRoomId: Long,
        eventId: Long,
    ): ReactionEventDetailDto {
        val user = userService.getById(userId)
        val chatRoom = chatRoomService.getById(chatRoomId)
        chatRoomUserService.validateUserInChatRoom(user, chatRoom)

        val chat = chatService.getById(eventId)
        val eventAssignedUsers = eventAssignedUserService.findAllByEventId(eventId)
        val eventAssignedUserIds = eventAssignedUsers.map { it.userId }

        val users = userService.findAllByUserIdsToMap(eventAssignedUserIds.union(listOf(chat.userId)).toList())

        val reactionEvent = reactionEventService.getById(eventId)

        val reactionOptions = reactionOptionService.getAllByEventId(eventId)

        val (finishedUsers, waitingUsers) = eventAssignedUsers.partition { it.isFinished }

        return ReactionEventDetailDto.of(
            reactionEvent = reactionEvent,
            reactionOptions = reactionOptions,
            eventCreator = UserDto.of(users[chat.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND)),
            finishUser =
                finishedUsers.map {
                    UserDto.of(
                        users[it.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND),
                    )
                },
            waitingUser =
                waitingUsers.map {
                    UserDto.of(
                        users[it.userId] ?: throw BusinessException(StatusCode.USER_NOT_FOUND),
                    )
                },
        )
    }

    @Transactional
    fun createReplyEvent(
        userId: String,
        chatRoomId: Long,
        replyEventCreateDto: ReplyEventCreateDto,
    ): ReplyEventDto {
        val user = userService.getById(userId)
        val chatRoom = chatRoomService.getById(chatRoomId)
        chatRoomUserService.validateUserInChatRoom(user, chatRoom)

        val chat: Chat = chatService.save(chatRoom.createChat(user, replyEventCreateDto.eventTitle, ChatType.REPLY))
        chatRoomService.save(chatRoom.updateLastChatId(chat))

        val replyEvent =
            replyEventService.save(
                chat.createReplyEvent(
                    replyEventCreateDto.deadLine,
                    replyEventCreateDto.notificationStartHour,
                    replyEventCreateDto.notificationStartMinute,
                    replyEventCreateDto.interval,
                    replyEventCreateDto.eventDetails,
                ),
            )

        val users = userService.getAllByUserEmails(replyEventCreateDto.assignees)
        chatRoomUserService.validateUsersInChatRoom(users, chatRoom)

        eventAssignedUserService.saveAll(replyEvent.createAssignedUsers(users))

        eventPublisher.publishEvent(
            EventChatSaveEvent.of(
                chatRoomId,
                chat.createEventChatWithUser(replyEvent, user, users),
            ),
        )

        return ReplyEventDto.of(replyEvent)
    }

    @Transactional
    fun createTaskEvent(
        userId: String,
        chatRoomId: Long,
        taskEventCreateDto: TaskEventCreateDto,
    ): TaskEventDto {
        val user = userService.getById(userId)
        val chatRoom = chatRoomService.getById(chatRoomId)
        chatRoomUserService.validateUserInChatRoom(user, chatRoom)

        val chat: Chat = chatService.save(chatRoom.createChat(user, taskEventCreateDto.eventTitle, ChatType.TASK))
        chatRoomService.save(chatRoom.updateLastChatId(chat))

        val taskEvent =
            taskEventService.save(
                chat.createTaskEvent(
                    taskEventCreateDto.deadLine,
                    taskEventCreateDto.notificationStartHour,
                    taskEventCreateDto.notificationStartMinute,
                    taskEventCreateDto.interval,
                    taskEventCreateDto.eventDetails,
                ),
            )

        val users = userService.getAllByUserEmails(taskEventCreateDto.assignees)
        chatRoomUserService.validateUsersInChatRoom(users, chatRoom)

        eventAssignedUserService.saveAll(taskEvent.createAssignedUsers(users))

        eventPublisher.publishEvent(
            EventChatSaveEvent.of(
                chatRoomId,
                chat.createEventChatWithUser(taskEvent, user, users),
            ),
        )

        return TaskEventDto.of(taskEvent)
    }

    @Transactional
    fun createReactionEvent(
        userId: String,
        chatRoomId: Long,
        reactionEventCreateDto: ReactionEventCreateDto,
    ): ReactionEventDto {
        val user = userService.getById(userId)
        val chatRoom = chatRoomService.getById(chatRoomId)
        chatRoomUserService.validateUserInChatRoom(user, chatRoom)

        val chat: Chat =
            chatService.save(chatRoom.createChat(user, reactionEventCreateDto.eventTitle, ChatType.REACTION))
        chatRoomService.save(chatRoom.updateLastChatId(chat))

        val reactionEvent =
            reactionEventService.save(
                chat.createReactionEvent(
                    reactionEventCreateDto.deadLine,
                    reactionEventCreateDto.notificationStartHour,
                    reactionEventCreateDto.notificationStartMinute,
                    reactionEventCreateDto.interval,
                ),
            )

        val reactionOptions =
            reactionOptionService.saveAll(reactionEvent.createReactionOption(reactionEventCreateDto.options))

        val users = userService.getAllByUserEmails(reactionEventCreateDto.assignees)
        chatRoomUserService.validateUsersInChatRoom(users, chatRoom)

        eventAssignedUserService.saveAll(reactionEvent.createAssignedUsers(users))

        eventPublisher.publishEvent(
            EventChatSaveEvent.of(
                chatRoomId,
                chat.createEventChatWithUser(reactionEvent, user, users),
            ),
        )

        return ReactionEventDto.of(reactionEvent, reactionOptions)
    }

    fun getEvents(
        userId: String,
        workspaceId: Long,
        eventStatus: EventStatus,
    ): List<EventWithUserAndChatRoomDto> {
        workspaceUserService.validateUserInWorkspace(userId, workspaceId)
        val events: List<Event> = eventService.findEventByWorkspaceId(workspaceId)
        val eventUserMap: Map<Long, List<EventAssignedUser>> =
            eventAssignedUserService.findByEventIdsToEventIdMapped(events.map { it.id })
        val filteredEvents =
            events.filter { eventStatus.filter(it, eventUserMap[it.id] ?: emptyList()) }
        val chatToMap = chatService.findAllByIdsToMap(filteredEvents.map { it.id })

        val userMap = userService.findAllByUserIdsToMap(eventUserMap.values.flatten().map { it.userId })

        return filteredEvents.map { event: Event ->
            val eventAssignedUsers: List<User> =
                eventUserMap[event.id]?.mapNotNull { userMap[it.userId] } ?: emptyList()
            val finishedNumber = eventUserMap[event.id]?.count { it.isFinished } ?: 0
            val chat = chatToMap[event.id] ?: throw BusinessException(StatusCode.CHAT_NOT_FOUND)

            EventWithUserAndChatRoomDto.of(event, chat, eventAssignedUsers, finishedNumber)
        }
    }
}
