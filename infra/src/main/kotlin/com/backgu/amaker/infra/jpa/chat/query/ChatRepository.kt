package com.backgu.amaker.infra.jpa.chat.query

import com.backgu.amaker.infra.jpa.chat.repository.ChatJpaRepository

interface ChatRepository :
    ChatJpaRepository,
    ChatQueryRepository
