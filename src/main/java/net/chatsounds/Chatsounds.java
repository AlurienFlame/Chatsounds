package net.chatsounds;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.ActionResult;

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
            return ActionResult.SUCCESS;
        });
        holder.registerSaveListener((manager, newData) -> {
            config = newData;
            return ActionResult.SUCCESS;
        });
    }
}
