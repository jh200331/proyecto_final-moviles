package com.example.proyecto_final.learning

data class Lesson(
    val id: Int,
    val moduleId: Int,
    val order: Int,
    val title: String,
    val introduction: String,
    val explanation: String,
    val examples: String,
    val tips: String,
    val summary: String,
    val reflectionQuestion: String
)

data class QuizQuestion(
    val id: Int,
    val moduleId: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

enum class ModuleStatus { LOCKED, IN_PROGRESS, COMPLETED }

data class ModuleInfo(
    val id: Int,
    val title: String,
    val description: String,
    val difficulty: String,
    val estimatedMinutes: Int,
    val iconRes: Int,
    val gradientStart: Int,
    val gradientEnd: Int
)

data class ModuleProgress(
    val moduleId: Int,
    val lessonPercent: Int,
    val lessonsCompleted: Int,
    val totalLessons: Int,
    val quizPassed: Boolean,
    val quizScore: Int,
    val status: ModuleStatus,
    val isUnlocked: Boolean,
    val xp: Int
)

data class UserProfile(
    val totalXp: Int,
    val level: Int,
    val levelTitle: String,
    val xpForNextLevel: Int,
    val xpProgressInLevel: Int,
    val certificateIssued: Boolean
)

data class Badge(
    val id: String,
    val moduleId: Int?,
    val name: String,
    val description: String,
    val unlocked: Boolean
)

data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val unlocked: Boolean
)
