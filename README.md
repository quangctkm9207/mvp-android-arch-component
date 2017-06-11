## MVP with Android architecture components

An Android project with MVP pattern which adapts with new Android architecture components introduced in Google IO 2017.    

It provides lifecycle-aware Presenter with support of LifeCycle and local data source handled by Room (an abstraction layer over SQLite).


This demo project uses StackExchange API as a remote data source.
Also, it covers basic and general tasks which most Android apps deal with.

Frameworks and libraries:  
* Android architecture components:  
  1. [Room](https://developer.android.com/reference/android/arch/persistence/room/package-summary.html).
  2. [Lifecycle](https://developer.android.com/reference/android/arch/lifecycle/package-summary.html).
* [RxJava 2](https://github.com/ReactiveX/RxJava)
* [Dagger 2](https://github.com/google/dagger)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Timber](https://github.com/naman14/Timber)
* [Retrofit](https://github.com/square/retrofit) + [OkHttp](https://github.com/square/okhttp)
* UI:
  1. RecyclerView
  2. CardView
  3. ConstraintLayout
