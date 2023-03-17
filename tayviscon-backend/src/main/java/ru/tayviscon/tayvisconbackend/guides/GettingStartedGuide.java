package ru.tayviscon.tayvisconbackend.guides;

import ru.tayviscon.tayvisconbackend.renderer.GuideContent;

public class GettingStartedGuide extends AbstractGuide{

    private static final String GUIDE_TYPE_LABEL = "Getting Started";
    public GettingStartedGuide() {
        this.setTypeLabel(GUIDE_TYPE_LABEL);
    }

    public GettingStartedGuide(GuideHeader header, GuideContent content) {
        super(GUIDE_TYPE_LABEL, header, content);
    }
}
