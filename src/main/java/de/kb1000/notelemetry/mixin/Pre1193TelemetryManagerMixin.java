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
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "net.minecraft.client.telemetry.ClientTelemetryManager")
@Environment(EnvType.CLIENT)
public class Pre1193TelemetryManagerMixin {
    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/SharedConstants;IS_RUNNING_IN_IDE:Z"))
    private boolean disableTelemetrySession() {
        return true;
    }
}
