package net.doheco

class Questions {
    companion object {
        fun qX() {
            /*
                #########
            */
        }
    }
}

/* Вопросы



*/

/* Задания

    Стирать данные при выходе
    Добавить на экран текст ошибки "Нету игр"
    Вьюмодель для загрузки данных
    Придумать текст ошибки "Закрытый профиль"
    (1+3)/2
    Подписать KDA

    Отдельный экран для загрузки
    https://stackoverflow.com/questions/50514758/how-to-clear-navigation-stack-after-navigating-to-another-fragment-in-android
    https://stackoverflow.com/questions/66845899/compose-navigation-remove-previous-composable-from-stack-before-navigating
navController.navigate(Screens.Login.name) {
    popUpTo(Screens.Splash.name) {
        inclusive = true
    }
}

    Сайт
    Публикация
    Продвижение

    KTLINT
    SwiftLINT
    Firebase Performance (Пубер)
    Рефакторинг

*/

/* Пройдено
    Переделать профиль под новую логику сервера +
    Экраны при отсутствии данных +
    Иконки вместо кнопок +
    Автозагрузка статы при входе в профиль +
    Дизайн формы обратной связи +
    Реакции формы обратной связи +
    Статистика игрока +
    ProfileViewModel Refactoring +
    Метод отправки формы обратной связи +
    Серверная часть формы обратной связи +
    Текст к форме обратной связи +
    Обновлялка +
    Провайдер ресурсов для выноса обработки строк во viewmodel +
    Адаптация под планшеты +
    Запоминание выбранных пунктов горизонтального меню на страницах Героя и Сравнения +
    Баг с картинкой уровня в рекомендациях +
    Дублирующий источник изображений на подконтрольном сервере  +
    Иконки героев-лидеров по атрибутам на страницу Героя  +
    Обновления только для залогиненных в Steam +
    Передавать на сервак Steam ID +
    Записывать на серваке Steam ID и дату обращения +
    Перед отдачей вычислять разницу между временем предыдущего обращения и настоящим моментом +
    Отдавать данные только в том случае, если разница составила более 10 сек +
    Оформить SSL +
    Запоминание геров в сравнении +
    Рейт атрибута в виде столбца +
    Переименовать "побед/выборов" в "вин рейт" +
    Хинт в поле поиска +
    Переверстать списки данных +
    Поменять иконки +
    Звёздочки у избранных +
    Значок поиска в поле для ввода +
    Группировка данных на страницах Героя и Сравнения +
    Возврат к предыдущей странице после выбора уровня +
    Альтернативный источник данных +
    Написать cron-скрипт обновления альтернативного источника +
    Баовое подключение Qonversion +
    Рекомендации по уровням +
    Сравнялка +
    Кэш иконок на главной +
    Переименовать package +
    Подключить Firebase +
    Иконка на эмуляторе (квадрат) +
    Helpers (состояния, события, перечень экранов) перенести к экранам +
    Убрать кнопки "Назад" и переделать вёрстку шапок, где необходимо +
    Кнопка "Удаления" из избранного +
    Листалка скролла +
    Русский язык +
    Прижатие изображения на странице героя +
    Исключить дубликаты при генерации списков +
    Избавиться от stickyHeader +
    Атрибуты во FLowRow +
    Фон сделать чёрным +
    Заглушки под изображения +
    Кнопка обновления данных +
    Заблокировать Landscape +
    Посмотреть developer.huawei.com +
    Название (Dota Heroes Comparing DoHeСo) +
    Аккаунт Huawei App Gallery +
    Модуль клиент +
    Карточка героя +
    Размер картинок +
    Двойное нажатие в навигации +
    Карточка атрибута +
    Перенос расчётов артибутов +
    Расчёты в UseCase +
    Репозиторий во viewModel через UseCase +
    Избранные герои вместо Dashboard +
    Профиль вместо Notifications +
    Основы дизайна +
    Авторизация и определение уровня пользователя +
    Страница уровней +
    Возможность обновить данные вручную после установки +

*/

/*

    Архитектура
    data
    contracts (названия таблиц и столбцов в БД)
    converters (Roles List <-> Roles String)
    dao (база данных, запросы)
    domain
    di (модули для ktor)
    model (дата классы: героя, избранного, пользователей и т.п.)
    repository (запросы: в БД, Steam,  OpenDota, Dotabuff и т.п.)
    useCases (добавить в избранное, получить списки героев, данные о пользователе и т.п.)
    utils (вспомогательные конструкции)
    presentation
    activity
    screens
    theme

*/

