package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import com.example.retrofitexample.R
import com.example.retrofitexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var quotex: String? = "No quote, Check your connection!"
    var authorx: String? = "-Atakan"
    var count : Int? = 0
    var rand : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
        binding.quote.setText(quotex)
        binding.author.text = authorx
        // launching a new coroutine
        GlobalScope.launch {
            val result = quotesApi.getQuotes()
            if (result != null){
                result.body().toString()
                count = result.body()?.count
                rand = (0..(count?.minus(1))!!).random()
                quotex = (result.body()?.results)?.get(rand)?.content
                authorx = (result.body()?.results)?.get(rand)?.author

            }
            // Checking the results
            binding.quote.text = quotex
            binding.author.text = authorx
            binding.buttonRand.setOnClickListener(){
                rand = (0..(count?.minus(1))!!).random()
                quotex = (result.body()?.results)?.get(rand)?.content
                authorx = (result.body()?.results)?.get(rand)?.author
                binding.quote.text = quotex
                binding.author.text = authorx
            }
        }

        setContentView(binding.root)
    }
}