package ru.tayviscon.tayvisconrenderer.guides;

import lombok.Getter;

@Getter
public class GuideRenderingException extends RuntimeException {
    private final String repositoryName;
    public GuideRenderingException(String repositoryName, Throwable cause) {
        super("Невозможно отрисовать руководство [" + repositoryName + "]", cause);
        this.repositoryName = repositoryName;
    }
}
