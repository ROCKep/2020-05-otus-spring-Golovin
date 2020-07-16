package ru.otus.library.config;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomPromptProvider implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("library-shell:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE)
        );
    }
}
