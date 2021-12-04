package de.kb1000.notelemetry;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;
import java.util.Optional;

public class NoTelemetryPreLaunch implements PreLaunchEntrypoint {
    public static final Logger LOGGER = LogManager.getLogger("no-telemetry");

    @Override
    public void onPreLaunch() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Method m = classLoader.getClass().getMethod("addURL", URL.class);
            m.setAccessible(true);
            m.invoke(classLoader, getSource(classLoader, "com/mojang/authlib/yggdrasil/YggdrasilUserApiService.class").orElseThrow());
            LOGGER.info("Added authlib to knot");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchElementException e) {
            LOGGER.warn("Failed to add authlib to knot", e);
        }

        LOGGER.info("Killing telemetry");
    }

    // from Fabric Loader, copied to avoid depending on Loader internals
    private static Optional<URL> getSource(ClassLoader loader, String filename) {
        URL url;

        if ((url = loader.getResource(filename)) != null) {
            URL urlSource = getSource(filename, url);

            return Optional.of(urlSource);
        }

        return Optional.empty();
    }

    private static URL getSource(String filename, URL resourceURL) {
        URL codeSourceURL;

        try {
            URLConnection connection = resourceURL.openConnection();

            if (connection instanceof JarURLConnection) {
                codeSourceURL = ((JarURLConnection) connection).getJarFileURL();
            } else {
                String path = resourceURL.getPath();

                if (path.endsWith(filename)) {
                    codeSourceURL = new URL(resourceURL.getProtocol(), resourceURL.getHost(), resourceURL.getPort(), path.substring(0, path.length() - filename.length()));
                } else {
                    throw new RuntimeException("Could not figure out code source for file '" + filename + "' and URL '" + resourceURL + "'!");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return codeSourceURL;
    }
}
