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
                quotex = (result.body()?.results)?.get(0)?.content
                authorx = (result.body()?.results)?.get(0)?.author
                println(quotex)
                println(authorx)
                println("here")

            }
            // Checking the results
            binding.quote.setText(quotex)
            binding.author.text = authorx
        }





        setContentView(binding.root)
    }
}