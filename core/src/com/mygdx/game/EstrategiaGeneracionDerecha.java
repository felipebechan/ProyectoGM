package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;

// estrategia de generacion derecha: los objetos vienen desde la izquierda
public class EstrategiaGeneracionDerecha implements EstrategiaGeneracion {
    
    @Override
    public ObjetoCayendo generarObjeto(GestorDeRecursos recursos) {
        float y = MathUtils.random(0, 480 - 64);
        float x = -64; // viene desde la izquierda de la pantalla
        int tipo = MathUtils.random(1, 100);
        
        ObjetoCayendo objeto;
        // menos powerups en modo giro
        if (tipo <= 45) 
            objeto = new Fruta(recursos.getManzanaImg(), x, y, recursos.getDropSound());
        else if (tipo <= 90) 
            objeto = new Fruta(recursos.getSandiaImg(), x, y, recursos.getDropSound());
        else 
            objeto = new Bomba(recursos.getBombaImg(), x, y);
        
        // reorientamos el objeto para que vaya hacia la derecha
        objeto.reorientar(GameScreen.EstadoJuego.GRAVEDAD_DERECHA);
        return objeto;
    }
}