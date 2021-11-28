package net.minecraft.client;

import net.minecraft.SharedConstants;

/**
 * This is a stub used by the mixin only.
 */
public class ClientTelemetryManager {
    public ClientTelemetryManager() {
        if (SharedConstants.isDevelopment) {
            System.out.println("Is development");
        }
    }
}
