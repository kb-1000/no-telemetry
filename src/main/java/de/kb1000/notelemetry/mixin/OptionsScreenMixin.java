/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package de.kb1000.notelemetry.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(targets = "net.minecraft.client.gui.screens.options.OptionsScreen")
public class OptionsScreenMixin {
    @Group(name = "removeTelemetryButton", min = 1, max = 1)
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;", ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;TELEMETRY:Lnet/minecraft/network/chat/Component;")))
    private @Coerce Object removeTelemetryButton(@Coerce Object adder, @Coerce Object widget) {
        return widget;
    }

    @Group(name = "removeTelemetryButton", min = 1, max = 1)
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_7845$class_7939;method_47612(Lnet/minecraft/class_339;)Lnet/minecraft/class_339;", ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;TELEMETRY:Lnet/minecraft/network/chat/Component;")))
    private @Coerce Object removeTelemetryButtonOld(@Coerce Object adder, @Coerce Object widget) {
        return widget;
    }
}
