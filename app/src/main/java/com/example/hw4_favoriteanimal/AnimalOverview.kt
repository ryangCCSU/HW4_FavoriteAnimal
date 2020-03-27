package com.example.hw4_favoriteanimal


import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_animal_overview.*
import kotlinx.android.synthetic.main.fragment_animal_overview.view.*

/**
 * A simple [Fragment] subclass.
 */
class AnimalOverview : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate layout for this fragment
        val view = inflater.inflate(R.layout.fragment_animal_overview, container, false)

        //Set on click listeners for image buttons
        view.dog_button.setOnClickListener { view -> selectAnimal(view) }
        view.cat_button.setOnClickListener { view -> selectAnimal(view) }
        view.bear_button.setOnClickListener { view -> selectAnimal(view) }
        view.rabbit_button.setOnClickListener { view -> selectAnimal(view) }

        return view
    }

    override fun onStart() {
        super.onStart()

        //Create SharedPreferences instance to retrieve data
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)

        //Get animal ratings
        val dogRating = sharedPreferences?.getString("dog_rating", "?")
        val catRating = sharedPreferences?.getString("cat_rating", "?")
        val bearRating = sharedPreferences?.getString("bear_rating", "?")
        val rabbitRating = sharedPreferences?.getString("rabbit_rating", "?")

        //Set obtained ratings to corresponding textviews
        dog_rating.text = "Your rating: $dogRating"
        cat_rating.text = "Your rating: $catRating"
        bear_rating.text = "Your rating: $bearRating"
        rabbit_rating.text = "Your rating: $rabbitRating"
    }

    private fun selectAnimal(view: View){

        //Create variable to store which animal is selected
        var selectedAnimal: String

        //Set the selection based on the image button clicked
        when(view.id){
            R.id.dog_button -> selectedAnimal = "Dog"
            R.id.cat_button -> selectedAnimal = "Cat"
            R.id.bear_button -> selectedAnimal = "Bear"
            R.id.rabbit_button -> selectedAnimal = "Rabbit"
            else -> selectedAnimal = ""
        }

        //Create instance of target fragment
        val myAnimalSpecific = AnimalSpecific()

        //Put animal data into bundle
        val bundle = Bundle()
        bundle.putString("animal", selectedAnimal)
        //Set bundle to arguments
        myAnimalSpecific.arguments = bundle

        //Check orientation to inflate the specific fragment in appropriate container
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, myAnimalSpecific)
                .addToBackStack(null)
                .commit()
        }
        else{
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.rating_container, myAnimalSpecific)
                .addToBackStack(null)
                .commit()
        }
    }
}