package com.example.pedidos

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_info.view.*
import kotlinx.android.synthetic.main.my_textview.*
import kotlinx.android.synthetic.main.my_textview.view.*
import kotlinx.android.synthetic.main.my_textview.view.numero_pedido

class PedidoAdapter(private val list: ArrayList<Pedido>, private val onPedidoClickListener: OnPedidoClickListener):RecyclerView.Adapter<PedidoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder{
        return PedidoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_textview,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = list[position]

        holder.itemView.numero_pedido.text = pedido.numero
        holder.itemView.data_pedido.text = pedido.data
        holder.itemView.texto_observacao.text = pedido.observacao

        //mostra o alerta de observação no item
        if(pedido.observacao == ""){
            holder.itemView.texto_observacao.text = ""
        }else if(pedido.observacao == "null"){
            holder.itemView.texto_observacao.text = ""
        }else{
            holder.itemView.texto_observacao.text = "!"
        }

        holder.itemView.setOnClickListener {
            onPedidoClickListener.onPedidoClickListener(position)
        }
    }
}