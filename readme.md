# Studio Ghibli Movies

## Index
1. About this app
2. Folder Structure
3. Libraries used
4. Tools used


## About this app
This app is a simple Android application which consumes a public API to fetch all the Studio Ghibli movies. The main pruporse of this app is improve my very own skills in Android Development ecosystem.

## Folder Structure
The app has the following folder structure:
```
|- data
|  |- model
|  |- repository
|- domain
|- framework
|- ui
|- utils
|- widget
```

* **data:** Contains all the data of the application. It contains two sub-folders:
    
    * **model:** Contains the local, remote and domain model objects.

    * **repository:** Contains the repositories classes. Here is the logic to transfer data between local, remote, or mock data sources.

* **domain:** Contains all the use cases.

* **framework:** Contains the configurations and managers used by the external frameworks / libraries.

* **ui:** Contains all the UI (Activity and Fragment) including the ViewModel and UIStates.

* **utils:** Contains tools to use with the app.

* **widget:** Contains the custom view that we have created.


## Libraries used to build this app
This app is made with the following libraries:

* **Hilt:** dependency injection

* **Coil:** image loader

* **Retrofit + Moshi:** networking

* **AndroidX lifecycle:** coroutines with lifecycle aware

* **Google Material 3:** theming


## Tools used to build this app
To build this app, I used the following tools / resources:

* Material Design 3: https://m3.material.io

* Material Symbol (icons): https://fonts.google.com/icons?icon.platform=web

* Material theme generator: https://m3.material.io/theme-builder#/custom

* Totoro Gifs: https://mott.pe/noticias/estos-gifs-de-totoro-haciendo-ejercicio-te-sacaran-del-sedentarismo/