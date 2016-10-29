package com.victor.thread;
public class CheckIntAtomic {

    protected int l = -1;

    public static void main(String[] args) {
        System.out.println(toBinary(-1));
        System.out.println(toBinary(1));
        CheckIntAtomic t = new CheckIntAtomic();
        Worker3 w1 = new Worker3(t);
        Worker4 w2 = new Worker4(t);
        w1.setDaemon(true);
        w2.setDaemon(true);
        w1.start();
        w2.start();
        while (true) {
            if (t.l != -1 && t.l != 1) {
                System.out.println(toBinary(t.l));
                System.out.println("int的写不是原子操作");
                break;
            }
        }
    }

    private static String toBinary(int l) {
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(l));
        while (sb.length() < 64) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }
}

class Worker3 extends Thread {

    public Worker3(CheckIntAtomic t) {
        this.t = t;
    }

    private CheckIntAtomic t;

    public void run() {
        while (true) {
            t.l = -1;
        }
    }
}

class Worker4 extends Thread {

    public Worker4(CheckIntAtomic t) {
        this.t = t;
    }

    private CheckIntAtomic t;

    public void run() {
        while (true) {
            t.l = 1;
        }
    }

	}