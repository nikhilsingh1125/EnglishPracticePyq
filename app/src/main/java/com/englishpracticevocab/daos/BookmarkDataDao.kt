package com.englishpracticevocab.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englishpracticevocab.model.BookmarkRoomModel

@Dao
interface BookmarkDataDao {
    @Query("SELECT * FROM bookmarked_questions WHERE questionIdentifier = :questionIdentifier")
    fun getQuestionById(questionIdentifier: String): BookmarkRoomModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(questionData: BookmarkRoomModel)

    @Delete
    fun deleteQuestion(questionData: BookmarkRoomModel)
}
