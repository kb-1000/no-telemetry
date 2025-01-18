/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package de.kb1000.notelemetry;

import net.neoforged.fml.loading.FMLLoader;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;

public class NoTelemetryNeoForgeAbstractMixinConfigPlugin extends NoTelemetryAbstractMixinConfigPlugin {
    @Override
    public String getRefMapperConfig() {
        if (Util.minecraftNewerThan("1.21")) {
            return "no-telemetry-mojank-refmap.json";
        } else {
            return "no-telemetry-mojank-1.20-refmap.json";
        }
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        // We can just use the mixin.json for this, for now!
        return true;
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
