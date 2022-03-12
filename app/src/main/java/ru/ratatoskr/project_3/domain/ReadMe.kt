package ru.ratatoskr.project_3.domain

/*

Вопросы:
    ошибки в e-логе

    2022-03-12 15:47:40.333 207-210/? E/android.system.suspend@1.0-service: Error opening kernel wakelock stats for: wakeup35: Permission denied

    Модуль клиент +
    Карточка героя +
    Размер картинок +
    "Двойное нажатие" в навигации +
    Карточка атрибута +
    Перенос расчётов артибутов +-
    Расчёты в UseCase +
    Репозиторий во viewModel через UseCase +
    Избранные герои вместо Dashboard
    Профиль вместо Notifications
    Дизайн
    чувствительные данные (пароль)
    как передавать пароль (md5 не вариант)
    как обойти кражу мобилы
    как обойти снифферы (трафик может быть прослушан)
    UseCase может объединять два репозитория
*
    https://github.com/AlexGladkov/JetpackComposeDemo/blob/main/app/src/main/java/ru/alexgladkov/jetpackcomposedemo/screens/daily/DailyViewModel.kt
    https://github.com/AlexGladkov/JetpackComposeDemo/blob/main/app/src/main/java/ru/alexgladkov/jetpackcomposedemo/screens/daily/DailyScreen.kt

К консультации:

    enum
    Архитектура (репозитории, юзкейсы и т.д.)
    база данных

* K3
* viewState,    action,    event -> ViewModel -> viewState -> compose,
*
* Книги:

    Карьера программиста крэкинг зэ пот
    Java Concurrency in Practiсe


*
* */

class ReadMe