/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package refactorizar.colores;

import java.io.File;
import java.util.prefs.Preferences;
import java.awt.Color;

public class RefactorizarColores {

    private static Color currentColor;
    private static Preferences preferences;
    private static String CURTAIN_R;
    private static String CURTAIN_G;
    private static String CURTAIN_B;
    private static String CURTAIN_A;

    public static void main(String[] args) {
        DDLoggerInterface logger;
        testOperatingSystem();
        initializeLogFIles();
        setDefaultColor();
        int DEFAULT_LOG_LEVEL = OperatingSystemTest();

        initializeLogFIles(DEFAULT_LOG_LEVEL);

        // (3, 4) recupera los ajustes del programa y establece el color por defecto.
       preferences = Preferences.userNodeForPackage(this.getClass());
        int r = preferences.getInt(CURTAIN_R, 0);
        int g = preferences.getInt(CURTAIN_G, 0);
        int b = preferences.getInt(CURTAIN_B, 0);
        int a = preferences.getInt(CURTAIN_A, 255);
        currentColor = new Color(r, g, b, a);

    }

    private static DDLoggerInterface initializeLogFIles(int DEFAULT_LOG_LEVEL) {
        DDLoggerInterface logger;
        // (2) inicializar ficheros de log
        int currentLoggingLevel = DEFAULT_LOG_LEVEL;
        String ERROR_LOG_FILENAME = "error";
        String WARNING_LOG_FILENAME = "warning";
        String DEBUG_LOG_FILENAME = "debug";
        String CANON_DEBUG_FILENAME = "current.log";
        File errorFile = new File(ERROR_LOG_FILENAME);
        File warningFile = new File(WARNING_LOG_FILENAME);
        File debugFile = new File(DEBUG_LOG_FILENAME);

        // el orden de los tests es importante en caso de que existan varios ficheros
        if (errorFile.exists()) {
            currentLoggingLevel = DDLoggerInterface.LOG_ERROR;
        }
        if (warningFile.exists()) {
            currentLoggingLevel = DDLoggerInterface.LOG_WARNING;
        }
        if (debugFile.exists()) {
            currentLoggingLevel = DDLoggerInterface.LOG_DEBUG;
        }
        logger = new DDSimpleLogger(CANON_DEBUG_FILENAME, currentLoggingLevel, true, true);
        return logger;
    }

    private static int OperatingSystemTest() {
        // (1) comprobar el sistema Operativo y asegurarnos qeu solo funciona en linux
        boolean mrjVersionExists = System.getProperty("mrj.version") != null;
        boolean osNameExists = System.getProperty("os.name").startsWith("linux");
        if (!mrjVersionExists || !osNameExists) {
            System.err.println("Este programa no puede ejecutarse en sistemas operativo inferiores");
            System.exit(1);
        }
        int DEFAULT_LOG_LEVEL = 0;
        return DEFAULT_LOG_LEVEL;
    }

    private static class DDLoggerInterface {

        private static int LOG_ERROR;
        private static int LOG_WARNING;
        private static int LOG_DEBUG; }}
