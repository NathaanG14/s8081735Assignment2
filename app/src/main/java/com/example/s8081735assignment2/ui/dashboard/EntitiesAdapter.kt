package com.example.s8081735assignment2.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s8081735assignment2.R
import com.example.s8081735assignment2.data.model.Entity

class EntitiesAdapter(
    private var items: List<Entity> = emptyList(),
    private val itemClicked: (Entity) -> Unit
) : RecyclerView.Adapter<EntitiesAdapter.EntityVH>() {

    fun setItems(newItems: List<Entity>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_entity, parent, false)
        return EntityVH(v)
    }

    override fun onBindViewHolder(holder: EntityVH, position: Int) {
        holder.bind(items[position], itemClicked)
    }

    override fun getItemCount(): Int = items.size

    class EntityVH(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.tvEntityTitle)
        private val subtitle = view.findViewById<TextView>(R.id.tvEntitySubtitle)

        fun bind(entity: Entity, click: (Entity) -> Unit) {
            title.text = entity.technique ?: entity.subject ?: "Unknown"
            subtitle.text = entity.equipment ?: entity.pioneeringPhotographer ?: ""
            view.setOnClickListener { click(entity) }
        }
    }
}
