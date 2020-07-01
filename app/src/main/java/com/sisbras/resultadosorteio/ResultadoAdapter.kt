package com.sisbras.resultadosorteio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.resultado_item.view.*

class ResultadoAdapter(
    val Result: ArrayList<Resultado>,
    val itemClick: (Resultado) -> Unit
) :
    RecyclerView.Adapter<ResultadoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.resultado_item, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(Result[position])
    }

    override fun getItemCount() = Result.size

    class ViewHolder(view: View, val itemClick: (Resultado) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindForecast(result: Resultado) {
            with(result) {
                itemView.txtSorteio.text = result.nome
                itemView.txtGanhador.text = result.ganhador?.let { pegaGanhador(it) }
                itemView.txtData.text = result.data
                //itemView.setOnClickListener { itemClick(this) }
                //itemView.btnDividirConta.setOnClickListener { itemClick(this) }
            }
        }

        //pegar usuário e email
        fun pegaGanhador(ganhador: String): String? {
            //pegar o usuário
            var nome: String? = null
            if (ganhador != null) {
                if (ganhador.contains(",")) {
                    nome =
                        ganhador.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                } else {
                    nome = ganhador
                }
            }
            return nome
        }
    }


}
