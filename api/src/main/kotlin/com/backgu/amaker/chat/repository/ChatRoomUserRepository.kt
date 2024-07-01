package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.jpa.ChatRoomUserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomUserRepository : JpaRepository<ChatRoomUserEntity, Long>
