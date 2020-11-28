# Weather

Weather application lets users take a photo, add current weather informationas and share it.
* for cheking the project please checkout develop Branch

## Application

This app using the following views and techniques:

* MVVM as Architecture for the project
* [Retrofit](https://square.github.io/retrofit/) to make api calls to an HTTP web service
* [GSON](https://github.com/google/gson) which handles the deserialization of the returned JSON to Kotlin data objects
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [Data Binding](https://developer.android.com/topic/libraries/data-binding/) with binding adapters
* [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) with the SafeArgs plugin for parameter passing between fragments
* SharedPreference to save data
* google-play-services-location to retrive current location
