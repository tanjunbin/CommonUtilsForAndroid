package com.tan.lib_utils_android.common;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {

    /**
     * 判断文件是否存在
     *
     * @param strFile 文件路劲
     * @return
     */
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
            return size;
        } catch (Exception e) {

        }
        return 0;
    }


    /**
     * 读取文件，以字符串形式显示
     *
     * @param file
     * @return
     */
    public static String readFile2String(File file) {
        if (!file.exists()) {
            return null;
        }
        FileInputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                byteArrayOutputStream.write(buffer, 0, len); // 把读取的内容写入到输出流中
            }
            String source = byteArrayOutputStream.toString();
            if (!source.equals("")) {
                source = source.replaceAll("\ufeff", "");
            }
            return source;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在assets里读取文件
     *
     * @param fileName 要读取的文件名
     * @return
     */
    public static String getAssetsJson(Application application, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = application.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 在assets里读取zip文件
     *
     * @param zipName       要读取的zip文件名
     * @param readFileNames 要读取的json文件名
     * @return
     */
    public static ArrayList<String> readZipFile(Application application, String zipName, String... readFileNames) {
        ArrayList<String> jsonList = new ArrayList<>();
        int count = 0;
        try {
            InputStream open = application.getAssets().open(zipName);
            ZipInputStream zipinputstream = new ZipInputStream(open);
            ZipEntry nextEntry;
            while ((nextEntry = zipinputstream.getNextEntry()) != null) {
                if (nextEntry.isDirectory()) {
                    //Do nothing
                } else {
                    for (String readFileName : readFileNames) {
                        if (nextEntry.getName().equals(readFileName + ".json")) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] buffer = new byte[2048];
                            int len = 0;
                            while ((len = zipinputstream.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                                byteArrayOutputStream.write(buffer, 0, len); // 把读取的内容写入到输出流中
                            }
                            String source = byteArrayOutputStream.toString();
                            if (!source.equals("")) {
                                source = source.replaceAll("\ufeff", "");
                            }
                            jsonList.add(source);
                            count++;
                            byteArrayOutputStream.close();
                            break;
                        }
                    }
                    if (count == readFileNames.length) {
                        break;
                    }
                }
            }
            zipinputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonList;
    }

    /**
     * 在sd卡里读取zip文件
     *
     * @param path          要读取的zip路径
     * @param readFileNames 要读取的json文件名
     * @return
     */
    public static ArrayList<String> readSdCardZipFile(String path, String... readFileNames) {
        ArrayList<String> jsonList = new ArrayList<>();
        int count = 0;
        try {
            InputStream open = new FileInputStream(new File(path));
            ZipInputStream zipinputstream = new ZipInputStream(open);
            ZipEntry nextEntry;
            while ((nextEntry = zipinputstream.getNextEntry()) != null) {
                if (nextEntry.isDirectory()) {
                    //Do nothing
                } else {
                    for (String readFileName : readFileNames) {
                        if (nextEntry.getName().equals(readFileName + ".json")) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] buffer = new byte[2048];
                            int len = 0;
                            while ((len = zipinputstream.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                                byteArrayOutputStream.write(buffer, 0, len); // 把读取的内容写入到输出流中
                            }
                            String source = byteArrayOutputStream.toString();
                            if (!source.equals("")) {
                                source = source.replaceAll("\ufeff", "");
                            }
                            jsonList.add(source);
                            count++;
                            byteArrayOutputStream.close();
                            break;
                        }
                    }
                    if (count == readFileNames.length) {
                        break;
                    }
                }
            }
            zipinputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonList;
    }

    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * decoderBase64File:(将base64字符解码保存文件). <br/>
     *
     * @param base64Code 编码后的字串
     * @param savePath   文件保存路径
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }


    /**
     * 读取sdcard文件
     *
     * @param fileName
     * @return
     */
    @SuppressWarnings("resource")
    public String getInputStream(String fileName) {
        // 从文件名中获得inputstream
        // 注意FileInputStream是从相应连路径的文件中读数据 而不是写 注意注意
        FileInputStream fileInputStream = null;// 获得一个文件输入流对象

        // ByteArrayOutputStream是缓存流 与磁盘无关 不需要关闭
        ByteArrayOutputStream OutputStream = new ByteArrayOutputStream();
        // 获得sdcard文件跟目录路径
        File file = new File(Environment.getExternalStorageDirectory(), fileName);// 创建一个新的文件

        // 是否sdcard卡开启
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            try {
                fileInputStream = new FileInputStream(file);// 找到相应文件的路径的输入流
                int len = 0;
                byte[] data = new byte[1024];

                // 读取数据到OutputStream中
                while ((len = fileInputStream.read(data)) != -1) {
                    OutputStream.write(data, 0, len);
                }

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (fileInputStream != null) {
                    fileInputStream = null;
                }
            }
        }
        return new String(OutputStream.toByteArray());
    }

    /**
     * 写入文件到sdcard中
     *
     * @param Filename 文件名
     * @param content  文件内容
     * @return
     */
    public boolean saveContentToSDCard(String Filename, String content) {
        boolean flag = false;

        // 注意FileOutputStream是打开特定的文件 在其中<<写>>东西 人不是 顾名思义第去读 也就是参照是软件的内存
        FileOutputStream outputStream = null;// 获得一个文件输出流
        // 获得sdcard的根目录
        // 注意这个file不是文件 而是 一个路径
        File file = new File(Environment.getExternalStorageDirectory(), Filename);
        // 判断sdcard是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            try {
                outputStream = new FileOutputStream(file);
                // 把指定的内容写入相应的文件中
                outputStream.write(content.getBytes());// 从edittext中获得我输入的字符
                // 并且放入outputStream中
                flag = true;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                // 注意要关闭
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 调用方法:
     * 取---
     *   FileUtils fileService = new FileUtils(ResetDomainNameActivity.this);
     *   String msg = fileService.getInputStream("路劲Environment.getExternalStorageDirectory()"+"/文本名.txt");//也可以直接不要路径
     *
     * 存---
     *   //在文件管理器中新建fostha.txt文件
     *    FileUtils fileService = new FileUtils(content);
     *    if (!FileUtil.fileIsExists(Environment.getExternalStorageDirectory() + "/" + "文本名.txt")) {
     *         boolean isSuccess =  fileService.saveContentToSDCard("文本名.txt", ObjectUtils.toString("需要保存的文本"));
     *         if(isSuccess){
     *              ToastUtils.showCenter(ResetDomainNameActivity.this,"保存成功");
     *          }
     *     }
     */
}
