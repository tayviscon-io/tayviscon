package ru.tayviscon.tayvisconbackend.blog;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import ru.tayviscon.tayvisconbackend.renderer.TayvisconRendererClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostContentRenderer {

    private final TayvisconRendererClient tayvisconRendererClient;

    public PostContentRenderer(TayvisconRendererClient tayvisconRendererClient) {
        this.tayvisconRendererClient = tayvisconRendererClient;
    }

    public String render(String content, PostFormat format) {
        if(StringUtils.isEmpty(content)) {
            return "";
        }
        switch (format) {
            case MARKDOWN:
                String renderedMarkdown = this.tayvisconRendererClient.renderMarkdown(content);
                return renderCallouts(decode(renderedMarkdown));
            case ASCIIDOC:
                return this.tayvisconRendererClient.renderAsciidoc(content);
            default:
                throw new IllegalArgumentException("Не поддерживаемый формат поста: [" + format + "]");
        }
    }

    private String decode(String html) {
        Matcher matcher = Pattern.compile("<pre>!(.*)</pre>").matcher(html);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).replace('{', '<').replace('}', '>'));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private String renderCallouts(String html) {
        Pattern calloutPatter = Pattern.compile("\\[callout title=([^\\]]+)\\]([^\\[]*)\\[\\/callout\\]");
        Matcher matcher = calloutPatter.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb,
                    String.format("<div class=\"callout\">\n" + "<div class=\"callout-title\">%s</div>\n" + "%s\n"
                                  + "</div>", matcher.group(1), matcher.group(2)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
