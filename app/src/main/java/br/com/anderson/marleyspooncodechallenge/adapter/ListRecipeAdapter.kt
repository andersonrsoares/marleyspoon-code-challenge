package br.com.anderson.marleyspooncodechallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.anderson.marleyspooncodechallenge.R
import br.com.anderson.marleyspooncodechallenge.model.Recipe
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.concurrent.Executors

class ListRecipeAdapter : ListAdapter<Recipe, ListRecipeAdapter.Holder>(
    AsyncDifferConfig.Builder<Recipe>(diffCallback)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    var itemOnClick: (Recipe) -> Unit = { _ -> }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRecipeAdapter.Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_recipe, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        binds(holder, getItem(position))
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    private fun binds(holder: Holder, data: Recipe) {
        with(holder) {
            itemView.setOnClickListener {
                itemOnClick(data)
            }

            Glide.with(imageview.context).load(data.photo).apply(
                RequestOptions()
                    .placeholder(R.drawable.image_placeholder)
                    .centerCrop()
            ).into(imageview)

            title.text = data.title
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val imageview: ImageView = view.findViewById(R.id.imageview)
    }

    companion object {
        private val diffCallback: DiffUtil.ItemCallback<Recipe> =
            object : DiffUtil.ItemCallback<Recipe>() {
                override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
