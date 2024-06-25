package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.domain.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository : JpaRepository<ChatRoom, Long>
