package net.chatsounds.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;

// TODO: Get mixins working
@Mixin(ChatHud.class)
public class ChatsoundsMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At("HEAD"))
    private void addMessage(Text message, int messageId, int timestamp, boolean refresh, CallbackInfo ci) {
        System.out.println("Hello World from Mixin!");
    }
}
