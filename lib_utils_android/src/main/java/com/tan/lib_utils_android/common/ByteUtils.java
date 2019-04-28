package com.tan.lib_utils_android.common;

public class ByteUtils {

    /**
     * 将byte数组转化为字符串
     *
     * @param src
     * @return
     */
    public static String byteToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }


    /**
     * 一般使用情况:android NFC
     * 把十六进制字符串翻转后，转换程十进制字符串
     * @param hex
     * @return
     */
    public static String hex2Decimal(String hex) {
        StringBuilder builder = new StringBuilder();
        if(hex.length() == 8) {
            for(int i = 0; i<4; i++) {
                String str = hex.substring(hex.length()-2 * (i+1), hex.length()-2*i);
                builder.append(str);
            }
        }
        String decimal = String.valueOf(Long.parseLong(builder.toString(), 16));
        while (decimal.length() < 10) {
            decimal = "0" + decimal;
        }

        return decimal;
    }

}
