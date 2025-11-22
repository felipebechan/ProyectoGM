package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;

// estrategia de generacion normal: los objetos caen desde arriba
public class EstrategiaGeneracionNormal implements EstrategiaGeneracion {
    
    @Override
    public ObjetoCayendo generarObjeto(GestorDeRecursos recursos) {
        float x = MathUtils.random(0, 800 - 64);
        int tipo = MathUtils.random(1, 100);
        
        // probabilidades de generacion
        if (tipo <= 35) 
            return new Fruta(recursos.getManzanaImg(), x, 480, recursos.getDropSound());
        else if (tipo <= 70) 
            return new Fruta(recursos.getSandiaImg(), x, 480, recursos.getDropSound());
        else if (tipo <= 85) 
            return new Bomba(recursos.getBombaImg(), x, 480);
        else if (tipo <= 90) 
            return new PowerUpEstrella(recursos.getEstrellaImg(), x, 480);
        else if (tipo <= 93) 
            return new PowerUpGiro(recursos.getGiroImg(), x, 480);
        else if (tipo <= 96) 
            return new PowerUpTamanio(recursos.getTamanioImg(), x, 480);
        else 
            return new PowerUpVelocidad(recursos.getVelocidadImg(), x, 480);
    }
}