package com.mygdx.game;

// interfaz para el patron strategy
// define como se generan los objetos cayendo
public interface EstrategiaGeneracion {
    ObjetoCayendo generarObjeto(GestorDeRecursos recursos);
}