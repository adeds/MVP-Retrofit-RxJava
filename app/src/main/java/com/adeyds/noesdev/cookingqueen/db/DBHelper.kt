package com.adeyds.noesdev.cookingqueen.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.adeyds.noesdev.cookingqueen.db.FavoriteRecipe.Companion.DB_NAME
import com.adeyds.noesdev.cookingqueen.db.FavoriteRecipe.Companion.TABLE_FAVORITE_RESEP
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, 1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance as DBHelper
        }
    }
    fun reCreate() {
        clearTable()
        onCreate(writableDatabase)
    }

    fun clearTable(){
        writableDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITE_RESEP")
    }



    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteRecipe.TABLE_FAVORITE_RESEP, true,
                FavoriteRecipe.THUMBNAIL to TEXT,
                FavoriteRecipe.INGREDIENT to TEXT,
                FavoriteRecipe.HREF to TEXT + UNIQUE,
                FavoriteRecipe.TITLE to TEXT
              )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(TABLE_FAVORITE_RESEP, true)
    }
}

val Context.database: DBHelper
    get() = DBHelper.getInstance(applicationContext)