/* Разное

+ Coil Cache
+ https://dash.qonversion.io/monitoring?project=vkGq2TLJ&environment=0&realTimeMode=false
+ MVP / MVVM (см. ниже)
+ Single Responsibility - класс должен иметь только одну причину для изменений
+ Open|Closed - класс должно быть легко дополнить
+ Liskov substitution: если x из T, y из S, а T -> S, то q(x) -> q(y)
+ ISP - Interface Segregation Principle
+ DIP - Dependency Inversion Principle
+ MVI - под Intent имеется в виду нечто подобное содержимому HeroViewModel.reduce()? см. q2
+ Test

+ Как разбить contracts  по фичам (и для чего это?) ГЛЯНУТЬ ПРИМЕРЫ
+ Как сохранять файлы на телефон? и стоит ли
https://coil-kt.github.io/coil/image_loaders/#caching
Composition Local
+  Как приступить к написанию тестов? Например, как протестировать кнопку "Обновить" (?)
https://developer.android.com/jetpack/compose/testing
Firebase Тестирование
+ Как интегрируется монетизация обновлений?
Billing Библиотеки
+  Отражение картинки
https://github.com/Commit451/coil-transformations
https://coil-kt.github.io/coil/transformations/
+ Бизнес аналитика
Firebase Log Event
https://www.youtube.com/watch?v=9-emL-Fbvp0
+ Куда опубликовать? Huawei
+ Что показывать вместо пустого списка избранных? (добавить быстрое удаление, арт в центр экрана)
+ Безопасность exoplayer +
+ Дизайн под планшеты (?)
+ Какими данные могут быть "чувствительными" (пароль)?
+ Как передавать пароль (md5 не вариант)?
+ Как учесть кражу мобилы? (привязка устройств на стороне сервиса)
+ Как обойти снифферы? Оконечное шифрование!
+ Как обойти получение ссылки на видео? Blob(?) blob:// Класть видео в базу, сторонние сервисы

LocalConfiguration.current  (width, height)
1. получаю ширину
2. узнаю текущий элемент (scrollState)
3. переменную делаем mutableState
4. swipeable модификатор

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

/* MVP

data
    contracts (названия таблиц и столбцов в БД) | Model
    converters (Roles List <-> Roles String) | Model
    dao (база данных, запросы) | Model
domain
    di (модули для ktor)  | Model
    model (дата классы: героя, избранного, пользователей и т.п.)  | Model
    repository (запросы: в БД, Steam,  OpenDota, Dotabuff и т.п.)  | Model
    useCases (добавить в избранное, получить списки героев, данные о пользователе и т.п.) | Presenter
    utils (вспомогательные конструкции)  | Model
presentation
    activity  | View
    screens | View
    theme | View

    Если придерживаться MVP, то в ответ на действия пользователя - для получения результатов этих действий
    и синхронизации представления: View классы использовали бы Presenter классы, которые а) реализовали бы логику
    использования методов Model классов, б) управляли бы представлением методами интерфейсов View классов

*/

/* MVVM

data
    contracts (названия таблиц и столбцов в БД) | Model
    converters (Roles List <-> Roles String) | Model
    dao (база данных, запросы) | Model
domain
    di (модули для ktor)  | Model
    model (дата классы: героя, избранного, пользователей и т.п.)  | Model
    repository (запросы: в БД, Steam,  OpenDota, Dotabuff и т.п.)  | Model
    useCases (добавить в избранное, получить списки героев, данные о пользователе и т.п.) | Model
    utils (вспомогательные конструкции)  | Model
presentation
    activity | View
    screens  | View & ViewModel
    theme | View

    Т.к. придерживаемся MVVM, то в ответ на действия пользователя - для получения результатов этих действий
    и синхронизации представления: View классы используют ViewModel классы а) реализующие логику
    использования методов Model классов и б) управляющие своим состоянием. Управление представления
    остаётся у View классов, которые в своих алгоритимах используют детерминированные (определённые заранее)
    результаты наблюдения (observeAsState) за состоянием ViewModel

*/

/* Ссылки:
https://foso.github.io/Jetpack-Compose-Playground/material/divider/
https://startandroid.ru/ru/blog/493-mvp-dlja-nachinajuschih-bez-bibliotek-i-interfejsov.html
https://coil-kt.github.io/coil/image_loaders/#caching
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
/* HomeView:
    when(configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            listColumnsCount = 7
            listBannerHeight = 480.dp
            lazYColumnSpaceBoxHeight = 480.dp
        }
    }
*/
/*
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    var listBannerHeight: Dp = 240.dp
    painter = rememberAsyncImagePainter(imageAddr)
    val localCurrentContext = LocalContext.current

     AsyncImage(model = ImageRequest.Builder(LocalContext.current)
        .data(hero.icon)
        .crossfade(true)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build(),
         contentDescription = hero.name,
           modifier = Modifier
          .width(70.dp)
         .height(35.dp),

      )


viewModel.saveHeroImage(request)

AsyncImage(
model = hero.icon,
modifier = Modifier
                                                .width(70.dp)
                                                .height(35.dp),
                                            contentDescription = hero.name,

                                        )
Image(
                                        rememberAsyncImagePainter(
                                            remember(hero.icon) {
                                                ImageRequest.Builder(localCurrentContext)
                                                    .data(hero.icon)
                                                    .diskCacheKey(hero.icon)
                                                    .memoryCacheKey(hero.icon)
                                                    .diskCachePolicy(CachePolicy.ENABLED)
                                                    .build()
                                            }
                                        ),
                                        hero.name
                                    )

                                    val request = ImageRequest.Builder(localCurrentContext)
                                        .data(hero.icon)
                                        .diskCacheKey(hero.icon)
                                        .target({AsyncImage(
                                            model = hero.icon,
                                            contentDescription = hero.name,
                                            modifier = Modifier
                                                .width(70.dp)
                                                .height(35.dp)
                                    )})
                                        .size(Size.ORIGINAL)
                                        .build()
                                    imageLoader.enqueue(request)
*/
/*  var flowRowWidth = 170.dp
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            flowRowWidth = 400.dp
        }
    }
*/
/*
    fun registerFirebaseEvent() {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Test")
        Log.e(
            "TOHAFB",
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle).toString()
        )
    }

 */
