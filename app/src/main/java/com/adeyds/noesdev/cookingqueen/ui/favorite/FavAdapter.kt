package com.adeyds.noesdev.jadwaltandingbola.main


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adeyds.noesdev.cookingqueen.R
import com.adeyds.noesdev.cookingqueen.ui.model.Resep
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_item.*

class FavAdapter(
        private val context: Context?,
        private val resep: List<Resep.ResultsItem>,
        private val listener: (Resep.ResultsItem) -> Unit)
    : RecyclerView.Adapter<FavAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.bindItem(resep[position], listener)

    }

    override fun getItemCount(): Int = resep.size


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bindItem(resep: Resep.ResultsItem, listener: (Resep.ResultsItem) -> Unit) {
            tv_judul.text = resep.title
            tv_ingredients.text = resep.ingredients
            Glide.with(itemView.context).apply {
                RequestOptions.fitCenterTransform()
                        .error(R.drawable.food)
                        .placeholder(R.drawable.food)
            }.load(resep.thumbnail).into(img_thumb)

            containerView.setOnClickListener { listener(resep) }
        }

    }
}