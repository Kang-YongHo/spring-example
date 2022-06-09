package io.github.kyago.domain.i18n.entity;

import javax.persistence.*;

@Entity
@Table(name = "languages")
public class LanguageEntity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column
    private Integer id;
    @Column
    private String locale;
    @Column(name = "messagekey")
    private String key;
    @Column(name = "messagecontent")
    private String content;

    public Integer getId() {
        return id;
    }

    public String getLocale() {
        return locale;
    }

    public String getKey() {
        return key;
    }

    public String getContent() {
        return content;
    }

    public LanguageEntity() {
    }

    public LanguageEntity(Integer id, String locale, String key, String content) {
        this.id = id;
        this.locale = locale;
        this.key = key;
        this.content = content;
    }
}
