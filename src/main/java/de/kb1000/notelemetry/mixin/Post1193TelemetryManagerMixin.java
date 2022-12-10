package de.kb1000.notelemetry.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.util.telemetry.TelemetryManager")
@Environment(EnvType.CLIENT)
public class Post1193TelemetryManagerMixin {
    @Redirect(method = "Lnet/minecraft/client/util/telemetry/TelemetryManager;getSender", at = @At(value = "FIELD", target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"), remap = true)
    private boolean disableTelemetrySession() {
        return true;
    }
}
