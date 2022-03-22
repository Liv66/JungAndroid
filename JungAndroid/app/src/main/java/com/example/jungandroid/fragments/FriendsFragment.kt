package com.example.jungandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jungandroid.R
import com.example.jungandroid.databinding.FragmentFriendsBinding
import com.example.jungandroid.databinding.FragmentHomeBinding

class FriendsFragment : Fragment() {

    private var mBinding : FragmentFriendsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFriendsBinding.inflate(inflater, container, false)
        mBinding = binding
        return mBinding?.root
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

}