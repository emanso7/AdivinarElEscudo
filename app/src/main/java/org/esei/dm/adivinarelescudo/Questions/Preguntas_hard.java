package org.esei.dm.adivinarelescudo.Questions;

import android.content.Context;

import org.esei.dm.adivinarelescudo.database.AppDatabaseManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preguntas_hard {
    private List<Pregunta> preguntas;

    public Preguntas_hard(Context context) {
        preguntas = new ArrayList<>();
        AppDatabaseManager gameDatabase = new AppDatabaseManager(context);
        gameDatabase.open();
        // Configurar las 10 preguntas manualmente
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Allariz"), // ID del escudo
                Arrays.asList("Maside", "Seixalbo", "Viana", "Allariz"),
                "Allariz"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Corgomo"), // ID del escudo
                Arrays.asList("Melias", "Polígono", "Córgomo", "Loñoá"),
                "Córgomo"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Antela"), // ID del escudo
                Arrays.asList("Cartelle", "Antela", "Celanova", "Carballiño"),
                "Antela"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Peroxa"), // ID del escudo
                Arrays.asList("A Peroxa", "Velle", "Muiños", "Maceda"),
                "A Peroxa"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Maside"), // ID del escudo
                Arrays.asList("Nogueira", "A Peroxa", "Seixalbo", "Maside"),
                "Maside"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Monterrei"), // ID del escudo
                Arrays.asList("Loñoá", "Maceda", "Monterrei", "Polígono"),
                "Monterrei"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Muinos"), // ID del escudo
                Arrays.asList("Melias", "Muiños", "Monterrei", "Carballiño"),
                "Muiños"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Viana"), // ID del escudo
                Arrays.asList("Viana", "Nogueira", "Allariz", "Melias"),
                "Viana"
        ));

    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
