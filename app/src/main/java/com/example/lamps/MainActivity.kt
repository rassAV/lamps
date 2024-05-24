package com.example.lamps

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

data class Coord(val x: Int, val y: Int)

class MainActivity : AppCompatActivity() {

    private lateinit var tiles: Array<Array<View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tiles = arrayOf(
            arrayOf(findViewById(R.id.t00), findViewById(R.id.t01), findViewById(R.id.t02), findViewById(R.id.t03)),
            arrayOf(findViewById(R.id.t10), findViewById(R.id.t11), findViewById(R.id.t12), findViewById(R.id.t13)),
            arrayOf(findViewById(R.id.t20), findViewById(R.id.t21), findViewById(R.id.t22), findViewById(R.id.t23)),
            arrayOf(findViewById(R.id.t30), findViewById(R.id.t31), findViewById(R.id.t32), findViewById(R.id.t33))
        )

        initField()
        addRandomFilledCircles(7)
        Toast.makeText(this, "Field generated", Toast.LENGTH_SHORT).show()
    }

    fun onClick(v: View) {
        val cord = getCoordFromString(v.id.toString())

        // Toggle entire row and column
        for (i in tiles.indices) {
            toggleLight(i, cord.y)
            toggleLight(cord.x, i)
        }

        checkVictory()
    }

    private fun getCoordFromString(s: String): Coord {
        val id = (s[8].toString() + s[9].toString()).toInt() - 20
        return Coord(id / 4, id % 4)
    }

    private fun toggleLight(row: Int, col: Int) {
        if (row in 0 until tiles.size && col in 0 until tiles[0].size) {
            val tile = tiles[row][col]
            val tag = tile.tag as String
            if (tag == "outline") {
                tile.setBackgroundResource(R.drawable.circle_filled)
                tile.tag = "filled"
            } else {
                tile.setBackgroundResource(R.drawable.circle_outline)
                tile.tag = "outline"
            }
        }
    }

    private fun checkVictory() {
        var counter = 0
        for (row in tiles) {
            for (tile in row) {
                val tag = tile.tag as String
                if (tag == "filled") {
                    counter++
                }
            }
        }
        if (counter == 0 || counter == tiles.size * tiles[0].size) {
            Toast.makeText(this, "YOU WON!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initField() {
        for (row in tiles) {
            for (tile in row) {
                tile.setBackgroundResource(R.drawable.circle_outline)
                tile.tag = "outline" // Устанавливаем начальный тег для всех кругов
            }
        }
    }

    private fun addRandomFilledCircles(count: Int) {
        var remaining = count
        while (remaining > 0) {
            val x = Random.nextInt(tiles.size)
            val y = Random.nextInt(tiles[0].size)
            val tile = tiles[x][y]
            if (tile.tag as String != "filled") {
                toggleLight(x, y)
                remaining--
            }
        }
    }
}