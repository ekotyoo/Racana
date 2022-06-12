<h1 align="center">Racana Android App</h1>

<p align="center">  
🗡️ An Android application for Racana project, built with Jetpack (Compose, ViewModel, Datastore), Hilt, Coroutines, Flow based on MVVM architecture.
</p>
</br>

<p align="center">
<img src="hero.jpg"/>
</p>

## Tech stack & Open-source libraries

- Minimum SDK level 21
- [100% Kotlin](https://kotlinlang.org/), [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- Jetpack
  - Compose - Android's modern toolkit for building native UI.   
  - ViewModel - Manages UI-related state and event which is lifecycle aware.
  - Datastore - Save key-value data locally.
- Architecture
  - MVVM Architecture
  - Repository Pattern
- [Google Maps SDK](https://developers.google.com/maps/documentation/android-sdk/overview) - Provide map and location service for Android App.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Landscapist](https://github.com/skydoves/landscapist) -  Jetpack Compose image loading library which fetches and displays network images with Glide, Coil, and Fresco
- [Lottie](https://github.com/airbnb/lottie/) - Display lottie animation in Jetpack Compose UI.
- [ComposeCalendar](https://github.com/boguszpawlowski/ComposeCalendar) - A Jetpack Compose library for handling calendar component rendering.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API.

## Getting Started

To use this app follow these simple example steps.

### Installation

1. Download the .apk file.
2. Copy the file to your Androi phone.
3. Install the app and enjoy!

or

1. Clone `https://github.com/ekotyoo/Racana`.
2. Checkout to `racana-mobile` branch.
3. Git pull.
4. Run the app.

## App Architechture
Racana Android App built using MVVM Architecture and Repository Patten.
![racana-flow (1)](https://user-images.githubusercontent.com/70650963/173214848-4564c880-be0a-411f-a713-ea48d4364820.png)

## Features
<a href="https://github.com/ekotyoo/Racana">
    <img src="overview-animation.gif" alt="Animation" width="25%" height="25%">
</a>

* Authentication using JWT token.
* Generate itinerary based on your budget preferences.
* Save and customize itinerary.
* View your tour route in map mode.
* Explore beautiful destinations in Bali.


