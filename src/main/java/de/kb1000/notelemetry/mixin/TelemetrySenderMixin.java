package de.kb1000.notelemetry.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.telemetry.TelemetrySender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(value = TelemetrySender.class, targets = "net.minecraft.client.ClientTelemetryManager")
@Environment(EnvType.CLIENT)
public class TelemetrySenderMixin {
    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"))
    private static boolean disableTelemetrySession() {
        return true;
    }
}
