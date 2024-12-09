package org.esei.dm.adivinarelescudo;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preguntas_easy {
    private List<Pregunta> preguntas;

    public Preguntas_easy(Context context) {
        preguntas = new ArrayList<>();
        GameDatabase gameDatabase = new GameDatabase(context);

        // Configurar las 10 preguntas manualmente
        preguntas.add(new Pregunta(
                1, // ID del escudo
                Arrays.asList("FC Barcelona", "Real Madrid", "Atlético Madrid", "Valencia"),
                "FC Barcelona"
        ));
        preguntas.add(new Pregunta(
                2, // ID del escudo
                Arrays.asList("Sevilla", "Real Sociedad", "Real Madrid", "Betis"),
                "Real Madrid"
        ));
        // Agrega más preguntas aquí manualmente, asegurándote de que los IDs sean válidos.
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
