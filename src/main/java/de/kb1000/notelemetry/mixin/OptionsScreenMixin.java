package de.kb1000.notelemetry.mixin;

import net.minecraft.client.gui.screen.option.OptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(targets = "net.minecraft.client.gui.screen.option.OptionsScreen")
public class OptionsScreenMixin {
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget$Adder;add(Lnet/minecraft/client/gui/widget/ClickableWidget;)Lnet/minecraft/client/gui/widget/ClickableWidget;", ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/option/OptionsScreen;TELEMETRY_TEXT:Lnet/minecraft/text/Text;")))
    private @Coerce Object removeTelemetryButton(@Coerce Object adder, @Coerce Object widget) {
        return widget;
    }
}
