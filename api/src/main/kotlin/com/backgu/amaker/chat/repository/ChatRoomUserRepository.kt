package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.domain.ChatRoomUser
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomUserRepository : JpaRepository<ChatRoomUser, Long>
