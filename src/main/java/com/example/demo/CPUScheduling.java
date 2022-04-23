package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author zsx
 * @Date: 2020/6/22
 * 代码功能：编写算法，实现 CPU调度算法：非抢占短进程优先、可抢占优先权调度、多级反馈队列调度算法
 */
public class CPUScheduling {

    public static void main(String[] args) {

        boolean judge2 = true; //判断循环是否结束
        while (judge2) {
            System.out.println("**********************************");
            System.out.println("********请先输入进程数量：**********");
            System.out.println("**********************************");
            Scanner sc = new Scanner(System.in);
            int processNum = sc.nextInt(); // 进程数

            // 进程的数组，记录进程的所有相关信息。构建多个是为了用于不同算法，避免修改原数组
            Process process[] = new Process[processNum];
            Process process1[] = new Process[processNum];
            Process process2[] = new Process[processNum];

            Random r = new Random(); // 采用随机数的方式来产生各项参数
            for (int i = 0; i < processNum; i++) {
                int ran1 = r.nextInt(156); // 优先级参数的随机数 100~255
                int ran2 = r.nextInt(9); // 到达时间参数的随机数 1~9
                int ran3 = r.nextInt(9); // 服务时间参数的随机数 1~9

                // 进程相关信息的初始化
                process[i] = new Process(i, ran1 + 100, ran2 + 1, ran3 + 1);
                process1[i] = new Process(i, ran1 + 100, ran2 + 1, ran3 + 1);
                process2[i] = new Process(i, ran1 + 100, ran2 + 1, ran3 + 1);
            }


            boolean judge = true;//判断循环是否结束
            while (judge) {
                System.out.println("**********************************");
                System.out.println("*****" + processNum + "个进程已经随机初始化成功！*****");
                System.out.println("*****请输入数字，进行相关操作：******");
                System.out.println("*****数字1：非抢占短进程优先算法*****");
                System.out.println("*****数字2：可抢占优先权调度算法*****");
                System.out.println("*****数字3：多级反馈队列调度算法*****");
                System.out.println("*****数字4：重置进程相关信息*********");
                System.out.println("*****数字5：退出程序****************");
                System.out.println("**********************************");
                int num = sc.nextInt(); // 输入数字
                if (num == 1) {
                    // 调用非抢占短进程优先算法
                    compute t = new compute(); // 线程
                    t.start();
                    System.out.println("**********************************");
                    System.out.println("***********即将调用算法！***********");
                    System.out.println("**********************************");
                    while (t.isAlive()) {
                    }

                    long starttime = System.currentTimeMillis();
                    NSP(process);
                    System.out.println("非抢占短进程优先算法运行时间：");
                    System.out.println(System.currentTimeMillis() - starttime + "ms");

                    compute t1 = new compute(); // 线程
                    t1.start();
                    System.out.println("**********************************");
                    System.out.println("***********算法运行结束！***********");
                    System.out.println("**********************************");
                    while (t1.isAlive()) {
                    }
                } else if (num == 2) {
                    // 调用可抢占优先权调度算法
                    compute t = new compute(); // 线程
                    t.start();
                    System.out.println("**********************************");
                    System.out.println("***********即将调用算法！***********");
                    System.out.println("**********************************");
                    while (t.isAlive()) {
                    }

                    long starttime = System.currentTimeMillis();
                    PPS(process1);
                    System.out.println("可抢占优先权调度算法运行时间：");
                    System.out.println(System.currentTimeMillis() - starttime + "ms");

                    compute t1 = new compute(); // 线程
                    t1.start();
                    System.out.println("**********************************");
                    System.out.println("***********算法运行结束！***********");
                    System.out.println("**********************************");
                    while (t1.isAlive()) {
                    }
                } else if (num == 3) {
                    // 调用多级反馈队列调度算法
                    compute t = new compute(); // 线程
                    t.start();
                    System.out.println("**********************************");
                    System.out.println("***********即将调用算法！***********");
                    System.out.println("**********************************");
                    while (t.isAlive()) {
                    }

                    long starttime = System.currentTimeMillis();
                    MFQS(process2);
                    System.out.println("多级反馈队列调度算法运行时间：");
                    System.out.println(System.currentTimeMillis() - starttime + "ms");

                    compute t1 = new compute(); // 线程
                    t1.start();
                    System.out.println("**********************************");
                    System.out.println("***********算法运行结束！***********");
                    System.out.println("**********************************");
                    while (t1.isAlive()) {
                    }
                } else if (num == 4) {
                    compute t = new compute(); // 重置线程
                    t.start();
                    System.out.println("**********************************");
                    System.out.println("************即将重置！*************");
                    while (t.isAlive()) {
                    }
                    break;
                } else if (num == 5) {
                    System.out.println("**********************************");
                    System.out.println("************退出成功！*************");
                    System.out.println("**********************************");
                    judge2 = false;
                    judge = false;
                    break;
                } else {
                    System.out.println("输入的数字信息有误，建议重新输入");
                }
            }
        }
    }

