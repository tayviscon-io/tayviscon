package ru.tayviscon.tayvisconbackend.support.nav;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableFactory {

    public static Pageable all() {return build(0, Integer.MAX_VALUE);}

    public static Pageable first(int count) {return build(0, count);}

    public static Pageable forList(int page) {
        return build(page - 1, 10);
    }

    public static Pageable forDashboard(int page) {
        return build(-1, 30);
    }

    private static Pageable build(int page, int pageSize) {
        return PageRequest.of(page, pageSize, Sort.Direction.DESC, "publishAt");
    }
}
