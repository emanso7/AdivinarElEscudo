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

        preguntas.add(new Pregunta(
                3, // ID del escudo
                Arrays.asList("Valencia", "Celta", "Mallorca", "Athletic"),
                "Valencia"
        ));

        preguntas.add(new Pregunta(
                4, // ID del escudo
                Arrays.asList("Sevilla", "Betis", "Villarreal", "Valencia"),
                "Villarreal"
        ));

        preguntas.add(new Pregunta(
                5, // ID del escudo
                Arrays.asList("Athletic", "Celta", "Valencia", "Atlético Madrid"),
                "Celta"
        ));

        preguntas.add(new Pregunta(
                6, // ID del escudo
                Arrays.asList("Mallorca", "Celta", "Villarreal", "Athletic"),
                "Mallorca"
        ));
        preguntas.add(new Pregunta(
                7, // ID del escudo
                Arrays.asList("Atlético Madrid", "Real Sociedad", "Mallorca", "Athletic"),
                "Atlético Madrid"
        ));

        preguntas.add(new Pregunta(
                8, // ID del escudo
                Arrays.asList("Athletic", "Betis", "Real Madrid", "Valencia"),
                "Athletic"
        ));

    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
