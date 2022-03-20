package com.example.jungandroid

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var homeFragment: HomeFragment
    private lateinit var rankingFragment: RankingFragment
    private lateinit var profileFragment: ProfileFragment

    companion object {
        val TAG: String = "로그"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        findViewById<NavigationBarView>(R.id.bottom_nav).setOnItemSelectedListener(onBottomNavItemSelectedListener)
        findViewById<NavigationBarView>(R.id.bottom_nav).setOnItemSelectedListener(this)
        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragment_frame, homeFragment)
            .commit() // 처음 프래그먼트를 올릴 때는 add로 올려야한다.

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "MainActivity- onNavigationItemSelected() called")

        when (item.itemId) {
            R.id.menu_home -> {
                Log.d(TAG, "MainActivity- 홈버튼 클릭 called")
                homeFragment = HomeFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_frame, homeFragment)
                    .commit()
                // replace(출력할 레이아웃, 올릴 레이아웃), replace는 올려져있는 프래그먼트를 교체한다.
//                supportFragmentManager.beginTransaction().add(R.id.fragment_frame, homeFragment).commit()
                // add는 올려져있는 프래그먼트를 더한한다.
            }
            R.id.menu_ranking -> {
                Log.d(TAG, "MainActivity- 랭킹버튼 클릭 called")
                rankingFragment = RankingFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_frame, rankingFragment).commit()
            }
            R.id.menu_profile -> {
                Log.d(TAG, "MainActivity- 메뉴버튼 클릭 called")
                profileFragment = ProfileFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_frame, profileFragment).commit()
            }
        }
        return true
    }

    // 인터페이스를 사용하지 않는 경우 바텀네비게이션 아이템 클릭 리스너 설정
    private val onBottomNavItemSelectedListener = NavigationBarView.OnItemSelectedListener {
        when (it.itemId) {
            R.id.menu_home -> {
                Log.d(TAG, "MainActivity- 홈버튼 클릭 called")
            }
            R.id.menu_ranking -> {
                Log.d(TAG, "MainActivity- 랭킹버튼 클릭 called")
            }
            R.id.menu_profile -> {
                Log.d(TAG, "MainActivity- 메뉴버튼 클릭 called")
            }
        }
        true
    }
}