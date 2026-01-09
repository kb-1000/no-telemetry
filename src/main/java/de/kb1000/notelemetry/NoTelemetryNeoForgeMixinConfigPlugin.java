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
import net.neoforged.fml.loading.VersionInfo;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class NoTelemetryNeoForgeMixinConfigPlugin extends NoTelemetryAbstractMixinConfigPlugin {
    @Override
    public String getRefMapperConfig() {
        if (this.minecraftNewerThan("1.21")) {
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

    private static final MethodHandle getVersionInfo;

    static {
        MethodHandle methodHandle;
        try {
            //noinspection JavaLangInvokeHandleSignature
            methodHandle = MethodHandles.lookup().findStatic(FMLLoader.class, "versionInfo", MethodType.methodType(VersionInfo.class));
        } catch (NoSuchMethodException e) {
            try {
                methodHandle = MethodHandles.filterReturnValue(MethodHandles.lookup().findStatic(FMLLoader.class, "getCurrent", MethodType.methodType(FMLLoader.class)), MethodHandles.lookup().findVirtual(FMLLoader.class, "getVersionInfo", MethodType.methodType(VersionInfo.class)));
            } catch (NoSuchMethodException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        getVersionInfo = methodHandle;
    }

    protected boolean minecraftNewerThan(String version) {
        try {
            return VersionRange.createFromVersionSpec("[" + version + ",)").containsVersion(new DefaultArtifactVersion(((VersionInfo) getVersionInfo.invoke()).mcVersion()));
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
