package io.virgo.randomX;


import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        RandomX.Builder build = new RandomX.Builder();
        build.fastInit(true);
        build.flag(RandomX.Flag.DEFAULT);
        long startTime = System.currentTimeMillis();
        RandomX randomX = build.build();
        Random random = new Random();
        random.nextInt();
        byte[] bytes = new byte[]{(byte) random.nextInt(), (byte) random.nextInt(),(byte) random.nextInt(),
                (byte) random.nextInt(),(byte) random.nextInt(), (byte) random.nextInt()};
        randomX.init(bytes);
        RandomX_VM vm = randomX.createVM();
        byte[] hash = vm.getHash(bytes);
        while(true){
            if(isValidHashDifficulty(byte2Hex(hash))){
                System.out.println(count);
                long end = System.currentTimeMillis();
                System.out.println((end-startTime)+" ms");
                return;
            }
            count++;
            nextbyte(bytes);
            hash = vm.getHash(bytes);
        }
    }
    //00000048bfdc5e67aa448686438f1350a6cc7f4477feb5562b0368a808fdef57
    private static boolean isValidHashDifficulty(String hash) {
        //定义难度系数
        int dificutty = 1;
        //定义标志符0(当然也可以定义其他，一般是0)
        char zero = '0';
        int i;
        for (i = 0; i < hash.length(); i++) {
            //获得hash字符串的i位置的字符
            char ichar = hash.charAt(i);
            //如果i处的值不为0则跳出
            if (ichar != zero) {
                break;
            }
        }
        //判断i是否大于等于难度系数，返回即可
        return i >= dificutty;
    }
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
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

    private static boolean nextbyte(byte[]bytes){
        int len = bytes.length;
        for(int i=0;i<len;i++){
            if(bytes[i] + 128 != 255){
                bytes[i]++;
                return true;
            }
        }
        return false;
    }
}
