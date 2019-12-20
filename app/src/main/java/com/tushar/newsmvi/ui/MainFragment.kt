package com.tushar.newsmvi.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.tushar.newsmvi.R
import com.tushar.newsmvi.model.Article
import com.tushar.newsmvi.ui.state.MainStateEvent
import com.tushar.newsmvi.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(),
    MainRecyclerAdapter.Interaction {

    private lateinit var viewModel: MainViewModel
    private lateinit var mainRecyclerAdapter: MainRecyclerAdapter
    private lateinit var dataStateHandler: DataStateListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        viewModel.setStateEvent(MainStateEvent.FetchMovies)
        subscribeObservers()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        rvNews.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            mainRecyclerAdapter = MainRecyclerAdapter(this@MainFragment)
            adapter = mainRecyclerAdapter
        }
    }

    private fun subscribeObservers() {
        //Get Data from Network or cache
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            // Handle Loading and Message
            dataStateHandler.onDataStateChange(dataState)

            // handle Data<T>
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->

                    println("DEBUG: DataState: $mainViewState")

                    mainViewState.newsModel?.let {
                        // set BlogPosts data
                        viewModel.setNewsListData(it)
                    }
                }
            }
        })

        //Set Data to UI
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.newsModel?.articles?.let { articles ->
                // set News List to RecyclerView
                println("DEBUG: Setting blog posts to RecyclerView: $articles")
                mainRecyclerAdapter.submitList(articles)
            }
        })
    }

    override fun onItemSelected(position: Int, item: Article) {
        println("DEBUG: CLICKED $position")
        println("DEBUG: CLICKED $item")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            println("$context must implement DataStateListener")
        }

    }


}
