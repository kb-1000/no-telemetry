package de.kb1000.notelemetry;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.VersionParsingException;

public class FabricUtil {
    public static boolean minecraftNewerThan(String version) {
        try {
            return FabricLoader.getInstance().getModContainer("minecraft").orElseThrow().getMetadata().getVersion().compareTo(SemanticVersion.parse(version)) >= 0;
        } catch (VersionParsingException e) {
            throw new RuntimeException(e);
        }
    }
}
