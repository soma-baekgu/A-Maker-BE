package com.backgu.amaker.infra.jpa.user.reposotory

import com.backgu.amaker.infra.jpa.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<UserEntity, String> {
    fun findByEmail(email: String): UserEntity?

    fun existsByEmail(email: String): Boolean

    fun findAllByIdIn(userIds: Set<String>): Set<UserEntity>

    fun findAllByEmailIn(userEmails: List<String>): List<UserEntity>

    @Query("select u from User u join fetch WorkspaceUser wu on u.id = wu.userId where wu.workspaceId = :workspaceId")
    fun findByWorkspaceId(workspaceId: Long): List<UserEntity>

    @Query("select u from User u join fetch ChatRoomUser cru on u.id = cru.userId where cru.chatRoomId = :chatRoomId")
    fun findByChatRoomId(chatRoomId: Long): List<UserEntity>
}
