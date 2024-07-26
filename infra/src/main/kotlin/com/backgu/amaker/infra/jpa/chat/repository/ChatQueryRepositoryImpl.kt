package com.backgu.amaker.infra.jpa.chat.repository

import com.backgu.amaker.infra.jpa.chat.entity.QChatEntity.chatEntity
import com.backgu.amaker.infra.jpa.chat.query.ChatWithUserQuery
import com.backgu.amaker.infra.jpa.chat.query.QChatWithUserQuery
import com.backgu.amaker.infra.jpa.user.entity.QUserEntity.userEntity
import com.querydsl.jpa.impl.JPAQueryFactory

class ChatQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ChatQueryRepository {
    override fun findTopByChatRoomIdLittleThanCursorLimitCountWithUser(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatWithUserQuery> =
        queryFactory
            .select(
                QChatWithUserQuery(
                    chatEntity.id,
                    chatEntity.chatRoomId,
                    chatEntity.content,
                    chatEntity.chatType,
                    chatEntity.createdAt,
                    chatEntity.updatedAt,
                    userEntity.id,
                    userEntity.name,
                    userEntity.email,
                    userEntity.picture,
                ),
            ).from(chatEntity)
            .innerJoin(userEntity)
            .on(chatEntity.userId.eq(userEntity.id))
            .where(chatEntity.chatRoomId.eq(chatRoomId).and(chatEntity.id.lt(cursor)))
            .orderBy(chatEntity.id.desc())
            .limit(size.toLong())
            .fetch()

    override fun findTopByChatRoomIdGreaterThanCursorLimitCountWithUser(
        chatRoomId: Long,
        cursor: Long,
        size: Int,
    ): List<ChatWithUserQuery> =
        queryFactory
            .select(
                QChatWithUserQuery(
                    chatEntity.id,
                    chatEntity.chatRoomId,
                    chatEntity.content,
                    chatEntity.chatType,
                    chatEntity.createdAt,
                    chatEntity.updatedAt,
                    userEntity.id,
                    userEntity.name,
                    userEntity.email,
                    userEntity.picture,
                ),
            ).from(chatEntity)
            .innerJoin(userEntity)
            .on(chatEntity.userId.eq(userEntity.id))
            .where(chatEntity.chatRoomId.eq(chatRoomId).and(chatEntity.id.gt(cursor)))
            .orderBy(chatEntity.id.asc())
            .limit(size.toLong())
            .fetch()

    override fun findByIdWithUser(chatId: Long): ChatWithUserQuery? =
        queryFactory
            .select(
                QChatWithUserQuery(
                    chatEntity.id,
                    chatEntity.chatRoomId,
                    chatEntity.content,
                    chatEntity.chatType,
                    chatEntity.createdAt,
                    chatEntity.updatedAt,
                    userEntity.id,
                    userEntity.name,
                    userEntity.email,
                    userEntity.picture,
                ),
            ).from(chatEntity)
            .innerJoin(userEntity)
            .on(chatEntity.userId.eq(userEntity.id))
            .where(chatEntity.id.eq(chatId))
            .fetchOne()
}
