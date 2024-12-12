package org.esei.dm.adivinarelescudo.Questions;

import android.content.Context;

import org.esei.dm.adivinarelescudo.Database.AppDatabaseManager;
import org.esei.dm.adivinarelescudo.Questions.Pregunta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preguntas_medium {
    private List<Pregunta> preguntas;

    public Preguntas_medium(Context context) {
        preguntas = new ArrayList<>();
        AppDatabaseManager gameDatabase = new AppDatabaseManager(context);
        gameDatabase.open();
        // Configurar las 8 preguntas manualmente
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Granada"), // ID del escudo
                Arrays.asList("Granada", "Real Betis", "Rayo Vallecano", "UD Almería"),
                "Granada"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Real Zaragoza"), // ID del escudo
                Arrays.asList("Deportivo La Coruña", "Real Zaragoza", "Real Valladolid", "Celta"),
                "Real Zaragoza"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Albacete"), // ID del escudo
                Arrays.asList("Albacete Balompié", "CD Lugo", "SD Ponferradina", "CD Mirandés"),
                "Albacete Balompié"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Cádiz"), // ID del escudo
                Arrays.asList("Cádiz CF", "UD Las Palmas", "Málaga CF", "Granada"),
                "Cádiz CF"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Córdoba"), // ID del escudo
                Arrays.asList("Córdoba CF", "Real Jaén", "Recreativo de Huelva", "Betis"),
                "Córdoba CF"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Oviedo"), // ID del escudo
                Arrays.asList("Real Oviedo", "Racing de Santander", "CD Numancia", "UD Logroñés"),
                "Real Oviedo"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Sporting Gijón"), // ID del escudo
                Arrays.asList("Sporting Gijón", "Cádiz", "Zaragoza", "Granada"),
                "Sporting Gijón"
        ));
        preguntas.add(new Pregunta(
                gameDatabase.getEscudoIdByName("Tenerife"), // ID del escudo
                Arrays.asList("Tenerife", "Mallorca", "Córdoba", "Zaragoza"),
                "Tenerife"
        ));
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
