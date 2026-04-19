package dev.skaba.soma.app.data.targets

import dev.skaba.soma.app.data.targets.local.TargetsDao
import dev.skaba.soma.app.data.targets.local.toDomain
import dev.skaba.soma.app.data.targets.local.toEntity
import dev.skaba.soma.app.domain.targets.Targets
import dev.skaba.soma.app.domain.targets.TargetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TargetsRepositoryImpl(
  private val targetsDao: TargetsDao,
) : TargetsRepository {
  override suspend fun update(targets: Targets) {
    targetsDao.insertOrUpdate(targets.toEntity())
  }

  override fun get(): Flow<Targets> {
    return targetsDao.getTargets().map { entity ->
      entity.toDomain()
    }
  }
}