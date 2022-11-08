package net.doheco.presentation.screens.recommendations

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import coil.ImageLoader
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.extensions.set
import net.doheco.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import net.doheco.domain.useCases.heroes.*
import net.doheco.domain.useCases.user.GetPlayerTierFromSPUseCase
import net.doheco.presentation.screens.recommendations.models.RecommendationsState
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
                val favoriteHeroes = getAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()

                if (!heroes.isEmpty()) {
                    _recommendations_state.postValue(
                        RecommendationsState.LoadedRecommendationsState(
                            heroes,player_tier_from_sp,
                            favoriteHeroes
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