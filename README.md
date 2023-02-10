<br/>
<p align="center">
    <img src="https://user-images.githubusercontent.com/58399384/194954659-5b43e8a6-2168-4cfa-8153-6dc9f4dfe119.png" alt="Logo" width="80" height="80">
  </a>
  <h3 align="center">Travel App</h3>
  <p align="center">
    Where's your next destination?
    <br/>
    <br/>
  </p>
</p>



## Table Of Contents

* [About the Project](#about-the-project)
* [Preview](#preview)
* [Technologies Used](#technologies-used)
* [Roadmap](#roadmap)

## About The Project

Travel app is your guide on your journey around the world. You can discover new places and plan your next trip with this application. This project is also graduation project of Patika-FMSS bootcamp. 

## Preview


| Register | Login | Home | Search | Trips | Bookmarks | Guide and Detail |
| ------------ | ------------ | ------------ | ------------ | ------------ | ------------ | ------------ |
|  <img src="https://user-images.githubusercontent.com/58399384/194963689-11cb34f3-781f-45b2-9f5b-2b53badde9f5.gif" width="250" height="540" /> | <img src="https://user-images.githubusercontent.com/58399384/194963721-bc549b4a-485d-424d-b05c-03f17febf090.gif" width="250" height="540" /> | <img src="https://user-images.githubusercontent.com/58399384/194964140-eec8e029-0129-43a9-84f2-294f4f13a0f6.gif" width="250" height="540" /> |   <img src="https://user-images.githubusercontent.com/58399384/194963777-d5db1cbc-e04f-4834-b6e0-f6146f2da176.gif" width="250" height="540" /> |   <img src="https://user-images.githubusercontent.com/58399384/194963796-61bce097-a397-48d5-b681-a89e2295c8f8.gif" width="250" height="540" /> |   <img src="https://user-images.githubusercontent.com/58399384/194963810-99c53217-73bf-4d7d-a553-7fdbce93ece7.gif" width="250" height="540" /> |   <img src="https://user-images.githubusercontent.com/58399384/194963826-1072c0a7-653e-464b-a797-d89394932fa6.gif" width="250" height="540" /> | 

* First screen is splash screen which displays an animation
* For login&register,Firebase is being used
* Home screen stores deals adapter where you can categorize deals as flight,hotels and transportation
* In addition to TopDestinations and Nearby Attractions, Search screen contains search bar where you can find items. Search bar navigates to Search Result and Search History screens. Search history data is being stored with RoomDB.
* In Trip tab item of Trip Plan screen you can plan your next trip. Planned trip data is being stored with shared preferences.
* In bookmark screen you can observe and remove your bookmarked items. Bookmark status of items are stored and updated in API.
* Guide screen displays Must-See and Places To Go Before You Die categories
* Detail screen displays items' images,location and description. Like search and guide screens you can (un)bookmark your item here too.

## Technologies Used

* Kotlin
* MVVM Architecture
* Dagger-Hilt
* Coroutine
* Retrofit (GET-PUT)
* RoomDB
* Firebase
* Shared Preferences
* ViewModel - LiveData
* Navigation Component
* Databinding
* Recyclerview
* Glide
* Lottie
* Datepicker



## Roadmap

This part represents features that may or may not be implemented in the future.

* User Screen
* Settings Screen
* Language Support
