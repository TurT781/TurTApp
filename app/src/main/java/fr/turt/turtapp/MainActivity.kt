package fr.turt.turtapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.turt.turtapp.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // charger notre PlantRepository
        val repo = PlantRepository()

        // mettre a jour la liste de plantes
        repo.updateData{
            //Injecter le fragment dans notre boite (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HomeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

}