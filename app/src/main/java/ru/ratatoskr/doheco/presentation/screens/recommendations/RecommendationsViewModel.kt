package ru.ratatoskr.doheco.presentation.screens.recommendations

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.ImageResult
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.doheco.domain.extensions.set
import ru.ratatoskr.doheco.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import ru.ratatoskr.doheco.domain.useCases.heroes.*
import ru.ratatoskr.doheco.domain.useCases.user.GetPlayerTierFromSPUseCase
import ru.ratatoskr.doheco.presentation.screens.recommendations.models.RecommendationsState
import java.util.*
import javax.inject.Inject


@HiltViewModel
class RecommendationsViewModel @Inject constructor(
    mFirebaseAnalytics: FirebaseAnalytics,
    imageLoader: ImageLoader,
    appSharedPreferences: SharedPreferences,
    val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val addHeroesUserCase: AddHeroesUserCase,
    val getAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
    val getPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase,
) : AndroidViewModel(Application()) {

    var appSharedPreferences = appSharedPreferences
    var imageLoader = imageLoader
    var mFirebaseAnalytics = mFirebaseAnalytics

    val _recommendations_state: MutableLiveData<RecommendationsState> =
        MutableLiveData<RecommendationsState>(RecommendationsState.LoadingRecommendationsState())
    val recommendationsState: LiveData<RecommendationsState> = _recommendations_state

    fun getAllHeroes() {
        _recommendations_state.set(RecommendationsState.LoadingRecommendationsState())

        val player_tier_from_sp =
            getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesByStrSortByName("")
                if (!heroes.isEmpty()) {
                    _recommendations_state.postValue(
                        RecommendationsState.LoadedRecommendationsState(
                            heroes,player_tier_from_sp
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                Log.e("TOHA", "e:" + e.toString())
                e.printStackTrace()
            }
        }
    }

}