    /**
     * NSPP：非抢占短进程优先算法（Nonpreemptive short process priority）
     * 算法简介：非抢占方式就是一旦把处理机分配给某进程后，就一直让它运行下去，绝不会因为
     * 时钟中断或任何其他原因去抢占当前正在运行进程的处理机，直至该进程完成。
     * 短进程优先就是指处理机只会被分配给尚需运行时间最短的进程。
     * （显然，如果是抢占式短进程优先，那么process还需要一个"尚需运行时间"属性）
     * 使用到的相关参数如下：
     * process：存放进程相关信息的数组
     */
    public static void NSP(Process process[]) {

        int time = 1; //本次算法当前正在运行的时间片，会随着时间不断增加

        int finish = 0; //记录进程完成的数量，当完成数量=进程数量，就证明所有进程运行完毕
        int queueFinish = 0; //记录进程进入队列的数量，当进入数量=进程数量，就证明所有进程进入队列

        Queue queue = new Queue(); //就绪队列：每次循环按照服务时间由小到大顺序的存放已经到达进程。
        Queue queue2 = new Queue(); //输出队列：所有已经完成的进程就会被存入到该队列中。
        int index = -1;  //记录这个短进程的下标
        Process firstProcess = new Process(); //记录就绪队列中的队首进程

        System.out.println("首先展示进程初始化后的基本信息");
        System.out.println("进程ID 进程优先级   进程到达时间   进程服务时间");
        for (int i = 0; i < process.length; i++) {
            System.out.println("   " + process[i].id + "      " + process[i].priority + "         " + process[i].arriveTime + "            " + process[i].serviceTime);
        }

        //为了后续算法的方便，直接对所有进程按照服务时间大小排序
        //排序之后，只要该进程是当前时间片，那么按顺序存放到队列中就必然是从小到大的顺序
        //采用冒泡排序法
        Process processTemp = new Process(); //临时变量
        boolean flag = false; // 标识变量，表示是否进行过交换
        for (int j = 0; j < process.length - 1; j++) {
            for (int k = 0; k < process.length - 1 - j; k++) {
                // 如果该进程的服务时间比后面进程的服务时间长，则交换
                if (process[k].serviceTime > process[k + 1].serviceTime) {
                    flag = true;
                    processTemp = process[k];
                    process[k] = process[k + 1];
                    process[k + 1] = processTemp;
                } else if (process[k].serviceTime == process[k + 1].serviceTime) {
                    // 如果该进程的服务时间和后一个进程的服务时间相同，那么就比较优先级
                    if (process[k].priority < process[k + 1].priority) {
                        //如果该进程优先级比上一个进程低那就交换，否则不需要作任何改动
                        flag = true;
                        processTemp = process[k];
                        process[k] = process[k + 1];
                        process[k + 1] = processTemp;
                    }
                }
            }
            if (!flag) { // 在一趟排序中，一次交换都没有发生过
                break;
            } else {
                flag = false; // 重置flag, 进行下次判断
            }
        }

        while (true) {
            //因为之前已经对进程数组进行过服务时间的冒泡排序
            //所以对process数组进行循环，寻找的第一个已经到达的进程必然是最短的。
            for (int i = 0; i < process.length; i++) {
                //找到一个当前已经到达，且还没进入到队列当中的进程
                if (process[i].arriveTime <= time && process[i].queueNum == 0) {
                    if (index != -1) {
                        //如果在循环过程中，找到了又一个满足条件的进程
                        //但是很可惜它无法现在进入到就绪队列中，因为index获取到了上一个短进程
                        //因此它的等待时间需要增加
                        process[i].waitTime++;
                    } else {
                        //获取它的下标
                        index = i;
                        //该进程已经进入队列
                        process[i].queueNum = 1;
                        queue.in(process[i]);
                        //进入队列数+1
                        queueFinish++;
                    }
                }
            }
            //如果没有找到进程
            if (index == -1) {
                //情况1，所有进程进入队列，直接继续向下运行即可
                if (queueFinish == process.length) {
                    //向下运行
                } else if (queue.list.size() == 0) {
                    //情况2，仍有进程没到达，且就绪队列中也没有进程
                    System.out.println("在当前第" + time + "个时间片，没有进程到达且就绪队列中没有进程！");
                    time++;
                    compute t = new compute(); // 线程
                    t.start();
                    while (t.isAlive()) {
                    }
                    continue;
                }
            }
            //运行到这里，证明就绪队列的队首元素就是当前时间下的最短进程
            //获取队首进程
            firstProcess = queue.get(0);
            firstProcess.state = "R"; //设置进程为运行状态

            compute t = new compute(); // 线程
            t.start();
            while (t.isAlive()) {
            }

            //如果队首进程的已经服务时长=进程所需服务时长，证明该进程已经完成任务
            //在这里提前设置一个，是为了让输出看起来更合理
            if (firstProcess.hasServiceTime == firstProcess.serviceTime) {
                firstProcess.state = "F"; //设置进程为完成状态
                firstProcess.finishTime = time; //记录进程完成的时间
            }

            System.out.println("******************************************************");
            System.out.println("在当前第" + time + "个时间片，进入到就绪队列的进程如下：");
            System.out.println("进程ID 进程优先级    进程到达时间   进程服务时间   进程状态    进程已经服务时间    进程完成时间    进程等待时间");
            queue.outputQueueAll(time);

            //如果队首进程的已经服务时长=进程所需服务时长，证明该进程已经完成任务
            if (firstProcess.hasServiceTime == firstProcess.serviceTime) {
                finish++;
                queue2.in(queue.out()); //将完成的进程放入到完成队列中
                if (finish == process.length) {
                    // 如果进程完成数量等于进程数量，就证明所有进程已经完成工作
                    break;
                }
                for (int a = 1; a < queue.list.size(); a++) {
                    //让除了队首进程以外，所有进入就绪队列的进程，其等待时间+1
                    queue.get(a).waitTime++;
                }
                if (queue.list.size() != 0) {
                    //如果队列中还存在着下一个进程
                    //上一个进程已经执行完毕，下一个进程开始执行
                    queue.get(0).hasServiceTime++; //已经服务时间增加
                }

                time++; //时间增加
                index = -1; //初始化
                //运行到这里证明还有进程没完成操作，因此进入下一次循环，避免进行下面的初始化
                continue;
            }

            for (int a = 1; a < queue.list.size(); a++) {
                if (firstProcess.hasServiceTime + 1 == firstProcess.serviceTime && a == 1) {
                    //如果下一回合队列首部的进程完成任务，那么队首后的第一个进程等待时间就不需要增加了
                    continue;
                }
                //让除了队首进程以外，所有进入就绪队列的进程，其等待时间+1
                queue.get(a).waitTime++;
            }
            firstProcess.hasServiceTime++; //已经服务时间增加
            time++; //时间增加
            index = -1; //初始化

        }

        compute t = new compute(); // 线程
        t.start();
        while (t.isAlive()) {
        }

        System.out.println("******************************************************");
        System.out.println("在当前第" + time + "个时间片，所有进程都执行完毕：");
        System.out.println("进程ID 进程优先级    进程到达时间   进程服务时间   进程状态    进程已经服务时间    进程完成时间    进程等待时间");
        queue2.outputQueueAll(time);

        System.out.println("******************************************************");
        System.out.println("算法结束，开始计算平均周转时间和平均等待时间");

        compute t1 = new compute(); // 线程
        t1.start();
        while (t1.isAlive()) {
        }

        outputTime(process);
    }

