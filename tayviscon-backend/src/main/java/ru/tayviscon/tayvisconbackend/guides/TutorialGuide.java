package ru.tayviscon.tayvisconbackend.guides;

import ru.tayviscon.tayvisconbackend.renderer.GuideContent;

public class TutorialGuide extends AbstractGuide{
    private static final  String TYPE_LABEL = "Tutorial";

    public TutorialGuide() {
        setTypeLabel(TYPE_LABEL);
    }

    public TutorialGuide(GuideHeader header, GuideContent content) {
        super(TYPE_LABEL, header, content);
    }
}
