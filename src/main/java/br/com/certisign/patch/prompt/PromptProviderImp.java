package br.com.certisign.patch.prompt;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * Created by egazetta on 09/10/2015.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PromptProviderImp implements PromptProvider {
    @Override
    public String getPrompt() {
        return "AdminCA>";
    }

    @Override
    public String getProviderName() {
        return null;
    }
}
