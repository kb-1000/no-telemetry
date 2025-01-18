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
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return switch (mixinClassName) {
            case "de.kb1000.notelemetry.mixin.YggdrasilUserApiServiceMixin" ->
                    !Util.minecraftNewerThan("1.18-beta.3");
            case "de.kb1000.notelemetry.mixin.NewYggdrasilUserApiServiceMixin" ->
                    Util.minecraftNewerThan("1.18-beta.3");
            case "de.kb1000.notelemetry.mixin.Pre1193TelemetryManagerMixin" ->
                    !Util.minecraftNewerThan("1.19.3-alpha.22.46.a");
            case "de.kb1000.notelemetry.mixin.MinecraftClientMixin", "de.kb1000.notelemetry.mixin.OptionsScreenMixin",
                    "de.kb1000.notelemetry.mixin.Post1193TelemetryManagerMixin" -> Util.minecraftNewerThan("1.19.3-alpha.22.46.a");
            default -> true;
        };
    }

    static class Util {
        private static boolean minecraftNewerThan(String version) {
            try {
                return FabricLoader.getInstance().getModContainer("minecraft").orElseThrow().getMetadata().getVersion().compareTo(SemanticVersion.parse(version)) >= 0;
            } catch (VersionParsingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
