package ru.nc.portal.service;

import org.springframework.web.multipart.MultipartFile;
import ru.nc.portal.model.Page;

import java.io.IOException;
import java.util.List;

public interface PageService {
    List<Page> getAllPagesForLesson(Long lesson_id);
    Page getPageById(Long page_id);
    Page createPageForLesson(Long lesson_id, Page page, Long user_id, MultipartFile image, String pageTypeName) throws IOException;
    Page updatePageForLesson(Long lesson_id, Page page, Long user_id);
    void deletePageById(Long page_id, Long user_id);

}
