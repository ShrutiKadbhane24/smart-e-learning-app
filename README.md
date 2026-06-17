# Smart E-Learning App

A full-stack e-learning system with an Android client, a PHP REST-style backend, and a MySQL database. Students can register/login, view their class's study material, homework, recordings, notice board, question papers, and quizzes. An admin panel lets staff manage classes and content.

## Project structure

```
.
├── android-app/    Android Studio project (Java), package com.mountreachsolution.e_learningapp
├── backend-api/     PHP API consumed by the app (place inside your web server's document root)
└── database/        MySQL schema + seed data
```

## How the pieces connect

- The Android app calls endpoints defined in `android-app/app/src/main/java/com/mountreachsolution/e_learningapp/Comman/Urls.java`, all pointing at a base path of `e_learningapi/`.
- Every PHP script in `backend-api/` connects to a MySQL database named `e_learningdb` (see `backend-api/database.php`).
- `database/e_learningdb.sql` creates and seeds that exact `e_learningdb` database with the tables the PHP code queries (`class`, `login_admin`, `register_student`, `notice_board`, `study_material`, `tbl_homework`, `tbl_quiz`, `tbl_recording`, `question_and_answer_paper`).

All three folders are required together to run the app end-to-end.

## Setup

### 1. Database
1. Install MySQL/MariaDB (e.g. via XAMPP or WAMP).
2. Create a database named `e_learningdb`.
3. Import `database/e_learningdb.sql` into it (via phpMyAdmin's Import tab, or `mysql -u root e_learningdb < database/e_learningdb.sql`).

### 2. Backend API
1. Copy the `backend-api/` folder into your web server's document root as `e_learningapi` (e.g. `C:\xampp\htdocs\e_learningapi` on Windows/XAMPP).
2. Check `backend-api/database.php` and `backend-api/Admin/database.php` — by default they connect as `mysqli_connect("localhost","root","","e_learningdb")`. Update the username/password if your local MySQL setup differs.
3. Start Apache + MySQL (e.g. via the XAMPP control panel) and confirm `http://localhost/e_learningapi/getAllClasses.php` returns JSON.

### 3. Android app
1. Open `android-app/` in Android Studio.
2. Edit `app/src/main/java/com/mountreachsolution/e_learningapp/Comman/Urls.java` and replace the IP `192.168.1.6` with:
   - `10.0.2.2` if testing on the Android emulator (this is the emulator's alias for your host machine), or
   - your computer's actual LAN IP if testing on a physical device on the same Wi-Fi network.
3. Build and run.

## Default admin login

From the seed data in `database/e_learningdb.sql`:
- Username: `admin`
- Password: `admin`

(Change this before using the app for anything beyond local testing — credentials are stored in plain text in the current schema.)

## Notes / known gaps

- `backend-api/Admin/` includes a few scripts (`getAllWorker.php`, `deleteWorker.php`, `getAllComplaintReport.php`, `updateComplaintProgressandWorker.php`) that reference `worker`/`complaint` tables not present in the current SQL dump. These may be leftovers from a template and are likely non-functional until that schema is added — safe to ignore unless you plan to build out that feature.
- `local.properties`, `.idea/`, `.gradle/`, and `app/build/` are intentionally excluded (see `.gitignore`) since they're machine-specific or regenerated automatically by Android Studio/Gradle.