    /**
     * PPS：可抢占优先权调度算法（Preemptive priority scheduling）
     * 算法简介：可抢占优先权调度算法又被称为抢占式优先级调度算法。
     * 它会把处理机分配给优先级最高的进程，使之执行。但在其执行期间，只要出现了
     * 另一个其优先级更高的进程，调度程序就将处理机分配给新到的优先级最高的进程。
     * 使用到的相关参数如下：
     * process：存放进程相关信息的数组
     */
    public static void PPS(Process process[]) {

        int time = 1; //记录当前正在运行的时间片

        int finish = 0; //记录进程完成的数量

        int priorityT = 0; //记录哪个优先级最高
        int index = -1;  //记录这个优先级最高进程的下标

        System.out.println("首先展示进程初始化后的基本信息");
        System.out.println("进程ID 进程优先级   进程到达时间   进程服务时间");
        for (int i = 0; i < process.length; i++) {
            System.out.println("   " + process[i].id + "      " + process[i].priority + "         " + process[i].arriveTime + "            " + process[i].serviceTime);
        }

        while (true) {
            // 因为是可抢占优先权调度算法，所以每次循环都是一个时间片内做的操作
            // 每次循环都需要找到当前优先级最高的进程
            for (int i = 0; i < process.length; i++) {
                //找到一个当前已经到达，且还没有运行完成的进程
                if (process[i].arriveTime <= time && process[i].state != "F") {
                    // 如果该进程的优先级比上一个进程的优先级大
                    if (process[i].priority > priorityT) {
                        index = i; //记录这个下标
                        priorityT = process[i].priority; //记录这个优先级
                    } else if (process[i].priority == priorityT) {
                        // 如果该进程的优先级和上一个进程的优先级相同，那么就比较哪个进程服务时间短
                        if (process[i].serviceTime < process[index].serviceTime) {
                            //如果该进程服务时间比上一个进程小那就修改，否则不需要作任何改动
                            index = i; //记录这个下标
                        }
                    }

                }
            }
            if (index == -1) {
                compute t = new compute(); // 线程
                t.start();
                while (t.isAlive()) {
                }

                System.out.println("******************************************************");
                //如果index为-1，也就意味着当前没有进程到达
                System.out.println("当前时间片" + time + "没有进程到达！");
                time++;
                continue;
            }

            compute t = new compute(); // 线程
            t.start();
            while (t.isAlive()) {
            }

            process[index].state = "R"; //当前进程开始运行
            outputContent(process, time);
            process[index].hasServiceTime++; //服务时间增加

            if (process[index].hasServiceTime == process[index].serviceTime) {
                //如果相等，也就意味着该进程已经运行完毕
                finish++;
                process[index].state = "F"; //当前进程运行完成

                //动态优先级：让本次时间片内除运行进程外的所有进程优先级上调，以便给其他进程机会
                for (int a = 0; a < process.length; a++) {
                    //只要该进程未完成，且还没到达，并且该进程不是正在运行的进程
                    if (process[a].state != "F" && a != index && process[a].arriveTime <= time) {
                        //那么 优先级上调
                        process[a].priority += 3;
                    }
                }

                time++; //时间增加
                process[index].finishTime = time; //当前进程的完成时间就是当前时间片
                priorityT = 0; //初始化
                index = -1; //初始化

                if (finish == process.length) {
                    compute t1 = new compute(); // 线程
                    t1.start();
                    while (t1.isAlive()) {
                    }

                    // 如果完成数量等于进程数量，就证明所有进程已经完成工作
                    outputContent(process, time);
                    break;
                }

                continue;
            }

            //动态优先级：让本次时间片内除运行进程外的所有进程优先级上调，以便给其他进程机会
            for (int a = 0; a < process.length; a++) {
                //只要该进程未完成，且还没到达，并且该进程不是正在运行的进程
                if (process[a].state != "F" && a != index && process[a].arriveTime <= time) {
                    //那么 优先级上调
                    process[a].priority += 3;
                }
            }

            time++;
            priorityT = 0; //初始化
            process[index].state = "W"; //初始化，因为下一次循环不一定还是该进程运行
            index = -1; //初始化
        }

        compute t2 = new compute(); // 线程
        t2.start();
        while (t2.isAlive()) {
        }

        System.out.println("算法结束，开始计算平均周转时间和平均等待时间");
        outputTime(process);
    }

