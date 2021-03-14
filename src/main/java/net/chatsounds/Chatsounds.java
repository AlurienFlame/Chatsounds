package net.chatsounds;

import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

public class Chatsounds implements ClientModInitializer {

    public String latestMessage;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            List<String> messages = client.inGameHud.getChatHud().getMessageHistory();
            // TODO: Detect join and leave messages and play a different sound for them.
            if (messages.size() > 0) {
                String newMessage = messages.get(messages.size() - 1);
                // TODO: Find better way to detect new messages that allows duplicates to give notifications
                if (!newMessage.equals(latestMessage)) {
                    latestMessage = newMessage;
                    // TODO: Configuration, mod menu integration.
                    client.getSoundManager().play(new PositionedSoundInstance(new Identifier("entity.item.pickup"), SoundCategory.PLAYERS, 1f, 0.3f, false, 0, SoundInstance.AttenuationType.LINEAR, client.player.getX(), client.player.getY(), client.player.getZ(), false));
                }
            }
        });
    }
}
