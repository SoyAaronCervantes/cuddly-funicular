package com.soyaaroncervantes.controles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.Serializable

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val adultContentText: String
        var actionsText = "Actions enabled: "

        val actionsMovieSerializable = intent.getSerializableExtra( "Actions")
        val genreMovieSerializable = intent.getSerializableExtra("Genres")
        val isAdultMovie = intent.getBooleanExtra("AdultContent", false )
        val movieName = intent.getStringExtra("Input")

        val actionsMovie = actionsMovieSerializable as HashMap<*, *>
        val genresMovie = genreMovieSerializable as HashMap<*, *>

        val genre = genresMovie.keys.firstOrNull()
        val genreText = "The genres is: $genre"

        actionsMovie.forEach{ actions -> actionsText += "${actions.key}, " }

        adultContentText = if ( isAdultMovie ) "It has adult content" else "It doesn't have adult content"

        movieNameTextView.text = movieName
        movieGenreTextView.text = genreText
        adultContentTextView.text = adultContentText
        actionsTextView.text = actionsText
    }
}