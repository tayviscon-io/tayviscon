package ru.tayviscon.tayvisconbackend.guides;

import java.util.Optional;

public interface GuidesRepository <T extends Guide>{
    GuideHeader[] findAll();
    Optional<GuideHeader> findGuideHeaderByName(String name);
    T findByName(String name);
}
