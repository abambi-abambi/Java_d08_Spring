package edu.school21.classes;

import interfaces.PreProcessor;

public class PreProcessorToLowerImpl implements PreProcessor {

    @Override
    public String preProcess(String str) {
        return str.toLowerCase();
    }

}