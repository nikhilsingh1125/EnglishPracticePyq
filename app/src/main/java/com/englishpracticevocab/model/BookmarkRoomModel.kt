package com.englishpracticevocab.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bookmarked_questions")
data class BookmarkRoomModel(
    @PrimaryKey
    val questionIdentifier: String,
    val question: String,
    val answer: String,
    val option_A: String,
    val option_B: String,
    val option_C: String,
    val option_D: String,
    val solutions: String,
    val isGivenAnswer: Boolean = false,
    val optionsSelected: String,
    val selectedOptionsAnswer: String,
    val testCategory: String,
    val isBookmark: Boolean = false,
    val isSelectedAnswer: Boolean = false,
    val isSkipAnswer: Boolean = false,
    val isWrongAnswer: Boolean = false
)
