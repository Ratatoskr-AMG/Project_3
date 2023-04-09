package net.doheco.presentation.screens.hero

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.utils.EventHandler
import net.doheco.domain.extensions.set
import net.doheco.presentation.screens.hero.models.HeroEvent
import net.doheco.presentation.screens.hero.models.HeroState
import net.doheco.domain.model.Hero
import net.doheco.domain.useCases.favorites.InsertHeroToFavoritesUseCase
import net.doheco.domain.useCases.favorites.DropHeroFromFavoritesUseCase
import net.doheco.domain.useCases.favorites.GetIfHeroIsFavoriteUseCase
import net.doheco.domain.useCases.heroes.*
import net.doheco.domain.utils.AttributeMaximum
import javax.inject.Inject

@HiltViewModel
class HeroViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    private val getHeroByIdUseCase: GetHeroByIdUseCase,
    private val getIfHeroIsFavoriteUseCase: GetIfHeroIsFavoriteUseCase,
    private val dropHeroFromFavorites: DropHeroFromFavoritesUseCase,
    private val insertHeroToFavoritesUseCase: InsertHeroToFavoritesUseCase,
    private val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    private val getHeroesAttrsMaxUseCase: GetHeroesAttrsMaxUseCase,
    private val currentInfoBlockUseCase: CurrentInfoBlockUseCase,
) : ViewModel(), EventHandler<HeroEvent> {

    var appSharedPreferences = appSharedPreferences
    private val _heroState: MutableLiveData<HeroState> = MutableLiveData<HeroState>()
    val heroState: LiveData<HeroState> = _heroState

    fun getAttrMax(attr: String): Int {

        var max = 0;

        viewModelScope.launch(Dispatchers.IO) {
            val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr, false)
            heroes.sortedByDescending { attr }
            for (hero in heroes) {
                Log.e("TOHA", "Hero " + hero.localizedName + ", attr:" + hero.proPick.toString())
            }
            max = heroes[0].proPick;

        }
        Log.e("TOHA", "max " + max)
        return max
    }

    fun getHeroById(id: String) {

        _heroState.set(newValue = HeroState.LoadingHeroState())

        viewModelScope.launch(Dispatchers.IO) {

            try {
                Log.e("TOHAXXX","id2:"+id)
                val currentInfoBlock = currentInfoBlockUseCase.getCurrentInfoBlockHero(appSharedPreferences)
                Log.e("TOHAXXX","id3:"+id)
                val hero = getHeroByIdUseCase.GetHeroById(id)
                Log.e("TOHAXXX","id4:"+id)
                Log.e("TOHAXXX","hero:"+hero)
                val currentAttrsMax: List<AttributeMaximum> =
                    getHeroesAttrsMaxUseCase.getHeroesAttrsMax()
                Log.e("TOHAXXX","currentAttrsMax:"+currentAttrsMax)
                if (hero.id < 1) {
                    //переносим в ЮзКейс трайкетч
                    Log.e("TOHAXXX","postValue:mid")
                    _heroState.postValue(HeroState.NoHeroState())
                } else {
                    Log.e("TOHAXXX","postValue:begin")
                    _heroState.postValue(
                        HeroState.HeroLoadedState(
                            hero = hero,
                            isFavorite = getIfHeroIsFavoriteUseCase.getIfHeroIsFavoriteById(hero.id),
                            currentInfoBlock,
                            currentAttrsMax = currentAttrsMax
                        )

                    )
                    Log.e("TOHAXXX","postValue:end")
                }
            } catch (e: java.lang.Exception) {
                /*
                Здесь следует измененять состояние, а не просто печатать в лог
                 */
                Log.e("TOHA", "getHeroById exception:" + e.toString());
                e.printStackTrace()
            }
        }
    }

    override fun obtainEvent(event: HeroEvent) {
        when (val currentState = _heroState.value) {
            is HeroState.HeroLoadedState -> reduce(event, currentState)
            else -> {}
        }
    }

    private fun reduce(event: HeroEvent, currentState: HeroState.HeroLoadedState) {
        when (event) {
            is HeroEvent.OnFavoriteCLick -> isFavoriteSwitch(
                currentState.hero,
                currentState.isFavorite,
                currentState.currentInfoBlock,
                currentState.currentAttrsMax,
            )
            is HeroEvent.OnInfoBlockSelect -> selectInfoBlock(
                currentState.hero,
                currentState.isFavorite,
                event.infoBlockName,
                currentState.currentAttrsMax,
                event.scrollState
            )
        }
    }

    private fun selectInfoBlock(
        hero: Hero,
        isFavorite: Boolean = false,
        newInfoBlock: String,
        currentAttrsMax: List<AttributeMaximum>,
        scrollState:LazyListState
    ) {

        currentInfoBlockUseCase.setCurrentInfoBlockHero(appSharedPreferences,newInfoBlock)

        viewModelScope.launch {
            _heroState.postValue(
                HeroState.HeroLoadedState(
                    hero = hero,
                    isFavorite = isFavorite,
                    currentInfoBlock = newInfoBlock,
                    currentAttrsMax = currentAttrsMax,
                )
            )
            //scrollState.animateScrollToItem(index = 0)
        }
    }

    private fun isFavoriteSwitch(
        hero: Hero,
        isFavorite: Boolean = false,
        currentInfoBlock: String,
        currentAttrsMax: List<AttributeMaximum>
    ) {
        viewModelScope.launch {
            if (isFavorite) {
                try {
                    dropHeroFromFavorites.dropHeroFromFavorites(hero.id)

                    _heroState.postValue(
                        HeroState.HeroLoadedState(
                            hero = hero,
                            isFavorite = false,
                            currentInfoBlock = currentInfoBlock,
                            currentAttrsMax
                        )


                    )
                } catch (e: Exception) {
                    _heroState.postValue(HeroState.ErrorHeroState())
                }
            } else {
                try {
                    insertHeroToFavoritesUseCase.insertHeroToFavorites(hero.id)
                    _heroState.postValue(
                        HeroState.HeroLoadedState(
                            hero = hero,
                            isFavorite = true,
                            currentInfoBlock = currentInfoBlock,
                            currentAttrsMax = currentAttrsMax
                        )
                    )
                } catch (e: Exception) {
                    _heroState.postValue(HeroState.ErrorHeroState())
                }
            }
        }

    }

}