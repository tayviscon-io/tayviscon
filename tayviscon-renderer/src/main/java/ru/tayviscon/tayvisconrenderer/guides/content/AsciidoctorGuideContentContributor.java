package ru.tayviscon.tayvisconrenderer.guides.content;

import org.asciidoctor.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.tayviscon.tayvisconrenderer.guides.GuideContentModel;
import ru.tayviscon.tayvisconrenderer.guides.GuideRenderingException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

@Component
public class AsciidoctorGuideContentContributor implements GuideContentContributor {

    private static final String README_FILENAME = "README.adoc";
    private final Asciidoctor asciidoctor;

    public AsciidoctorGuideContentContributor(Asciidoctor asciidoctor) {
        this.asciidoctor = asciidoctor;
    }

    @Override
    public void contribute(GuideContentModel guideContent, File repositoryRoot) {
        try {
            Attributes attributes = new Attributes();
            attributes.setAllowUriRead(true);
            attributes.setSkipFrontMatter(true);
            File readmeAdocFile = new File(repositoryRoot.getAbsolutePath() + File.separator + README_FILENAME);
            OptionsBuilder options = Options.builder()
                    .safe(SafeMode.SAFE)
                    .baseDir(repositoryRoot)
                    .headerFooter(true)
                    .attributes(attributes);
            StringWriter writer = new StringWriter();
            asciidoctor.convert(new FileReader(readmeAdocFile), writer, options);
            Document doc = Jsoup.parse(writer.toString());
            guideContent.setContent(doc.select("#content").html() + "\n<!-- Файл отрисован сервисом Tayviscon Renderer-->");
            guideContent.setTableOfContent(findTableOfContent(doc));
        } catch (IOException exception) {
            throw new GuideRenderingException(guideContent.getName(), exception);
        }

    }

    private String findTableOfContent(Document doc) {
        Elements toc =  doc.select("div#toc > ul.sectlevel1");
        toc.select("ul.sectlevel2").forEach(subsection -> subsection.remove());
        toc.forEach(part -> part.select("a[href]").stream()
                .filter(anchor -> doc.select(anchor.attr("href")).get(0).parent()
                        .classNames().stream()
                        .anyMatch(clazz -> clazz.startsWith("reveal")))
                .forEach(href -> href.parent().remove()));
        return toc.toString();
    }
}
