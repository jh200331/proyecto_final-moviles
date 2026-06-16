package com.example.proyecto_final

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyecto_final.learning.GamificationHelper
import com.example.proyecto_final.learning.LearningContent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ProgressRecord(
    val moduleId: Int,
    val moduleName: String,
    val modulePercent: Int,
    val evaluationPercent: Int,
    val simulationPercent: Int,
    val xp: Int
)

data class RewardRecord(
    val id: String,
    val moduleId: Int?,
    val name: String,
    val description: String,
    val unlocked: Boolean,
    val unlockedAt: String?
)

data class LessonEvaluationResult(
    val lessonId: Int,
    val moduleId: Int,
    val score: Int,
    val totalQuestions: Int,
    val passed: Boolean,
    val attempts: Int
)

data class SimulationResult(
    val scenarioId: Int,
    val moduleId: Int,
    val score: Int,
    val passed: Boolean,
    val attempts: Int
)

class ProgressDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun onCreate(db: SQLiteDatabase) {
        createProgressTable(db)
        createUserProfileTable(db)
        createLessonProgressTable(db)
        createQuizResultsTable(db)
        createLessonEvaluationResultsTable(db)
        createSimulationResultsTable(db)
        createBadgesTable(db)
        createAchievementsTable(db)
        seedInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            createUserProfileTable(db)
            createLessonProgressTable(db)
            createQuizResultsTable(db)
            createBadgesTable(db)
            createAchievementsTable(db)
            seedLessonsAndGamification(db)
            seedUserProfile(db)
        }
        if (oldVersion < 3) {
            createLessonEvaluationResultsTable(db)
            createSimulationResultsTable(db)
        }
    }

    private fun createProgressTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS progress (
                module_id INTEGER PRIMARY KEY,
                module_name TEXT NOT NULL,
                module_percent INTEGER NOT NULL,
                evaluation_percent INTEGER NOT NULL,
                simulation_percent INTEGER NOT NULL,
                xp INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    private fun createUserProfileTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS user_profile (
                id INTEGER PRIMARY KEY DEFAULT 1,
                total_xp INTEGER NOT NULL DEFAULT 0,
                user_level INTEGER NOT NULL DEFAULT 1,
                level_title TEXT NOT NULL DEFAULT 'Novato Digital',
                certificate_issued INTEGER NOT NULL DEFAULT 0
            )
            """.trimIndent()
        )
    }

    private fun createLessonProgressTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS lesson_progress (
                lesson_id INTEGER PRIMARY KEY,
                module_id INTEGER NOT NULL,
                completed INTEGER NOT NULL DEFAULT 0,
                completed_at TEXT
            )
            """.trimIndent()
        )
    }

    private fun createQuizResultsTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS quiz_results (
                module_id INTEGER PRIMARY KEY,
                score INTEGER NOT NULL DEFAULT 0,
                total_questions INTEGER NOT NULL DEFAULT 10,
                passed INTEGER NOT NULL DEFAULT 0,
                completed_at TEXT
            )
            """.trimIndent()
        )
    }

    private fun createLessonEvaluationResultsTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS lesson_evaluation_results (
                lesson_id INTEGER PRIMARY KEY,
                module_id INTEGER NOT NULL,
                score INTEGER NOT NULL DEFAULT 0,
                total_questions INTEGER NOT NULL DEFAULT 5,
                passed INTEGER NOT NULL DEFAULT 0,
                attempts INTEGER NOT NULL DEFAULT 0,
                completed_at TEXT
            )
            """.trimIndent()
        )
    }

    private fun createSimulationResultsTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS simulation_results (
                scenario_id INTEGER PRIMARY KEY,
                module_id INTEGER NOT NULL,
                score INTEGER NOT NULL DEFAULT 0,
                passed INTEGER NOT NULL DEFAULT 0,
                attempts INTEGER NOT NULL DEFAULT 0,
                completed_at TEXT
            )
            """.trimIndent()
        )
    }

    private fun createBadgesTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS badges (
                badge_id TEXT PRIMARY KEY,
                module_id INTEGER,
                name TEXT NOT NULL,
                description TEXT NOT NULL,
                unlocked INTEGER NOT NULL DEFAULT 0,
                unlocked_at TEXT
            )
            """.trimIndent()
        )
    }

    private fun createAchievementsTable(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS achievements (
                achievement_id TEXT PRIMARY KEY,
                name TEXT NOT NULL,
                description TEXT NOT NULL,
                unlocked INTEGER NOT NULL DEFAULT 0,
                unlocked_at TEXT
            )
            """.trimIndent()
        )
    }

    private fun seedInitialData(db: SQLiteDatabase) {
        seedInitialProgress(db)
        seedLessonsAndGamification(db)
        seedUserProfile(db)
    }

    private fun seedInitialProgress(db: SQLiteDatabase) {
        AppData.modules.forEach { module ->
            val values = ContentValues().apply {
                put("module_id", module.id)
                put("module_name", module.title)
                put("module_percent", 0)
                put("evaluation_percent", 0)
                put("simulation_percent", 0)
                put("xp", 0)
            }
            db.insertWithOnConflict("progress", null, values, SQLiteDatabase.CONFLICT_IGNORE)
        }
    }

    private fun seedLessonsAndGamification(db: SQLiteDatabase) {
        LearningContent.allLessons().forEach { lesson ->
            val values = ContentValues().apply {
                put("lesson_id", lesson.id)
                put("module_id", lesson.moduleId)
                put("completed", 0)
            }
            db.insertWithOnConflict("lesson_progress", null, values, SQLiteDatabase.CONFLICT_IGNORE)
        }
        LearningContent.allModules().forEach { module ->
            val values = ContentValues().apply {
                put("badge_id", "badge_module_${module.id}")
                put("module_id", module.id)
                put("name", "Insignia: ${module.title}")
                put("description", "Completaste el módulo ${module.title}")
                put("unlocked", 0)
            }
            db.insertWithOnConflict("badges", null, values, SQLiteDatabase.CONFLICT_IGNORE)
        }
        listOf(
            Triple("first_lesson", "Primera lección", "Completaste tu primera lección"),
            Triple("first_quiz", "Primer cuestionario", "Aprobaste tu primer cuestionario"),
            Triple("three_modules", "Tres módulos", "Completaste 3 módulos"),
            Triple("all_modules", "Maestro CyberLearn", "Completaste los 6 módulos"),
            Triple("perfect_quiz", "Perfección", "Obtuviste 10/10 en un cuestionario"),
            Triple("password_guardian", "Guardián de contraseñas", "Completaste el módulo de Contraseñas Seguras"),
            Triple("phishing_detector", "Detector de phishing", "Completaste el módulo de Phishing"),
            Triple("safe_browser", "Navegante seguro", "Completaste el módulo de Navegación Segura"),
            Triple("data_keeper", "Protector de datos", "Completaste el módulo de Protección de Datos"),
            Triple("wifi_defender", "Defensor Wi-Fi", "Completaste el módulo de Redes Wi-Fi Seguras"),
            Triple("social_shield", "Escudo social", "Completaste el módulo de Ingeniería Social"),
            Triple("quiz_streak", "Racha de evaluaciones", "Aprobaste 3 cuestionarios"),
            Triple("curious_tap", "Curioso de logros", "Presionaste varias insignias para revisar tu progreso"),
            Triple("three_day_streak", "Racha de 3 días", "Estudia durante 3 días diferentes")
        ).forEach { (id, name, desc) ->
            val values = ContentValues().apply {
                put("achievement_id", id)
                put("name", name)
                put("description", desc)
                put("unlocked", 0)
            }
            db.insertWithOnConflict("achievements", null, values, SQLiteDatabase.CONFLICT_IGNORE)
        }
    }

    private fun seedUserProfile(db: SQLiteDatabase) {
        val values = ContentValues().apply {
            put("id", 1)
            put("total_xp", 0)
            put("user_level", 1)
            put("level_title", "Novato Digital")
            put("certificate_issued", 0)
        }
        db.insertWithOnConflict("user_profile", null, values, SQLiteDatabase.CONFLICT_IGNORE)
    }

    fun getProgress(): List<ProgressRecord> {
        val result = mutableListOf<ProgressRecord>()
        readableDatabase.query("progress", null, null, null, null, null, "module_id ASC").use { cursor ->
            while (cursor.moveToNext()) {
                result.add(
                    ProgressRecord(
                        moduleId = cursor.getInt(cursor.getColumnIndexOrThrow("module_id")),
                        moduleName = cursor.getString(cursor.getColumnIndexOrThrow("module_name")),
                        modulePercent = cursor.getInt(cursor.getColumnIndexOrThrow("module_percent")),
                        evaluationPercent = cursor.getInt(cursor.getColumnIndexOrThrow("evaluation_percent")),
                        simulationPercent = cursor.getInt(cursor.getColumnIndexOrThrow("simulation_percent")),
                        xp = cursor.getInt(cursor.getColumnIndexOrThrow("xp"))
                    )
                )
            }
        }
        return result
    }

    fun isLessonCompleted(lessonId: Int): Boolean {
        readableDatabase.query(
            "lesson_progress", arrayOf("completed"),
            "lesson_id = ?", arrayOf(lessonId.toString()),
            null, null, null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getInt(0) == 1
            }
        }
        return false
    }

    fun getCompletedLessonsCount(moduleId: Int): Int {
        readableDatabase.rawQuery(
            "SELECT COUNT(*) FROM lesson_progress WHERE module_id = ? AND completed = 1",
            arrayOf(moduleId.toString())
        ).use { cursor ->
            if (cursor.moveToFirst()) return cursor.getInt(0)
        }
        return 0
    }

    fun completeLesson(lessonId: Int, moduleId: Int): Boolean {
        if (isLessonCompleted(lessonId)) return false
        val now = dateFormat.format(Date())
        val values = ContentValues().apply {
            put("completed", 1)
            put("completed_at", now)
        }
        writableDatabase.update("lesson_progress", values, "lesson_id = ?", arrayOf(lessonId.toString()))

        val completed = getCompletedLessonsCount(moduleId)
        val percent = GamificationHelper.lessonPercent(completed)
        val current = getProgress().find { it.moduleId == moduleId }
        val newXp = (current?.xp ?: 0) + GamificationHelper.XP_PER_LESSON
        updateProgress(moduleId, percent, current?.evaluationPercent ?: 0, current?.simulationPercent ?: 0, newXp)
        addTotalXp(GamificationHelper.XP_PER_LESSON)

        checkModuleMastery(moduleId)
        checkAchievement("first_lesson") { getTotalCompletedLessons() >= 1 }
        checkCertificate()
        return true
    }

    fun saveQuizResult(moduleId: Int, score: Int, total: Int): Boolean {
        val previousResult = getQuizResult(moduleId)
        val passed = score >= GamificationHelper.QUIZ_PASS_THRESHOLD
        val now = dateFormat.format(Date())
        val values = ContentValues().apply {
            put("module_id", moduleId)
            put("score", score)
            put("total_questions", total)
            put("passed", if (passed) 1 else 0)
            put("completed_at", now)
        }
        writableDatabase.insertWithOnConflict("quiz_results", null, values, SQLiteDatabase.CONFLICT_REPLACE)

        if (passed && previousResult?.third != true) {
            val current = getProgress().find { it.moduleId == moduleId }
            val evalPercent = (score * 100 / total).coerceIn(0, 100)
            val newXp = (current?.xp ?: 0) + GamificationHelper.XP_PER_QUIZ_PASS
            updateProgress(
                moduleId,
                current?.modulePercent ?: 0,
                evalPercent,
                current?.simulationPercent ?: 0,
                newXp
            )
            addTotalXp(GamificationHelper.XP_PER_QUIZ_PASS)
            checkAchievement("first_quiz") { hasAnyPassedQuiz() }
            checkAchievement("perfect_quiz") { score == total }
            checkAchievement("quiz_streak") { getPassedQuizCount() >= 3 }
        }
        return passed
    }

    fun getQuizResult(moduleId: Int): Triple<Int, Int, Boolean>? {
        readableDatabase.query(
            "quiz_results", null,
            "module_id = ?", arrayOf(moduleId.toString()),
            null, null, null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                return Triple(
                    cursor.getInt(cursor.getColumnIndexOrThrow("score")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("total_questions")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("passed")) == 1
                )
            }
        }
        return null
    }

    fun saveLessonEvaluationResult(
        lessonId: Int,
        moduleId: Int,
        score: Int,
        totalQuestions: Int
    ): Boolean {
        val previous = getLessonEvaluationResult(lessonId)
        val passed = score >= GamificationHelper.LESSON_EVALUATION_PASS_THRESHOLD
        val bestScore = maxOf(score, previous?.score ?: 0)
        val passedBefore = previous?.passed == true
        val values = ContentValues().apply {
            put("lesson_id", lessonId)
            put("module_id", moduleId)
            put("score", bestScore)
            put("total_questions", totalQuestions)
            put("passed", if (passed || passedBefore) 1 else 0)
            put("attempts", (previous?.attempts ?: 0) + 1)
            put("completed_at", dateFormat.format(Date()))
        }
        writableDatabase.insertWithOnConflict(
            "lesson_evaluation_results",
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )

        if (passed && !passedBefore) {
            addModuleXp(moduleId, GamificationHelper.XP_PER_LESSON_EVALUATION)
            addTotalXp(GamificationHelper.XP_PER_LESSON_EVALUATION)
            checkAchievement("first_quiz") { getPassedLessonEvaluationsCount() >= 1 }
            checkAchievement("perfect_quiz") { score == totalQuestions }
        }
        refreshEvaluationProgress(moduleId)
        checkModuleMastery(moduleId)
        return passed
    }

    fun getLessonEvaluationResult(lessonId: Int): LessonEvaluationResult? {
        readableDatabase.query(
            "lesson_evaluation_results",
            null,
            "lesson_id = ?",
            arrayOf(lessonId.toString()),
            null,
            null,
            null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                return LessonEvaluationResult(
                    lessonId = cursor.getInt(cursor.getColumnIndexOrThrow("lesson_id")),
                    moduleId = cursor.getInt(cursor.getColumnIndexOrThrow("module_id")),
                    score = cursor.getInt(cursor.getColumnIndexOrThrow("score")),
                    totalQuestions = cursor.getInt(cursor.getColumnIndexOrThrow("total_questions")),
                    passed = cursor.getInt(cursor.getColumnIndexOrThrow("passed")) == 1,
                    attempts = cursor.getInt(cursor.getColumnIndexOrThrow("attempts"))
                )
            }
        }
        return null
    }

    fun getModuleEvaluationPercent(moduleId: Int): Int {
        val passed = readableDatabase.rawQuery(
            "SELECT COUNT(*) FROM lesson_evaluation_results WHERE module_id = ? AND passed = 1",
            arrayOf(moduleId.toString())
        ).use { cursor -> if (cursor.moveToFirst()) cursor.getInt(0) else 0 }
        return (passed * 100 / GamificationHelper.LESSONS_PER_MODULE).coerceIn(0, 100)
    }

    fun saveSimulationResult(scenarioId: Int, moduleId: Int, score: Int): Boolean {
        val previous = getSimulationResult(scenarioId)
        val passed = score >= 1
        val passedBefore = previous?.passed == true
        val values = ContentValues().apply {
            put("scenario_id", scenarioId)
            put("module_id", moduleId)
            put("score", maxOf(score, previous?.score ?: 0))
            put("passed", if (passed || passedBefore) 1 else 0)
            put("attempts", (previous?.attempts ?: 0) + 1)
            put("completed_at", dateFormat.format(Date()))
        }
        writableDatabase.insertWithOnConflict(
            "simulation_results",
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )

        if (passed && !passedBefore) {
            addModuleXp(moduleId, GamificationHelper.XP_PER_SIMULATION)
            addTotalXp(GamificationHelper.XP_PER_SIMULATION)
        }
        refreshSimulationProgress(moduleId)
        checkModuleMastery(moduleId)
        return passed
    }

    fun getSimulationResult(scenarioId: Int): SimulationResult? {
        readableDatabase.query(
            "simulation_results",
            null,
            "scenario_id = ?",
            arrayOf(scenarioId.toString()),
            null,
            null,
            null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                return SimulationResult(
                    scenarioId = cursor.getInt(cursor.getColumnIndexOrThrow("scenario_id")),
                    moduleId = cursor.getInt(cursor.getColumnIndexOrThrow("module_id")),
                    score = cursor.getInt(cursor.getColumnIndexOrThrow("score")),
                    passed = cursor.getInt(cursor.getColumnIndexOrThrow("passed")) == 1,
                    attempts = cursor.getInt(cursor.getColumnIndexOrThrow("attempts"))
                )
            }
        }
        return null
    }

    fun getModuleSimulationPercent(moduleId: Int): Int {
        val passed = readableDatabase.rawQuery(
            "SELECT COUNT(*) FROM simulation_results WHERE module_id = ? AND passed = 1",
            arrayOf(moduleId.toString())
        ).use { cursor -> if (cursor.moveToFirst()) cursor.getInt(0) else 0 }
        return (passed * 100 / GamificationHelper.LESSONS_PER_MODULE).coerceIn(0, 100)
    }

    private fun refreshEvaluationProgress(moduleId: Int) {
        val current = getProgress().find { it.moduleId == moduleId } ?: return
        updateProgress(
            moduleId,
            current.modulePercent,
            getModuleEvaluationPercent(moduleId),
            current.simulationPercent,
            current.xp
        )
    }

    private fun refreshSimulationProgress(moduleId: Int) {
        val current = getProgress().find { it.moduleId == moduleId } ?: return
        updateProgress(
            moduleId,
            current.modulePercent,
            current.evaluationPercent,
            getModuleSimulationPercent(moduleId),
            current.xp
        )
    }

    private fun addModuleXp(moduleId: Int, amount: Int) {
        val current = getProgress().find { it.moduleId == moduleId } ?: return
        updateProgress(
            moduleId,
            current.modulePercent,
            current.evaluationPercent,
            current.simulationPercent,
            current.xp + amount
        )
    }

    private fun getPassedLessonEvaluationsCount(): Int =
        readableDatabase.rawQuery(
            "SELECT COUNT(*) FROM lesson_evaluation_results WHERE passed = 1",
            null
        ).use { cursor -> if (cursor.moveToFirst()) cursor.getInt(0) else 0 }

    fun updateProgress(
        moduleId: Int,
        modulePercent: Int,
        evaluationPercent: Int,
        simulationPercent: Int,
        xp: Int
    ) {
        val values = ContentValues().apply {
            put("module_percent", modulePercent)
            put("evaluation_percent", evaluationPercent)
            put("simulation_percent", simulationPercent)
            put("xp", xp)
        }
        writableDatabase.update("progress", values, "module_id = ?", arrayOf(moduleId.toString()))
    }

    fun getTotalXp(): Int {
        readableDatabase.query("user_profile", arrayOf("total_xp"), "id = 1", null, null, null, null)
            .use { cursor ->
                if (cursor.moveToFirst()) return cursor.getInt(0)
            }
        return 0
    }

    fun getUserLevel(): Pair<Int, String> {
        readableDatabase.query(
            "user_profile", arrayOf("user_level", "level_title"),
            "id = 1", null, null, null, null
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getInt(0) to cursor.getString(1)
            }
        }
        return 1 to "Novato Digital"
    }

    fun isCertificateIssued(): Boolean {
        readableDatabase.query("user_profile", arrayOf("certificate_issued"), "id = 1", null, null, null, null)
            .use { cursor ->
                if (cursor.moveToFirst()) return cursor.getInt(0) == 1
            }
        return false
    }

    fun getBadges(): List<Pair<String, Boolean>> {
        val result = mutableListOf<Pair<String, Boolean>>()
        readableDatabase.query("badges", null, null, null, null, null, "module_id ASC").use { cursor ->
            while (cursor.moveToNext()) {
                result.add(
                    cursor.getString(cursor.getColumnIndexOrThrow("name")) to
                        (cursor.getInt(cursor.getColumnIndexOrThrow("unlocked")) == 1)
                )
            }
        }
        return result
    }

    fun getBadgeDetails(): List<RewardRecord> {
        val result = mutableListOf<RewardRecord>()
        readableDatabase.query("badges", null, null, null, null, null, "module_id ASC").use { cursor ->
            while (cursor.moveToNext()) {
                result.add(
                    RewardRecord(
                        id = cursor.getString(cursor.getColumnIndexOrThrow("badge_id")),
                        moduleId = cursor.getIntOrNull("module_id"),
                        name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        unlocked = cursor.getInt(cursor.getColumnIndexOrThrow("unlocked")) == 1,
                        unlockedAt = cursor.getStringOrNull("unlocked_at")
                    )
                )
            }
        }
        return result
    }

    fun getUnlockedBadgesCount(): Int {
        readableDatabase.rawQuery("SELECT COUNT(*) FROM badges WHERE unlocked = 1", null).use { cursor ->
            if (cursor.moveToFirst()) return cursor.getInt(0)
        }
        return 0
    }

    fun getAchievements(): List<Triple<String, String, Boolean>> {
        val result = mutableListOf<Triple<String, String, Boolean>>()
        readableDatabase.query("achievements", null, null, null, null, null, null).use { cursor ->
            while (cursor.moveToNext()) {
                result.add(
                    Triple(
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("unlocked")) == 1
                    )
                )
            }
        }
        return result
    }

    fun getAchievementDetails(): List<RewardRecord> {
        val result = mutableListOf<RewardRecord>()
        readableDatabase.query("achievements", null, null, null, null, null, null).use { cursor ->
            while (cursor.moveToNext()) {
                result.add(
                    RewardRecord(
                        id = cursor.getString(cursor.getColumnIndexOrThrow("achievement_id")),
                        moduleId = null,
                        name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        unlocked = cursor.getInt(cursor.getColumnIndexOrThrow("unlocked")) == 1,
                        unlockedAt = cursor.getStringOrNull("unlocked_at")
                    )
                )
            }
        }
        return result
    }

    private fun addTotalXp(amount: Int) {
        val current = getTotalXp() + amount
        val (level, title) = com.example.proyecto_final.learning.GamificationHelper.calculateLevel(current)
        val values = ContentValues().apply {
            put("total_xp", current)
            put("user_level", level)
            put("level_title", title)
        }
        writableDatabase.update("user_profile", values, "id = 1", null)
    }

    private fun unlockBadge(badgeId: String) {
        val values = ContentValues().apply {
            put("unlocked", 1)
            put("unlocked_at", dateFormat.format(Date()))
        }
        writableDatabase.update("badges", values, "badge_id = ?", arrayOf(badgeId))
    }

    private fun checkAchievement(id: String, condition: () -> Boolean) {
        if (!condition()) return
        if (isAchievementUnlocked(id)) return
        val values = ContentValues().apply {
            put("unlocked", 1)
            put("unlocked_at", dateFormat.format(Date()))
        }
        writableDatabase.update("achievements", values, "achievement_id = ? AND unlocked = 0", arrayOf(id))
        addTotalXp(150)
    }

    fun unlockAchievement(id: String) {
        if (isAchievementUnlocked(id)) return
        val values = ContentValues().apply {
            put("unlocked", 1)
            put("unlocked_at", dateFormat.format(Date()))
        }
        writableDatabase.update("achievements", values, "achievement_id = ? AND unlocked = 0", arrayOf(id))
        addTotalXp(150)
    }

    private fun isAchievementUnlocked(id: String): Boolean {
        readableDatabase.query(
            "achievements", arrayOf("unlocked"),
            "achievement_id = ?", arrayOf(id),
            null, null, null
        ).use { cursor ->
            if (cursor.moveToFirst()) return cursor.getInt(0) == 1
        }
        return false
    }

    private fun checkModuleAchievement(moduleId: Int) {
        val achievementId = when (moduleId) {
            1 -> "password_guardian"
            2 -> "phishing_detector"
            3 -> "safe_browser"
            4 -> "data_keeper"
            5 -> "wifi_defender"
            6 -> "social_shield"
            else -> null
        }
        if (achievementId != null) {
            checkAchievement(achievementId) { true }
        }
    }

    private fun getCompletedModulesCount(): Int =
        getProgress().count {
            it.modulePercent >= 100 &&
                it.evaluationPercent >= 100 &&
                it.simulationPercent >= 100
        }

    private fun checkModuleMastery(moduleId: Int) {
        val progress = getProgress().find { it.moduleId == moduleId } ?: return
        val mastered = progress.modulePercent >= 100 &&
            progress.evaluationPercent >= 100 &&
            progress.simulationPercent >= 100
        if (!mastered || isBadgeUnlocked("badge_module_$moduleId")) return

        unlockBadge("badge_module_$moduleId")
        addModuleXp(moduleId, GamificationHelper.XP_PER_MODULE_COMPLETE)
        addTotalXp(GamificationHelper.XP_PER_MODULE_COMPLETE)
        checkAchievement("three_modules") { getCompletedModulesCount() >= 3 }
        checkAchievement("all_modules") { getCompletedModulesCount() >= 6 }
        checkCertificate()
    }

    private fun isBadgeUnlocked(badgeId: String): Boolean {
        readableDatabase.query(
            "badges",
            arrayOf("unlocked"),
            "badge_id = ?",
            arrayOf(badgeId),
            null,
            null,
            null
        ).use { cursor ->
            return cursor.moveToFirst() && cursor.getInt(0) == 1
        }
    }

    private fun getTotalCompletedLessons(): Int {
        readableDatabase.rawQuery("SELECT COUNT(*) FROM lesson_progress WHERE completed = 1", null)
            .use { cursor ->
                if (cursor.moveToFirst()) return cursor.getInt(0)
            }
        return 0
    }

    private fun hasAnyPassedQuiz(): Boolean {
        readableDatabase.rawQuery("SELECT COUNT(*) FROM quiz_results WHERE passed = 1", null)
            .use { cursor ->
                if (cursor.moveToFirst()) return cursor.getInt(0) > 0
            }
        return false
    }

    private fun getPassedQuizCount(): Int {
        readableDatabase.rawQuery("SELECT COUNT(*) FROM quiz_results WHERE passed = 1", null)
            .use { cursor ->
                if (cursor.moveToFirst()) return cursor.getInt(0)
            }
        return 0
    }

    private fun checkCertificate() {
        if (getCompletedModulesCount() >= 6) {
            val values = ContentValues().apply { put("certificate_issued", 1) }
            writableDatabase.update("user_profile", values, "id = 1", null)
        }
    }

    fun isModuleUnlocked(moduleId: Int): Boolean {
        if (moduleId == 1) return true
        val previous = getProgress().find { it.moduleId == moduleId - 1 }
        return previous?.let {
            it.modulePercent >= 100 && it.evaluationPercent >= 100 && it.simulationPercent >= 100
        } ?: false
    }

    private companion object {
        const val DATABASE_NAME = "cyberedu_progress.db"
        const val DATABASE_VERSION = 3
    }
}

private fun android.database.Cursor.getStringOrNull(columnName: String): String? {
    val index = getColumnIndexOrThrow(columnName)
    return if (isNull(index)) null else getString(index)
}

private fun android.database.Cursor.getIntOrNull(columnName: String): Int? {
    val index = getColumnIndexOrThrow(columnName)
    return if (isNull(index)) null else getInt(index)
}
