package net.doheco.domain.useCases.heroes

import net.doheco.domain.repository.HeroesRepoImpl
import javax.inject.Inject

class DeleteMatchesUseCase @Inject constructor(
    private val repoImpl: HeroesRepoImpl
) {
    suspend fun deleteMatches() {
        repoImpl.deleteAllMatchesFromDb()
    }
}