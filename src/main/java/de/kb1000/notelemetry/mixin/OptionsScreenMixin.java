package de.kb1000.notelemetry.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(targets = "net.minecraft.client.gui.screen.option.OptionsScreen")
public class OptionsScreenMixin {
    @Group(name = "removeTelemetryButton", min = 1, max = 1)
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget$Adder;add(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;", ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/option/OptionsScreen;TELEMETRY_TEXT:Lnet/minecraft/text/Text;")))
    private @Coerce Object removeTelemetryButton(@Coerce Object adder, @Coerce Object widget) {
        return widget;
    }

    @Group(name = "removeTelemetryButton", min = 1, max = 1)
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;m_264139_(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;", ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/option/OptionsScreen;TELEMETRY_TEXT:Lnet/minecraft/text/Text;")))
    private @Coerce Object removeTelemetryButtonForge(@Coerce Object adder, @Coerce Object widget) {
        return widget;
    }

    @Group(name = "removeTelemetryButton", min = 1, max = 1)
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_7845$class_7939;method_47612(Lnet/minecraft/class_339;)Lnet/minecraft/class_339;", ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/option/OptionsScreen;TELEMETRY_TEXT:Lnet/minecraft/text/Text;")))
    private @Coerce Object removeTelemetryButtonOld(@Coerce Object adder, @Coerce Object widget) {
        return widget;
    }
}
