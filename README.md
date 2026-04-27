# citi-stock-dashboard
Live Dow Jones stock market dashboard built during Citi Technology Virtual Experience Program on Forage. Built with Java and JavaFX
# 📈 Citi Live Stock Dashboard

Built as part of the **Citi Technology Software Development 
Virtual Experience Program** on Forage — April 2026

---

## 📌 What This Application Does

- Queries the **Dow Jones Industrial Average (DJIA)** stock 
  price from Yahoo Finance every 5 seconds
- Stores each stock price and timestamp in a **Queue** 
  data structure
- Displays a **live updating line graph** using JavaFX
- Handles network errors gracefully with retry logic
- Runs as a real-time risk monitoring tool for internal use

---

## 🛠️ Technologies Used

- Java 26
- JavaFX 21
- Gradle
- Yahoo Finance API 3.17.0
- Object Oriented Programming
- Data Structures (Queue)
- REST API Integration

---

## 📂 Project Structureciti-stock-dashboard/
│
├── app/
│   ├── build.gradle.kts
│   └── src/
│       └── main/
│           └── java/
│               └── citi/
│                   └── App.java
└── README.md---

## 🚀 How to Run

1. Install Java JDK 17 or higher
2. Install Gradle
3. Clone this repository
4. Run the following command:

```bash
gradle run --no-configuration-cache
```

A JavaFX window will open showing the live stock dashboard!

---

## 📊 Features

| Feature | Description |
|---------|-------------|
| Live querying | Fetches DJIA price every 5 seconds |
| Queue storage | Stores all prices with timestamps |
| Line graph | Updates in real time with JavaFX |
| Error handling | Catches network errors gracefully |

---

