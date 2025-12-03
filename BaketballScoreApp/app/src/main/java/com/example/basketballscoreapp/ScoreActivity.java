package com.example.basketballscoreapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.basketballscoreapp.databinding.ActivityScoreBinding;

public class ScoreActivity extends AppCompatActivity {

    // Data Binding
    private ActivityScoreBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar Data Binding
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtener los marcadores del Intent usando las constantes definidas en MainActivity
        int scoreLocal = getIntent().getIntExtra(MainActivity.EXTRA_SCORE_LOCAL, 0);
        int scoreVisitante = getIntent().getIntExtra(MainActivity.EXTRA_SCORE_VISITANTE, 0);

        // Mostrar los marcadores individuales
        binding.tvFinalScoreLocal.setText(String.valueOf(scoreLocal));
        binding.tvFinalScoreVisitante.setText(String.valueOf(scoreVisitante));

        // Calcular y mostrar el resultado
        displayResult(scoreLocal, scoreVisitante);

        // Configurar botón de volver
        binding.btnBack.setOnClickListener(v -> finish());
    }

    /**
     * Calcula el ganador y muestra el mensaje correspondiente
     * @param scoreLocal Marcador del equipo Local
     * @param scoreVisitante Marcador del equipo Visitante
     */
    private void displayResult(int scoreLocal, int scoreVisitante) {
        String resultMessage;
        int resultColor;

        if (scoreLocal > scoreVisitante) {
            // Ganó el equipo Local - Color verde/turquesa
            resultMessage = getString(R.string.result_local_wins);
            resultColor = Color.parseColor("#4ECCA3");
            // Destacar al ganador
            binding.tvFinalScoreLocal.setTextSize(72);
            binding.tvFinalScoreVisitante.setTextSize(56);
            binding.tvFinalScoreVisitante.setAlpha(0.6f);
        } else if (scoreVisitante > scoreLocal) {
            // Ganó el equipo Visitante - Color rojo
            resultMessage = getString(R.string.result_visitante_wins);
            resultColor = Color.parseColor("#E94560");
            // Destacar al ganador
            binding.tvFinalScoreVisitante.setTextSize(72);
            binding.tvFinalScoreLocal.setTextSize(56);
            binding.tvFinalScoreLocal.setAlpha(0.6f);
        } else {
            // Empate - Color dorado
            resultMessage = getString(R.string.result_tie);
            resultColor = Color.parseColor("#FFD700");
            // Cambiar icono para empate
            binding.ivTrophy.setImageResource(android.R.drawable.btn_star_big_on);
        }

        // Aplicar el mensaje y color al TextView
        binding.tvResultMessage.setText(resultMessage);
        binding.tvResultMessage.setTextColor(resultColor);
        binding.ivTrophy.setColorFilter(resultColor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpiar binding para evitar memory leaks
        binding = null;
    }
}