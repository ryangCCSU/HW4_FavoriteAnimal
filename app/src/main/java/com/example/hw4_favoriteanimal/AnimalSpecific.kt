package com.example.hw4_favoriteanimal


import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_animal_specific.*
import kotlinx.android.synthetic.main.fragment_animal_specific.view.*

/**
 * A simple [Fragment] subclass.
 */
class AnimalSpecific : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate layout for this fragment
        val view = inflater.inflate(R.layout.fragment_animal_specific, container, false)

        //Set on click listener for save button
        view.save_button.setOnClickListener { view -> saveRating(view) }

        return view
    }

    override fun onStart() {
        super.onStart()

        //Determine if any arguments are passed
        if (arguments != null) {
            //Set animal information based on argument passed in
            val animal = arguments?.getString("animal") ?: "Dog"
            updateAnimal(animal)

            //Create SharedPreferences instance to retrieve data
            val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)

            //Get rating based on the animal argument passed in
            val animalRating = when(animal){
                "Dog" -> sharedPreferences?.getString("dog_rating", "?")
                "Cat" -> sharedPreferences?.getString("cat_rating", "?")
                "Bear" -> sharedPreferences?.getString("bear_rating", "?")
                "Rabbit" -> sharedPreferences?.getString("rabbit_rating", "?")
                else -> sharedPreferences?.getString("dog_rating", "?")
            }

            //If the rating is not null convert to float and set the rating bar
            if(animalRating != null){
                rating_bar.rating = animalRating.toFloat()
            }
        }
        else{
            //Load the default animal
            updateAnimal("Dog")

            //Create SharedPreferences instance to retrieve data
            val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)

            //Get rating for dog
            val dogRating = sharedPreferences?.getString("dog_rating", "?")

            //If the rating is not null convert to float and set the rating bar
            if(dogRating != null){
                rating_bar.rating = dogRating.toFloat()
            }
        }
    }

    private fun updateAnimal(animal: String) {

        //Set animal type text
        animal_type.text = animal

        //Based on animal selected, set corresponding image
        val imageId = when(animal){
            "Dog" -> R.drawable.dog
            "Cat" -> R.drawable.cat
            "Bear" -> R.drawable.bear
            "Rabbit" -> R.drawable.rabbit
            else -> R.drawable.dog
        }
        animal_image.setImageResource(imageId)
    }

    private fun saveRating(view: View){
        //Create SharedPreferences instance for edit
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        //Determine the animal the rating is being saved for to set proper key
        val animalRating = when(animal_type.text){
            "Dog" -> "dog_rating"
            "Cat" -> "cat_rating"
            "Bear" -> "bear_rating"
            "Rabbit" -> "rabbit_rating"
            else -> "none_rating"
        }

        //Save rating
        editor?.putString(animalRating, rating_bar.rating.toString())
        //Apply changes
        editor?.apply()

        // Create an instance of the target fragment
        val myAnimalOverview = AnimalOverview()

        //Load animal overview fragment into main container
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, myAnimalOverview)
            .addToBackStack(null)
            .commit()
    }
}