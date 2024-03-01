package com.example.listado_solicitud

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listado_solicitud.ListadoClases
import com.example.listado_solicitud.ListadoClasesAdapter
import com.example.listado_solicitud.ListadoClasesApiService
import com.example.listado_solicitud.ListadoClasesRespuesta
import com.example.listado_solicitud.databinding.ActivityListadoClaseBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListadoClasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListadoClaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ///Inflar actividad con Binding
        binding = ActivityListadoClaseBinding.inflate(layoutInflater)
        ///Require context
        setContentView(binding.root)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofitBuilder.create(ListadoClasesApiService::class.java)

        val call = service.getListadoClases() //Consulta el listado de clases

        call.enqueue(object : Callback<ListadoClasesRespuesta> {

            override fun onResponse(
                call: Call<ListadoClasesRespuesta>,
                response: Response<ListadoClasesRespuesta>
            ) {

                if (response.isSuccessful) {
                    val pokemon: ListadoClasesRespuesta? = response.body()
                    var listadoClasesList = pokemon!!.results

                    //listaPokemones = (listadoClases?.results ?:
                    for (listadoClases in listadoClasesList) {

                        //val p: ListadoClases = listadoClasesList.get(i)
                        //haz lo que quieras con la info del listadoClases
                        //Toast.makeText(this@ListadoClasesActivity,"El consumido es ${listadoClases.name}", Toast.LENGTH_SHORT).show()

                        //Log.i(TAG, "ListadoClases: "+ p.name)
                        // Suponiendo que tienes tu lista de clases lista, inicializa el RecyclerView así:
                        val recyclerView = binding.listListadoClases
                        recyclerView.layoutManager = LinearLayoutManager(this@ListadoClasesActivity)
                        // Suponiendo que listadoClasesList es tu lista de clases
                        recyclerView.adapter = ListadoClasesAdapter(listadoClasesList)
                    }

                } else {
                    //Manejar errores
                }
            }

            override fun onFailure(call: Call<ListadoClasesRespuesta>, t: Throwable) {
                TODO("Not yet implemented")//Manejar errores de la red
            }

        })
    }
}
