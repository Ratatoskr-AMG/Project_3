package net.doheco.presentation.screens.recommendations

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.lifecycle.*
import coil.ImageLoader
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import net.doheco.data.converters.DotaMatchesConverter
import net.doheco.domain.extensions.set
import net.doheco.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import net.doheco.domain.useCases.heroes.*
import net.doheco.domain.useCases.system.ServerApiCallUseCase
import net.doheco.domain.useCases.user.GetPlayerTierFromSPUseCase
import net.doheco.domain.useCases.user.MatchesUseCase
import net.doheco.domain.useCases.user.UUIdSPUseCase
import net.doheco.domain.utils.EventHandler
import net.doheco.presentation.screens.profile.models.ProfileEvent
import net.doheco.presentation.screens.profile.models.ProfileState
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
    private val UUIdSPUseCase: UUIdSPUseCase,
    private val serverApiCallUseCase: ServerApiCallUseCase,
    private val getHeroByIdUseCase: GetHeroByIdUseCase,
    private val matchesUseCase: MatchesUseCase,
) : AndroidViewModel(Application()), EventHandler<ProfileEvent> {

    var appSharedPreferences = appSharedPreferences
    var imageLoader = imageLoader
    var mFirebaseAnalytics = mFirebaseAnalytics

    val _recommendations_state: MutableLiveData<RecommendationsState> =
        MutableLiveData<RecommendationsState>(RecommendationsState.LoadingRecommendationsState)
    val recommendationsState: LiveData<RecommendationsState> = _recommendations_state

    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun obtainEvent(event: ProfileEvent) {
        reduce(event)
    }

    private fun reduce(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnUpdate -> updateHeroesAndMatches()
            else -> {}
        }
    }

    fun setRefreshingState(){
        _recommendations_state.set(RecommendationsState.RefreshingRecommendationsState)
    }

    fun updateHeroesAndMatches(/*isInit: Boolean = false*/) {

        _recommendations_state.set(RecommendationsState.LoadingRecommendationsState)

        viewModelScope.launch(Dispatchers.IO) {

            var UUId = UUIdSPUseCase.GetSteamUUIdFromSP(appSharedPreferences)

            if (UUId.isEmpty()) {
                UUId = UUIdSPUseCase.GetAppUUIdFromSP(appSharedPreferences)
            }

            try {

                val apiCallResult = serverApiCallUseCase.getHeroesAndMatches(UUId)
                val openDotaMatchesList = apiCallResult.matches
                if (openDotaMatchesList != null) {
                    val appDotaMatches = openDotaMatchesList.map {
                        val hero = getHeroByIdUseCase.GetHeroById(it.heroId.toString())
                        DotaMatchesConverter.doForward(it, hero)
                    }
                    matchesUseCase.updateMatchesDb(appDotaMatches)
                    Log.e("APICALL", appDotaMatches.toString())
                }
                if(!apiCallResult.heroes!!.isEmpty()){
                    val heroes = getAllHeroesFromOpendotaUseCase.calculate(apiCallResult.heroes!!)
                    addHeroesUserCase.addHeroes(heroes)
                    _recommendations_state.postValue(
                        RecommendationsState.LoadedRecommendationsState(
                            heroes,
                            getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences),
                            getAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()
                        )
                    )
                }
            } catch (e: Exception) {
                _recommendations_state.postValue(RecommendationsState.ErrorRecommendationsState(e.toString()))
            }
        }
    }



}