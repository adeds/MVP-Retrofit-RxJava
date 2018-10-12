package com.adeyds.noesdev.cookingqueen.ui.home.homeModel

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.adeyds.noesdev.cookingqueen.R
import com.adeyds.noesdev.cookingqueen.R.id.img_thumb
import com.adeyds.noesdev.cookingqueen.R.id.tv_judul
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


//class MainAdapter(private val context: Context?,
//                  private val recipes: List<Resep.ResultsItem>,
//                  private val listener: (Resep.ResultsItem) -> Unit)
//    : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
class MainAdapter(
        private val recipes: List<Resep.ResultsItem>?,
        var itemClick: recipeClickListener) :
        RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.e("onCreateHolder", " 123123")
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        Log.e("getCount", "${recipes?.size} 123123")
        return recipes?.size ?: 0
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("onBind", "${recipes?.get(position).toString()} 123123")
        holder.bindData(recipes, position)
    }


    interface recipeClickListener {
        fun getItem(position: Int)
    }


    class ViewHolder(itemView: View, var itemClick: recipeClickListener) : RecyclerView.ViewHolder(itemView) {
        var tvJudul: TextView = itemView.findViewById(R.id.tv_judul)
        var tvIng: TextView = itemView.findViewById(R.id.tv_ingredients)
        var imgThumb: ImageView = itemView.findViewById(R.id.img_thumb)

        fun bindData(recipe: List<Resep.ResultsItem>?, position: Int) {
            Log.e("binding", "${recipe?.get(position).toString()} tot123123")
            tvJudul.text = recipe!!.get(position).title
            tvIng.text = recipe!!.get(position).ingredients

            Glide.with(itemView.context).apply {
                RequestOptions.fitCenterTransform()
                        .error(R.drawable.food)
                        .placeholder(R.drawable.food)
            }.load(recipe.get(position).thumbnail).into(imgThumb)
//            textAddress.text = hospitalsList!!.get(position).address
            itemView.setOnClickListener(View.OnClickListener {
                itemClick.getItem(adapterPosition)
            })
        }
    }
}
