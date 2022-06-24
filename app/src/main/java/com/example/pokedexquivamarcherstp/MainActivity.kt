package com.example.pokedexquivamarcherstp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

import java.io.Serializable



public class MainActivity : AppCompatActivity() {


        // CLASSES DES SPRITES
                data class Icons(
                    val front_default: String
                ): Serializable
                data class Gen7(
                    val icons: Icons,
                ) : Serializable

                data class SpritesVersions(
                    @SerializedName("generation-vii") val generationVii: Gen7

                ) : Serializable


                data class  PokemonSprites (
                    val front_default: String,
                    val front_shiny: String,
                    val versions: SpritesVersions,
                ) : Serializable



        // CLASSES DES DONNEES
                data class  TypeInfo (
                    val name: String,
                    val url: String,
                ) : Serializable

                data class  PokemonType (
                    val type: TypeInfo,
                ) : Serializable


     // INFOS GLOBALES POKEMON
                data class PokemonInfo (
                    val id: Int,
                    val name: String,
                    val sprites: PokemonSprites,
                    val types: List<PokemonType>,
                )   : Serializable



                data class Pokemon  (
                    val name: String,
                    val url: String,
                    var info: PokemonInfo,
                ) : Serializable


    // CHARGEMENT DES POKEMONS
                data class PokemonList (
                    val results: List<Pokemon>
                )   : Serializable








    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
                    setContentView(R.layout.activity_main)





                    val pokemonRV : RecyclerView = findViewById(R.id.pokemonRecyclerView) // newRecyclerview


                    loadPokemonList(898) { pokemonList ->
                        val pokeAdapter = PokeAdapter(pokemonList.results as MutableList<Pokemon>)

                        pokemonRV.adapter = pokeAdapter
                        pokemonRV.layoutManager = LinearLayoutManager(this@MainActivity)












                            pokeAdapter.setOnItemClickListener(object : PokeAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                val intent = Intent(this@MainActivity, Activity2::class.java)

                                intent.putExtra("pokemon", pokemonList.results[position] as Serializable)

                                startActivity(intent)
                            }





                        })




                        }
                    }




                private fun loadPokemonList (limit: Int, cb: (PokemonList) -> Unit) {

                    val httpAsync = "https://pokeapi.co/api/v2/pokemon?limit=${limit}&offset=0"
                        .httpGet()
                        .responseString { _, _, result ->
                            when (result) {
                                is Result.Failure -> {
                                    val ex = result.getException()
                                    println(ex)
                                }
                                is Result.Success -> {
                                    val data = result.get()

                                    val parsedJson = Gson().fromJson<PokemonList>(data, PokemonList::class.java)

                                    for (i in 0 until limit) {

                                        loadPokemonProfile(parsedJson.results[i].url) { pokemon ->
                                            parsedJson.results[i].info = pokemon

                                            if (parsedJson.results[i].info.id == limit) {
                                                cb(parsedJson)

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    httpAsync.join()

                }

                private fun loadPokemonProfile (url: String, cb: (PokemonInfo) -> Unit) {

                    val httpAsync = url
                        .httpGet()
                        .responseString { _, _, result ->
                            when (result) {
                                is Result.Failure -> {
                                    val ex = result.getException()
                                    println(ex)
                                }
                                is Result.Success -> {
                                    val data = result.get()
                                    val pokemon = Gson().fromJson<PokemonInfo>(data, PokemonInfo::class.java)
                                    cb(pokemon)

                                }
                            }
                        }
                    httpAsync.join()
                }
            }