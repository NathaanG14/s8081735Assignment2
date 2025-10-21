package com.example.s8081735assignment2.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.s8081735assignment2.data.model.Entity
import com.example.s8081735assignment2.databinding.ItemEntityBinding

class EntitiesAdapter(private val onClick: (Entity) -> Unit) :
    ListAdapter<Entity, EntitiesAdapter.EntityViewHolder>(DiffCallback()) {

    class EntityViewHolder(
        private val binding: ItemEntityBinding,
        private val click: (Entity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Entity) {
            // Main title (Technique or fallback to Subject)
            val title = entity.technique?.takeIf { it.isNotBlank() }
                ?: entity.subject?.takeIf { it.isNotBlank() }
                ?: "Untitled"

            // Subtitle containing multiple fields if available
            val details = buildString {
                entity.equipment?.let { append("Equipment: $it\n") }
                entity.subject?.let { append("Subject: $it\n") }
                entity.pioneeringPhotographer?.let { append("Pioneer: $it\n") }
                entity.yearIntroduced?.let { append("Year: $it") }
            }.trim().ifEmpty { "No additional information" }

            binding.itemTechnique.text = title
            binding.itemEquipment.text = details

            // Enable click
            binding.root.setOnClickListener { click(entity) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding =
            ItemEntityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntityViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Entity>() {
        override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean =
            oldItem.technique == newItem.technique

        override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean =
            oldItem == newItem
    }
}

