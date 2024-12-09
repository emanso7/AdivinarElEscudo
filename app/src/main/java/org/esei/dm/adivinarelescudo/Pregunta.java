package org.esei.dm.adivinarelescudo;

import java.util.List;

public class Pregunta {
    private int escudoId; // ID del escudo en la base de datos
    private List<String> opciones; // Opciones de respuesta
    private String respuestaCorrecta; // Respuesta correcta

    public Pregunta(int escudoId, List<String> opciones, String respuestaCorrecta) {
        this.escudoId = escudoId;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public int getEscudoId() {
        return escudoId;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
}
