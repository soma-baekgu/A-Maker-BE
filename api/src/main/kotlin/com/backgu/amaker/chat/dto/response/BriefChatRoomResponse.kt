package com.backgu.amaker.chat.dto.response

import com.backgu.amaker.chat.dto.BriefChatRoomViewDto
import io.swagger.v3.oas.annotations.media.Schema

data class BriefChatRoomResponse(
    @Schema(description = "채팅방 리스트")
    val chatRooms: List<BriefChatRoomWithUserResponse>,
) {
    companion object {
        fun of(chatRoom: BriefChatRoomViewDto): BriefChatRoomResponse =
            BriefChatRoomResponse(
                chatRooms = chatRoom.chatRooms.map { BriefChatRoomWithUserResponse.of(it) },
            )
    }
}
