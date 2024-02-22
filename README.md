# GamersHub
This mobile application allows users to explore games based on different genres. Users can view a list of available genres and select any genre of interest. Upon selecting a genre, the app displays a list of games belonging to that genre.

## Features

- View a list of genres available
- Select a genre to view games within that genre
- Explore details of each game including name, description, and other relevant information
- Clean architecture following MVVM pattern
- Integration with Hilt for dependency injection
- Network requests handled using Retrofit
- Local storage managed with Room Database
- Asynchronous operations managed using Coroutines
- SharedPreferences to track guest user status
- Navigation managed through NavGraph
- BaseViewModel for Error Handling
- BaseFragment for Error Message Dialog
- Pagination



  The design decisions were made with user experience, maintainability, and scalability in mind:

1. View a list of genres available: Providing users with a clear overview of available genres allows for easy navigation and exploration of different types of games. This enhances user engagement and satisfaction by catering to various preferences.
2. Select a genre to view games within that genre: Enabling users to filter games by genre enhances content discoverability and ensures a more personalized experience.
3. Explore details of each game including name, description, and other relevant information: Offering comprehensive information about each game fosters user engagement and informed decision-making. 
4. Clean architecture following MVVM pattern: Adopting the MVVM (Model-View-ViewModel) architectural pattern promotes separation of concerns, making the codebase more modular, testable, and maintainable.
5. Integration with Hilt for dependency injection: Utilizing Hilt for dependency injection simplifies the management of dependencies and reduces boilerplate code.
6. Network requests handled using Retrofit: Retrofit offers a robust and efficient way to handle network requests, providing features such as type-safe HTTP client generation and automatic serialization/deserialization.
7. Local storage managed with Room Database: Room Database provides a convenient and efficient way to store and manage app data locally.
8. Asynchronous operations managed using Coroutines: Coroutines offer a concise and intuitive way to handle asynchronous programming tasks in Kotlin. 
9. SharedPreferences serve as a lightweight tool for storing minimal user data, perfectly suited for determining if the user is signed in as a guest.
10. Navigation managed through NavGraph: NavGraph facilitates the implementation of a structured and predictable navigation flow within the app.
11. BaseViewModel for Error Handling: Implementing a BaseViewModel allows you to centralize error handling logic, making it easier to manage errors across different view models.
12. BaseFragment for Error Message Dialog: By creating a BaseFragment that handles the display of error message dialogs.
13. Implementing pagination for loading more games.

## Tech Stack

- Kotlin
- Android Jetpack components
- Retrofit for network requests
- Hilt for dependency injection
- Coroutines for asynchronous programming
- SharedPreferences for managing user preferences
- Authentication options: Users can sign in as a guest or sign in with Google

## Screenshots
<div>
<img src="https://github.com/KatarinaB96/GamersHub/assets/106242222/8e556cff-ea38-49f1-8dae-07c522c5176d" width="300" height="600">
<img src="https://github.com/KatarinaB96/GamersHub/assets/106242222/9e0fb2ad-bdc7-4964-916e-0494cc36c7b4" width="300" height="600">
  <div>
<img src="https://github.com/KatarinaB96/GamersHub/assets/106242222/4e4b34e5-6947-4e5b-8f0d-c1e54e92fee0" width="300" height="600">
<img src="https://github.com/KatarinaB96/GamersHub/assets/106242222/ab41a0c6-6f39-40df-98e2-d52114c98085" width="300" height="600">
</div>
  
In opting for the dark theme, I aimed to maintain visual consistency with the webpage from which I sourced the API. 
Additionally, I placed the viewpager on the first screen to showcase the origins of the games.
When users click on a genre, I ensured that the text color and background dynamically change to highlight their selection, making it easily distinguishable and enhancing the overall user experience.

