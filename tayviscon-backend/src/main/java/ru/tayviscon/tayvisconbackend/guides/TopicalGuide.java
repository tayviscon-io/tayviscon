package ru.tayviscon.tayvisconbackend.guides;

import ru.tayviscon.tayvisconbackend.renderer.GuideContent;

public class TopicalGuide extends AbstractGuide{
    private static final String TYPE_LABEL = "Topical Guide";

    public TopicalGuide() {
        setTypeLabel(TYPE_LABEL);
    }

    public TopicalGuide(GuideHeader header, GuideContent content) {
        super(TYPE_LABEL, header, content);
    }
}
