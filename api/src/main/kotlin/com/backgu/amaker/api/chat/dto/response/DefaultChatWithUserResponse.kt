package com.backgu.amaker.chat.dto.response

import com.backgu.amaker.chat.domain.ChatType
import com.backgu.amaker.chat.dto.ChatWithUser
import com.backgu.amaker.user.dto.response.UserResponse
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class DefaultChatWithUserResponse(
    @Schema(description = "채팅 ID", example = "1")
    override val id: Long,
    override val user: UserResponse,
    @Schema(description = "채팅방 ID", example = "1")
    override val chatRoomId: Long,
    @Schema(description = "채팅 내용", example = "안녕하세요")
    override var content: String,
    @Schema(description = "채팅 타입(GENERAL, REPLY, REACTION, TASK)", example = "GENERAL")
    override val chatType: ChatType,
    @Schema(description = "생성일시", example = "2021-05-29T00:00:00")
    override val createdAt: LocalDateTime,
    @Schema(description = "수정일시", example = "2021-05-29T00:00:00")
    override val updatedAt: LocalDateTime,
) : ChatWithUserResponse<String> {
    companion object {
        fun of(chatWithUserDto: ChatWithUser<*>): ChatWithUserResponse<*> =
            when (chatWithUserDto.chatType) {
                ChatType.FILE -> FileChatWithUserResponse.of(chatWithUserDto)
                else ->
                    DefaultChatWithUserResponse(
                        id = chatWithUserDto.id,
                        user = UserResponse.of(chatWithUserDto.user),
                        chatRoomId = chatWithUserDto.chatRoomId,
                        content = chatWithUserDto.content.toString(),
                        chatType = chatWithUserDto.chatType,
                        createdAt = chatWithUserDto.createdAt,
                        updatedAt = chatWithUserDto.updatedAt,
                    )
            }
    }
}
