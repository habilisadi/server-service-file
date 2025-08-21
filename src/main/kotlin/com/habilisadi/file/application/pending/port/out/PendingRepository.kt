package com.habilisadi.file.application.pending.port.out

import com.habilisadi.file.common.application.port.out.BaseRedisRepository
import com.habilisadi.file.domain.pending.model.PendingEntity


interface PendingRepository : BaseRedisRepository<PendingEntity>