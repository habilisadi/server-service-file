package com.habilisadi.file.application.port.out

import com.habilisadi.file.common.application.port.out.BaseRedisRepository
import com.habilisadi.file.domain.model.PendingEntity


interface PendingRepository : BaseRedisRepository<PendingEntity>