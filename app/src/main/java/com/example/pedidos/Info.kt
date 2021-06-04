package com.example.pedidos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_info.*

class Info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        //Mostra dados do item clicado
        val numero = intent.getStringExtra("numero")
        val data = intent.getStringExtra("data")
        val observacao = intent.getStringExtra("observacao")
        numero_pedido.text = numero
        fechamento.text = data
        mostra_observacao.text = observacao

        //Adiciona uma observação no item
        btn_add_observacao.setOnClickListener{
            if(campo_observacao.text.isNotEmpty()){
                var obs_digitada = campo_observacao.text.toString()
                var url = "http://192.168.0.105/php/adicionaOBS.php?observacao="+ campo_observacao.text.toString() + "&id_item=" + numero
                var rq: RequestQueue=Volley.newRequestQueue(this)
                var sr= StringRequest(Request.Method.GET,url, { response ->
                    Toast.makeText(this, "Criado com sucesso", Toast.LENGTH_LONG).show()
                }, { error ->
                    Toast.makeText(this, "Erro ao criar", Toast.LENGTH_LONG).show()
                })
                rq.add(sr)
                campo_observacao.text.clear()
            }else{
                campo_observacao.error = if(campo_observacao.text.isNotEmpty()) "Digite alguma coisa" else null
            }
        }
    }
}