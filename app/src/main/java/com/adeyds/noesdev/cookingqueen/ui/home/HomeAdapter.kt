package com.adeyds.noesdev.cookingqueen.ui.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adeyds.noesdev.cookingqueen.R
import com.adeyds.noesdev.cookingqueen.ui.model.Resep
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.footer_row.view.*
import kotlinx.android.synthetic.main.row_item.view.*


class HomeAdapter(
        private val recipes: List<Resep.ResultsItem>?,
        var itemClick: recipeClickListener,
        var page: recipePageListener,
        var numpage: Int) :
        RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val VIEW_TYPE_FOOTER = 733
    private val VIEW_TYPE_CELL = 732

    override fun getItemViewType(position: Int): Int {
        if (recipes?.size == position){
            return VIEW_TYPE_FOOTER
        }else return VIEW_TYPE_CELL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        if (viewType == VIEW_TYPE_CELL) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)

        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.footer_row, parent, false)
        }
        return ViewHolder(view, itemClick, page)
    }

    override fun getItemCount(): Int {
        if (recipes == null) return 0
        else return recipes.size.plus(1)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == recipes?.size) {
            holder.bindPage(numpage)
        } else  {
            holder.bindData(recipes, position)
        }
    }


    interface recipeClickListener {
        fun getItem(position: Int)
    }

    interface recipePageListener {
        fun page(isNext: Boolean)
    }


    class ViewHolder(itemView: View,
                     var itemClick: recipeClickListener,
                     var page: recipePageListener
    ) : RecyclerView.ViewHolder(itemView) {


        fun bindPage(numpage: Int) {
            itemView.tv_page.text = numpage.toString()
            itemView.btn_next.setOnClickListener {
                page.page(true)
            }

            itemView.btn_prev.setOnClickListener {

                page.page(false)
            }
        }

        fun bindData(recipe: List<Resep.ResultsItem>?, position: Int) {

            itemView.tv_judul.text = recipe!!.get(position).title
            itemView.tv_ingredients.text = recipe.get(position).ingredients

            Glide.with(itemView.context).apply {
                RequestOptions.fitCenterTransform()
                        .error(R.drawable.food)
                        .placeholder(R.drawable.food)
            }.load(recipe.get(position).thumbnail).into(itemView.img_thumb)
            itemView.setOnClickListener(View.OnClickListener {
                itemClick.getItem(adapterPosition)
            })
        }


    }
}
