
# 📰 News App

This application allows users to search for news articles using the NewsAPI and view details of each article. Users can also bookmark articles to view later.

## 🏆 Features
 - Search: Users can search for news articles by entering keywords in the search bar.
 - Article Details: Users can view detailed information about each article, including the title, description, date, and source.
 - External Link: Users can visit the source website of an article by clicking on the "Go to Source" button.
 - Bookmarking: Users can bookmark articles to save them for later viewing.
 - Favorites Tab: Users can view their bookmarked articles in the Favorites tab.
 - Share: Users can share article links with others.
 - Swipe to delete items: Users can delete saved news articles by swiping left or right on the item in the Saved News page.


## 👩🏻‍💻 Tech Stack

[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) : The ViewModel class is a business logic or screen level state holder. It exposes state to the UI and encapsulates related business logic.

[Coroutines](https://developer.android.com/kotlin/coroutines) : Coroutines are used for asynchronous programming to perform network requests and database operations efficiently.

[Retrofit](https://github.com/square/retrofit): Retrofit is used as the HTTP client for making API requests to NewsAPI.

[Glide](https://github.com/bumptech/glide): Glide is used for image loading and caching to display article images efficiently.

[LiveData](https://developer.android.com/topic/libraries/architecture/livedata): LiveData is used to observe changes in data and update the UI accordingly.

[JUnit](https://junit.org/junit5/): JUnit is used for unit testing to ensure the correctness of individual components.

[MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver): MockWebServer is used for mocking HTTP responses in unit tests.

[Truth](https://truth.dev/): Truth is used for assertions in unit tests to make test code more readable and maintainable.

[Flow](https://developer.android.com/kotlin/flow): Flow is used for handling streams of data asynchronously and reactively.

[Hilt](https://developer.android.com/training/dependency-injection/hilt-android): Hilt is used for dependency injection to manage dependencies in a modular and maintainable way.

[ViewBinding](https://developer.android.com/topic/libraries/view-binding): ViewBinding is used to replace findViewById for accessing views in the layout XML files.

[Jetpack Navigation](https://developer.android.com/guide/navigation): Navigation Component is used for implementing navigation between fragments.

[DiffUtil](https://developer.android.com/reference/androidx/recyclerview/widget/DiffUtil) : DiffUtil is used for calculating the difference between two lists efficiently, particularly in RecyclerView adapters.

[Skeleton Loading](https://github.com/Faltenreich/SkeletonLayout) : Skeleton Layout is used to show loading placeholders while content is loading.

[Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) : Paging library helps you load and display pages of data from a larger dataset from local storage or over a network. 

[Room](https://developer.android.com/training/data-storage/room) : The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.


## 📱 Screenshots
### The logo used in this application has been created by AI.
| Splash       | Skeleton Loading         | Feed       |
| ------------- |:-------------:| -----:|
| <img src="screenshots/splash.png" width="300px"> </img>      | <img src="screenshots/skeleton.png" width="300px"> </img> |<img src="screenshots/feed.png" width="300px"> </img> |



| Article Details        | Share           | Webview   |
| ------------- |:-------------:| -----:|
| <img src="screenshots/details.png" width="300px"> </img>      | <img src="screenshots/share.png" width="300px"> </img> |<img src="screenshots/webview.png" width="300px"> </img> |


| Bookmarks        | Empty Bookmarks           | Search
| ------------- |:-------------:| -----:|
| <img src="screenshots/savedScreen.png" width="300px"> </img>      | <img src="screenshots/emptySaved.png" width="300px"> </img> |<img src="screenshots/search.png" width="300px"> </img> |


## 🔒 Security

This application incorporates the following security measures to ensure the safety of sensitive data:

Storage of Secret Keys: Secret keys are stored in the gradle.properties file of the application. This file is treated with special care within the project.

Usage of Proguard: The application is compiled using Proguard. This is a measure for code optimization and obfuscation, aiding in the protection of sensitive information from malicious use.
