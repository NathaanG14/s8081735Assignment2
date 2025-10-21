package com.example.s8081735assignment2.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.s8081735assignment2.data.model.Entity
import com.example.s8081735assignment2.databinding.ItemEntityBinding

// Adapter to display a list of entities in a RecyclerView.
class EntitiesAdapter(private val onClick: (Entity) -> Unit) :
    ListAdapter<Entity, EntitiesAdapter.EntityViewHolder>(DiffCallback()) {

    class EntityViewHolder(
        private val binding: ItemEntityBinding,
        private val click: (Entity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: Entity) {
            // Determine the main title: technique if available, otherwise subject, otherwise "Untitled".
            val title = entity.technique?.takeIf { it.isNotBlank() }
                ?: entity.subject?.takeIf { it.isNotBlank() }
                ?: "Untitled"

            // Construct subtitle with available details
            val details = buildString {
                entity.equipment?.let { append("Equipment: $it\n") }
                entity.subject?.let { append("Subject: $it\n") }
                entity.pioneeringPhotographer?.let { append("Pioneer: $it\n") }
                entity.yearIntroduced?.let { append("Year: $it") }
            }.trim().ifEmpty { "No additional information" }

            // Bind data to UI
            binding.itemTechnique.text = title
            binding.itemEquipment.text = details

            // Set click listener to invoke callback with selected entity
            binding.root.setOnClickListener { click(entity) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        // Inflate item layout.
        val binding =
            ItemEntityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntityViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        // Bind entity data at current position.
        holder.bind(getItem(position))
    }

    // Difficult class to optimize RecycleView updates
    class DiffCallback : DiffUtil.ItemCallback<Entity>() {
        override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean =
            oldItem.technique == newItem.technique // Compare by unique field

        override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean =
            oldItem == newItem // compare entire data
    }
}

