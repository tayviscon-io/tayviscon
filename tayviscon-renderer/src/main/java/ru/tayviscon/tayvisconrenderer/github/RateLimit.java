package ru.tayviscon.tayvisconrenderer.github;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class RateLimit {
    private final Integer limit;
    private final Integer remaining;
    private final Instant reset;
    private final Integer used;

    @JsonCreator
    public RateLimit(@JsonProperty("rate")Map<String, String> rate) {
        this.limit = Integer.valueOf(rate.get("limit"));
        this.remaining = Integer.valueOf(rate.get("remaining"));
        this.reset = Instant.ofEpochSecond(Long.parseLong(rate.get("reset")));
        this.used = Integer.parseInt(rate.get("used"));
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public Instant getReset() {
        return reset;
    }

    public Integer getUsed() {
        return used;
    }

    public Map<String , Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("limit", this.limit);
        map.put("remaining", this.remaining);
        map.put("reset", this.reset);
        map.put("used", this.used);
        return map;
    }
}
