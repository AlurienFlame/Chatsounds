package io.github.alurienflame.chatsounds;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfigClient;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return parent -> AutoConfigClient.getConfigScreen(ChatsoundsConfig.class, parent).get();
    }
}