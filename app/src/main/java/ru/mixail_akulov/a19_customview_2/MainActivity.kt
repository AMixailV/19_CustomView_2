package ru.mixail_akulov.a19_customview_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.mixail_akulov.a19_customview_2.databinding.ActivityMainBinding
import kotlin.properties.Delegates
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isFirstPlayer by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setup UI
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Восстановить или инициализировать поле и переменные
        val field = savedInstanceState?.getParcelable<TicTacToeField.Memento>(KEY_FIELD)?.restoreField() ?: TicTacToeField(10, 10)
        binding.ticTacToeField.field = field
        isFirstPlayer = savedInstanceState?.getBoolean(KEY_IS_FIRST_PLAYER, true) ?: true

        // прослушивание действий пользователя
        binding.ticTacToeField.actionListener = { row, column, currentField ->
            // пользователь нажал/выбрал cell[row, column]

            // получить текущее значение ячейки
            val cell = currentField.getCell(row, column)
            if (cell == Cell.EMPTY) {
                // ячейка пуста, меняем ее на X или O
                if (isFirstPlayer) {
                    currentField.setCell(row, column, Cell.PLAYER_1)
                } else {
                    currentField.setCell(row, column, Cell.PLAYER_2)
                }
                isFirstPlayer = !isFirstPlayer
            }
        }

        binding.randomFieldButton.setOnClickListener {
            // generate random empty field
            binding.ticTacToeField.field = TicTacToeField(
                Random.nextInt(3, 100),
                Random.nextInt(3, 100)
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val field = binding.ticTacToeField.field
        outState.putParcelable(KEY_FIELD, field!!.saveState())
        outState.putBoolean(KEY_IS_FIRST_PLAYER, isFirstPlayer)
    }

    companion object {
        private const val KEY_FIELD = "KEY_FIELD"
        private const val KEY_IS_FIRST_PLAYER = "KEY_IS_FIRST_PLAYER"
    }

}