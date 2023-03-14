package com.gxl.code;

import java.io.*;
import java.net.URL;

/**
 * @Auther: GengXuelong
 * @Date: 2022/9/7
 * @ClassName: IamgeGetter_others
 * @Description:
 */
public class IamgeGetter_others {
    public static void work() throws IOException {
        URL url = new URL("https://th.bing.com/th/id/R.e28b07852faa583ef24f8a4b65c2f00d?rik=LXP7wmTjpX34XQ&riu=http%3a%2f%2fphotocdn.sohu.com%2f20111126%2fImg326982888.jpg&ehk=oyzfBTtMhyqo5x0PmSHXmJZJC8rvDJLpF1VrxUd8DSY%3d&risl=&pid=ImgRaw&r=0");
        InputStream inputStream = url.openStream();
        byte[] bytes = readForStream(inputStream);//一次读取不能获取完整的信息，地读好多次
        OutputStream outputStream = new DataOutputStream(new FileOutputStream("npu.png"));
        outputStream.write(bytes);
        outputStream.close();
        inputStream.close();
    }

    public static byte[] readForStream(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[20480];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        while((len = inputStream.read(bytes))!=-1){
            System.out.println(len);
            byteArrayOutputStream.write(bytes,0,len);
            System.out.println("读取了一次");
        }
        inputStream.close();
        return byteArrayOutputStream.toByteArray();
    }


    private static class DynamicArray{
        int max = 20240;
        int len = 0;
        private byte[] bytes = new byte[max];
        public void add(byte[] bs,int start,int num){
            if(start<0||num<1)return;
            if (len + num >= max) {
                max = (int) (1.5 * (len + num));
                byte[] bytes2 = new byte[max];
                System.arraycopy(bytes, 0, bytes2, 0, len);
                bytes = bytes2;
            }
            System.arraycopy( bs, start,bytes, len,   num );
            len = len + num;
        }
        public byte[] getBytes(){
            byte[] res = new byte[len];
            System.arraycopy(bytes,0,res,0,len);
            System.out.println("d len "+ res.length);
            return res;
        }
    }
    public static void main(String[] args) {
        try {
            work();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
