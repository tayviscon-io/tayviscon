package ru.tayviscon.tayvisconbackend.guides;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultGuideHeaderTest {

    private DefaultGuideHeader guide;

    @BeforeEach
    public void setUp() {
        Set<String> projects = new HashSet<>();
        projects.add("tayviscon");
        this.guide = new DefaultGuideHeader("tayviscon-guide", "tayviscon-guides/gs-tayviscon-guide",
                "Tayviscon Guide Title", "Tayviscon Guide Description",
                "https://github.com/tayviscon-guides/gs-tayviscon-guide",
                "git://github.com/tayviscon-guides/gs-tayviscon-guide.git",
                "git@github.com:tayviscon-guides/gs-tayviscon-guide.git",
                "https://github.com/tayviscon-guides/gs-tayviscon-guide.git",
                projects);
    }

    @Test
    public void testHeaderData() {
        assertThat(guide.getName()).isEqualTo("tayviscon-guide");
        assertThat(guide.getRepositoryName()).isEqualTo("tayviscon-guides/gs-tayviscon-guide");
        assertThat(guide.getTitle()).isEqualTo("Tayviscon Guide Title");
        assertThat(guide.getDescription()).isEqualTo("Tayviscon Guide Description");
        assertThat(guide.getGithubUrl()).isEqualTo("https://github.com/tayviscon-guides/gs-tayviscon-guide");
        assertThat(guide.getGitUrl()).isEqualTo("git://github.com/tayviscon-guides/gs-tayviscon-guide.git");
        assertThat(guide.getSshUrl()).isEqualTo("git@github.com:tayviscon-guides/gs-tayviscon-guide.git");
        assertThat(guide.getCloneUrl()).isEqualTo("https://github.com/tayviscon-guides/gs-tayviscon-guide.git");
        assertThat(guide.getZipUrl()).isEqualTo("https://github.com/tayviscon-guides/gs-tayviscon-guide/archive/master.zip");
        assertThat(guide.getProjects()).containsExactly("tayviscon");
    }

    @Test
    public void testEmptyProjectList() {
        guide = new DefaultGuideHeader("tayviscon-guide", "tayviscon-guides/gs-tayviscon-guide",
                "Tayviscon Guide Title", "Tayviscon Guide Description",
                "https://github.com/tayviscon-guides/gs-tayviscon-guide",
                "git://github.com/tayviscon-guides/gs-tayviscon-guide.git",
                "git@github.com:tayviscon-guides/gs-tayviscon-guide.git",
                "https://github.com/tayviscon-guides/gs-tayviscon-guide.git",
                null);
        assertThat(guide.getProjects()).isNotNull().isEmpty();
    }

}
