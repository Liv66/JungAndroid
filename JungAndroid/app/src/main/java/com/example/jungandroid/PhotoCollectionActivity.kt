package com.example.jungandroid

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jungandroid.model.Photo
import com.example.jungandroid.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.jungandroid.utills.Constants.TAG
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar

class PhotoCollectionActivity : AppCompatActivity() {

    //데이터
    private var photoList = ArrayList<Photo>()
    //어뎁터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter
    //리사이클러뷰
    private lateinit var myRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)


        val bundle = intent.getBundleExtra("array_bundle")

        val searchTerm = intent.getStringExtra("search_term")
        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

        findViewById<MaterialToolbar>(R.id.topAppBar).title = searchTerm

        Log.d(
            TAG,
            "PhotoCollectionActivity - onCreate() called / searchTerm : $searchTerm, photoList.count() : ${photoList.count()}"
        )

        this.photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()
        this.photoGridRecyclerViewAdapter.submitList(photoList)

        myRecyclerView = findViewById<RecyclerView>(R.id.my_photo_recycler_view)
        myRecyclerView.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        myRecyclerView.adapter = photoGridRecyclerViewAdapter

    }
}