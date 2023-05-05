package fr.turt.turtapp

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.turt.turtapp.PlantRepository.Singleton.databaseRef
import fr.turt.turtapp.PlantRepository.Singleton.plantList

class PlantRepository {

    object Singleton{
    // se connecter a la reference "plants"
    val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

    // creer une liste qui va contenir nos plantes
    val plantList = arrayListOf<PlantModel>()
    }

    fun updateData(callback: () -> Unit){
        // absorber les donnees depuis la databaseRef -> liste de plantes
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer les anciennes
                plantList.clear()
                // recolter la liste
                for (ds in snapshot.children){
                    // construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    // verifier que la plante n'est pas null
                    if(plant != null) {
                        // ajouter la plante a notre liste
                        plantList.add(plant)
                    }
                }
                // actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }
    // mettre a jour un objet plante en bdd
    fun updatePlant(plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

}
