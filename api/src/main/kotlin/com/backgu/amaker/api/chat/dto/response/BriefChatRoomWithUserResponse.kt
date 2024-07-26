package com.backgu.amaker.api.chat.dto.response

import com.backgu.amaker.api.chat.dto.ChatRoomWithUserDto
import io.swagger.v3.oas.annotations.media.Schema

data class BriefChatRoomWithUserResponse(
    @Schema(description = "채팅방 id", example = "1")
    val chatRoomId: Long,
    @Schema(description = "채팅방 이름", example = "채팅방")
    val chatRoomName: String,
    @Schema(description = "채팅방 참여자")
    val participants: List<String>,
) {
    companion object {
        fun of(chatRoomWithUserDto: ChatRoomWithUserDto): BriefChatRoomWithUserResponse =
            BriefChatRoomWithUserResponse(
                chatRoomId = chatRoomWithUserDto.chatRoomId,
                chatRoomName = chatRoomWithUserDto.chatRoomName,
                participants = chatRoomWithUserDto.participants.map { it.picture },
            )
    }
}
