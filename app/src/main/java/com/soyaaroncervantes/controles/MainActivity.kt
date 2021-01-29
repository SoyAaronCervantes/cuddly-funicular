package com.soyaaroncervantes.controles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private val genresMovie = arrayOf(
    "action", "adventures", "drama", "absurdist", "comedy", "crime", "fantasy", "historical",
    "horror", "magical_realism","mystery", "paranoid_fiction", "philosophical", "political",
    "romance", "saga", "satire", "science_fiction", "social", "speculative", "thriller", "urban",
    "western"
  )

  private var actions = arrayOf("sell", "rent")

  private var contentAdult = false
  private var checkboxMap = hashMapOf<String, Boolean>()
  private var genresMap = hashMapOf<String, Boolean>()

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val radioGroup = radioGroup__content
    val spinner = spinner__genres
    val radioButtonConfirmed = radioButton__confirmed
    val checkBoxSell = checkBox__sell
    val checkBoxRent = checkBox__rent

    genresMovie.forEach { genre -> genresMap[ genre ] = false }
    actions.forEach { action-> checkboxMap[action] = false }

    /** Spinner Element */
    if ( spinner != null ) {
      val arrayAdapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, genresMovie )
      arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item )
      spinner.adapter = arrayAdapter

      spinner.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected( parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
          val genre = genresMovie[position]
          genresMap = hashMapOf()
          genresMap[ genre ] = true
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          genresMap = hashMapOf()
        }
      }
    }

    /** Radio Group Element */
    radioGroup.setOnCheckedChangeListener { _, _ ->
      contentAdult = radioButtonConfirmed.id == radioGroup.checkedRadioButtonId
    }

    /** Input text */
    layout__inputText__name.editText?.addTextChangedListener( object : TextWatcher {
      override fun beforeTextChanged( charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
        layout__inputText__name.error = null
      }

      override fun onTextChanged( charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if( charSequence?.length!! > layout__inputText__name.counterMaxLength ) {
          layout__inputText__name.error = "La cadena llego a su limte"
        }
      }

      override fun afterTextChanged( editable: Editable?) {}
    })

    /** Checkbox Element */
    checkBoxRent.setOnCheckedChangeListener { _, isChecked -> checkboxMap["rent"] = isChecked }
    checkBoxSell.setOnCheckedChangeListener { _, isChecked -> checkboxMap["sell"] = isChecked }

    button__submit.setOnClickListener {

      Log.d("[Logger] Checkbox Map", checkboxMap.toString() )
      Log.d("[Logger] Genres Map", genresMap.filter { it.value }.toString() )
      Log.d("[Logger] Radio Group", contentAdult.toString() )
      Log.d("[Logger] Text Input", layout__inputText__name.editText?.text.toString() )

      val detailIntent = Intent( this, DetailActivity::class.java )
      val detailBundle = Bundle()
      val inputText = layout__inputText__name.editText?.text.toString()

      detailBundle.putSerializable("Actions", checkboxMap )
      detailBundle.putSerializable("Genres", genresMap )
      detailBundle.putBoolean("AdultContent", contentAdult )
      detailBundle.putString("Input", inputText )

      detailIntent.putExtras(detailBundle)

      startActivity(  detailIntent )

    }

  }

}