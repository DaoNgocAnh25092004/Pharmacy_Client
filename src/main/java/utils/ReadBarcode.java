package utils;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReadBarcode {
    private Thread logcatThread;
    private Timer deviceTimeChecker;
    private static final String TAG_NAME = "BarcodeScannerActivity";
    private static final String VALUE = "Barcode: ";
    private final AtomicBoolean isAppRunning = new AtomicBoolean(true);

    public void startBarcodeScanner(BarcodeDataCallback dataCallback) {
        deviceTimeChecker = new Timer();
        deviceTimeChecker.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (checkDeviceConnection()){
                        System.out.println("Connected");
                        logcatCatcher(new LogcatCallback() {
                            @Override
                            public void barcodeData(String barcodeData) {
                                if (isAppRunning.get()){
                                    dataCallback.barcodeData(barcodeData);
                                }
                            }
                        });
//                        deviceTimeChecker.cancel();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 5000);
    }

    private boolean checkDeviceConnection() throws IOException {
        //create ProcessBuilder to execute adb command
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("adb", "devices");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            boolean hasDevice = false;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith("device")) {
                    hasDevice = true;
                    break;
                }
                System.out.println(line);
            }
            process.waitFor();
            return hasDevice;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void logcatCatcher(LogcatCallback logCallback) throws IOException {
        clearLogcat();

        logcatThread = new Thread(() -> {
            BufferedReader reader = null;
            Process process = null;
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("adb", "logcat", "-s", "BarcodeScannerActivity");
                process = processBuilder.start();
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while (!Thread.currentThread().isInterrupted() && (line = reader.readLine()) != null) {
                    if (line.contains(VALUE)) {
                        String barcodeData = line.split(VALUE)[1];
                        System.out.println("Barcode received: " + barcodeData);

                        //update UI in main thread
                        if (isAppRunning.get()){
                            Platform.runLater(() -> {
                                logCallback.barcodeData(barcodeData);
                            });
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        logcatThread.setDaemon(true);  // ensure thread stops when application exits
        logcatThread.start();
    }

    private static void clearLogcat() {
        try {
            ProcessBuilder clearLog = new ProcessBuilder("adb", "logcat", "-c");
            clearLog.start().waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stopListening() {
        isAppRunning.set(false);
        if (logcatThread != null && logcatThread.isAlive()) {
            logcatThread.interrupt();
            System.out.println("Thread interrupted");
        }
        if (deviceTimeChecker != null) {
            deviceTimeChecker.cancel();
            System.out.println("Timer cancelled");
        }
    }

    private interface LogcatCallback {
        void barcodeData(String barcodeData);
    }
}
