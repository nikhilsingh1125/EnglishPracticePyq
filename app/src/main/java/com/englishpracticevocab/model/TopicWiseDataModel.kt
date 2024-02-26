package com.englishpracticevocab.model

data class ExamCategory(
    val name: String,
    val id: String
)

data class ExamSection(
    val CGL_2022: List<ExamCategory>,
    val collections: Map<String, Any>?
)

data class ExamData(
    val CGL_2022: ExamSection
)
