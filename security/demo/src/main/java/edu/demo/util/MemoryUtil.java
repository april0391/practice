package edu.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryUtil {

    private static final Logger log = LoggerFactory.getLogger(MemoryUtil.class);

    public static void printMemory() {
        // 최대 힙 메모리 크기
        long maxMemory = Runtime.getRuntime().maxMemory();
        // 초기 힙 메모리 크기
        long totalMemory = Runtime.getRuntime().totalMemory();
        // 현재 사용 중인 메모리
        long freeMemory = Runtime.getRuntime().freeMemory();

        log.info("Max Heap Memory: {} MB", maxMemory / (1024 * 1024));
        log.info("Total Heap Memory: {} MB", totalMemory / (1024 * 1024));
        log.info("Free Memory: {} MB", freeMemory / (1024 * 1024));
    }
}
