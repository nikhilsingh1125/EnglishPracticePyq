package com.englishpracticevocab.model

data class Test(
    val name: String?,
    val description: String?,
    val id: String?
)

data class YearData(
    val testYear: List<Test>?,
    val collections: Map<String, Any>?
)

data class PaperCategory(
    val year: YearData?
)
