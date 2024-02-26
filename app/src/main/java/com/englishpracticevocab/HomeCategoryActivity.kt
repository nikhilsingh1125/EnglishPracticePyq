package com.englishpracticevocab

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.englishpracticevocab.adapter.BannerAdapter
import com.englishpracticevocab.adapter.HomeCategoryAdapter
import com.englishpracticevocab.model.CategoryModel
import com.englishpracticevocab.model.HomeBannerData
import com.englishpracticevocab.utils.Constants
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_home_category.adView
import kotlinx.android.synthetic.main.activity_home_category.indicator
import kotlinx.android.synthetic.main.activity_home_category.recyclerViewHome
import kotlinx.android.synthetic.main.activity_home_category.viewPagerMain
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit
import libs.mjn.prettydialog.PrettyDialog

class HomeCategoryActivity : AppCompatActivity() {

    val TAG = "HomeCategoryActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_category)

        getVersion()
        getTechnicalIssue()
        MobileAds.initialize(this)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)



        addBannerImages()
        action_bar_share.setOnClickListener {
            Constants.shareApp(this)
        }

        btnSubmit.visibility = View.GONE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "22"
            val channelName = "SSC-English"
            val channelDescription = "My Channel Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        FirebaseMessaging.getInstance().subscribeToTopic("my_topic")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Subscribed to my_topic")
                } else {
                    Log.e(TAG, "Failed to subscribe to my_topic", task.exception)
                }
            }

        val array = ArrayList<CategoryModel>()
        array.add(CategoryModel("Practice", R.drawable.book))
        array.add(CategoryModel("Quiz", R.drawable.quiz_exam))
        array.add(CategoryModel("Bookmark", R.drawable.bookmark_app))
        array.add(CategoryModel("About Us", R.drawable.contact))


        recyclerViewHome.layoutManager = GridLayoutManager(this@HomeCategoryActivity, 2)
        val rvAdapter = HomeCategoryAdapter(this@HomeCategoryActivity, array)
        recyclerViewHome.adapter = rvAdapter
    }

    private fun addBannerImages() {

        val images = ArrayList<HomeBannerData>()
        images.add(HomeBannerData(R.drawable.quotes_banner))
        images.add(HomeBannerData(R.drawable.banner_2))


        val mViewPagerAdapter = BannerAdapter(this@HomeCategoryActivity, images)

        // Adding the Adapter to the ViewPager

        // Adding the Adapter to the ViewPager
        viewPagerMain.adapter = mViewPagerAdapter
        indicator.setViewPager(viewPagerMain)
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    fun goToCategory(model: CategoryModel, position: Int) {

        if (position == 0) {
            startActivity(Intent(this, HomeActivity::class.java))
        } else if (position == 1) {
            startActivity(Intent(this, QuizCategoryActivity::class.java))
        } else if (position == 2) {
            startActivity(Intent(this, BookmarkSaveActivity::class.java))
        } else if (position == 3) {
            startActivity(Intent(this, AboutUsActivity::class.java))
        }


    }

    fun getVersion() {
        val db = FirebaseFirestore.getInstance()

        // Reference to a collection
        val collectionRef = db.collection("app_version")

        // Get documents in the collection
        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                Log.d("FirestoreData", "querySnapshot: $querySnapshot")
                for (doc in querySnapshot) {
                    val versionData = doc.data
                    val documentId = doc.id
                    val versionCodeValue = versionData["version_code"]

                    if (versionCodeValue is Number) {
                        val versionCode = versionCodeValue.toInt()
                        Log.d(
                            "FirestoreData",
                            "Document ID: $documentId, versionCode: $versionCode"
                        )
                        checkAndUpdateApp(versionCode)
                    } else {
                        Log.e(
                            "FirestoreData",
                            "Invalid versionCode format in document: $documentId"
                        )
                    }
                }
            }
            .addOnFailureListener { error ->
                Log.e("FirestoreError", "Error getting documents: $error")
            }


    }

    fun getTechnicalIssue() {
        val db = FirebaseFirestore.getInstance()

        // Reference to a collection
        val collectionRef = db.collection("technical_issue")

// Get documents in the collection
        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                Log.d("FirestoreData", "querySnapshot: $querySnapshot")
                for (doc in querySnapshot) {
                    val versionData = doc.data
                    val documentId = doc.id
                    val message = versionData["message"] as? String
                    val Title = versionData["title"] as? String
                    val number =
                        versionData["number"] as? String // You can use appropriate data type (Long or Int) based on your data structure

                    if (number == "0") {
                        val pDialog = PrettyDialog(this)
                        pDialog.setCancelable(false) // Set the dialog non-cancelable
                        pDialog
                            .setTitle(Title)
                            .setIcon(R.drawable.app)
                            .setMessage(message)
                            .addButton(
                                "OK",  // button text
                                R.color.white,  // button text color
                                R.color.black_light
                            )  // button background color
                            {
                                pDialog.dismiss()
                            }
                            .show()
                    }
                    // Now you have the "message" and "number" values for each document
                    Log.d(
                        "FirestoreData",
                        "Document ID: $documentId, Message: $message, Number: $number"
                    )
                }
            }
            .addOnFailureListener { error ->
                Log.e("FirestoreError", "Error getting documents: $error")
            }


    }

    fun checkAndUpdateApp(versionCode: Int) {
        val currentVersionCode = Constants.getCurrentVersionCode(this)

        if (currentVersionCode != versionCode) {
            Constants.showUpdateDialog(this)
        }
    }
}