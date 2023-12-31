package com.englishpracticevocab

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.englishpracticevocab.adapter.BookmarkQuizAdapter
import com.englishpracticevocab.model.QuestionData
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_home_category.adView
import kotlinx.android.synthetic.main.activity_quiz_bookmark.ad_view_quiz
import kotlinx.android.synthetic.main.activity_quiz_bookmark.idViewPagerBookmark
import libs.mjn.prettydialog.PrettyDialog

class BookmarkQuizActivity : AppCompatActivity() {

    val TAG = "BookmarkQuizActivity"
    lateinit var questions: ArrayList<QuestionData>
    var receivedTestTitle =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_bookmark)

        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        ad_view_quiz.loadAd(adRequest)

        initBookmark()
    }

    private fun initBookmark() {
        val receivedBundle = intent.extras
        if (receivedBundle != null) {
            questions =
                receivedBundle.getParcelableArrayList<QuestionData>("questionsList") as ArrayList<QuestionData>
            receivedTestTitle = receivedBundle.getString("testTitle").toString()
            Log.e(TAG, "questions ==>: $questions")
        }

        val adapter = BookmarkQuizAdapter(this@BookmarkQuizActivity, questions, receivedTestTitle, "","")
        idViewPagerBookmark.adapter = adapter
    }



    fun clickOnBtnNextBook() {
        idViewPagerBookmark.setCurrentItem(idViewPagerBookmark.currentItem + 1, true)
    }

    fun onPrevClickBook() {
        idViewPagerBookmark.setCurrentItem(idViewPagerBookmark.currentItem - 1, true)
    }

    override fun onBackPressed() {

        val pDialog = PrettyDialog(this)
        pDialog
            .setTitle(getString(R.string.app_name))
            .setMessage("Are you sure you don't want continue?")
            .setIconTint(R.color.black_light)
            .addButton(
                "OK",  // button text
                R.color.white,  // button text color
                R.color.black_light
            )  // button background color
            {
                pDialog.dismiss()
                finish()
            }
            .addButton(
                "Cancel",  // button text
                R.color.white,  // button text color
                R.color.black_light
            )  // button background color
            { pDialog.dismiss() }
            .show()
    }
}