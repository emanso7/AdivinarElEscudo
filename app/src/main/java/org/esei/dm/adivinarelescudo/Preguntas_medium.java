package org.esei.dm.adivinarelescudo;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preguntas_medium {
    private List<Pregunta> preguntas;

    public Preguntas_medium(Context context) {
        preguntas = new ArrayList<>();

        // Configurar las 8 preguntas manualmente
        preguntas.add(new Pregunta(
                1, // ID del escudo
                Arrays.asList("Granada", "Real Betis", "Rayo Vallecano", "UD Almería"),
                "Granada"
        ));
        preguntas.add(new Pregunta(
                2, // ID del escudo
                Arrays.asList("Deportivo La Coruña", "Real Zaragoza", "Real Valladolid", "Celta"),
                "Real Zaragoza"
        ));
        preguntas.add(new Pregunta(
                3, // ID del escudo
                Arrays.asList("Albacete Balompié", "CD Lugo", "SD Ponferradina", "CD Mirandés"),
                "Albacete Balompié"
        ));
        preguntas.add(new Pregunta(
                4, // ID del escudo
                Arrays.asList("Cádiz CF", "UD Las Palmas", "Málaga CF", "Granada"),
                "Cádiz CF"
        ));
        preguntas.add(new Pregunta(
                5, // ID del escudo
                Arrays.asList("Córdoba CF", "Real Jaén", "Recreativo de Huelva", "Betis"),
                "Córdoba CF"
        ));
        preguntas.add(new Pregunta(
                6, // ID del escudo
                Arrays.asList("Real Oviedo", "Racing de Santander", "CD Numancia", "UD Logroñés"),
                "Real Oviedo"
        ));
        preguntas.add(new Pregunta(
                7, // ID del escudo
                Arrays.asList("Sporting Gijón", "Cádiz", "Zaragoza", "Granada"),
                "Sporting Gijón"
        ));
        preguntas.add(new Pregunta(
                8, // ID del escudo
                Arrays.asList("Tenerife", "Mallorca", "Córdoba", "Zaragoza"),
                "Tenerife"
        ));
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
