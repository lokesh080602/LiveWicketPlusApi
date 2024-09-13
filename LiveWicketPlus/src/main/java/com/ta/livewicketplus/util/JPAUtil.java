package com.ta.livewicketplus.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPAUtil {

    private static final Logger logger = LoggerFactory.getLogger(JPAUtil.class);
    private static volatile EntityManagerFactory emf;

    private static final Path CONFIG_PATH = Paths.get("D:\\LiveWicketPlus\\config\\db.properties");

    static {
        loadConfig();
        try {
            startFileWatcher(CONFIG_PATH.getParent());
        } catch (IOException e) {
            logger.error("Error initializing file watcher: " + e.getMessage(), e);
        }
    }

    private JPAUtil() {}

    private static void loadConfig() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            logger.info("Previous EntityManagerFactory closed.");
        }

        Properties externalProperties = new Properties();
        try (FileInputStream fileInput = new FileInputStream(CONFIG_PATH.toFile())) {
            externalProperties.load(fileInput);
            emf = Persistence.createEntityManagerFactory("LiveWicketPlus", externalProperties);
            logger.info("EntityManagerFactory created successfully with external properties.");
        } catch (IOException e) {
            logger.error("Error loading external properties file: " + e.getMessage(), e);
        } catch (Exception ex) {
            logger.error("Failed to create EntityManagerFactory: " + ex.getMessage(), ex);
        }
    }

    public static EntityManager getEntityManager() {
        if (emf == null || !emf.isOpen()) {
            loadConfig();
        }
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            logger.info("EntityManagerFactory closed.");
        }
    }

    private static void startFileWatcher(Path dir) throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        dir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        new Thread(() -> {
            while (true) {
                try {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }

                        @SuppressWarnings("unchecked")
						WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path changedFile = ev.context();

                        if (changedFile.equals(CONFIG_PATH.getFileName())) {
                            logger.info("Configuration file modified. Reloading...");
                            loadConfig();
                        }
                    }
                    key.reset();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("File watcher thread interrupted: " + e.getMessage(), e);
                    break;
                }
            }
        }).start();
    }
}
