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
import com.mojang.authlib.yggdrasil.YggdrasilUserApiService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Group;

import java.util.concurrent.Executor;

@Mixin(YggdrasilUserApiService.class)
public class YggdrasilUserApiServiceMixin {
    /**
     * @author kb1000
     */
    //Overwrite(remap = false)
    public boolean telemetryAllowed() {
        return false;
    }

    /**
     * @author kb1000
     */
    @Overwrite(remap = false)
    public TelemetrySession newTelemetrySession(final Executor executor) {
        return TelemetrySession.DISABLED;
    }
}
