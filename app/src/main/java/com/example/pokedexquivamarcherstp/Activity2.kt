package com.example.pokedexquivamarcherstp


import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class Activity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokemon_profile)


        val name : TextView = findViewById(R.id.pokemonname)
        val type : TextView = findViewById(R.id.types)
        val id : TextView = findViewById(R.id.id_4)
        val buttonShiny: Button = findViewById(R.id.buttonshiny)

        val bundle : Bundle?= intent.extras
        val pokemon = bundle!!.getSerializable("pokemon") as MainActivity.Pokemon

        if(pokemon.info.types.size==1)
        {
            var types = pokemon.info.types.first().type.name
            type.text = types.toString()
        }
        else
        {
            val types = pokemon.info.types.first().type.name + "," + " " + pokemon.info.types.last().type.name

            type.text = types.toString()
        }





        val ids = pokemon.info.id
        val sprites = pokemon.info.sprites.front_default
        val spritesShiny = pokemon.info.sprites.front_shiny
        name.text = pokemon.name
        id.text = ids.toString()


        var url = sprites
        var urlshiny = spritesShiny
        val imagePath : ImageView = findViewById(R.id.sprite)

        Glide.with(this)
            .load(url)
            .into(imagePath)

        val btn_click = findViewById(R.id.buttonshiny) as Button

        btn_click.setOnClickListener{
            if (buttonShiny.text == "base") {
                Glide.with(this)
                    .load(url)
                    .into(imagePath)
                buttonShiny.text="shiny"
                buttonShiny.setTextColor(resources.getColor(R.color.black))
                buttonShiny.setBackgroundColor(resources.getColor(R.color.gold))
            }
            else
            {
                Glide.with(this)
                    .load(urlshiny)
                    .into(imagePath)
                buttonShiny.text = "base"
                buttonShiny.setTextColor(resources.getColor(R.color.white))
                buttonShiny.setBackgroundColor(resources.getColor(R.color.redpokemon))

            }
        }
    }



}