package com.example.pokedexquivamarcherstp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokeAdapter(var dataSet: MutableList<MainActivity.Pokemon>) :
    RecyclerView.Adapter<PokeAdapter.ViewHolder>() {
    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val pokemonName: TextView = view.findViewById(R.id.pokemonName)
        val pokemonID: TextView = view.findViewById(R.id.pokemonID)
        val pokemonSprite: ImageView = view.findViewById(R.id.pokemonLogo)


        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)

            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.pokemon_list_item, viewGroup, false)

        return ViewHolder(view, mListener)

    }
    // SPRITE POKEMON


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.pokemonName.text = dataSet[position].info.name
        viewHolder.pokemonID.text = dataSet[position].info.id.toString()


        var url = dataSet[position].info.sprites.versions.generationVii.icons.front_default

        Glide.with(viewHolder.itemView)
            .load(url)
            .into(viewHolder.pokemonSprite)


    }

    override fun getItemCount() = dataSet.size


    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position : Int)



    }


    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }





}