package com.example.pedidos

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_textview.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity(), OnPedidoClickListener{
    @RequiresApi(Build.VERSION_CODES.O)

    val list = ArrayList<Pedido>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Luiz Alberto Furmann"

        //filtra data
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        //filtra data
        filtra_data.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val parsedDate: String? = if(monthOfYear < 10 && dayOfMonth > 10){
                    "${dayOfMonth}/0${monthOfYear + 1}/${year}"
                } else if(dayOfMonth < 10 && monthOfYear > 10){
                    "0${dayOfMonth}/${monthOfYear + 1}/${year}"
                } else if(dayOfMonth < 10 && monthOfYear < 10){
                    "0${dayOfMonth}/0${monthOfYear + 1}/${year}"
                }else{
                    "0${dayOfMonth}/${monthOfYear + 1}/${year}"
                }
                var data_certa = parsedDate

                var url = "http://192.168.0.105/php/filtraData.php?dia=$data_certa" //seu ip/pasta
                val list = ArrayList<Pedido>()
                var rq: RequestQueue = Volley.newRequestQueue(this)
                var jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->

                    for (x in 0..response.length() - 1){
                        list.add(
                            Pedido(
                                response.getJSONObject(x).getString("id_item"),
                                response.getJSONObject(x).getString("DATE_FORMAT(dia, \"%d/%m/%Y\")"),
                                response.getJSONObject(x).getString("observacao")
                            )
                        )
                    }
                    var adp = PedidoAdapter(list, this)
                    pedido_rv.layoutManager = LinearLayoutManager(this)
                    pedido_rv.adapter = adp
                }, { error ->
                })
                rq.add(jar)
            }, year, month, day)
            dpd.show()
        }

//        da o update no app
        refreshLayout.setOnRefreshListener {
            var url = "http://192.168.0.105/php/mostraPedidos.php" //seu ip/pasta
            var rq: RequestQueue = Volley.newRequestQueue(this)
            var jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->

                for (x in 0..response.length() - 1){
                    list.add(
                        Pedido(
                            response.getJSONObject(x).getString("id_item"),
                            response.getJSONObject(x).getString("DATE_FORMAT(dia, \"%d/%m/%Y\")"),
                            response.getJSONObject(x).getString("observacao")
                        )
                    )
                }
                var adp = PedidoAdapter(list, this)
                pedido_rv.layoutManager = LinearLayoutManager(this)
                pedido_rv.adapter = adp

            }, { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
            })
            campo_pedido.text.clear()
            rq.add(jar)

            //Atualiza a activity para mostrar a notificação
            val intent = intent
            finish()
            startActivity(intent)

            refreshLayout.isRefreshing = false
        }
        //mostra os dados do banco na tela inicial
        var url = "http://192.168.0.105/php/mostraPedidos.php" //seu ip/pasta
        var rq: RequestQueue = Volley.newRequestQueue(this)
        var jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->

            for (x in 0..response.length() - 1) {
                list.add(
                    Pedido(
                        response.getJSONObject(x).getString("id_item"),
                        response.getJSONObject(x).getString("DATE_FORMAT(dia, \"%d/%m/%Y\")"),
                        response.getJSONObject(x).getString("observacao")
                    )
                )
            }
            var adp = PedidoAdapter(list, this)
            pedido_rv.layoutManager = LinearLayoutManager(this)
            pedido_rv.adapter = adp

        }, { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
        })
        rq.add(jar)

        //botão do lado do fechamento para ordenar as datas na ordem inversa
        invert_data.setOnClickListener {
            var url = "http://192.168.0.105/php/mostraPedidosDESC.php" //seu ip/pasta
            var rq: RequestQueue = Volley.newRequestQueue(this)
            val list = ArrayList<Pedido>()
            var jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->

                for (x in 0..response.length() - 1) {
                    list.add(
                        Pedido(
                            response.getJSONObject(x).getString("id_item"),
                            response.getJSONObject(x).getString("DATE_FORMAT(dia, \"%d/%m/%Y\")"),
                            response.getJSONObject(x).getString("observacao")
                        )
                    )
                }
                var adp = PedidoAdapter(list, this)
                pedido_rv.layoutManager = LinearLayoutManager(this)
                pedido_rv.adapter = adp

            }, { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
            })
            rq.add(jar)
        }

        //filtro por item
        busca_pedido.setOnClickListener{
            val num_pedido = campo_pedido.text.toString()
            var url = "http://192.168.0.105/php/filtraPedido.php?id_item=$num_pedido" //seu ip/pasta
            val list = ArrayList<Pedido>()
            var rq: RequestQueue = Volley.newRequestQueue(this)
            var jar = JsonArrayRequest(Request.Method.GET, url, null, { response ->

                for (x in 0..response.length() - 1) {
                    list.add(
                        Pedido(
                            response.getJSONObject(x).getString("id_item"),
                            response.getJSONObject(x).getString("DATE_FORMAT(dia, \"%d/%m/%Y\")"),
                            response.getJSONObject(x).getString("observacao")
                        )
                    )
                }
                var adp = PedidoAdapter(list, this)
                pedido_rv.layoutManager = LinearLayoutManager(this)
                pedido_rv.adapter = adp

            }, { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
            })
            rq.add(jar)
        }
    }

    override fun onPedidoClickListener(position: Int) {
        val intent = Intent(this, Info::class.java)
        intent.putExtra("numero",list[position].numero)
        intent.putExtra("data",list[position].data)
        intent.putExtra("observacao",list[position].observacao)
        startActivity(intent)
    }
}