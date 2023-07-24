package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.retrofitexample.R
import com.example.retrofitexample.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory
    var quotex: String? = "No quote, Check your connection!"
    var authorx: String? = "-Atakan"
    var count: Int? = 0
    var num : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
        viewModelFactory = MainActivityViewModelFactory( 0)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        binding.quote.text = quotex
        binding.author.text = authorx
        // launching a new coroutine
        lifecycleScope.launch {
            val result = quotesApi.getQuotes()
            if (result != null){

                result.body().toString()
                count = (result.body()?.count)?.minus(1)
                quotex = (result.body()?.results)?.get(num)?.content
                authorx = (result.body()?.results)?.get(num)?.author
                withContext(Dispatchers.Main){
                    viewModel.res.observe(this@MainActivity, Observer {
                        num = it
                    })
                }
            }

            // Checking the results
            binding.quote.text = quotex
            binding.author.text = authorx
            binding.prevbtn.setOnClickListener(){
                if(num == 0){
                    viewModel.assign(19)
                    quotex = (result.body()?.results)?.get(num)?.content
                    authorx = (result.body()?.results)?.get(num)?.author
                    binding.quote.text = quotex
                    binding.author.text = authorx

                }
                else{
                    viewModel.previous()
                    quotex = (result.body()?.results)?.get(num)?.content
                    authorx = (result.body()?.results)?.get(num)?.author
                    binding.quote.text = quotex
                    binding.author.text = authorx
            }

            }
            binding.nextbtn.setOnClickListener(){
                if(num == count){
                    viewModel.assign(0)
                    quotex = (result.body()?.results)?.get(num)?.content
                    authorx = (result.body()?.results)?.get(num)?.author
                    binding.quote.text = quotex
                    binding.author.text = authorx

                }
                else{
                    viewModel.nextQ()
                    quotex = (result.body()?.results)?.get(num)?.content
                    authorx = (result.body()?.results)?.get(num)?.author
                    binding.quote.text = quotex
                    binding.author.text = authorx
                }

            }
        }

        setContentView(binding.root)
    }
}