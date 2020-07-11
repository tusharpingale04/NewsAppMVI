package com.tushar.newsmvi.ui

import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tushar.newsmvi.R
import com.tushar.newsmvi.di.NewsScope
import com.tushar.newsmvi.model.Article
import com.tushar.newsmvi.ui.state.MainStateEvent
import com.tushar.newsmvi.util.TopSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(),
    MainRecyclerAdapter.Interaction {

    private lateinit var dataStateHandler: DataStateListener
    private val viewModel by activityViewModels<MainViewModel>()

    @NewsScope
    @Inject
    lateinit var mainRecyclerAdapter: MainRecyclerAdapter

    @NewsScope
    @Inject
    lateinit var itemDecoration: TopSpacingItemDecoration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setStateEvent(MainStateEvent.FetchNews)
        subscribeObservers()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(itemDecoration)
            mainRecyclerAdapter.setInteraction(this@MainFragment)
            adapter = mainRecyclerAdapter
        }
    }

    private fun subscribeObservers() {
        //Get Data from Network or cache
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            // Handle Loading and Error Message
            dataStateHandler.onDataStateChange(dataState)

            // handle Data<T>
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    //Set Data to ViewState
                    mainViewState.newsModel?.let {
                        viewModel.setNewsListData(it)
                    }
                }
            }
        })

        //Set ViewState Data to UI
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.newsModel?.articles?.let { articles ->
                mainRecyclerAdapter.submitList(articles)
            }
        })
    }

    override fun onItemSelected(position: Int, item: Article) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        builder.addDefaultShareMenuItem()
        val anotherCustomTab = CustomTabsIntent.Builder().build()
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_close_24)
        val requestCode = 100
        val intent = anotherCustomTab.intent
        intent.data = Uri.parse(item.url)
        val pendingIntent = PendingIntent.getActivity(requireContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setActionButton(bitmap, "Android", pendingIntent, true)
        builder.setShowTitle(true)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(item.url))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            println("$context must implement DataStateListener")
        }

    }

    private fun getInt() = 2+3

    private fun getIntTemp() = 2+3


}
