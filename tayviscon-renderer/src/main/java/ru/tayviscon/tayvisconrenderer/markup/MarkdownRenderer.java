package ru.tayviscon.tayvisconrenderer.markup;

import org.pegdown.Extensions;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.RootNode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.w3c.dom.Text;

import java.util.Collections;

@Component
public class MarkdownRenderer implements MarkupRenderer {

    private final PegDownProcessor pegdown;

    public MarkdownRenderer() {
        this.pegdown = new PegDownProcessor(Extensions.ALL ^ Extensions.ANCHORLINKS);
    }

    private static final MediaType TEXT_MARKDOWN = MediaType.TEXT_MARKDOWN;

    @Override
    public boolean canRender(MediaType mediaType) {
        return TEXT_MARKDOWN.isCompatibleWith(mediaType);
    }

    @Override
    public String renderToHtml(String markup) {
        synchronized (this.pegdown) {
            RootNode asRoot = this.pegdown.parseMarkdown(markup.toCharArray());
            MarkdownToHtmlSerializer serializer = new MarkdownToHtmlSerializer(
                    new LinkRenderer(),
                    Collections.singletonMap(VerbatimSerializer.DEFAULT, PrettifyVerbatimSerializer.INSTANCE));
            return serializer.toHtml(asRoot);
        }
    }
}
