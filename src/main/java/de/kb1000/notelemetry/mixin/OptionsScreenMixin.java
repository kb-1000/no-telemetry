/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package de.kb1000.notelemetry.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

/**
 * Uses @WrapOperation instead of @Redirect: current Fabric Loader builds encode @Redirect.at as an array, which the
 * MixinExtras shipped with Forge cannot read.
 */
@Mixin(OptionsScreen.class)
public class OptionsScreenMixin {
    @WrapOperation(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;", ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;TELEMETRY:Lnet/minecraft/network/chat/Component;")))
    private LayoutElement removeTelemetryButton(GridLayout.RowHelper rowHelper, LayoutElement element, Operation<LayoutElement> original) {
        return element;
    }
}
