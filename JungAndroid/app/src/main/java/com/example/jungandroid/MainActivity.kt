package com.example.jungandroid

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class MainActivity : AppCompatActivity() {

    //데이터 배열 선언
    private var pageItemList = ArrayList<PageItem>()
    private lateinit var myIntroPagerRecyclerAdapter: MyIntroPagerRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dotIndicator = findViewById<DotsIndicator>(R.id.dots_indicator)
        val myIntroViewPager2 = findViewById<ViewPager2>(R.id.my_intro_view_pager)

        //데이터 배열 생성
        pageItemList.add(PageItem(R.color.colorOrange, R.drawable.ic_pager_item_1, "안녕하세요"))
        pageItemList.add(PageItem(R.color.colorBlue, R.drawable.ic_pager_item_2, "반갑습니다"))
        pageItemList.add(PageItem(R.color.colorWhite, R.drawable.ic_pager_item_3, "잘부탁드립니다"))

        //어댑터 인스턴스 생성
        myIntroPagerRecyclerAdapter = MyIntroPagerRecyclerAdapter(pageItemList)

        myIntroViewPager2.apply {
            adapter = myIntroPagerRecyclerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            dotIndicator.setViewPager2(this) // 오픈소스 인디케이터 사용
        }

        findViewById<ImageView>(R.id.previous_btn).setOnClickListener {
            myIntroViewPager2.currentItem = myIntroViewPager2.currentItem - 1
        }
        findViewById<ImageView>(R.id.next_btn).setOnClickListener {
            myIntroViewPager2.currentItem = myIntroViewPager2.currentItem + 1
        }

    }


}