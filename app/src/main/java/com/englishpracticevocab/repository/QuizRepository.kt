package com.englishpracticevocab.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuizRepository {
    val db = Firebase.database.reference

    fun getData(): DatabaseReference {
        return db.child("Quizes")
    }
}