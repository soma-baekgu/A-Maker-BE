package com.backgu.amaker.infra.jpa.user.reposotory

import com.backgu.amaker.infra.jpa.user.entity.UserDeviceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserDeviceRepository : JpaRepository<UserDeviceEntity, Long> {
    @Query("select ud from UserDevice ud where ud.userId in :userIds")
    fun findByUserIds(userIds: List<String>): List<UserDeviceEntity>
}