    /**
     * MFQS：多级反馈队列调度算法（Multileved feedback queue scheduling）
     * 算法简介：在系统中设置多个就绪队列，并为每个队列赋予不同的优先级。第一个队列的优先级最高，
     * 第二个次之，其余队列的优先级逐个降低。该算法为不同队列中的进程所赋予的执行时间片
     * 的大小也各不相同，在优先级愈高的队列中，其时间片就愈小。例如：第二个队列的时间片
     * 要比第一个的时间片长一倍。因此在这里假设第一个队列分配时间片长度为2，第二个队列
     * 分配时间片长度为4....以此类推。且在这里假设一共只有4个队列.
     * 除此以外，每个队列都采用FCFS算法。当新进程进入内存后，首先将它放入第一队列的末尾，
     * 按FCFS原则等待调度。当轮到该进程执行时，如它能在该时间片内完成，便可撤离系统。
     * 否则，它将被转入第二队列的末尾等待调度...以此类推。
     * 使用到的相关参数如下：
     * process：存放进程相关信息的数组
     */
    public static void MFQS(Process process[]) {

        int time = 1;//记录当前正在运行的时间片

        int index = -1;  //记录这个优先级最高进程的下标
        int priorityT = 0; //记录优先级最高的这个值

        int serviceNum = 0;//记录process数组中已经放入队列的个数：如果个数=进程数，那就意味着所有进程都已经放入队列，不需要再去数组中寻找进程
        int finish = 0; //记录进程完成的数量：如果数量=进程数，那就意味着所有进程运行完毕，算法结束

        boolean isQueueEmpty = true; //记录所有队列是否为空：如果所有队列都为空，且没有进程到达，那就意味着此次循环不需要做任何操作。

        System.out.println("首先展示进程初始化后的相关信息");
        System.out.println("进程ID 进程优先级   进程到达时间   进程服务时间");
        for (int i = 0; i < process.length; i++) {
            System.out.println("   " + process[i].id + "      " + process[i].priority + "         " + process[i].arriveTime + "            " + process[i].serviceTime);
        }

        //对四个队列进行内容的初始化
        Queue queue1 = new Queue();
        queue1.q = 2;
        queue1.num = 1;
        Queue queue2 = new Queue();
        queue2.q = 4;
        queue2.num = 2;
        Queue queue3 = new Queue();
        queue3.q = 8;
        queue3.num = 3;
        Queue queue4 = new Queue();
        queue4.q = 16;
        queue4.num = 4;
        //为了方便看到最后的输出结果，用队列5存放已经完成的进程
        Queue queue5 = new Queue();

        while (true) {
            // 因为是多级反馈队列调度，所以每次循环都是一个指定队列时间片内做的操作
            // 每次循环都从优先级高的队列选出第一个进程进行操作

            //process数组中的进程，早晚都会全部送进队列中，所以需要一个参数记录
            //如果serviceNum == process.length就证明所有进程全部存入队列
            //所以就不需要去process数组中寻找进程
            if (serviceNum != process.length) {
                //通过循环，找到当前process数组中，应该存放到队列中的进程
                //这个进程应该满足以下条件：
                //1、当前已经到达且还没有运行完成的进程
                //2、在当前已经到达的进程中它的优先级最高，那么就先把它放进队列中
                //3、如果出现优先级相等的情况，就比较服务时间
                for (int i = 0; i < process.length; i++) {
                    //找到一个当前已经到达，且还没有进入队列的进程
                    if (process[i].arriveTime <= time && process[i].queueNum == 0) {
                        // 如果该进程的优先级比上一个进程的优先级大
                        if (process[i].priority > priorityT) {
                            index = i; //记录这个下标
                            priorityT = process[i].priority; //记录这个优先级
                        } else if (process[i].priority == priorityT) {
                            // 如果该进程的优先级和上一个进程的优先级相同，那么就比较哪个进程服务时间短
                            if (process[i].serviceTime < process[index].serviceTime) {
                                //如果该进程服务时间比上一个进程小那就修改，否则不需要作任何改动
                                index = i; //记录这个下标
                            }
                        }
                    }
                }
                if (index != -1) {
                    serviceNum++; //只有找到满足条件的进程，才会进行自加
                }
                // 如果没有找到到达的进程，并且队列是空的，也就意味着当前不会进行任何操作
                if (index == -1 && isQueueEmpty && serviceNum != process.length) {
                    System.out.println("******************************************************");
                    System.out.println("当前" + time + "时间点，没有进程到达，且队列中也没有进程在运行！");
                    time++; //时间增加

                    compute t = new compute(); // 线程
                    t.start();
                    while (t.isAlive()) {
                    }

                    continue;
                }
            }
            if (index != -1) {
                //证明，有新进程进入，需要给它分配到第一个队列当中
                queue1.in(process[index]);
                process[index].queueNum = 1;
                isQueueEmpty = false;
            }
            // 判断哪个队列里有进程，优先级高的队列优先运行
            // 如果队列1不空
            if (!queue1.list.isEmpty()) {
                System.out.println("******************************************************");
                System.out.println("在当前第" + time + "个时间点，有进程在队列1中");
                System.out.println("现在为其队首进程分配q=2的时间片");
                int a[] = MFQSRunning(queue1, queue2, queue5, finish, process, time);
                finish = a[0];
                time = a[1];
            } else if (!queue2.list.isEmpty()) { //如果队列1为空，队列2不空
                System.out.println("******************************************************");
                System.out.println("在当前第" + time + "个时间点，队列1为空，但队列2中有进程");
                System.out.println("现在为其队首进程分配q=4的时间片");
                int a[] = MFQSRunning(queue2, queue3, queue5, finish, process, time);
                finish = a[0];
                time = a[1];
            } else if (!queue3.list.isEmpty()) { //如果队列1，2为空，队列3不空
                System.out.println("******************************************************");
                System.out.println("在当前第" + time + "个时间点，队列1，2为空，但队列3中有进程");
                System.out.println("现在为其队首进程分配q=8的时间片");
                int a[] = MFQSRunning(queue3, queue4, queue5, finish, process, time);
                finish = a[0];
                time = a[1];
            } else if (!queue4.list.isEmpty()) { //如果队列1，2，3为空，队列4不空
                System.out.println("******************************************************");
                System.out.println("在当前第" + time + "个时间点，队列1，2，3为空，但队列4中有进程");
                System.out.println("现在为其队首进程分配q=16的时间片");
                int a[] = MFQSRunning(queue4, queue4, queue5, finish, process, time);
                finish = a[0];
                time = a[1];
            } else { //如果队列1，2，3，4都为空
                isQueueEmpty = true;
            }

            index = -1;  //初始化
            priorityT = 0; //初始化

            compute t = new compute(); // 线程
            t.start();
            while (t.isAlive()) {
            }

            if (finish == process.length) {
                // 如果完成数量等于进程数量，就证明所有进程已经完成工作
                System.out.println("******************************************************");
                System.out.println("在当前第" + (time - 1) + "个时间点，所有进程已经运行完毕。它们的相关信息为：");
                System.out.println("进程ID 进程优先级    进程到达时间   进程服务时间   进程状态    进程已经服务时间    进程完成时间    进程等待时间");
                queue5.outputQueueAll(time - 1);
                break;
            }
        }

        System.out.println("算法结束，开始计算平均周转时间和平均等待时间");
        outputTime(process);
    }

