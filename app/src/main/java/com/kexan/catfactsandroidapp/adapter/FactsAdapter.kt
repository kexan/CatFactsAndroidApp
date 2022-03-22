package com.kexan.catfactsandroidapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kexan.catfactsandroidapp.databinding.CardFactBinding
import com.kexan.catfactsandroidapp.dto.Fact

interface OnInteractionListener {
    fun clickedOnCard(fact: Fact) {}
    fun clickedOnShare(fact: Fact) {}
}

class FactsAdapter(
    private val onInteractionListener: OnInteractionListener,
) : PagingDataAdapter<Fact, FactViewHolder>(FactDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val binding = CardFactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FactViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val fact = getItem(position) ?: return
        holder.bind(fact)
    }

}
class FactViewHolder(
    private val binding: CardFactBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(fact: Fact) {
        binding.apply {
            when (fact.type) {
                "cat" -> {
                    author.text = "Cat fact №" + fact.id.toString()
                }
                "dog" -> {
                    author.text = "Dog fact №" + fact.id.toString()
                }
            }
            published.text = "Published: " + fact.updatedAt
            content.text = fact.text

            card.setOnClickListener {
                onInteractionListener.clickedOnCard(fact)
            }

            share.setOnClickListener {
                onInteractionListener.clickedOnShare(fact)
            }
        }
    }
}

class FactDiffCallback : DiffUtil.ItemCallback<Fact>() {
    override fun areItemsTheSame(oldItem: Fact, newItem: Fact): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: Fact, newItem: Fact): Boolean {
        return oldItem == newItem
    }
}