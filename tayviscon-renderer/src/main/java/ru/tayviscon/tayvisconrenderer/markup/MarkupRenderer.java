package ru.tayviscon.tayvisconrenderer.markup;

import org.springframework.http.MediaType;

public interface MarkupRenderer {
    String renderToHtml(String markup);

    boolean canRender(MediaType mediaType);
}