    /**
     * MFQSWaitTime：专门用于计算MFQS中各进程的等待时间
     * 使用到的相关参数如下：
     * process：存放进程相关信息的数组
     * id：当前正在运行的进程
     * time：当前经历了多少时间片
     */
    public static void MFQSWaitTime(Process process[], int id, int time) {
        //除了当前正在运行的进程以外，所有已经到达，且没有完成的进程，等待时间全部+1
        for (int i = 0; i < process.length; i++) {
            if (process[i].arriveTime <= time && process[i].state != "F") {
                //找到了当前正在运行的进程，跳过该进程
                if (process[i].id == id) {
                    continue;
                }
                process[i].waitTime++;
            }
        }
    }

    /**
     * MFQSRunning：专门用于计算MFQS中各进程在每个队列中运行的过程
     * 使用到的相关参数如下：
     * process：存放进程相关信息的数组
     * id：当前正在运行的进程
     * time：当前经历了多少时间片
     * 它返回一个int数组，数组中存放finish和time变化后的数值
     */
    public static int[] MFQSRunning(Queue queue1, Queue queue2, Queue queue3, int finish, Process process[], int time) {

        System.out.println("进程ID 进程优先级    进程到达时间   进程服务时间   进程状态    进程已经服务时间    进程完成时间    进程等待时间");
        //获取当前队列中的队首进程
        Process firstProcess = queue1.get(0);
        //给该进程设置为运行状态
        firstProcess.state = "R";
        //该进程在队列中的时间片增加
        firstProcess.queueTime++;

        //给除了该进程以外的其他已经到达的进程增加等待时间
        MFQSWaitTime(process, firstProcess.id, time);


        //如果达成条件，证明在队列中运行时，该进程已经完成了任务
        if (firstProcess.hasServiceTime == firstProcess.serviceTime) {
            finish++; //进程完成数量增加
            firstProcess.state = "F"; //设置该进程为完成状态
            firstProcess.finishTime = time; //记录当前的完成时间

            if (queue1.list.size() > 1) {
                //如果包括当前已经完成的进程在内，它的后面还有进程
                //那么这个后面的进程就做以下操作
                queue1.get(1).state = "R";
            }

            //输出情况
            queue1.outputQueueAll(time);

            if (queue1.list.size() > 1) {
                //如果包括当前已经完成的进程在内，它的后面还有进程
                //那么这个后面的进程就做以下操作
                queue1.get(1).hasServiceTime++;
            } else if (queue2.list.size() >= 1 && queue2.num != queue1.num) {
                //如果当前队列中，除了已经完成的进程外，没有其他进程，并且不是最后一个队列
                //那么这个队列下面的队列的队首元素做如下操作
                queue2.get(0).hasServiceTime++;
            }

            time++;//时间增加
            System.out.println("队首进程，在当前队列" + queue1.num + "中完成了任务");
            //加入到队列5中
            queue3.in(queue1.out());
            //返回在当前函数中运行的结果
            int a[] = {finish, time};
            return a;
        }

        //输出情况
        queue1.outputQueueAll(time);
        //该进程的服务时间增加
        firstProcess.hasServiceTime++;
        time++;//时间增加

        if (firstProcess.queueTime == queue1.q) {
            if (queue1.num != 4) {
                if (firstProcess.hasServiceTime == firstProcess.serviceTime) {
                    //如果相等，就意味着该进程在本队列的最后一个时间片上，完成了任务
                    //因此需要放入完成队列中
                    finish++; //进程完成数量增加
                    firstProcess.state = "F"; //设置该进程为完成状态
                    firstProcess.finishTime = time - 1; //记录当前的完成时间

                    if (queue1.list.size() > 1) {
                        //如果包括当前已经完成的进程在内，它的后面还有进程
                        //那么这个后面的进程就做以下操作
                        queue1.get(1).state = "R";
                    }

                    System.out.println("队首进程，在当前队列" + queue1.num + "的最后一个时间片上完成了任务");
                    //加入到队列5中
                    queue3.in(queue1.out());
                    //返回在当前函数中运行的结果
                    int a[] = {finish, time};
                    return a;
                } else {
                    //运行到这一步，一定意味着该进程在该队列中没有完成任务，因此会被放入下一个队列
                    firstProcess.state = "W";
                    firstProcess.queueTime = 0;
                    queue2.in(queue1.out());
                    System.out.println("队首进程未完成，将会被放入到" + queue2.num + "队列中");
                    //返回在当前函数中运行的结果
                    int a[] = {finish, time};
                    return a;
                }
            } else {
                //在第四个队列中，如果时间片运行结束，该进程还没有完成任务，那么就会被放入当前队列的队尾。
                firstProcess.state = "W";
                firstProcess.queueTime = 0;
                queue1.in(queue1.out());
                System.out.println("队首进程未完成，将会被放入到本队列的队尾");
                //返回在当前函数中运行的结果
                int a[] = {finish, time};
                return a;
            }
        }

        //返回在当前函数中运行的结果
        int a[] = {finish, time};
        return a;
    }

