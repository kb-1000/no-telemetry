/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package de.kb1000.notelemetry.mixin;

import com.mojang.authlib.minecraft.TelemetrySession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.Executor;

/**
 * authlib before 10 (Minecraft 26.1 - 26.2). Pseudo, since only one of the two authlib services exists in a given version.
 */
@Pseudo
@Mixin(targets = "com.mojang.authlib.yggdrasil.YggdrasilUserApiService")
public class YggdrasilUserApiServiceMixin {
    @Inject(method = "newTelemetrySession(Ljava/util/concurrent/Executor;)Lcom/mojang/authlib/minecraft/TelemetrySession;", at = @At("HEAD"), cancellable = true)
    private void disableTelemetrySession(Executor executor, CallbackInfoReturnable<TelemetrySession> cir) {
        cir.setReturnValue(TelemetrySession.DISABLED);
    }
}
