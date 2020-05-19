package ru.nc.portal.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nc.portal.model.Page;
import ru.nc.portal.model.PageType;
import ru.nc.portal.model.dto.PageDTO;
import ru.nc.portal.security.CurrentUser;
import ru.nc.portal.security.UserPrincipal;
import ru.nc.portal.service.PageService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class PageController {

    @Autowired
    public PageService pageService;

    @Autowired
    public ModelMapper modelMapper;

    @GetMapping(value = "/lessons/{lesson_id}/pages")
    @PreAuthorize("hasRole('USER')")
    public List<Page> getAllPagesForLesson(@PathVariable("lesson_id") Long lesson_id){
        return pageService.getAllPagesForLesson(lesson_id);
    }

    @GetMapping(value = "/pages/{page_id}")
    public Page getPageById(@PathVariable("page_id") Long page_id){
        return pageService.getPageById(page_id);
    }

    @PostMapping(value = "/lessons/{lesson_id}/pages")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page> createPageForLesson(@RequestParam(value = "image", required = false) MultipartFile image,
                                                    @PathVariable("lesson_id") Long lesson_id,
                                                    @RequestParam("pageNumber") Integer pageNumber,
                                                    @RequestParam("pageName") String pageName,
                                                    @RequestParam("pageType") String pageType,
                                                    @RequestParam(value = "pageContent", required = false) String pageContent,
                                                    @CurrentUser UserPrincipal userPrincipal) throws IOException {
        Page receivedPage = new Page(pageNumber, pageName, pageContent);
        Page page = pageService.createPageForLesson(lesson_id, receivedPage, userPrincipal.getId(), image, pageType);
        return new ResponseEntity<>(page, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/lessons/{lesson_id}/pages/{page_id}")
    @PreAuthorize("hasRole('USER')")
    public void updatePage(@PathVariable("lesson_id") Long lesson_id,
                           @PathVariable("page_id") Long page_id,
                           @Valid @RequestBody PageDTO pageDTO,
                           @CurrentUser UserPrincipal userPrincipal){
        Page page = convertPageDTOToEntity(pageDTO);
        page.setId(pageDTO.getId());
        pageService.updatePageForLesson(lesson_id, page, userPrincipal.getId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/pages/{page_id}")
    @PreAuthorize("hasRole('USER')")
    public void deletePageById(@PathVariable("page_id") Long page_id,
                               @CurrentUser UserPrincipal userPrincipal){
        pageService.deletePageById(page_id, userPrincipal.getId());
    }

    private Page convertPageDTOToEntity(PageDTO pageDTO) {
        return modelMapper.map(pageDTO, Page.class);
    }
}
