package edu.school21.classes;

import interfaces.Printer;
import interfaces.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {

    private final Renderer  renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String str) {
        if (LocalDateTime.now() != null) {
            renderer.render(LocalDateTime.now() + " " + str);
        }
        else {
            renderer.render(str);
        }
    }

}