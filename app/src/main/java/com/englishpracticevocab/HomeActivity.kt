package com.englishpracticevocab

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.englishpracticevocab.adapter.HomeAdapter
import kotlinx.android.synthetic.main.activity_home.loader
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_Title
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit


class HomeActivity : AppCompatActivity() {

    val TAG = "HomeActivity"
    private var rewardedAd: RewardedAd? = null
    var arrYearData = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val TAG = "HomeActivity"
        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()
//        getVersion()
        getYearWiseData()
       /* MobileAds.initialize(this)



        val adRequest = AdRequest.Builder().build()
        ad_view_home.c(adRequest)*/


        action_bar_share.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        action_bar_back.visibility = View.VISIBLE
        action_bar_back.setOnClickListener {
            onBackPressed()
            finish()
        }

        action_bar_Title.text = "Practice"


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

       /* val array = ArrayList<CategoryModel>()
        array.add(CategoryModel("2023", R.drawable.study))
        array.add(CategoryModel("2022", R.drawable.study))
        array.add(CategoryModel("2021", R.drawable.study))
        array.add(CategoryModel("2020", R.drawable.study))
        array.add(CategoryModel("2019", R.drawable.study))*/


    }



    fun goToCategory(model: String, position: Int) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("TYPE", model)
        startActivity(intent)

        fun showAd() {
            rewardedAd?.let { ad ->
                ad.show(this, OnUserEarnedRewardListener { rewardItem ->
                    // Handle the reward.
                    val rewardAmount = rewardItem.amount
                    val rewardType = rewardItem.type
                    Toast.makeText(this, "UnLocked", Toast.LENGTH_SHORT).show()
                })
            } ?: run {
                Log.d(TAG, "The rewarded ad wasn't ready yet.")
            }
        }


    }



    fun getYearWiseData() {
        val db = FirebaseFirestore.getInstance()

        // Reference to a collection
        val collectionRef = db.collection("yeardata")
        // Get documents in the collection
        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                Log.d("FirestoreData", "querySnapshot: $querySnapshot")
                loader.visibility = View.GONE
                for (doc in querySnapshot) {
                    val yeardata = doc.data
                    val yearData = yeardata["yearWisePaper"]

                    if (yearData is List<*>) {
                        arrYearData.addAll(yearData.filterIsInstance<String>())
                    } else if (yearData is String) {
                        arrYearData.add(yearData)
                    }

                    Log.e(TAG, "yearData: $yearData")

                    println(yearData)
                }

                if(arrYearData.isNotEmpty()){
                    Log.d(TAG, "arrYearData ==> $arrYearData")
                    recyclerView.layoutManager = GridLayoutManager(this@HomeActivity, 2)
                    val rvAdapter = HomeAdapter(this@HomeActivity, arrYearData)
                    recyclerView.adapter = rvAdapter
                }

            }
            .addOnFailureListener { error ->
                Log.e("FirestoreError", "Error getting documents: $error")
            }


    }

}