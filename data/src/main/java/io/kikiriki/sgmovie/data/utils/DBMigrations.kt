package io.kikiriki.sgmovie.data.utils

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DBMigrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {

            // 1. rename old table
            db.execSQL("ALTER TABLE MovieLocal RENAME TO movies_old")

            // 2. create the new table
            db.execSQL("""
                CREATE TABLE movies (
                    id TEXT PRIMARY KEY NOT NULL,
                    title TEXT NOT NULL,
                    title_romanised TEXT NOT NULL,
                    image_cartel TEXT NOT NULL,
                    image_banner TEXT NOT NULL,
                    description TEXT NOT NULL,
                    director TEXT NOT NULL,
                    producer TEXT NOT NULL,
                    soundtrack TEXT NOT NULL,
                    release_date INTEGER NOT NULL,
                    running_time INTEGER NOT NULL,
                    rt_score INTEGER NOT NULL,
                    coproduction INTEGER NOT NULL CHECK (coproduction IN (0,1)),
                    like INTEGER NOT NULL CHECK (like IN (0,1))
                )
            """)

            // 3. migrate data from old to new table
            db.execSQL("""
                INSERT INTO movies (
                    id, title, title_romanised, image_cartel, image_banner, 
                    description, director, producer, soundtrack, 
                    release_date, running_time, rt_score, coproduction, like
                )
                SELECT 
                    id, title, COALESCE(original_title_romanised, ''), image, COALESCE(movie_banner, ''), 
                    description, director, COALESCE(producer, ''), '', 
                    COALESCE(NULLIF(release_date, ''), '0'), 
                    COALESCE(NULLIF(running_time, ''), '0'), 
                    COALESCE(NULLIF(rt_score, ''), '0'), 
                    0, 
                    favourite
                FROM movies_old
            """)

            // 4. drop old table
            db.execSQL("DROP TABLE movies_old")
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE movies ADD COLUMN tmdb_id TEXT NOT NULL DEFAULT ''")
        }
    }
    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE movies ADD COLUMN like_count INTEGER NOT NULL DEFAULT 0")
        }
    }
}