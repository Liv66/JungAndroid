package com.example.jungandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jungandroid.R
import com.example.jungandroid.databinding.FragmentHomeBinding
import com.example.jungandroid.databinding.FragmentMusicBinding

class MusicFragment : Fragment() {

    private var mBinding : FragmentMusicBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMusicBinding.inflate(inflater, container, false)
        mBinding = binding
        return mBinding?.root
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

}