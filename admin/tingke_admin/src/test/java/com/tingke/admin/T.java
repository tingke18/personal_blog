package com.tingke.admin;

import java.util.concurrent.TimeUnit;

public class T {

private volatile static Integer num = 0;

    public static void main(String[] args) {

        new Thread(()->{
            while (num == 0){}

        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                num = 1;
                System.out.println(num+"已修改"+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
