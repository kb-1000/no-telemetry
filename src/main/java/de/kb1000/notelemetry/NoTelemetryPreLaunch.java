package de.kb1000.notelemetry;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoTelemetryPreLaunch implements PreLaunchEntrypoint {
    public static final Logger LOGGER = LogManager.getLogger("no-telemetry");

    @Override
    public void onPreLaunch() {
        LOGGER.info("Killing telemetry");
    }
}
