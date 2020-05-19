package ru.nc.portal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "page_types")
public class PageType {
    @Id
    @SequenceGenerator(name = "pageTypeSeq", sequenceName = "PAGE_TYPE_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pageTypeSeq")
    private Long id;
    @Column(name = "type_name")
    private String typeName;
    @OneToMany(mappedBy = "pageType")
    @JsonIgnore
    private List<Page> pages;

    public PageType() {
    }

    public PageType(String typeName, List<Page> pages) {
        this.typeName = typeName;
        this.pages = pages;
    }

    public Long getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
