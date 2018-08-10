package u4s5.githubrepofinder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import u4s5.githubrepofinder.base.inflate
import u4s5.githubrepofinder.base.string
import u4s5.githubrepofinder.model.SearchItem
import javax.inject.Inject

class SearchResultsAdapter @Inject constructor() : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    var data: List<SearchItem> = listOf()
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = parent.inflate(R.layout.search_item)
        val holder = ViewHolder(itemView)
        itemView.setOnClickListener { itemClickListener?.onItemClicked(holder.adapterPosition) }
        return holder
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val item = data[position]
        repoName.text = item.repoName
        projectLang.text = item.projectLang ?: itemView.context.string(R.string.unknown_lang_text)
    }

    class ViewHolder(
            itemView: View,
            val repoName: TextView = itemView.findViewById(R.id.repo_name),
            val projectLang: TextView = itemView.findViewById(R.id.project_lang)
    ) : RecyclerView.ViewHolder(itemView)

    interface ItemClickListener {
        fun onItemClicked(position: Int)
    }
}
