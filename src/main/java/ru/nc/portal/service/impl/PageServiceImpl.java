package ru.nc.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nc.portal.exception.ResourceNotFoundException;
import ru.nc.portal.model.*;
import ru.nc.portal.repository.*;
import ru.nc.portal.service.PageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PageTypeRepository pageTypeRepository;

    @Override
    public List<Page> getAllPagesForLesson(Long lesson_id) {
        return pageRepository.findAllByLessonIdOrderByNumber(lesson_id);
    }

    @Override
    public Page getPageById(Long page_id) {
        return pageRepository.findById(page_id).orElse(null);
    }

    @Override
    public Page createPageForLesson(Long lesson_id, Page page, Long user_id, MultipartFile image, String pageTypeName) throws IOException {
        if (image != null && pageTypeName.equals("image")){
            page.setImage(image.getBytes());
            page.setPageType(pageTypeRepository.findByTypeName("image"));
        }
        else if (pageTypeName.equals("text")){
            page.setPageType(pageTypeRepository.findByTypeName("text"));
        }
        else if (pageTypeName.equals("code")){
            page.setPageType(pageTypeRepository.findByTypeName("code"));
        }
        else
            throw new ResourceNotFoundException(pageTypeName, "type_name", pageTypeName);
        Lesson lesson = lessonRepository.findById(lesson_id).orElseThrow(()->new ResourceNotFoundException("Lesson", "id", lesson_id));
        //incr num of all pages that has num more or eq this
        if (isUserAuthorOfCourse(user_id, lesson.getCourse().getId()) || isUserAdmin(user_id)) {
            List<Page> pages = pageRepository.getAllByNumberIsGreaterThanEqual(page.getNumber());
            for (Page p : pages) {
                p.setNumber(p.getNumber() + 1);
            }
            pageRepository.saveAll(pages);
            page.setLesson(lesson);
            return pageRepository.save(page);
        }
        return null;
    }

    @Override
    public Page updatePageForLesson(Long lesson_id, Page page, Long user_id) {
        Lesson lesson = lessonRepository.findById(lesson_id).orElseThrow(()-> new ResourceNotFoundException("lesson", "id", lesson_id));
        Page oldPage = pageRepository.findById(page.getId()).orElseThrow(()-> new ResourceNotFoundException("page", "id", page.getId()));
        if (lesson != null && (isUserAuthorOfCourse(user_id, lesson.getCourse().getId()) || isUserAdmin(user_id))) {
            oldPage.setName(page.getName());
            oldPage.setContent(page.getContent());
            oldPage.setImage(page.getImage());
            return pageRepository.save(oldPage);
        }
        return null;
    }

    @Override
    public void deletePageById(Long page_id, Long user_id) {
        Page page = pageRepository.findById(page_id).orElseThrow(()-> new ResourceNotFoundException("Page", "id", page_id));
        if (isUserAuthorOfCourse(user_id, page.getLesson().getCourse().getId()) || isUserAdmin(user_id)) {
            List<Page> pages = pageRepository.getAllByNumberIsGreaterThanEqual(page.getNumber());
            for (Page p : pages) {
                p.setNumber(p.getNumber() - 1);
            }
            pageRepository.deleteById(page_id);
        }
    }

    private boolean isUserAuthorOfCourse(Long user_id, Long course_id) {
        Optional<Course> courseOptional = courseRepository.findById(course_id);
        return courseOptional.isPresent() && courseOptional.get().getAuthorOfCourse().equals(user_id);
    }

    private boolean isUserAdmin(Long user_id){
        Optional<User> userOptional = userRepository.findById(user_id);
        if (userOptional.isPresent()){
            Optional<Role> roleOptional = userOptional.get().getRoles().stream().filter(x -> x.getName().equals("ROLE_ADMIN")).findFirst();
            return roleOptional.isPresent();
        }
        return false;
    }
}
