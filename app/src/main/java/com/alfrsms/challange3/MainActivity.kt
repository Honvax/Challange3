package com.alfrsms.challange3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.alfrsms.challange3.`interface`.OnDataPass
import com.alfrsms.challange3.databinding.ActivityMainBinding
import com.alfrsms.challange3.letter.LetterFragment
import com.alfrsms.challange3.word.WordFragment

class MainActivity : AppCompatActivity() , OnDataPass {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val letterFragment = LetterFragment()
        val fragmentManager = supportFragmentManager

        fragmentManager.commit {
            add(R.id.activity_host, letterFragment)
        }
    }

    override fun onDataPass(letter: String) {
        val wordFragment = WordFragment()

        val bundle = Bundle()
        bundle.putString("letter", letter)

        wordFragment.arguments = bundle
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.activity_host, wordFragment)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}