/*

private fun updateHeroesByDefinedUser() {

    appSharedPreferences.edit().putString("heroes_update_status", "wait").apply()

    try {
        _profileState.postValue(
            ProfileState.SteamNameIsDefinedState(
                getPlayerTierFromSP(),
                getPlayerSteamNameFromSP(),
                getHeroesBaseLastModifiedFromSP(),
                getUpdateBtnText()
            )
        )
    } catch (e: Exception) {
        _profileState.postValue(ProfileState.ErrorProfileState)
    }

    viewModelScope.launch(Dispatchers.IO) {

        var UUId = UUIdSPUseCase.GetSteamUUIdFromSP(appSharedPreferences)
        if (UUId.isEmpty())
            UUId = UUIdSPUseCase.GetAppUUIdFromSP(appSharedPreferences)

        try {
            Log.e("PROFILE", "UUId: $UUId")
            val matches = matchesUseCase.getMatches(UUId, getHeroByIdUseCase)
            matchesUseCase.updateFromDb(matches)
            val matchesFromDb = matchesUseCase.getFromDb()
            //HEROES API CALL
            val heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi(UUId)
            if (heroes.isEmpty()) {
                Log.e("TOHA.2", "isEmpty")
                appSharedPreferences.edit().putString("heroes_update_status", "time")
                    .apply()
                try {
                    _profileState.postValue(
                        ProfileState.SteamNameIsDefinedState(
                            getPlayerTierFromSP(),
                            getPlayerSteamNameFromSP(),
                            "time",
                            getUpdateBtnText(),
                            matchesFromDb
                        )
                    )
                } catch (e: Exception) {
                    _profileState.postValue(ProfileState.ErrorProfileState)
                }
            } else {
                Log.e("TOHA.2", "!isEmpty")
                appSharedPreferences.edit().putString("heroes_update_status", "updated")
                    .apply()
                val currTime = Date(System.currentTimeMillis()).time
                appSharedPreferences.edit()
                    .putLong("heroes_list_last_modified", currTime)
                    .apply()
                addHeroesUserCase.addHeroes(heroes)
                try {
                    _profileState.postValue(
                        ProfileState.SteamNameIsDefinedState(
                            getPlayerTierFromSP(),
                            getPlayerSteamNameFromSP(),
                            currTime.toString(),
                            getUpdateBtnText(),
                            matches
                        )
                    )
                } catch (e: Exception) {
                    _profileState.postValue(ProfileState.ErrorProfileState)
                }
            }

        } catch (e: java.lang.Exception) {
            Log.e("TOHA", "e:$e")
            e.printStackTrace()
        }

    }
}

private fun tryToSetUUIdByPlayerId() {
    //var PlayerId = getPlayerIdFromSP.getPlayerIdFromSP(appSharedPreferences)

    //if(!PlayerId.isEmpty()){
    //    UUIdSPUseCase.SetAppUUIdToSP(appSharedPreferences,PlayerId)
    //}
}
*/

/*
    private fun getUpdateBtnText(): String {

        var result = ""

        val updateStatus =
            appSharedPreferences.getString("heroes_update_status", "updated").toString()

        if (updateStatus == "error") {
            result = getResource.getString(id = R.string.error)
        }

        if (updateStatus == "wait") {
            result = getResource.getString(id = R.string.wait)
        }

        if (updateStatus == "time") {
            result = if (appSharedPreferences.getString("player_steam_name", "undefined")
                    .toString() == "undefined"
            ) {
                getResource.getString(id = R.string.time_block)
            } else {
                getResource.getString(id = R.string.time_block2)
            }
        }

        if (updateStatus == "updated") {

            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
            val currUpdateDate = appSharedPreferences.getLong("heroes_list_last_modified", 0)
            val formatedDateTime = simpleDateFormat.format(currUpdateDate).toString()

            result =
                getResource.getString(id = R.string.heroes_list_last_modified) + " (" + formatedDateTime + ")"
        }

        return result
    }

*/

/*
    private fun getHeroesBaseLastModifiedFromSP(): String {
        return getHeroesBaseLastModifiedFromSPUseCase.getHeroesBaseLastModifiedFromSPUseCase(
            appSharedPreferences
        )
    }

 */