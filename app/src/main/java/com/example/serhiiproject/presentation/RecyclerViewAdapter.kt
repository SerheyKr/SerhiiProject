package com.example.serhiiproject.presentation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.serhiiproject.R
import com.example.serhiiproject.data.remote.model.VideoData

class RecyclerViewAdapter(private var data: MutableList<VideoData>, private val processToNextFragment: (VideoData) -> Unit) :
    RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    fun setData(dataNew: MutableList<VideoData>)
    {
        data.clear()
        data.addAll(dataNew)
        notifyDataSetChanged()
    }
    fun addData(dataNew: VideoData)
    {
        data.add(dataNew)
        notifyDataSetChanged()
    }
    fun clearData()
    {
        data.clear()
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        var iView: ImageView = itemView.findViewById(R.id.imageView)
        var button: Button = itemView.findViewById(R.id.button)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.largeTextView.text = data[position].title
        Glide.with(holder.iView)
            .load(data[position].preview)
            .into(holder.iView)

        holder.button.setOnClickListener {
            processToNextFragment(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}