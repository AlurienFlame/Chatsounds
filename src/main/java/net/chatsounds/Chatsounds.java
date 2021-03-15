package net.chatsounds;

import java.util.List;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;

public class Chatsounds implements ClientModInitializer {

    public String latestMessage;
    ChatsoundsConfig config;

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

        // Every tick, do the following:
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            List<String> messages = client.inGameHud.getChatHud().getMessageHistory();
            // TODO: Detect join, leave, death, and private messages and play different sounds for them.
            if (messages.size() > 0) {
                String newMessage = messages.get(messages.size() - 1);
                // TODO: Find better way to detect new messages that allows duplicates to give notifications
                if (!newMessage.equals(latestMessage)) {
                    latestMessage = newMessage;
                    client.getSoundManager().play(new PositionedSoundInstance(config.message.sound.getId(), SoundCategory.PLAYERS, config.message.volume, config.message.pitch, false, 0, SoundInstance.AttenuationType.LINEAR, client.player.getX(), client.player.getY(), client.player.getZ(), false));
                }
            }
        });
    }
}
