package com.backgu.amaker.chat.repository

import com.backgu.amaker.chat.jpa.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository : JpaRepository<ChatEntity, String>
