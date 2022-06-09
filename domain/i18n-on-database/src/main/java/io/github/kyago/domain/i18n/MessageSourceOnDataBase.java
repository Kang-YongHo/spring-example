package io.github.kyago.domain.i18n;

import io.github.kyago.domain.i18n.entity.LanguageEntity;
import io.github.kyago.domain.i18n.repository.LanguageRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

@ConditionalOnClass(LanguageRepository.class)
@Component("messageSourceOnDataBase")
public class MessageSourceOnDataBase extends AbstractMessageSource {
    private static final String DEFAULT_LOCALE_CODE = "en";

    private final LanguageRepository languageRepository;

    public MessageSourceOnDataBase(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    protected MessageFormat resolveCode(String key, Locale locale) {
        LanguageEntity message = languageRepository.findByKeyAndLocale(key,locale.getLanguage());
        if (message == null) {
            message = languageRepository.findByKeyAndLocale(key,DEFAULT_LOCALE_CODE);
        }
        return new MessageFormat(message.getContent(), locale);
    }
}