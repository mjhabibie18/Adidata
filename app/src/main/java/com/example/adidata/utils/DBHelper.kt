package com.example.adidata.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.adidata.data.User

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory? = null) : SQLiteOpenHelper(context, DatabaseName, factory, DatabaseVersion) {
    // create table sql query
    private val createTableCustomer = ("CREATE TABLE " + TableName + " ("
            + columnID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            columnEmail + " TEXT," +
            columnName + " TEXT," +
            columnPass + " TEXT" + ")")

    // drop table sql query
    private val dropTableCustomer = "DROP TABLE IF EXISTS $TableName"

    override fun onCreate(db: SQLiteDatabase?) {
        val query = (createTableCustomer)

        // we are calling sqlite
        // method for executing our query
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(dropTableCustomer)
        onCreate(db)
    }

    fun checkUser(email: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(columnID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$columnEmail = ? AND $columnPass = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(TableName, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }

    fun registerCustomer(user: User) {
        // a content values variable
        val values = ContentValues()

        // we are inserting our values in the form of key-value pair
        values.put(columnEmail, user.email)
        values.put(columnName, user.name)
        values.put(columnPass, user.password)

        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TableName, null, values)

        // at last we are closing our database
        db.close()
    }

    companion object{
        private const val DatabaseName = "TEST123"
        private const val DatabaseVersion = 1
        const val TableName = "Customer"
        const val columnID = "columnID"
        const val columnEmail = "columnEmail"
        const val columnName = "columnName"
        const val columnPass = "columnPass"
    }
}