package io.github.alurienflame.chatsounds;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.InteractionResult;

public class Chatsounds implements ClientModInitializer {

    public String latestMessage;
    public static ChatsoundsConfig config;

    @Override
    public void onInitializeClient() {
        // Setup config
        ConfigHolder<ChatsoundsConfig> holder = AutoConfig.register(ChatsoundsConfig.class, GsonConfigSerializer::new);
        config = holder.getConfig();
        holder.registerLoadListener((manager, newData) -> {
            config = newData;
            return InteractionResult.SUCCESS;
        });
        holder.registerSaveListener((manager, newData) -> {
            config = newData;
            return InteractionResult.SUCCESS;
        });
    }
}
