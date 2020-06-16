package com.tushar.newsmvi.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tushar.newsmvi.R
import com.tushar.newsmvi.model.Article
import com.tushar.newsmvi.util.DateTimeUtils
import kotlinx.android.synthetic.main.row_news_light.view.*
import java.text.DateFormat

class MainRecyclerAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.publishedAt == newItem.publishedAt
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    private var interaction: Interaction? = null

    fun setInteraction(interaction: Interaction){
        this.interaction = interaction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_news_light,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Article>) {
        differ.submitList(list)
    }

    class NewsViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Article) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.title.text = item.title
            itemView.subtitle.text = item.author
            itemView.date.text = item.publishedAt
            itemView.date.text = DateFormat.getDateInstance(DateFormat.FULL).format(DateTimeUtils.getFormattedDate(item.publishedAt))

            Glide.with(itemView.context)
                .load(item.urlToImage)
                .into(itemView.image)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Article)
    }
}


