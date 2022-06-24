package ru.ratatoskr.project_3

/* Архитектура
data
    contracts
    converters
    remote (?)
        implementations
        services
    dao
domain
    base(?)
    di (ktor)
    helpers
        events
        states
     model
     repository
     useCases
presentation
    activity
        MainActivity
    screens
        WrapperScreen
    theme
    viewModels(domain.useCases)
*/
/* Задания:
Кэп-Рефакторинг (Prepare-For-Cap ревизии)
Аккаунт Huawei App Gallery
Кэп-Переговоры
*/

class Questions {
    companion object {
        fun q1() {
/*
    на что заменить if (hero.id < 1) {
 */
        }
        fun q2() {
/*

    Куда убрать метод stopPlayer из MainActivity?

 */
            //см. onDispose (и др. эффекты)
        }
        fun q3() {
/*
        Как использовать BGBox из Theme?
 */
        }
        fun q4() {
/*

        Здесь нужна фабрика ?

 */
        }

        fun q5() {
/*



 */
        }

        fun q6() {
/*



 */
        }

        fun q7() {
/*



 */
        }
    }
}
/* Вопросы

*/
/*Ответы

+ Куда опубликовать?
+ Что показывать вместо пустого списка избранных? (добавить быстрое удаление, арт в центр экрана)
+ Безопасность exoplayer
+ Дизайн под планшеты (?)
+ Какими данные могут быть "чувствительными" (пароль)?
+ Как передавать пароль (md5 не вариант)?
+ Как учесть кражу мобилы? (привязка устройств на стороне сервиса)
+ Как обойти снифферы? Оконечное шифрование!
+ Как обойти получение ссылки на видео? Blob(?) blob:// Класть видео в базу, сторонние сервисы

Google PLay Market: No in-app
+ Huawei App Gallery (Android HMS)
- NashStore (no push, no Geo)
RuStore (no push, no Geo)

Голый: AOSP
Наш: Android GMS -> Android HMS

SMT линии
GTPR ГлавТоргПродукт XDDD
AOS2
MTProto

*/
/* Пройдено:
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
Основы дизайна +
Авторизация и определение уровня пользователя +
Страница уровней+
Возможность обновить данные вручную после установки+
*/
/* Из конспектов
1:
UseCase может объединять два репозитория

2:
Многопоточность
Параллельность
Асинхронность
3 основных ошибки конкурентности
16.6 мс
В скоуп передается диспетчер, это SuperVisorJob

3:
Room Module - спец. класс в котором объявлены зависимости
scope http bd converters -> app.scope = @Singleton
Модуль привязан к lifecycle = @InstallIn
Внутри обычных классов @Provides (@Bind - для абстрактных)
Не можем повлиять на класс (библиотека), а только создать = @Provides
Все view : Context , @ApplicationContext
Когда подтягиваем ресурсы (строки, картинки): если передан Апп контекст то нет проблем, контекст вьюхи не подойдет

*/
/* Ссылки:
https://stackoverflow.com/questions/9279111/determine-if-the-device-is-a-smartphone-or-tablet
https://github.com/AlexGladkov/JetpackComposeDemo/blob/main/app/src/main/java/ru/alexgladkov/jetpackcomposedemo/screens/daily/DailyViewModel.kt
https://github.com/AlexGladkov/JetpackComposeDemo/blob/main/app/src/main/java/ru/alexgladkov/jetpackcomposedemo/screens/daily/DailyScreen.kt
http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=D076D1B0AD4391F8156F8EED08C597CE&steamids=76561198165608798
*/
/* Книги:
Карьера программиста крэкинг зэ пот
Java Concurrency in Practiсe
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
/*AnimatedVisibility(
                visible = visible,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Box(modifier = Modifier.padding(top = 30.dp)) {

                    Box(
                        modifier = Modifier
                            .height(70.dp)
                            .padding(start = 20.dp, end = 20.dp)
                            .padding(bottom = 20.dp)
                    ) {


                        TextField(
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.Black,
                                lineHeight = 220.sp,
                                textDecoration = TextDecoration.None
                            ),
                            value = searchState.text,
                            onValueChange = {
                                searchState = TextFieldValue(it, TextRange(it.length))
                                scope.launch {
                                    scrollState.scrollToItem(0)
                                    delay(250)
                                    focusRequesterTop.requestFocus()
                                }
                                //onHeroSearch(it)
                                visible = false
                                offsetPosition = 0f

                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .focusRequester(focusRequesterPopUp)
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        Log.e("TOHA", "Popup is focused")
                                    } else {
                                        Log.e("TOHA", "Popup is not focused")
                                    }
                                }
                                .clip(RoundedCornerShape(5.dp))
                                .fillMaxWidth()
                                .background(Color.White),

                            )
                    }
                }

            }

*/


