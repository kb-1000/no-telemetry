package de.kb1000.notelemetry.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.MinecraftClient")
public class MinecraftClientMixin {
    @Inject(method = "isTelemetryEnabledByApi()Z", at = @At("HEAD"), cancellable = true)
    public void isTelemetryEnabledByApi(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
