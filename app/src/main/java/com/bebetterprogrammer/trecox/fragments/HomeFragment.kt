package com.bebetterprogrammer.trecox.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bebetterprogrammer.trecox.R
import com.bebetterprogrammer.trecox.networking.DataBaseConnection
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        company_tv.text = DataBaseConnection.getName()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}