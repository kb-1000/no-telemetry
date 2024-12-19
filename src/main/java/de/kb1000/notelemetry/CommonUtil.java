/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
package de.kb1000.notelemetry;

class CommonUtil {
    static boolean isNeoForge() {
        return false; // TODO
    }

    static boolean isForge() {
        return classExists("net.minecraftforge.fml.common.Mod") && !classExists("net.fabricmc.loader.api.FabricLoader");
    }

    static boolean isMojank() {
        return isNeoForge() || (isForge() && minecraftNewerThan("1.20.5"));
    }

    static boolean classExists(String name) {
        try {
            return NoTelemetryMixinConfigPlugin.class.getClassLoader().loadClass(name) != null;
        } catch (Exception | LinkageError e) {
            return false;
        }
    }

    static boolean minecraftNewerThan(String version) {
        return isForge() ? ForgeUtil.minecraftNewerThan(version) : FabricUtil.minecraftNewerThan(version);
    }
}
