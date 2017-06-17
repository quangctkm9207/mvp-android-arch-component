## MVP with Android architecture components

[![Build Status](https://travis-ci.org/quangctkm9207/mvp-android-arch-component.svg?branch=master)](https://travis-ci.org/quangctkm9207/mvp-android-arch-component)  

An Android project with MVP pattern which adapts with new Android architecture components introduced in Google IO 2017.    
It provides lifecycle-aware Presenter with support of LifeCycle and local data source handled by Room (an abstraction layer over SQLite).


This demo project uses StackExchange API as a remote data source.
Also, it covers basic and general tasks which most Android apps deal with.

### Articles
To go in details, please check the following blog post.  
(Updating...)
* [Android MVP with new Architecture Components](https://blog.mindorks.com/android-mvp-with-new-architecture-components-7627b7cb8491)

### Libraries:  
* Architecture Components:  
  * [Room](https://developer.android.com/reference/android/arch/persistence/room/package-summary.html).
  * [Lifecycle](https://developer.android.com/reference/android/arch/lifecycle/package-summary.html).
* [RxJava 2](https://github.com/ReactiveX/RxJava)
* [Dagger 2](https://github.com/google/dagger)
* [ButterKnife](https://github.com/JakeWharton/butterknife)
* [Timber](https://github.com/naman14/Timber)
* [Retrofit](https://github.com/square/retrofit) + [OkHttp](https://github.com/square/okhttp)
* UI: RecyclerView, CardView, ConstraintLayout.
* Testing:
  * [JUnit4](https://github.com/junit-team/junit4)
  * [Mockito](https://github.com/mockito/mockito)
  * [Hamcrest](https://github.com/hamcrest/JavaHamcrest)
  * [Android Testing Support](https://google.github.io/android-testing-support-library/)

### License
This project is available under the MIT license. See the LICENSE file for more info.