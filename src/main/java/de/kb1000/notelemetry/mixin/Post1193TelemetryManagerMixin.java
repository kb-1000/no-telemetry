/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package de.kb1000.notelemetry.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.session.telemetry.TelemetryManager")
@Environment(EnvType.CLIENT)
public class Post1193TelemetryManagerMixin {
    @Group(name = "disableTelemetrySessionPost1193", min = 1, max = 1)
    @Redirect(method = "Lnet/minecraft/client/session/telemetry/TelemetryManager;computeSender", at = @At(value = "FIELD", target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"))
    private boolean disableTelemetrySessionPre1203() {
        return true;
    }

    @Group(name = "disableTelemetrySessionPost1193", min = 1, max = 1)
    @Redirect(method = "Lnet/minecraft/client/session/telemetry/TelemetryManager;computeSender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;isTelemetryEnabledByApi()Z"))
    private boolean disableTelemetrySessionPost1203(@Coerce Object minecraftClient) {
        return false;
    }
}
