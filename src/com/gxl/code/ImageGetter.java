import java.io.*;
import java.net.URL;

/**
 * @Auther: GengXuelong
 * @Date: 2022/9/7
 * @ClassName: ImageGetter
 * @Description:
 *  利用图片的url从网路中将一张图片保存到本地，位置为当前类的同级文件夹
 */
public class ImageGetter {

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description: 通过图片的url将图片保留到本地，默认位置为本类所在的同级文件夹，命名为npu.png
     */
    public static void getTheImage() throws IOException {
        //先准准备好一个URL类
        URL url = new URL("https://th.bing.com/th/id/R.e28b07852faa583ef24f8a4b65c2f00d?rik=LXP7wmTjpX34XQ&riu=http%3a%2f%2fphotocdn.sohu.com%2f20111126%2fImg326982888.jpg&ehk=oyzfBTtMhyqo5x0PmSHXmJZJC8rvDJLpF1VrxUd8DSY%3d&risl=&pid=ImgRaw&r=0");
        //从url中获取输入流用于得到图片数据。
        InputStream inputStream = url.openStream();
        /*
        因为通过实践得知由于网路传输数据大小的限制，一次读取所得的数据并不是完整的土图片数据，
        所以需要多词读取，自定义的这个方法实现多词读取的功能,返回拼接好的字节数组，并关闭该流
         */
        byte[] bytes = readFromStream(inputStream);
        //新建一个输出流
        OutputStream outputStream = new FileOutputStream("npu.png");
        //将从网路上获取的字节数据写入指定的文件
        outputStream.write(bytes);
        //关闭流
        outputStream.close();
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description: 对指定的流进行多词读取，并将多次夺取的数据按顺序合在同一个字节数组中
     */
    public static byte[] readFromStream(InputStream inputStream) throws IOException {
        //定义用于承载每次所读取数据的内容
        byte[] bytes = new byte[20480];
        //自定义的动态数组，用于拼接每次读出的字节数据
        DynamicArray dynamicArray = new DynamicArray();
        int len = 0;
        //读取开始
        while ((len = inputStream.read(bytes)) != -1) {
            System.out.println("本次读取的字节数: " + len);
            dynamicArray.add(bytes, 0, len);
        }
        //关闭流
        inputStream.close();
        //返回拼接好的字节数组
        return dynamicArray.getBytes();
    }

    /**
     * @Auther: GengXuelong
     * <p> 类 功能描述如下:
     * @Description: 自定义动态字节数组，用于拼接字节数组
     */
    private static class DynamicArray {
        //默认动态字节数据的大小为20480
        int max = 20480;
        //初始的动态数组长度为0
        int len = 0;
        //数据具体的存储位置
        private byte[] bytes = new byte[max];

        /**
         * @Auther: GengXuelong
         * <p> 函数功能描述如下:
         * @Description: 实现向动态字节数组拼接其他字节数组的功能
         */
        public void add(byte[] bs, int start, int num) {
            if (start < 0 || num < 1) return;
            if (len + num >= max) {
                //每次超过最大长度，就按以下规则扩容，以（1.5*真实需要空间）为扩容后的长度，尽可能中和空间使用和运行效率的问题
                max = (int) (1.5 * (len + num));
                byte[] bytes2 = new byte[max];
                System.arraycopy(bytes, 0, bytes2, 0, len);
                bytes = bytes2;
            }
            System.arraycopy(bs, start, bytes, len, num);
            len = len + num;
        }

        /**
         * @Auther: GengXuelong
         * <p> 函数功能描述如下:
         * @Description: 根据动态数据的具体长度，返回相应长度的静态字节数组
         */
        public byte[] getBytes() {
            byte[] res = new byte[len];
            System.arraycopy(bytes, 0, res, 0, len);
            System.out.println("所得动态数组的有效长度为: " + res.length);
            return res;
        }
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description: main函数
     */
    public static void main(String[] args) {
        try {
            getTheImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
