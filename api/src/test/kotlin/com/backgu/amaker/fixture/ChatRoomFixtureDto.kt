package com.backgu.amaker.fixture

import com.backgu.amaker.chat.domain.ChatRoom
import com.backgu.amaker.workspace.domain.Workspace

data class ChatRoomFixtureDto(
    val workspace: Workspace,
    val chatRoom: ChatRoom,
)
