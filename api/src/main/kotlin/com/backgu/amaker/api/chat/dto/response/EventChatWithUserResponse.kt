package com.backgu.amaker.api.chat.dto.response

import com.backgu.amaker.api.chat.dto.EventChatWithUserDto
import com.backgu.amaker.api.event.dto.response.EventWithUserResponse
import com.backgu.amaker.api.user.dto.response.UserResponse
import com.backgu.amaker.domain.chat.ChatType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class EventChatWithUserResponse(
    @Schema(description = "채팅 ID", example = "1")
    override val id: Long,
    override val user: UserResponse,
    @Schema(description = "채팅방 ID", example = "1")
    override val chatRoomId: Long,
    @Schema(description = "채팅 내용", example = "안녕하세요")
    override var content: EventWithUserResponse,
    @Schema(description = "채팅 타입(GENERAL, REPLY, REACTION, TASK, FILE, IMAGE)", example = "GENERAL")
    override val chatType: ChatType,
    @Schema(description = "생성일시", example = "2021-05-29T00:00:00")
    override val createdAt: LocalDateTime,
    @Schema(description = "수정일시", example = "2021-05-29T00:00:00")
    override val updatedAt: LocalDateTime,
) : ChatWithUserResponse<EventWithUserResponse> {
    companion object {
        fun of(eventChatWithUserDto: EventChatWithUserDto): EventChatWithUserResponse =
            EventChatWithUserResponse(
                id = eventChatWithUserDto.id,
                user = UserResponse.of(eventChatWithUserDto.user),
                chatRoomId = eventChatWithUserDto.chatRoomId,
                content = EventWithUserResponse.of(eventChatWithUserDto.content),
                chatType = eventChatWithUserDto.chatType,
                createdAt = eventChatWithUserDto.createdAt,
                updatedAt = eventChatWithUserDto.updatedAt,
            )
    }
}
