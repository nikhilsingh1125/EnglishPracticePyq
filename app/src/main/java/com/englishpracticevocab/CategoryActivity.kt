package com.englishpracticevocab

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.englishpracticevocab.adapter.CategoryAdapter
import com.englishpracticevocab.model.ListCategoryData
import com.englishpracticevocab.model.Test
import com.englishpracticevocab.model.YearData
import com.englishpracticevocab.utils.Constants
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_category2.adViewCat
import kotlinx.android.synthetic.main.activity_category2.loader
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_back
import kotlinx.android.synthetic.main.custom_toolbar.action_bar_share
import kotlinx.android.synthetic.main.custom_toolbar.btnSubmit

class CategoryActivity : AppCompatActivity() {
    var type = ""
    var TAG = "CategoryActivity"
    lateinit var testYear :ArrayList<Test>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category2)

        btnSubmit.visibility = View.GONE
        val intent = intent
        type = intent.getStringExtra("TYPE").toString()

        Log.d("CategoryActivity", "categoryType: $type")
        getPaperCategoryData(type)

        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        adViewCat.loadAd(adRequest)

        loader.setAnimation(R.raw.loader)
        loader.visibility = View.VISIBLE
        loader.playAnimation()

        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val categoryType = sharedPreferences.getString("categoryData", null)

        Log.d("sharedPreferences", "categoryType: $categoryType")

        Constants.getCategoryNames()

        if (categoryType != null) {
            Constants.getBookmarkDataForCategory(categoryType)
        }


        action_bar_back.visibility = View.VISIBLE
        action_bar_share.visibility = View.GONE

        action_bar_back.setOnClickListener {
            onBackPressed()
        }

 /*       val array = ArrayList<ListCategoryData>()
        if (type == "2022") {
            array.add(ListCategoryData("SSC CGL", "SSC CGL 2022 Tier-I"))
            array.add(ListCategoryData("SSC CHSL", "SSC CHSL 2022 Tier-I"))
            array.add(ListCategoryData("SSC CPO", "SSC CPO 2022 Tier-I"))
            array.add(ListCategoryData("SSC MTS", "SSC MTS 2022 Tier-I"))
            array.add(ListCategoryData("SSC Stenographer", "SSC Stenographer Grade ‘C’ & ‘D’ 2022 Tier-I"))
        } else if (type == "2021") {
            array.add(ListCategoryData("SSC CGL", "SSC CGL 2021 Tier-I"))
            array.add(ListCategoryData("SSC CHSL", "SSC CHSL 2021 Tier-I"))
            array.add(ListCategoryData("SSC MTS", "SSC MTS 2021 Tier-I"))
        } else if (type == "2020") {
            array.add(ListCategoryData("SSC CGL", "SSC CGL 2020 Tier-I"))
            array.add(ListCategoryData("SSC CHSL", "SSC CHSL 2020 Tier-I"))
            array.add(ListCategoryData("SSC MTS", "SSC MTS 2020 Tier-I"))
            array.add(ListCategoryData("SSC CPO", "SSC CPO 2020 Tier-I"))
        } else if (type == "2019") {
            array.add(ListCategoryData("SSC CGL", "SSC CGL 2019 Tier-I"))
            array.add(ListCategoryData("SSC MTS", "SSC MTS 2019 Tier-I"))
            array.add(ListCategoryData("SSC CPO", "SSC CPO 2019 Tier-I"))
            array.add(ListCategoryData("SSC CHSL", "SSC CHSL 2019 Tier-I"))
        } else if (type == "2023") {
            array.add(ListCategoryData("SSC CGL", "SSC CGL 2023 Tier-I"))
            array.add(ListCategoryData("SSC PHASE", "SSC PHASE 11 2023 Tier-I"))
            array.add(ListCategoryData("SSC CHSL", "SSC CHSL 2023 Tier-I"))
        }*/



      /*  recyclerView.layoutManager = LinearLayoutManager(this@CategoryActivity)
        val rvAdapter = CategoryAdapter(this@CategoryActivity, array)
        recyclerView.adapter = rvAdapter*/
    }

    fun goToNextExamList(model: String?, position: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("TYPE", model.toString())
        startActivity(intent)
/*
        if (type == "2022") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CHCL")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CPO")
            } else if (position == 3) {
                intent.putExtra("TYPE", "MTS")
            } else if (position == 4) {
                intent.putExtra("TYPE", "Stenographer")
            }
        }
        else if (type == "2021") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2021")
            } else if (position == 1) {
                intent.putExtra("TYPE", "CHCL_2021")
            } else if (position == 2) {
                intent.putExtra("TYPE", "MTS_2021")
            }
        } else if (type == "2020") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2020")
            } else if (position == 1) {
                intent.putExtra("TYPE", "MTS_2020")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CHSL_2020")
            } else if (position == 3) {
                intent.putExtra("TYPE", "CPO_2020")
            }
        } else if (type == "2019") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2019")
            } else if (position == 1) {
                intent.putExtra("TYPE", "MTS_2019")
            } else if (position == 2) {
                intent.putExtra("TYPE", "CPO_2019")
            }
            else if (position == 3) {
                intent.putExtra("TYPE", "CHSL_2019")
            }
        }else if (type == "2023") {
            if (position == 0) {
                intent.putExtra("TYPE", "CGL_2023")
            } else if (position == 1) {
                intent.putExtra("TYPE", "PHASE_2023")
            }
            else if (position == 2) {
                intent.putExtra("TYPE", "CHSL_2023")
            }
        }*/


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun getPaperCategoryData(type: String) {
        val db = FirebaseFirestore.getInstance()

        val collectionRef = db.collection("paperCategory")

        collectionRef.get()
            .addOnSuccessListener { querySnapshot ->
                Log.d("FirestoreData", "querySnapshot: $querySnapshot")
                loader.visibility = View.GONE
                for (doc in querySnapshot) {
                    val yeardata = doc.data
                    val yearData = yeardata[type]

                    if (yearData is Map<*, *>) {
                        val gson = Gson()
                        val testListType = object : TypeToken<List<Test>>() {}.type
                        val testList: List<Test> = gson.fromJson(gson.toJson(yearData[type]), testListType)

                        val yearDataObject = YearData(testList, yeardata["collections"] as Map<String, Any>?)

                        if (testList.isNotEmpty()){
                            recyclerView.layoutManager = LinearLayoutManager(this@CategoryActivity)
                            val rvAdapter =
                                yearDataObject.testYear?.let {
                                    CategoryAdapter(this@CategoryActivity,
                                        it
                                    )
                                }
                            recyclerView.adapter = rvAdapter
                        }

                        Log.e(TAG, "yearDataObject: $yearDataObject")
                    } else {
                        Log.e(TAG, "Invalid yearData type")
                    }
                }
            }
            .addOnFailureListener { error ->
                Log.e("FirestoreError", "Error getting documents: $error")
            }
    }





}