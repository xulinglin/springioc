package com.test;

import javax.annotation.PostConstruct;

/**
 * 分布式唯一ID - SnowflakID
 * Created by denny on 2018/12/26
 */
public class IdGenerator {
    /**
     * 开始时间截 (2010-01-01)
     */
    private static final long twepoch = 1546272000000L;
    /**
     * 服务节点所占的位数
     */
    private static final long serverIdBits = 5L;
    /**
     * 机器节点Id所占的位数
     */
    private static final long workerIdBits = 5L;
    /**
     * 支持的最大服务节点id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long maxServerId = -1L ^ (-1L << serverIdBits);
    /**
     * 支持的最大机器标识id，结果是31
     */
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 序列在id中占的位数
     */
    private static final long sequenceBits = 12L;
    /**
     * 服务ID向左移12位
     */
    private static final long serverIdShift = sequenceBits;
    /**
     * 机器标识id向左移17位(12+5)
     */
    private static final long workerIdShift = sequenceBits + serverIdBits;
    /**
     * 时间截向左移22位(5+5+12)
     */
    private static final long timestampLeftShift = sequenceBits + serverIdBits + workerIdBits;
    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);
    /**
     * 服务ID(0~31)
     */
    private static int serverId;
    /**
     * 机器ID(0~31)
     */
    private static int workerId;
    /**
     * 毫秒内序列(0~4095)
     */
    private static long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private static long lastTimestamp = -1L;

    private static final Object lock = new Object();

    public void setServerId(int serverId) {
        IdGenerator.serverId = serverId;
    }

    public void setWorkerId(int workerId) {
        IdGenerator.workerId = workerId;
    }

    @PostConstruct
    public void init() {
        if (serverId > maxServerId || serverId < 0) {
            throw new IllegalArgumentException(String.format("server Id can't be greater than %d or less than 0", maxServerId));
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public static long next() {
        long timestamp = timeGen();
        synchronized (lock) {
            //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
            if (timestamp < lastTimestamp) {
                throw new RuntimeException(
                        String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            }
            //如果是同一时间生成的，则进行毫秒内序列
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                //毫秒内序列溢出
                if (sequence == 0) {
                    //阻塞到下一个毫秒,获得新的时间戳
                    timestamp = tilNextMillis(lastTimestamp);
                }
            }
            //时间戳改变，毫秒内序列重置
            else {
                sequence = 0L;
            }
            //上次生成ID的时间截
            lastTimestamp = timestamp;
        }
        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp) << timestampLeftShift) //
                | (workerId << workerIdShift) //
                | (serverId << serverIdShift) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected static long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        for (int i = 0; i <10 ; i++) {
            new Thread(){
                @Override
                public void run() {
                  System.out.println(IdGenerator.next());
                }
            }.start();
        }
    }
}
