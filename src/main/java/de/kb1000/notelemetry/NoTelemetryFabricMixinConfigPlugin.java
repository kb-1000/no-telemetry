/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package de.kb1000.notelemetry;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class NoTelemetryFabricMixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return switch (mixinClassName) {
            case "de.kb1000.notelemetry.mixin.YggdrasilUserApiServiceMixin" ->
                    !FabricUtil.minecraftNewerThan("1.18-beta.3");
            case "de.kb1000.notelemetry.mixin.NewYggdrasilUserApiServiceMixin" ->
                    FabricUtil.minecraftNewerThan("1.18-beta.3");
            case "de.kb1000.notelemetry.mixin.Pre1193TelemetryManagerMixin" ->
                    !FabricUtil.minecraftNewerThan("1.19.3-alpha.22.46.a");
            case "de.kb1000.notelemetry.mixin.MinecraftClientMixin", "de.kb1000.notelemetry.mixin.OptionsScreenMixin",
                    "de.kb1000.notelemetry.mixin.Post1193TelemetryManagerMixin" -> FabricUtil.minecraftNewerThan("1.19.3-alpha.22.46.a");
            default -> true;
        };
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
