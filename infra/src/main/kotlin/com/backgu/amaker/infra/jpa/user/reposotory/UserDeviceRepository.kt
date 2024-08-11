package com.backgu.amaker.infra.jpa.user.reposotory

import com.backgu.amaker.infra.jpa.user.entity.UserDeviceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserDeviceRepository : JpaRepository<UserDeviceEntity, Long>
