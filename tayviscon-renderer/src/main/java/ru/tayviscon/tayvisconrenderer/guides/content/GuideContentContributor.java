package ru.tayviscon.tayvisconrenderer.guides.content;

import ru.tayviscon.tayvisconrenderer.guides.GuideContentModel;

import java.io.File;

/**
 * Служит для заполнения информацией содержимого руководства
 */
public interface GuideContentContributor {

    /**
     * Заполняет содержимое руководства посредством извлечения информации из репозитория руководства
     * @param guideContent содержимое руководства, которе необходимо заполнить
     * @param repositoryRoot разархивированная корневая папка руководства
     */
    void contribute(GuideContentModel guideContent, File repositoryRoot);

}
