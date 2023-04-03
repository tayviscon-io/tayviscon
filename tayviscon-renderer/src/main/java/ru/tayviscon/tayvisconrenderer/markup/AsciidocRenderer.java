package ru.tayviscon.tayvisconrenderer.markup;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class AsciidocRenderer implements MarkupRenderer {
    private static final MediaType TEXT_ASCIIDOC = MediaType.parseMediaType("text/asciidoc");
    private final Asciidoctor asciidoctor;

    public AsciidocRenderer(Asciidoctor asciidoctor) {
        this.asciidoctor = asciidoctor;
    }

    @Override
    public boolean canRender(MediaType mediaType) {
        return TEXT_ASCIIDOC.isCompatibleWith(mediaType);
    }

    @Override
    public String renderToHtml(String markup) {
        Attributes attributes = new Attributes();
        attributes.setAllowUriRead(true);
        attributes.setSkipFrontMatter(true);
        attributes.setAttribute("source-highlighter", "prettify");
        attributes.setAttribute("idprefix", "");
        attributes.setAttribute("idseparator", "-");
        attributes.setAnchors(true);
        OptionsBuilder options = OptionsBuilder.options().safe(SafeMode.SAFE).attributes(attributes);
        return asciidoctor.convert(markup, options);
    }
}
