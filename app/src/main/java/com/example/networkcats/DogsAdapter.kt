package com.example.networkcats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation

class DogsAdapter(
) :RecyclerView.Adapter<DogsAdapter.CatViewHolder>(), Filterable {
    private val dogs: MutableList<DogResponse> = mutableListOf()
    private var dogsFilterLists: MutableList<DogResponse> = dogs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(dog = dogs[position], position = position)
    }

    override fun getItemCount() = dogs.size

    fun addDogs(newDogs: List<DogResponse>) {
        dogs += newDogs
        notifyDataSetChanged()
    }


    fun cleanDogs() {
        dogs.clear()
        notifyDataSetChanged()
    }


    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val label: TextView = itemView.findViewById(R.id.label)
        private val image: ImageView = itemView.findViewById(R.id.image)

        fun bind(dog: DogResponse, position: Int) {
            if (!dog.breeds.isEmpty())
                label.text = "Песик по имени ${dog.breeds[0].name}"
            else
                label.text = "Песик"
            image.load(dog.url) {
                crossfade(true)
                placeholder(R.mipmap.ic_launcher)
                transformations(CircleCropTransformation())
            }
        }

    }

//    private var onNothingFound: (() -> Unit)? = null

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
//                dogsFilterLists.clear()
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    dogsFilterLists = dogs
                } else {
                    val filteredList: MutableList<DogResponse> = ArrayList()
                    for (row in dogs) {
                        if (row.breeds.isNotEmpty() && row.breeds[0].name.contains(charString)) {
                            filteredList.add(row)
                        }
                    }
                    dogsFilterLists = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = dogsFilterLists
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
//                dogsFilterLists = filterResults.values as MutableList<DogResponse>
//
//                // refresh the list with filtered data
//                notifyDataSetChanged()
                dogsFilterLists.clear()
                dogsFilterLists.addAll(filterResults.values as MutableList<DogResponse>)
                notifyDataSetChanged();
            }
        }
    }

}

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            private val filterResults = FilterResults()
//
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                dogsFilterLists.clear()
//                if (constraint.isNullOrBlank()) {
//                    dogsFilterLists.addAll(dogs)
//                } else {
//                    val filterPattern = constraint.toString().trim { it <= ' ' }
//                    if (filterPattern.isNotEmpty() && getItemCount() != 0) {
//                        for (item in 0..dogs.size) {
//                            if (dogs[item].breeds[0].name.contains(filterPattern)) {//вроде тут
//                                dogsFilterLists.add(dogs[item])
//                            }
//                        }
//                    }
//                }
//                return filterResults.also {
//                    it.values = dogsFilterLists
//                }
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                if (dogsFilterLists.isNullOrEmpty())
//                    onNothingFound?.invoke()
//                notifyDataSetChanged()
//
//            }
//        }
//    }
//}
