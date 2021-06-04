package com.example.pedidos

class Pedido {
    var numero: String
    var data: String
    var observacao: String

    constructor(numero:String,data:String,observacao: String){
        this.numero=numero
        this.data=data
        this.observacao=observacao
    }
}