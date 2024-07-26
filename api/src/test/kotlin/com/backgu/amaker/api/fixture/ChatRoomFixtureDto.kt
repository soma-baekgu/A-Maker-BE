package com.backgu.amaker.api.fixture

import com.backgu.amaker.domain.chat.ChatRoom
import com.backgu.amaker.domain.workspace.Workspace

data class ChatRoomFixtureDto(
    val workspace: Workspace,
    val chatRoom: ChatRoom,
)
