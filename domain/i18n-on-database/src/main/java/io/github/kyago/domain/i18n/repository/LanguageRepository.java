package io.github.kyago.domain.i18n.repository;

import io.github.kyago.domain.i18n.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {
    LanguageEntity findByKeyAndLocale(String key, String locale);
}