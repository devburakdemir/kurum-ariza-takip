package org.example.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.LocaleContextResolver;

import java.util.Locale;

public class LocaleResolverConfig implements LocaleContextResolver {

    private static final String DEFAULT_LANGUAGE = "tr";

    @Override
    public LocaleContext resolveLocaleContext(HttpServletRequest request) {
        String language = request.getParameter("lang");
        Locale locale;
        if (language != null && !language.isEmpty()) {
            locale = Locale.forLanguageTag(language);
        } else {
            locale = Locale.forLanguageTag(DEFAULT_LANGUAGE);
        }
        return new SimpleLocaleContext(locale);
    }

    @Override
    public void setLocaleContext(HttpServletRequest request, @Nullable HttpServletResponse response,
                                 @Nullable LocaleContext localeContext) {
        throw new UnsupportedOperationException("Cannot change locale context - use a different locale resolution strategy");
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getParameter("lang");
        if (language != null && !language.isEmpty()) {
            return Locale.forLanguageTag(language);
        }
        return Locale.forLanguageTag(DEFAULT_LANGUAGE);
    }

    @Override
    public void setLocale(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Locale locale) {
        throw new UnsupportedOperationException("Cannot change locale - use a different locale resolution strategy");
    }
}