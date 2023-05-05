package fr.turt.turtapp.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.turt.turtapp.MainActivity
import fr.turt.turtapp.PlantModel
import fr.turt.turtapp.PlantRepository
import fr.turt.turtapp.R
import kotlin.coroutines.coroutineContext

class PlantAdapter(
    private val context: MainActivity,
    private val plantList: List<PlantModel>,
    private val layoutId : Int
) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    //Boite pour ranger tous les composants a controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //Image de la plante
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName : TextView? = view.findViewById(R.id.name_item)
        val plantDescription : TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder (view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //recuper les info de la plante
        val currentPlant = plantList[position]

        // recuper le repository
        val repo = PlantRepository()

        //utiliser glide pour recup l'image a partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        // mettre a jour le nom de la plante
        holder.plantName?.text = currentPlant.name

        //mettre a jour la description de la plante
        holder.plantDescription?.text = currentPlant.description

        //verifier si la plante a ete like
        if (currentPlant.liked){
            holder.starIcon.setImageResource(R.drawable.ic_star)
        }
        else {
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }
        // rajouter une interaction sur cette etoile
        holder.starIcon.setOnClickListener{
            // inverser si le bouton est like ou non
            currentPlant.liked = !currentPlant.liked
            // mettre a jour l'objet de la plante
            repo.updatePlant(currentPlant)
        }
    }


    override fun getItemCount(): Int = plantList.size
}