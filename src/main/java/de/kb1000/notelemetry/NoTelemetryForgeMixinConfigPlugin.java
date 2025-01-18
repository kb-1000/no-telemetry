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

public class NoTelemetryForgeMixinConfigPlugin extends NoTelemetryAbstractMixinConfigPlugin {
    private boolean isForgeNeo = false;

    @Override
    public void onLoad(String mixinPackage) {
        this.isForgeNeo = Util.isForgeNeo();
    }

    @Override
    public String getRefMapperConfig() {
        if (this.isForgeNeo) {
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
        if (this.isForgeNeo) {
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

    private static class Util {
        private static boolean minecraftNewerThan(String version) {
            try {
                return VersionRange.createFromVersionSpec("[" + version + ",)").containsVersion(new DefaultArtifactVersion(FMLLoader.versionInfo().mcVersion()));
            } catch (InvalidVersionSpecificationException e) {
                throw new RuntimeException(e);
            }
        }

        private static boolean isForgeNeo() {
            try {
                return NoTelemetryFabricAbstractMixinConfigPlugin.class.getClassLoader().loadClass("net.neoforged.fml.common.Mod") != null;
            } catch (Exception | LinkageError e) {
                return false;
            }
        }
    }
}