    /**
     * outputContent：在运行过程中每进行一次所需要输出的内容，并作出相关修改操作。专门用于PPS算法。
     * 使用到的相关参数如下：
     * process：存放进程相关信息的数组
     * time：当前经历了多少时间片
     */
    public static void outputContent(Process process[], int time) {
        System.out.println("******************************************************");
        System.out.println("经过了第" + time + "个时间片后，各进程的状态如下：");
        System.out.println("进程ID 进程优先级   进程到达时间   进程服务时间   进程状态    进程已经服务时间    进程等待时间   进程完成时间");
        for (int i = 0; i < process.length; i++) {
            //当某进程处于等待阶段，且已经到达，等待时间才会增加
            if (process[i].state == "W" && process[i].arriveTime <= time) {
                process[i].waitTime++;
            }
            System.out.println("   " + process[i].id + "      " + process[i].priority + "         " + process[i].arriveTime + "            " + process[i].serviceTime + "         " + process[i].state + "         " + process[i].hasServiceTime + "              " + process[i].waitTime + "              " + process[i].finishTime);
        }
        System.out.println("******************************************************");
    }

    /**
     * outputTime：用于计算平均周转时间
     * 周转时间 = 完成时间 - 到达时间
     * 平均周转时间 = 周转时间 / 进程个数
     * 使用到的相关参数如下：
     * process：存放进程相关信息的数组
     */
    public static void outputTime(Process process[]) {
        double time = 0; //周转时间
        double waitTime = 0; //等待时间
        for (int i = 0; i < process.length; i++) {
            time += process[i].finishTime - process[i].arriveTime;
            waitTime += process[i].waitTime;
        }
        double averageTime = (time / process.length); //平均周转时间
        double averageWait = (waitTime / process.length); //平均等待时间
        System.out.println("算法的平均周转时间如下：" + averageTime);
        System.out.println("算法的平均等待时间如下：" + averageWait);
    }
}

