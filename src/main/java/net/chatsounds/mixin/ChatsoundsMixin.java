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
            System.out.println(String.format("Translation key: %s", key));

            if (config.join.enabled && key.contains("multiplayer.player.joined")) {
                System.out.println(String.format("Chatsounds playing join sound."));
                client.getSoundManager().play(config.join.getChatSound(x, y, z));

            } else if (config.leave.enabled && key == "multiplayer.player.left") {
                // FIXME: Chatsounds failed to recognize translation key: multiplayer.player.left
                System.out.println(String.format("Chatsounds playing leave sound."));
                client.getSoundManager().play(config.leave.getChatSound(x, y, z));

            } else if (config.death.enabled && key.contains("death.")) {
                System.out.println(String.format("Chatsounds playing death sound."));
                client.getSoundManager().play(config.death.getChatSound(x, y, z));

            } else if (config.pm.enabled && key == "commands.message.display.incoming") {
                System.out.println(String.format("Chatsounds playing pm sound."));
                client.getSoundManager().play(config.pm.getChatSound(x, y, z));

            } else if (config.advancement.enabled && key.contains("chat.type.advancement.")) {
                System.out.println(String.format("Chatsounds playing advancement sound."));
                client.getSoundManager().play(config.advancement.getChatSound(x, y, z));
                
            } else if (config.message.enabled && key.contains("chat.type.")) {
                System.out.println(String.format("Chatsounds playing message sound."));
                client.getSoundManager().play(config.message.getChatSound(x, y, z));

            } else {
                System.out.println(String.format("Chatsounds failed to recognize translation key: %s", key));
                client.getSoundManager().play(config.message.getChatSound(x, y, z));
            }

        } else {
            System.out.println("Chatsounds failed to find translation key.");
            if (config.message.enabled) client.getSoundManager().play(config.message.getChatSound(x, y, z));
        }
    }
}
