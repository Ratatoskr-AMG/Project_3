package ru.ratatoskr.project_3

class ReadMe {
    companion object {

        fun q1() {
/*
    на что заменить if (hero.id < 1) {
 */
        }

        fun q2() {
/*

    Куда убрать этот метод?

 */
        }

        fun q3() {
/*
        Как использовать BGBox из Theme?
 */
        }

        fun q4() {
/*


 */
        }
    }
}

/*

 * Вопросы

Безопасность exoplayer, accompanist
Какими данные могут быть "чувствительными" (пароль)?
Как передавать пароль (md5 не вариант)?
Как обойти кражу мобилы?
как обойти прослушку сигналов?
Как обойти передачу ссылки на видео? Blob(?)

 * Задания:

Дизайн

 * Ссылки:

https://github.com/AlexGladkov/JetpackComposeDemo/blob/main/app/src/main/java/ru/alexgladkov/jetpackcomposedemo/screens/daily/DailyViewModel.kt
https://github.com/AlexGladkov/JetpackComposeDemo/blob/main/app/src/main/java/ru/alexgladkov/jetpackcomposedemo/screens/daily/DailyScreen.kt
http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=D076D1B0AD4391F8156F8EED08C597CE&steamids=76561198165608798

 * Книги:

Карьера программиста крэкинг зэ пот
Java Concurrency in Practiсe

 * Пройдено:

Модуль клиент +
Карточка героя +
Размер картинок +
Двойное нажатие в навигации +
Карточка атрибута +
Перенос расчётов артибутов +-
Расчёты в UseCase +
Репозиторий во viewModel через UseCase +
Избранные герои вместо Dashboard +
Профиль вместо Notifications +

*/

/* Архитектура
domain
    base(?)
    di
    helpers
        events
        states
     model
     repository
     useCases
presentation
    activity
    screens
    theme
    viewModels(domain.useCases)
*/

/* Из конспектов
1:UseCase может объединять два репозитория
2:Многопоточность
Параллельность
Асинхронность
3 основных ошибки конкурентности
16.6 мс
В скоуп передается диспетчер, это SuperVisorJob
*/

/* val windowInsetsController =
    ViewCompat.getWindowInsetsController(window.decorView)
windowInsetsController!!.hide(WindowInsetsCompat.Type.systemBars())
 */
/* .drawBehind {
    val strokeWidth = density
    val y = size.height - strokeWidth / 1
    drawLine(
        Color.LightGray,
        Offset(0f, y),
        Offset(size.width, y),
        strokeWidth
    )
}  */
/*
    private val addSteamPlayerUseCase: AddSteamPlayerUseCase,
    suspend fun getSteamPlayer() : SteamPlayer {
        viewModelScope.launch(Dispatchers.IO) {

        }
        Log.e("TOHA","steamPlayer:"+steamPlayer.toString())

    }
 */
/*
    suspend fun getSteamUser() : SteamPlayer{
        try {
           roomAppDatabase.SteamUsersDao().player
        } catch (e: Exception) {
            Log.e("TOHA", "updateSqliteTable e: " + e.message.toString())
        } finally {
            Log.e("TOHA", "updateSqliteTable")
        }
         return  SteamPlayer("")
    }
*/
/*
    suspend fun updateAppUser(player: SteamPlayer) {
        Log.e("TOHA", "player.steamid"+player.steamid);
        if (player.steamid != null) {
            try {
                roomAppDatabase.SteamUsersDao().insertPlayer(player)
            } catch (e: Exception) {
                Log.e("TOHA", "updateSqliteTable e: " + e.message.toString())
            } finally {
                Log.e("TOHA", "updateSqliteTable")
            }
        }
    }

 */
/*
    systemUiController.isStatusBarVisible = false // Status bar
    systemUiController.isNavigationBarVisible = false // Navigation bar
    systemUiController.isSystemBarsVisible = false // Status & Navigation bars

 */
