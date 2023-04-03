package ru.tayviscon.tayvisconrenderer.markup;

import org.pegdown.LinkRenderer;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.HeaderNode;

import java.util.Map;

public class MarkdownToHtmlSerializer extends ToHtmlSerializer {

    MarkdownToHtmlSerializer(LinkRenderer linkRenderer, Map<String, VerbatimSerializer> verbatimSerializers) {
        super(linkRenderer, verbatimSerializers);
    }

    @Override
    public void visit(HeaderNode node) {

    }

}
