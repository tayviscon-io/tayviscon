package ru.tayviscon.tayvisconrenderer.markup;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Controller
public class MarkupController {

    private final List<MarkupRenderer> renderers;

    public MarkupController(List<MarkupRenderer> renderers) {
        this.renderers = renderers;
    }

    @PostMapping(path = "/document", produces = "text/html")
    public ResponseEntity<String> renderMarkup (@RequestHeader("Content-Type")MediaType contentType, @RequestBody String markup){
        return renderers.stream()
                .filter(renderer -> renderer.canRender(contentType))
                .findFirst()
                .map(renderer -> ResponseEntity.ok(renderer.renderToHtml(markup) + "\n<!-- Отрисовано Tayviscon-Renderer сервисом>"))
                .orElse(ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build());
    }
}
