package com.backgu.amaker.chat.dto.response

import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.chat.dto.ChatWithUser
import com.backgu.amaker.chat.dto.EventChatWithUserDto
import com.backgu.amaker.user.dto.response.UserResponse
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

interface ChatWithUserResponse<T> {
    @get:Schema(description = "채팅 ID", example = "1")
    val id: Long

    val user: UserResponse

    @get:Schema(description = "채팅방 ID", example = "1")
    val chatRoomId: Long

    @get:Schema(description = "채팅 내용", example = "안녕하세요")
    var content: T

    @get:Schema(description = "채팅 타입(GENERAL, REPLY, REACTION, TASK)", example = "GENERAL")
    val chatType: ChatType

    @get:Schema(description = "생성일시", example = "2021-05-29T00:00:00")
    val createdAt: LocalDateTime

    @get:Schema(description = "수정일시", example = "2021-05-29T00:00:00")
    val updatedAt: LocalDateTime

    companion object {
        fun of(chatWithUserDto: ChatWithUser<*>): ChatWithUserResponse<*> {
            return when (chatWithUserDto) {
                is EventChatWithUserDto -> EventChatWithUserResponse.of(chatWithUserDto)
                else -> DefaultChatWithUserResponse.of(chatWithUserDto)
            }
        }
    }
}
