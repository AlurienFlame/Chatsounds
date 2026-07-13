package io.github.alurienflame.chatsounds.mixin;

import io.github.alurienflame.chatsounds.ChatsoundsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessageSource;
import net.minecraft.client.multiplayer.chat.GuiMessageTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
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
        Minecraft client = Minecraft.getInstance();

        ComponentContents content = message.getContents();
        if (content instanceof TranslatableContents) {
            String key = ((TranslatableContents) content).getKey();

            // FIXME: non-system messages not playing nice with unsecured chat
            if (config.join.enabled && key.contains("multiplayer.player.joined")) {
                client.getSoundManager().play(config.join.getChatSound());

            } else if (config.leave.enabled && key.contains("multiplayer.player.left")) {
                client.getSoundManager().play(config.leave.getChatSound());

            } else if (config.death.enabled && key.contains("death.")) {
                client.getSoundManager().play(config.death.getChatSound());

            } else if (config.pm.enabled && key.contains("commands.message.display.")) {
                client.getSoundManager().play(config.pm.getChatSound());

            } else if (config.advancement.enabled && key.contains("chat.type.advancement.")) {
                client.getSoundManager().play(config.advancement.getChatSound());

            } else if (config.message.enabled && key.contains("chat.type.")) {
                client.getSoundManager().play(config.message.getChatSound());

            } else {
                System.out.println(String.format("Chatsounds failed to find translation key: %s", key));
                client.getSoundManager().play(config.message.getChatSound());
            }

        } else {
            // Fall back to the message sound
            if (config.message.enabled) {
                client.getSoundManager().play(config.message.getChatSound());
            }
        }
    }
}
