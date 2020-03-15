package org.ainy.deepmind.util;

import org.ainy.deepmind.model.MultiThreadRecord;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author AINY_uan
 * @description 压力测试
 * @date 2020-03-15 15:06
 */
public class MultiThreadProxyHubApi {

    private static int count = 0;
    /**
     * 并发量
     */
    private int threadNum;
    /**
     * 总访问量
     */
    private int clientNum;

    /**
     * 平均执行时间
     */
    float avgExecTime = 0;
    /**
     * 总执行时间
     */
    float sumExecTime = 0;
    /**
     * 最大执行时间
     */
    float maxExecTime = 0;
    /**
     * 最小执行时间
     */
    float minExecTime = 0;
    /**
     * 开始执行时间
     */
    long firstExecTime = Long.MAX_VALUE;
    /**
     * 最后执行时间
     */
    long lastDoneTime = Long.MIN_VALUE;
    /**
     * 总执行时间
     */
    float totalExecTime = 0;

    public MultiThreadProxyHubApi(int threadNum, int clientNum) {

        this.threadNum = threadNum;
        this.clientNum = clientNum;
    }

    public void run() {

        final ConcurrentHashMap<Integer, MultiThreadRecord> records = new ConcurrentHashMap<>();

        // 建立ExecutorService线程池
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        // threadNum个线程可以同时访问
        // 模拟clientNum个客户端访问

        final CountDownLatch doneSignal = new CountDownLatch(clientNum);

        for (int i = 0; i < clientNum; i++) {

            Runnable run = () -> {

                int index = getIndex();
                long st = System.currentTimeMillis();

                try {
                    Socket socket = new Socket("192.168.15.230", 4001);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    byte[] bytes = hexStringToBytes(
                            "00CE10701031F1200110024000300801832018110100055501000005017200018451060000012392400030080183000000000001234567894000000093000127400040000000930001279ACEFAFA0027140315015782100001000000010020181031235912A9C6214A003030302E3030303030303030302E303030303030000075000110600000000000000000000000000000000000000000002018103123591240000001400000123444000025300000000000000000000000000000000030001200000000000000000000FCE4301B");
                    out.write(bytes);
                    InputStream is = socket.getInputStream();
                    byte[] b = new byte[1024];
                    int c;
                    StringBuilder str = new StringBuilder();
                    while ((c = is.read(b)) != -1) {
                        // 在此拼接
                        str.append(new String(b, 0, c));
                    }
                    System.out.println(str);
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                records.put(index, new MultiThreadRecord(st, System.currentTimeMillis()));
                // 每调用一次countDown()方法，计数器减1
                doneSignal.countDown();
            };
            exec.execute(run);
        }

        try {
            // 计数器大于0 时，await()方法会阻塞程序继续执行
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * 获取每个线程的开始时间和结束时间
         */
        MultiThreadRecord temp = records.get(1);
        maxExecTime = (float) (((double) (temp.getEt() - temp.getSt())) / 1000);
        minExecTime = (float) (((double) (temp.getEt() - temp.getSt())) / 1000);
        for (int i : records.keySet()) {
            MultiThreadRecord r = records.get(i);
            System.out.println("******************************" + ((double) (r.getEt() - r.getSt())) / 1000);
            sumExecTime += ((double) (r.getEt() - r.getSt())) / 1000;

            if (((double) (r.getEt() - r.getSt())) / 1000 > maxExecTime) {
                maxExecTime = (float) (((double) (r.getEt() - r.getSt())) / 1000);
            }
            if ((float) (((double) (r.getEt() - r.getSt())) / 1000) < minExecTime) {
                minExecTime = (float) (((double) (r.getEt() - r.getSt())) / 1000);
            }

            if (r.getSt() < firstExecTime) {
                firstExecTime = r.getSt();
            }
            if (r.getEt() > lastDoneTime) {
                this.lastDoneTime = r.getEt();
            }
        }

        this.avgExecTime = this.sumExecTime / records.size();
        this.totalExecTime = ((float) (this.lastDoneTime - this.firstExecTime)) / 1000;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);

        System.out.println("************************************************************");
        System.out.println("线        程       数: " + threadNum + ", 客户端数: " + clientNum + ".");
        System.out.println("平均执行时间: " + nf.format(this.avgExecTime) + " s");
        System.out.println("最长执行时间: " + nf.format(this.maxExecTime) + " s");
        System.out.println("最短执行时间: " + nf.format(this.minExecTime) + " s");
        System.out.println("总  执 行 时 间: " + nf.format(this.totalExecTime) + " s");
        System.out.println("吞        吐       量: " + nf.format(this.clientNum / this.totalExecTime) + " /s");
    }

    public static int getIndex() {
        return ++count;
    }

    public static byte[] hexStringToBytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < src.length() / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    private static byte uniteBytes(byte src0, byte src1) {

        byte b0 = Byte.decode("0x" + new String(new byte[]{src0}));
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + new String(new byte[]{src1}));
        return (byte) (b0 ^ b1);
    }
}
