package com.example.s8081735assignment2.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.s8081735assignment2.data.model.Entity
import com.example.s8081735assignment2.databinding.ItemEntityBinding

// RecycleView adapter for displaying a list of entities objects
// Uses ListAdapter for efficient updates using DiffUtil
class EntitiesAdapter(private val onClick: (Entity) -> Unit) :
    ListAdapter<Entity, EntitiesAdapter.EntityViewHolder>(DiffCallback()) {


        // ViewHolder class binds Entity data to item layout
    class EntityViewHolder(
        private val binding: ItemEntityBinding,
        private val click: (Entity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        // Binds entity data to UI elements
        fun bind(entity: Entity) {
            // Determine the main title to display
            // Prefer technique, fallback to subject if available, else use "Untitled"
            val title = entity.technique?.takeIf { it.isNotBlank() }
                ?: entity.subject?.takeIf { it.isNotBlank() }
                ?: "Untitled"

            // Build a multi-line details string for the entity
            val details = buildString {
                entity.equipment?.let { append("Equipment: $it\n") }
                entity.subject?.let { append("Subject: $it\n") }
                entity.pioneeringPhotographer?.let { append("Pioneer: $it\n") }
                entity.yearIntroduced?.let { append("Year: $it") }
            }.trim().ifEmpty { "No additional information" }

            // Set text values in the item layout
            binding.itemTechnique.text = title
            binding.itemEquipment.text = details

            // Set click listener for the item root to trigger navigation callback
            binding.root.setOnClickListener { click(entity) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        // Inflate the item layout using ViewBinding
        val binding =
            ItemEntityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntityViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        // Bind the Entity object at the current position
        holder.bind(getItem(position))
    }

    // DiffCallback for efficient RecycleView updates
    class DiffCallback : DiffUtil.ItemCallback<Entity>() {
        override fun areItemsTheSame(oldItem: Entity, newItem: Entity): Boolean =
            oldItem.technique == newItem.technique // Use unique field as identifier

        override fun areContentsTheSame(oldItem: Entity, newItem: Entity): Boolean =
            oldItem == newItem // Compare all data fields to detect changes
    }
}

