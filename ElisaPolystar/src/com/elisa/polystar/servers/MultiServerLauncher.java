//package com.elisa.polystar.servers;
//
//import java.util.logging.Logger;
//
//public class MultiServerLauncher {
//    private static final Logger logger = Logger.getLogger(MultiServerLauncher.class.getName());
//
//    public static void main(String[] args) {
//        logger.info("Starting DraculaServer...");
//        Thread draculaThread = new Thread(() -> {
//            try {
//                DraculaServer.start();
//            } catch (Exception e) {
//                logger.severe("DraculaServer Exception: " + e.getMessage());
//            }
//        });
//
//        logger.info("Starting FrankensteinServer...");
//        Thread frankensteinThread = new Thread(() -> {
//            try {
//                FrankensteinServer.start();
//            } catch (Exception e) {
//                logger.severe("FrankensteinServer Exception: " + e.getMessage());
//            }
//        });
//
//        draculaThread.start();
//        frankensteinThread.start();
//        logger.info("Both servers should now be running...");
//    }
//}
