package com.example.proyecto_final

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class ProgressRecord(
    val moduleId: Int,
    val moduleName: String,
    val modulePercent: Int,
    val evaluationPercent: Int,
    val simulationPercent: Int,
    val xp: Int
)

class ProgressDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE progress (
                module_id INTEGER PRIMARY KEY,
                module_name TEXT NOT NULL,
                module_percent INTEGER NOT NULL,
                evaluation_percent INTEGER NOT NULL,
                simulation_percent INTEGER NOT NULL,
                xp INTEGER NOT NULL
            )
            """.trimIndent()
        )
        seedInitialProgress(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS progress")
        onCreate(db)
    }

    fun getProgress(): List<ProgressRecord> {
        val result = mutableListOf<ProgressRecord>()
        readableDatabase.query(
            "progress",
            null,
            null,
            null,
            null,
            null,
            "module_id ASC"
        ).use { cursor ->
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
            db.insert("progress", null, values)
        }
    }

    private companion object {
        const val DATABASE_NAME = "cyberedu_progress.db"
        const val DATABASE_VERSION = 1
    }
}
