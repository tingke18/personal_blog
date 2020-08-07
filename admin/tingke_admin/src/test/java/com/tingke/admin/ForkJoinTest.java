package com.tingke.admin;

import java.util.concurrent.RecursiveTask;

public class ForkJoinTest extends RecursiveTask<Long> {

    private Long start;
    private Long end;

    private final static Long border = 100000l;

    public ForkJoinTest(long start,long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        Long sum = 0l;
        if ((end-start) < border){
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else {
            long middle = (end + start)/2;
            ForkJoinTest fork1 = new ForkJoinTest(start, middle);
            fork1.fork();
            ForkJoinTest fork2 = new ForkJoinTest(middle+1, end);
            fork2.fork();
            return fork1.join()+fork2.join();
        }
    }
}
