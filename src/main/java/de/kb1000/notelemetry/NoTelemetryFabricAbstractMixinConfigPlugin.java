/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package de.kb1000.notelemetry;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.VersionParsingException;

public class NoTelemetryFabricAbstractMixinConfigPlugin extends NoTelemetryAbstractMixinConfigPlugin {
    @Override
    protected boolean minecraftNewerThan(String version) {
        try {
            return FabricLoader.getInstance().getModContainer("minecraft").orElseThrow().getMetadata().getVersion().compareTo(SemanticVersion.parse(version)) >= 0;
        } catch (VersionParsingException e) {
            throw new RuntimeException(e);
        }
    }
}
