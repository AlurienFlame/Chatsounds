package io.github.alurienflame.chatsounds.mixin;

import io.github.alurienflame.chatsounds.ChatsoundsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessageSource;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// FIXME: Conflicts with ReplayMod, AdvancedChatHUD
@Mixin(ChatComponent.class)
public class ChatsoundsMixin {
    // All public add*Message methods funnel into this private method
    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/multiplayer/chat/GuiMessageSource;Lnet/minecraft/client/multiplayer/chat/GuiMessageTag;)V", at = @At("HEAD"))
    public void addMessage(Component message, MessageSignature signature, GuiMessageSource source, GuiMessageTag tag, CallbackInfo ci) {
        ChatsoundsConfig config = AutoConfig.getConfigHolder(ChatsoundsConfig.class).getConfig();

        ComponentContents content = message.getContents();
        if (content instanceof TranslatableContents) {
            String key = ((TranslatableContents) content).getKey();

            // FIXME: non-system messages not playing nice with unsecured chat
            if (config.join.enabled && key.contains("multiplayer.player.joined")) {
                chatsounds$play(config.join);

            } else if (config.leave.enabled && key.contains("multiplayer.player.left")) {
                chatsounds$play(config.leave);

            } else if (config.death.enabled && key.contains("death.")) {
                chatsounds$play(config.death);

            } else if (config.pm.enabled && key.contains("commands.message.display.")) {
                chatsounds$play(config.pm);

            } else if (config.advancement.enabled && key.contains("chat.type.advancement.")) {
                chatsounds$play(config.advancement);

            } else if (config.message.enabled && key.contains("chat.type.")) {
                chatsounds$play(config.message);

            } else {
                System.out.println(String.format("Chatsounds failed to find translation key: %s", key));
                chatsounds$play(config.message);
            }

        } else {
            // Fall back to the message sound
            if (config.message.enabled) {
                chatsounds$play(config.message);
            }
        }
    }

    @Unique
    private static void chatsounds$play(ChatsoundsConfig.SoundConfig section) {
        SimpleSoundInstance sound = section.getChatSound();
        if (sound != null) {
            Minecraft.getInstance().getSoundManager().play(sound);
        }
    }
}
