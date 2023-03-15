package ru.tayviscon.tayvisconrenderer.guides;

import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StreamUtils;
import ru.tayviscon.tayvisconrenderer.RendererProperties;
import ru.tayviscon.tayvisconrenderer.github.GithubClient;
import ru.tayviscon.tayvisconrenderer.guides.content.GuideContentContributor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Преобразует тип и название руководства в визульный контент.
 * Загружает из Github репозитория ZIP-архив руководства и разархивирует его локально
 * перед запуском преобразования README.adoc файла посредством Asciidoctor.
 * Результатом преобразования является отрисованный HTML и оглавление.
 */
@Component
public class GuideRenderer {

    private final GithubClient githubClient;
    private final RendererProperties rendererProperties;
    private final List<GuideContentContributor> contributors;

    public GuideRenderer(GithubClient githubClient, RendererProperties rendererProperties, List<GuideContentContributor> contributors) {
        this.githubClient = githubClient;
        this.rendererProperties = rendererProperties;
        this.contributors = contributors;
    }

    public GuideContentModel render(GuideType guideType, String guideName) {
        GuideContentModel guideContent = new GuideContentModel();
        guideContent.setName(guideName);
        String repositoryName = guideType.getPrefix() + guideName;
        String organization = rendererProperties.getGuides().getOrganization();
        String tempFilePrefix = organization + "-" + repositoryName;

        File unzippedRoot = null;
        File zipball = null;
        try {
            byte[] download = githubClient.downloadRepositoryAsZipBall(organization, repositoryName);
            zipball = File.createTempFile(tempFilePrefix, ".zip");
            zipball.deleteOnExit();
            FileOutputStream zipOut = new FileOutputStream(zipball);
            zipOut.write(download);
            zipOut.close();

            try (ZipFile zipFile = new ZipFile(zipball)) {
                for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements(); ) {
                    ZipEntry entry = e.nextElement();
                    if (entry.isDirectory()) {
                        File dir = new File(zipball.getParent() + File.separator + entry.getName());
                        dir.mkdir();
                        if (unzippedRoot == null) {
                            unzippedRoot = dir;
                        }
                    } else {
                        StreamUtils.copy(zipFile.getInputStream(entry),
                                new FileOutputStream(zipball.getParent() + File.separator + entry.getName()));
                    }
                }
            }

            for (GuideContentContributor contentContributor : contributors) {
                contentContributor.contribute(guideContent, unzippedRoot);
            }
            return guideContent;

        } catch (IOException exception) {
            throw new IllegalStateException("Невозможно создать временный файл для ресурса " + tempFilePrefix, exception);
        } finally {
            FileSystemUtils.deleteRecursively(zipball);
            FileSystemUtils.deleteRecursively(unzippedRoot);
        }
    }
}
