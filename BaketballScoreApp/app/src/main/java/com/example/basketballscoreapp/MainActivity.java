package com.example.basketballscoreapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.basketballscoreapp.databinding.ActivityMainBinding;

/**
 * Activity principal de la aplicación de marcador de baloncesto.
 * Gestiona la puntuación de ambos equipos, actualiza la interfaz
 * y navega hacia la pantalla de resultados.
 */
public class MainActivity extends AppCompatActivity {

    /** Clave para pasar la puntuación del equipo local mediante Intent. */
    public static final String EXTRA_SCORE_LOCAL = "extra_score_local";

    /** Clave para pasar la puntuación del equipo visitante mediante Intent. */
    public static final String EXTRA_SCORE_VISITANTE = "extra_score_visitante";

    /** Binding generado automáticamente para acceder a las vistas. */
    private ActivityMainBinding binding;

    /** Clave para guardar el estado del marcador local. */
    private static final String STATE_SCORE_LOCAL = "state_score_local";

    /** Clave para guardar el estado del marcador visitante. */
    private static final String STATE_SCORE_VISITANTE = "state_score_visitante";

    /** Puntuación actual del equipo local. */
    private int scoreLocal = 0;

    /** Puntuación actual del equipo visitante. */
    private int scoreVisitante = 0;

    /**
     * Inicializa la Activity, configurando la interfaz y restaurando el estado
     * de la puntuación en caso de rotación o recreación.
     *
     * @param savedInstanceState estado anterior guardado por el sistema, si existe
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Restaurar estado previo
        if (savedInstanceState != null) {
            scoreLocal = savedInstanceState.getInt(STATE_SCORE_LOCAL, 0);
            scoreVisitante = savedInstanceState.getInt(STATE_SCORE_VISITANTE, 0);
        }

        // Listeners equipo Local
        binding.btnLocalMasUno.setText("+1");
        binding.btnLocalMasUno.setOnClickListener(v -> addPointsLocal(1));

        binding.btnLocalMasDos.setText("+2");
        binding.btnLocalMasDos.setOnClickListener(v -> addPointsLocal(2));

        binding.btnLocalMenosUno.setText("-1");
        binding.btnLocalMenosUno.setOnClickListener(v -> subtractPointsLocal(1));

        // Listeners equipo Visitante
        binding.btnVisitanteMasUno.setText("+1");
        binding.btnVisitanteMasUno.setOnClickListener(v -> addPointsVisitante(1));

        binding.btnVisitanteMasDos.setText("+2");
        binding.btnVisitanteMasDos.setOnClickListener(v -> addPointsVisitante(2));

        binding.btnVisitanteMenosUno.setText("-1");
        binding.btnVisitanteMenosUno.setOnClickListener(v -> subtractPointsVisitante(1));

        // Configurar botones de reset y resultados
        setupControlButtons();

        // Mostrar valores iniciales en pantalla
        updateScoreDisplay();
    }

    /**
     * Configura los botones de control (reset y navegación a resultados),
     * permitiendo que se pueda hacer clic tanto en el botón como en el contenedor.
     */
    private void setupControlButtons() {
        binding.btnReset.setOnClickListener(v -> resetScores());
        binding.btnResetContainer.setOnClickListener(v -> resetScores());

        binding.btnResults.setOnClickListener(v -> navigateToResults());
        binding.btnResultsContainer.setOnClickListener(v -> navigateToResults());
    }

    /**
     * Añade puntos al marcador del equipo local.
     *
     * @param points cantidad de puntos a sumar
     */
    private void addPointsLocal(int points) {
        scoreLocal += points;
        updateScoreDisplay();
    }

    /**
     * Resta puntos al marcador del equipo local.
     * No permite que la puntuación quede negativa.
     *
     * @param points cantidad de puntos a restar
     */
    private void subtractPointsLocal(int points) {
        if (scoreLocal - points >= 0) {
            scoreLocal -= points;
            updateScoreDisplay();
        }
    }

    /**
     * Añade puntos al marcador del equipo visitante.
     *
     * @param points cantidad de puntos a sumar
     */
    private void addPointsVisitante(int points) {
        scoreVisitante += points;
        updateScoreDisplay();
    }

    /**
     * Resta puntos al marcador del equipo visitante.
     * No permite que la puntuación quede negativa.
     *
     * @param points cantidad de puntos a restar
     */
    private void subtractPointsVisitante(int points) {
        if (scoreVisitante - points >= 0) {
            scoreVisitante -= points;
            updateScoreDisplay();
        }
    }

    /**
     * Resetea ambos marcadores dejándolos en 0.
     */
    private void resetScores() {
        scoreLocal = 0;
        scoreVisitante = 0;
        updateScoreDisplay();
    }

    /**
     * Actualiza los TextViews de la interfaz con las puntuaciones actuales.
     */
    private void updateScoreDisplay() {
        binding.tvScoreLocal.setText(String.valueOf(scoreLocal));
        binding.tvScoreVisitante.setText(String.valueOf(scoreVisitante));
    }

    /**
     * Navega a {@link ScoreActivity} enviando ambos marcadores mediante Intent.
     */
    private void navigateToResults() {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(EXTRA_SCORE_LOCAL, scoreLocal);
        intent.putExtra(EXTRA_SCORE_VISITANTE, scoreVisitante);
        startActivity(intent);
    }

    /**
     * Guarda el estado de los marcadores antes de que la Activity sea destruida.
     *
     * @param outState bundle donde se guardan los valores
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SCORE_LOCAL, scoreLocal);
        outState.putInt(STATE_SCORE_VISITANTE, scoreVisitante);
    }

    /**
     * Limpia el objeto binding para evitar pérdidas de memoria.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
