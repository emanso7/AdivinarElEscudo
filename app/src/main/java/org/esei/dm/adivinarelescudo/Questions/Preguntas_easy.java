package org.esei.dm.adivinarelescudo.Questions;

import android.content.Context;

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.Questions.Pregunta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preguntas_easy {
    private List<Pregunta> preguntas;

    public Preguntas_easy(Context context) {
        preguntas = new ArrayList<>();
        AppDatabaseManager gameDatabase = new AppDatabaseManager(context);
gameDatabase.open();
        // Configurar las 10 preguntas manualmente
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("FC Barcelona"), // ID del escudo
                Arrays.asList("Valencia", "Real Madrid", "Atlético Madrid", "FC Barcelona"),
                "FC Barcelona"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Real Madrid"), // ID del escudo
                Arrays.asList("Sevilla", "Real Sociedad", "Real Madrid", "Betis"),
                "Real Madrid"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Valencia"), // ID del escudo
                Arrays.asList("Valencia", "Celta", "Mallorca", "Athletic"),
                "Valencia"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Villarreal"), // ID del escudo
                Arrays.asList("Sevilla", "Betis", "Villarreal", "Valencia"),
                "Villarreal"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Celta"), // ID del escudo
                Arrays.asList("Athletic", "Celta", "Valencia", "Atlético Madrid"),
                "Celta"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Mallorca"), // ID del escudo
                Arrays.asList("Mallorca", "Celta", "Villarreal", "Athletic"),
                "Mallorca"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Atlético"), // ID del escudo
                Arrays.asList("Atlético Madrid", "Real Sociedad", "Mallorca", "Athletic"),
                "Atlético Madrid"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Athletic"), // ID del escudo
                Arrays.asList("Athletic", "Betis", "Real Madrid", "Valencia"),
                "Athletic"
        ));

    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
