package com.example.proyecto_final.learning

import android.content.Context
import com.example.proyecto_final.ProgressDatabaseHelper

class LearningRepository(context: Context) {

    private val db = ProgressDatabaseHelper(context.applicationContext)

    fun getModuleProgress(moduleId: Int): ModuleProgress {
        val lessons = LearningContent.getLessons(moduleId)
        val completed = db.getCompletedLessonsCount(moduleId)
        val percent = GamificationHelper.lessonPercent(completed)
        val record = db.getProgress().find { it.moduleId == moduleId }
        val quiz = db.getQuizResult(moduleId)
        val unlocked = db.isModuleUnlocked(moduleId)
        val status = when {
            !unlocked -> ModuleStatus.LOCKED
            percent >= 100 -> ModuleStatus.COMPLETED
            completed > 0 -> ModuleStatus.IN_PROGRESS
            else -> if (unlocked) ModuleStatus.IN_PROGRESS else ModuleStatus.LOCKED
        }
        return ModuleProgress(
            moduleId = moduleId,
            lessonPercent = percent,
            lessonsCompleted = completed,
            totalLessons = lessons.size,
            quizPassed = quiz?.third ?: false,
            quizScore = quiz?.first ?: 0,
            status = status,
            isUnlocked = unlocked,
            xp = record?.xp ?: 0
        )
    }

    fun getAllModuleProgress(): List<ModuleProgress> =
        LearningContent.allModules().map { getModuleProgress(it.id) }

    fun getUserProfile(): UserProfile {
        val totalXp = db.getTotalXp()
        val (level, title) = db.getUserLevel()
        return UserProfile(
            totalXp = totalXp,
            level = level,
            levelTitle = title,
            xpForNextLevel = GamificationHelper.xpForNextLevel(totalXp),
            xpProgressInLevel = GamificationHelper.xpProgressInCurrentLevel(totalXp),
            certificateIssued = db.isCertificateIssued()
        )
    }

    fun isLessonCompleted(lessonId: Int): Boolean = db.isLessonCompleted(lessonId)

    fun completeLesson(lessonId: Int, moduleId: Int): Boolean =
        db.completeLesson(lessonId, moduleId)

    fun saveQuizResult(moduleId: Int, score: Int, total: Int): Boolean =
        db.saveQuizResult(moduleId, score, total)

    fun getBadges(): List<Badge> {
        val badgeData = db.getBadges()
        return badgeData.mapIndexed { index, (name, unlocked) ->
            Badge(
                id = "badge_module_${index + 1}",
                moduleId = index + 1,
                name = name,
                description = "Insignia del módulo ${index + 1}",
                unlocked = unlocked
            )
        }
    }

    fun getAchievements(): List<Achievement> =
        db.getAchievements().mapIndexed { index, (name, desc, unlocked) ->
            Achievement("achievement_$index", name, desc, unlocked)
        }

    fun getUnlockedBadgesCount(): Int = db.getUnlockedBadgesCount()

    fun getOverallProgress(): Int {
        val all = getAllModuleProgress()
        if (all.isEmpty()) return 0
        return all.sumOf { it.lessonPercent } / all.size
    }

    fun getCompletedModulesCount(): Int =
        getAllModuleProgress().count { it.lessonPercent >= 100 }
}
