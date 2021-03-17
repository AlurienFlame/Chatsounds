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

// TODO: Detect join, leave, death, and private messages and play different sounds for them.
// Use lang files to find relevant messages so works with all languages
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

            switch(key) {
                case "chat.type.text":
                    // TODO: Check if config.enabled
                    client.getSoundManager().play(config.message.getChatSound(x, y, z));
                    break;
                
                case "commands.message.display.incoming":
                    client.getSoundManager().play(config.pm.getChatSound(x, y, z));
                    break;

                default:
                    System.out.println(String.format("Chatsounds failed to recognize translation key: %s", key));
                    client.getSoundManager().play(config.message.getChatSound(x, y, z));
            }

        } else {
            System.out.println("Chatsounds failed to find translation key.");
            client.getSoundManager().play(config.message.getChatSound(x, y, z));
        }
    }
}