// 进程类
class Process {
    public int id; // 进程ID
    public String state = "W"; // 进程状态：F完成，W等待，R运行
    public int priority; // 优先级
    public int arriveTime; // 进程到达时间
    public int serviceTime; // 进程服务时间
    public int finishTime;  //进程的完成时间
    public int waitTime; //进程的等待时间
    public int hasServiceTime; //进程已经被服务的时间 ->主要用于PPS算法，去判断该进程是否结束
    public int queueNum = 0; //进程目前是否进入队列 -> 主要用于MFQS和NSP算法，去判断该进程是否进入队列：0为没进入，1为进入
    public int queueTime = 0; //进程目前在队列中运行的时间 -> 主要用于MFQS算法

    public Process() {

    }

    public Process(int id, int priority, int arriveTime, int serviceTime) {
        this.id = id;
        this.priority = priority;
        this.arriveTime = arriveTime;
        this.serviceTime = serviceTime;
    }
}

//线程类：用于实现停顿效果
class compute extends Thread {
    public void run() {
        try {
            sleep(2500);
        } catch (Exception e) {

        }
    }
}

//使用集合定义一个队列，用于实现先进先出效果
class Queue {

    List<Process> list = new ArrayList<Process>();
    int index = 0;  //下标
    int num = 0; //队列编号
    int q; //时间片长度

    //入队
    public void in(Process n) {
        list.add(n);
        index++;
    }

    //出队
    public Process out() {
        if (!list.isEmpty()) {
            index--;
            return list.remove(0);
        } else {
            Process n = new Process();
            n.id = -1; // 如果id为-1就证明，队列为空
            return n;
        }
    }

    //获取当前队列中的相应位置的进程
    public Process get(int n) {
        return list.get(n);
    }

    //输出当前队列全部信息
    public void outputQueueAll(int time) {
        for (int i = 0; i < list.size(); i++) {
            Process process = list.get(i);
            System.out.println("   " + process.id + "      " + process.priority + "         " + process.arriveTime + "             " + process.serviceTime + "         " + process.state + "           " + process.hasServiceTime + "              " + process.finishTime + "           " + process.waitTime);
        }
    }

}
