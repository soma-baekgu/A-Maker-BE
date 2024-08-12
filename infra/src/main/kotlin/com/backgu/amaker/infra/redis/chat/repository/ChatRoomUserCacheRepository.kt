package com.backgu.amaker.infra.redis.chat.repository

import com.backgu.amaker.infra.redis.chat.data.ChatRoomUserCache
import org.springframework.data.repository.CrudRepository

interface ChatRoomUserCacheRepository : CrudRepository<ChatRoomUserCache, Long>
