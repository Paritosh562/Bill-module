package org.example;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class StringResourceParameterResolver implements ParameterResolver {

    private static String asString(Resource resource) {
        try (InputStream is = resource.getInputStream()) {
            return StreamUtils.copyToString(is, UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
        ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(StringResource.class) && parameterContext.getParameter()
            .getType().equals(String.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
        ExtensionContext extensionContext) throws ParameterResolutionException {
        ApplicationContext applicationContext =
            SpringExtension.getApplicationContext(extensionContext);
        Resource resource = applicationContext.getResource(parameterContext
            .findAnnotation(StringResource.class).map(StringResource::value).orElseThrow());
        return asString(resource);
    }
}
