package net.chatsounds.mixin;

import net.chatsounds.ChatsoundsConfig;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;

// FIXME: Conflicts with ReplayMod, AdvancedChatHUD
@Mixin(ChatHud.class)
public class ChatsoundsMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"))
    private void addMessage(Text message, @Nullable MessageSignatureData signature, int ticks, @Nullable MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        if (refresh) return;

        ChatsoundsConfig config = AutoConfig.getConfigHolder(ChatsoundsConfig.class).getConfig();
        MinecraftClient client = MinecraftClient.getInstance();

        TextContent content = message.getContent();

        if (content instanceof TranslatableTextContent) {
            String key = ((TranslatableTextContent) content).getKey();

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
