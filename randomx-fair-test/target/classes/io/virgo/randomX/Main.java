package io.virgo.randomX;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    private static int dificutty = 1;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.Hashcal();
        main.Randomxcal();

    }

    private void Hashcal() throws IOException {
        File writename = new File("output.txt");
        writename.createNewFile(); // 创建新文件
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        long [] time = new long[10];
        Random random = new Random();
        byte[] bytes;
        byte[] hashbytes;
        out.write("sha256\r\n");
        for(int j=1;j<10;j++){
            dificutty  = j;
            out.write("dificutty = "+ j+"  ");
            a:for(int i=0;i<10;i++){
                long starttime = System.currentTimeMillis();
                while (true) {
                    bytes = new byte[]{(byte) random.nextInt(), (byte) random.nextInt(), (byte) random.nextInt(),
                            (byte) random.nextInt(), (byte) random.nextInt(), (byte) random.nextInt()};
                    hashbytes = Sha256.hash(bytes);
                    if (isValidHashDifficulty(byte2Hex(hashbytes))) {
                        long end = System.currentTimeMillis();
                        time[i] = (end - starttime);
                        out.write(String.valueOf(time[i]));
                        out.write(" ");
                        System.out.println(time[i]);
                        continue a;
                    }
                }
            }
            long aver = 0;
            for (long l : time) {
                aver += l;
            }
            System.out.println("平均值 " + aver / 10);
            out.write("aver = "+ aver / 10 +"\r\n");
        }
        out.flush();
        out.close();
    }

    private void Randomxcal() throws IOException {
        Random random = new Random();
        byte[] bytes;
        File writename = new File("output1.txt");
        writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write("randomx \r\n");
        for(int k = 1;k<=4;k++){
            dificutty = k;
            out.write("dificutty = "+ k+"  ");
            long[] arr = new long[10];
            a:for (int i = 0; i < 10; i++) {
                RandomX.Builder build = new RandomX.Builder();
                build.fastInit(true);
                build.flag(RandomX.Flag.DEFAULT);
                long startTime = System.currentTimeMillis();
                RandomX randomX = build.build();
                while (true) {
                    bytes = new byte[]{(byte) random.nextInt(), (byte) random.nextInt(), (byte) random.nextInt(),
                            (byte) random.nextInt(), (byte) random.nextInt(), (byte) random.nextInt()};
                    randomX.init(bytes);
                    RandomX_VM vm = randomX.createVM();
                    byte[] hash = vm.getHash(bytes);
                    if (isValidHashDifficulty(byte2Hex(hash))) {
                        long end = System.currentTimeMillis();
                        arr[i] = (end - startTime);
                        System.out.println(arr[i]);
                        out.write(String.valueOf(arr[i]));
                        out.write(" ");
                        continue a;
                    }
                }
            }
            long aver = 0;
            for (long l : arr) {
                aver += l;
            }
            System.out.println("平均值 " + aver / 10);
            out.write("aver = "+ aver / 10 +"\r\n");
        }
        out.flush();
        out.close();
    }

    private static boolean isValidHashDifficulty (String hash){
        int i;
        for (i = 0; i < hash.length(); i++) {
            char ichar = hash.charAt(i);
            if (ichar != '0' && ichar != '1' && ichar != '2') {
                break;
            }
        }
        //判断i是否大于等于难度系数，返回即可
        return i >= dificutty;
    }
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
