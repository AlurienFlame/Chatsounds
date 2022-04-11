package net.chatsounds.mixin;

import net.chatsounds.ChatsoundsConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

// FIXME: Conflicts with ReplayMod, for some reason.
@Mixin(ChatHud.class)
public class ChatsoundsMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At("HEAD"))
    private void addMessage(Text message, int messageId, int timestamp, boolean refresh, CallbackInfo ci) {

        ChatsoundsConfig config = AutoConfig.getConfigHolder(ChatsoundsConfig.class).getConfig();
        MinecraftClient client = MinecraftClient.getInstance();
        double x = client.player.getX();
        double y = client.player.getY();
        double z = client.player.getZ();

        if (message instanceof TranslatableText) {
            String key = ((TranslatableText)message).getKey();

            if (config.join.enabled && key.contains("multiplayer.player.joined")) {
                client.getSoundManager().play(config.join.getChatSound(x, y, z));

            } else if (config.leave.enabled && key.contains("multiplayer.player.left")) {
                client.getSoundManager().play(config.leave.getChatSound(x, y, z));

            } else if (config.death.enabled && key.contains("death.")) {
                client.getSoundManager().play(config.death.getChatSound(x, y, z));

            } else if (config.pm.enabled && key.contains("commands.message.display.")) {
                client.getSoundManager().play(config.pm.getChatSound(x, y, z));

            } else if (config.advancement.enabled && key.contains("chat.type.advancement.")) {
                client.getSoundManager().play(config.advancement.getChatSound(x, y, z));
                
            } else if (config.message.enabled && key.contains("chat.type.")) {
                client.getSoundManager().play(config.message.getChatSound(x, y, z));

            } else {
                System.out.println(String.format("Chatsounds failed to find translation key: %s", key));
                client.getSoundManager().play(config.message.getChatSound(x, y, z));
            }

        } else {
            // Fall back to the message sound
            if (config.message.enabled) client.getSoundManager().play(config.message.getChatSound(x, y, z));
        }
    }
}
