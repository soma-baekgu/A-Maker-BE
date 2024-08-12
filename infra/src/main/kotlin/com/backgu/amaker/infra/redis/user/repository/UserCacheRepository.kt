package com.backgu.amaker.infra.redis.user.repository

import com.backgu.amaker.infra.redis.user.data.UserCache
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCacheRepository : CrudRepository<UserCache, String>
