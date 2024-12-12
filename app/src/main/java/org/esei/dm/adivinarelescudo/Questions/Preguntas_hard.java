package org.esei.dm.adivinarelescudo.Questions;

import android.content.Context;

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.Questions.Pregunta;

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
                gameDatabase.getEscudoIdByName("allariz"), // ID del escudo
                Arrays.asList("Maside", "Seixalbo", "Viana", "Allariz"),
                "Allariz"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("corgomo"), // ID del escudo
                Arrays.asList("Melias", "Polígono", "Córgomo", "Loñoá"),
                "Córgomo"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("antela"), // ID del escudo
                Arrays.asList("Cartelle", "Antela", "Celanova", "Carballiño"),
                "Antela"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("peroxa"), // ID del escudo
                Arrays.asList("A Peroxa", "Velle", "Muiños", "Maceda"),
                "A Peroxa"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("maside"), // ID del escudo
                Arrays.asList("Nogueira", "A Peroxa", "Seixalbo", "Maside Madrid"),
                "Maside"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("monterrei"), // ID del escudo
                Arrays.asList("Loñoá", "Macda", "Monterrei", "Polígono"),
                "Monterrei"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("muinos"), // ID del escudo
                Arrays.asList("Melias", "Muiños", "Monterrei", "Carballiño"),
                "Muiños"
        ));

        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("viana"), // ID del escudo
                Arrays.asList("Viana", "Nogueira", "Allariz", "Melias"),
                "Viana"
        ));

    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
