package edu.school21.classes;

import interfaces.PreProcessor;

public class PreProcessorToUpperImpl implements PreProcessor {

    @Override
    public String preProcess(String str) {
        return str.toUpperCase();
    }

}