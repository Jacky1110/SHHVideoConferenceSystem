package com.jotangi.twinsSum.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.jotangi.twinsSum.R
import com.jotangi.twinsSum.databinding.ActivityMainBinding
import com.jotangi.twinsSum.db.InfoCall
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val infoCall: InfoCall by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initHandler()
    }

    private fun initHandler() {

        binding.apply {

            bnv.setOnItemSelectedListener {

                backItem(it.itemId)
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun backItem(id: Int) {

        findNavController(R.id.fcv).apply {

            repeat(currentBackStack.value.size) { popBackStack() }

            when (id) {

                R.id.item_home -> navigate(R.id.homeFragment)
                R.id.item_member -> {

                    if (infoCall.getAccount().isNullOrBlank())
                        navigate(R.id.loginFragment)
                    else
                        navigate(R.id.memberInfoFragment)
                }
            }
        }
    }
}