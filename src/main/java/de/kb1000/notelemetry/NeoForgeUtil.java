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

public class NeoForgeUtil {
    public static boolean minecraftNewerThan(String version) {
        try {
            return VersionRange.createFromVersionSpec("[" + version + ",)").containsVersion(new DefaultArtifactVersion(FMLLoader.versionInfo().mcVersion()));
        } catch (InvalidVersionSpecificationException e) {
            throw new RuntimeException(e);
        }
    }
}
