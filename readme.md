# Studio Ghibli Movies

![Totoro image](./screenshots/icon.png)

## Index
1. About this app
2. Folder Structure
3. Libraries used
4. Tools used


## About this app
This app is a simple Android application which consumes a public API to fetch all the Studio Ghibli movies. The main pruporse of this app is improve my very own skills in Android Development ecosystem. The app is built following the  MVVM, repository pattern and multimodule architecture.


## Modules
The app is implemented with multi module architecture. The modules are the following:
```
:app
:data
:domain
:core:test
:core:coroutines
```

* **:app** Contains all the UI (Activity and Fragment) including the ViewModel and UIStates.

* **:data** Contains all the clases responsables of handle data between datasources (local and remote)

* **:domain** Contains all the UseCases. Is the most separated layer of the app.

* **:core:test** Contains the common resources used to execute test.

* **:core:coroutines** Contains DI for CoroutineContext to inject in different classes.


## Libraries used to build this app
This app is made with the following libraries:

* **Hilt:** dependency injection

* **Coil:** image loader

* **Room:** local database

* **Retrofit + Moshi:** networking

* **AndroidX lifecycle:** coroutines with lifecycle aware

* **Firebase:** analytics, crashlytics

* **Google Material 3:** theming


## Tools used to build this app
To build this app, I used the following tools / resources:

* Material Design 3: https://m3.material.io

* Material Symbol (icons): https://fonts.google.com/icons?icon.platform=web

* Material theme generator: https://m3.material.io/theme-builder#/custom

* Totoro Gifs: https://mott.pe/noticias/estos-gifs-de-totoro-haciendo-ejercicio-te-sacaran-del-sedentarismo/
