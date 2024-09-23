# Android Broadcast, Service, and Worker Project

This project demonstrates the use of Broadcast Receivers, Services, and Workers in an Android application. It consists of three main tasks, each focusing on different aspects of Android programming, including monitoring internet connectivity, Bluetooth/Airplane mode status changes, and displaying logs in a structured way.

## Project Objective
The main goal is to implement a simple Android application using broadcast receivers, services, and workers to monitor device states and display relevant information to the user.

## Key Features

### Part 1: Internet Connectivity Monitor
- **Description:** Uses a Broadcast Receiver and Service to monitor the device's internet connectivity status.
- **Functionality:** Displays a notification showing whether the device is connected or disconnected from the internet.
- **Implementation Details:** The notification updates dynamically as the internet connection status changes.
- **Branch:** `code_1`

### Part 2: Bluetooth and Airplane Mode Logger
- **Description:** Implements a periodic worker that runs at fixed intervals to monitor changes in Bluetooth and Airplane mode status.
- **Functionality:** Logs the current status of Bluetooth and Airplane mode every few minutes, storing the logs for later access.
- **Log Display:** Use `logcat` to display messages with the tag `worker_airplane`.
- **Branch:** `code_2`

### Part 3: Log Display with LazyColumn
- **Description:** Combines logs from the previous tasks and displays them in the app using a LazyColumn.
- **Functionality:** Displays logs in descending order by timestamp, ensuring that the most recent logs appear first.

## Technologies Used
- **Programming Language:** Kotlin
- **Android Components:** Broadcast Receiver, Service, Worker, Jetpack Compose (for LazyColumn)

