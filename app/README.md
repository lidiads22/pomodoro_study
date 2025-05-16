# Pomodoro Study App

This is a mobile app I worked on with Destiny that uses the Pomodoro technique to help users stay productive.
We added features like a focus timer, task manager with calendar view, and flashcards by category like Bio, CS, Business, etc. 
It also uses Firebase so each user can sign in and save their own tasks.

# Features

- Focus timer that follows Pomodoro sessions (25 min focus + 5 or 15 min breaks)
- Add/view tasks by selecting a date on the calendar
- Flashcards by subject with tap-to-reveal answers
- User login using Firebase Authentication
- Tasks saved per user in Firestore
- Simple UI with gradients and rounded cards

# How to Set It Up

# Prerequisites

- Android Studio installed
- Firebase account + project set up
- Internet access (for Firebase to work)

# Firebase Setup (Required)

1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create a new project and register your app (use package name: `com.example.pomodoro_study`)
3. Download the `google-services.json` file and move it to the `app/` folder
4. Enable Email/Password sign-in in Authentication
5. Create a Firestore DB and a collection called `users`. Inside that, each UID should have a subcollection called `tasks`.

# Run the App

1. Clone this repo or download the code
2. Open it in Android Studio
3. Sync Gradle
4. Run it on a device or emulator

# Files Overview

- `HomeDashboard.java` – main screen with hour buttons + flashcards
- `Pomodoro.java` – timer logic (focus + breaks)
- `TasksActivity.java` – add a task
- `todoList.java` – calendar + list of tasks
- `FlashcardActivity.java` – shows flashcards by subject
- `ProjectDatabaseHelper.java` – local SQLite DB for flashcards

# Improvements I’d Like to Add

- Let users add their own flashcards
- Use an API to fetch flashcards for more subjects
- Add notification reminders for tasks
- Make flashcards visually more customizable
