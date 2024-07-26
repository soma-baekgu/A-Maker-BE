package com.backgu.amaker.api.chat.dto.response

import com.backgu.amaker.api.chat.dto.ChatWithUserDto
import com.backgu.amaker.api.user.dto.response.UserResponse
import com.backgu.amaker.domain.chat.ChatType
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
    @Schema(description = "채팅 타입(GENERAL, REPLY, REACTION, TASK, FILE)", example = "GENERAL")
    override val chatType: ChatType,
    @Schema(description = "생성일시", example = "2021-05-29T00:00:00")
    override val createdAt: LocalDateTime,
    @Schema(description = "수정일시", example = "2021-05-29T00:00:00")
    override val updatedAt: LocalDateTime,
) : ChatWithUserResponse<String> {
    companion object {
        fun of(chatWithUserDto: ChatWithUserDto<*>): ChatWithUserResponse<*> =
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
