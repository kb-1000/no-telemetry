/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package de.kb1000.notelemetry;

import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class NoTelemetryForgeMixinConfigPlugin implements IMixinConfigPlugin {
    private boolean isForgeNeo = false;

    @Override
    public void onLoad(String mixinPackage) {
        isForgeNeo = CommonUtil.isForgeNeo();
    }

    @Override
    public String getRefMapperConfig() {
        if (isForgeNeo) {
            return null;
        }

        if (Util.minecraftNewerThan("1.21")) {
            return "no-telemetry-mojank-refmap.json";
        } else if (Util.minecraftNewerThan("1.20.5")) {
            return "no-telemetry-mojank-1.20-refmap.json";
        }

        return "no-telemetry-forge-refmap.json";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (isForgeNeo) {
            return false;
        }

        return switch (mixinClassName) {
            // You can't use snapshots on Forge!
            case "de.kb1000.notelemetry.mixin.YggdrasilUserApiServiceMixin" -> false;
            case "de.kb1000.notelemetry.mixin.Pre1193TelemetryManagerMixin" ->
                    !Util.minecraftNewerThan("1.19.3-alpha.22.46.a");
            case "de.kb1000.notelemetry.mixin.MinecraftClientMixin", "de.kb1000.notelemetry.mixin.OptionsScreenMixin",
                    "de.kb1000.notelemetry.mixin.Post1193TelemetryManagerMixin" -> Util.minecraftNewerThan("1.19.3-alpha.22.46.a");
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

    private static class Util {
        private static boolean minecraftNewerThan(String version) {
            try {
                return VersionRange.createFromVersionSpec("[" + version + ",)").containsVersion(new DefaultArtifactVersion(FMLLoader.versionInfo().mcVersion()));
            } catch (InvalidVersionSpecificationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
