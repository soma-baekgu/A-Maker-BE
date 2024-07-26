package com.backgu.amaker.api.chat.dto.response

import com.backgu.amaker.api.chat.dto.ChatWithUserDto
import com.backgu.amaker.api.file.dto.response.FileResponse
import com.backgu.amaker.api.user.dto.response.UserResponse
import com.backgu.amaker.domain.chat.ChatType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class FileChatWithUserResponse(
    @Schema(description = "채팅 ID", example = "1")
    override val id: Long,
    override val user: UserResponse,
    @Schema(description = "채팅방 ID", example = "1")
    override val chatRoomId: Long,
    @Schema(description = "채팅 내용", example = "안녕하세요")
    override var content: FileResponse,
    @Schema(description = "채팅 타입(GENERAL, REPLY, REACTION, TASK, FILE)", example = "GENERAL")
    override val chatType: ChatType,
    @Schema(description = "생성일시", example = "2021-05-29T00:00:00")
    override val createdAt: LocalDateTime,
    @Schema(description = "수정일시", example = "2021-05-29T00:00:00")
    override val updatedAt: LocalDateTime,
) : ChatWithUserResponse<FileResponse> {
    companion object {
        fun of(fileChatWithUserDto: ChatWithUserDto<*>): FileChatWithUserResponse =
            FileChatWithUserResponse(
                id = fileChatWithUserDto.id,
                user = UserResponse.of(fileChatWithUserDto.user),
                chatRoomId = fileChatWithUserDto.chatRoomId,
                content = FileResponse.of(fileChatWithUserDto.content.toString()),
                chatType = fileChatWithUserDto.chatType,
                createdAt = fileChatWithUserDto.createdAt,
                updatedAt = fileChatWithUserDto.updatedAt,
            )
    }
}
