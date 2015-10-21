package br.com.certisign.patch.prompt;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.stereotype.Component;

/**
 * Created by egazetta on 09/10/2015.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BannerProviderImp implements BannerProvider {
    @Override
    public String getBanner() {
        String banner = "   _____       .___      .__         _________     _____   \n" +
                "  /  _  \\    __| _/_____ |__| ____   \\_   ___ \\   /  _  \\  \n" +
                " /  /_\\  \\  / __ |/     \\|  |/    \\  /    \\  \\/  /  /_\\  \\ \n" +
                "/    |    \\/ /_/ |  Y Y  \\  |   |  \\ \\     \\____/    |    \\\n" +
                "\\____|__  /\\____ |__|_|  /__|___|  /  \\______  /\\____|__  /\n" +
                "        \\/      \\/     \\/        \\/          \\/         \\/ ";

        return banner;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getWelcomeMessage() {
        return null;
    }

    @Override
    public String getProviderName() {
        return null;
    }
}
