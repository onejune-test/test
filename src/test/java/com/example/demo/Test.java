package com.example.demo;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述
 *
 * @author 王俊
 * @date create in 2023/8/30
 */
@Slf4j
public class Test {
    private static final ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {

        TestThread t1 = new TestThread("t1");
        TestThread t2 = new TestThread("t2");
        t1.start();
        threadLocal.set(0);
        t2.start();

        log.info("main end, {}", threadLocal.get());


    }

    static class TestThread extends Thread {
        private final String name;

        public TestThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            Integer integer = threadLocal.get();
            log.info("{}:, n{}", name, integer);
        }
    }



}
