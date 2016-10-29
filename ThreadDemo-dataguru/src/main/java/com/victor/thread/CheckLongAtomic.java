package com.victor.thread;
public class CheckLongAtomic {
    protected long l = -2l;
    public static void main(String[] args) {
        CheckLongAtomic t = new CheckLongAtomic();
        Worker w1 = new Worker(t);
        Worker2 w2 = new Worker2(t);
        w1.setDaemon(true);
        w2.setDaemon(true);
        w1.start();
        w2.start();
        while (true) {
            if (t.l != -2l && t.l != 2l) {
                System.out.println("l的写不是原子操作");
                break;
            }
        }
    }
}

class Worker extends Thread {

    public Worker(CheckLongAtomic t) {
        this.t = t;
    }
    private CheckLongAtomic t;

    public void run() {
        while (true) {
            t.l = -2l;
        }
    }
}

class Worker2 extends Thread {

    public Worker2(CheckLongAtomic t) {
        this.t = t;
    }

    private CheckLongAtomic t;

    public void run() {
        while (true) {
            t.l = 2l;
        }
    }

	}