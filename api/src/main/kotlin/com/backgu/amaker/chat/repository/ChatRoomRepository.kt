package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.jpa.ChatRoomEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository : JpaRepository<ChatRoomEntity, Long>
