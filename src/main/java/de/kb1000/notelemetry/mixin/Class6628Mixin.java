package de.kb1000.notelemetry.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_6628;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(class_6628.class)
@Environment(EnvType.CLIENT)
public class Class6628Mixin {
    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"))
    private boolean disableTelemetrySession() {
        return true;
    }
}
