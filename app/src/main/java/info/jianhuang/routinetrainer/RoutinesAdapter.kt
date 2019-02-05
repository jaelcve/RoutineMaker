package info.jianhuang.routinetrainer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.single_card.view.*

class RoutinesAdapter(private val routines: List<Routine>) : RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_card, parent, false)
        return RoutineViewHolder(view)
    }

//    override fun getItemCount(): Int {
//        return routines.size  //To change body of created functions use File | Settings | File Templates.
//    }

    override fun getItemCount() = routines.size


    override fun onBindViewHolder(holder: RoutineViewHolder, index: Int) {
            val routine = routines[index]
            holder.card.tv_title.text = routine.title
            holder.card.iv_icon.setImageURI(routine.image)
            holder.card.tv_description.text = routine.description
    }


    class RoutineViewHolder(val card: View) : RecyclerView.ViewHolder(card)
}