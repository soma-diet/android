package dev.skaba.soma.app.domain.targets

import kotlinx.coroutines.flow.Flow

interface TargetsRepository {
  suspend fun update(targets: Targets)
  suspend fun get(): Flow<Targets>
}