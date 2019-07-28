package ru.otus.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;
import java.util.Objects;

public class TemplateProcessor {
    private ClassLoaderTemplateResolver templateResolver;

    public TemplateProcessor() {
        templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
    }
    public String getProcessedTemplate(String template , Map<String, Object> dataModel){
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context ct = new Context();
        ct.setVariables(dataModel);
        return templateEngine.process(template, ct);

    }
}
