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
import kotlinx.android.synthetic.main.row_news.view.*

class MainRecyclerAdapter(private val interaction: Interaction? = null) :
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_news,
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

            itemView.tvTitle.text = item.title
            itemView.tvAuthor.text = item.author
            itemView.tvTime.text = item.publishedAt

            Glide.with(itemView.context)
                .load(item.urlToImage)
                .into(itemView.ivHeader)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Article)
    }
}


