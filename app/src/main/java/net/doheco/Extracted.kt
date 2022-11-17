package net.doheco

class Extracted {
}

/*
    == HomeViewModel ==

    fun saveHeroImage(request: ImageRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            imageLoader.execute(request)
        }
    }

    fun getHeroImage(request: ImageRequest): ImageResult {
        lateinit var imageres: ImageResult
        viewModelScope.launch(Dispatchers.IO) {
            imageres = imageLoader.execute(request)
        }
        return imageres
    }

    == ProfileViewModel ==

    fun getPlayerTierFromSP(): String {
        return getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
    }

    fun getPlayerSteamNameFromSP(): String {
        return getPlayerSteamNameFromSPUseCase.getPlayerSteamNameFromSP(appSharedPreferences)
    }


    fun getPlayerIdFromSP(): String {
        return getPlayerIdFromSP.getPlayerIdFromSP(appSharedPreferences)
    }
    fun setSteamIsDefinedProfileState() {
        //Log.e("TOHA", "setSteamIsDefinedProfileState")
        try {
            _profileState.postValue(
                ProfileState.SteamNameIsDefinedState(
                    playerTierFromSharedPreferences,
                    playerSteamNameFromSharedPreferences,
                    heroesBaseLastModifiedFromSharedPreferences!!,
                    getUpdateBtnText()
                )
            )
        } catch (e: Exception) {
            _profileState.postValue(ProfileState.ErrorProfileState)
        }
    }

    fun setUndefinedProfileState() {
        Log.e("TOHA", "setUndefinedProfileState")
        try {
            _profileState.postValue(
                ProfileState.UndefinedState(
                    playerTierFromSharedPreferences, heroesBaseLastModifiedFromSharedPreferences!!,
                    getUpdateBtnText()
                )
            )
        } catch (e: Exception) {
            _profileState.postValue(ProfileState.ErrorProfileState)
        }
    }

*/
