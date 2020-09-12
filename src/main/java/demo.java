import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import org.apache.commons.logging.LogFactory;


import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class demo {
    public static void main(String args[]) {

        final CreatUI UI=new CreatUI();

        UI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UI.setResizable(false);
        UI.ShowUI();

        final JButton jButton=UI.getjButton();
        final  JTextArea jTextArea=UI.getjTextArea();
        final  JTextField jTextField=UI.getjTextField();

        UI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        jTextField.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();
                if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){

                }else{
                    e.consume(); //屏蔽掉非法输入
                }
            }
        });


        final ExecutorService service = Executors.newCachedThreadPool(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r, "output");
            }
        });

        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                service.submit(new Runnable() {
                    public void run() {



                        jTextArea.setText("");
                        jTextArea.append("正在解析...(请稍等)\n");
                        jTextArea.append("将在桌面生成文件\n");
                        jButton.setEnabled(false);
                        start(jTextArea,jTextField.getText());
                       // System.out.println("~~~~BiliBlili干杯~~~~");
                        jTextArea.append("\n~~~~BiliBlili干杯~~~~\n");
                        jTextArea.setCaretPosition(jTextArea.getText().length());
                        jButton.setEnabled(true);
                    }
                });






            }


        });





    }


    public static HtmlPage make_page(WebClient webClient, String url, long time, boolean blean,JTextArea jmake_page) {


        HtmlPage page = null;
        int i = 0;
        try {
            page = webClient.getPage(url);//加载网页
            webClient.waitForBackgroundJavaScript(time);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        } catch (Exception e) {
            e.printStackTrace();
        }

        //从页面获取特定元素
        List<DomElement> aList = page.getByXPath("//ul[@class='clearfix cube-list']//a[@class='cover']");


        if (blean) {
            //输出从页面获取的特定元素
            for (DomElement dom : aList) {

                i = i + 1;

               // System.out.println(dom.asXml());
                //System.out.println("*********************************************************");

                //窗体输出
                jmake_page.append(dom.asXml()+"\n");
                jmake_page.append("*********************************************************\n");
            }

            //System.out.println("===============一共有" + i + "个视频==================");
            jmake_page.append("===============一共有" + i + "个视频==================\n");
        }
        return page;
    }

    public static int Test(HtmlPage page,String ID,JTextArea jTest) {

        //初始化
        int i_Img = 0, i_Video = 0;
        String regxUrl = "w[a-z0-9\\./]*BV[A-Za-z0-9]{10}";//w[a-z\\./]
        //String regxPN="[1-9]\\d* ";
        String regxImg = "//i[A-Za-z0-9-\\./]*";
        Pattern pURL = Pattern.compile(regxUrl);
        //Pattern pPN=Pattern.compile(regxPN);
        Pattern pIMG = Pattern.compile(regxImg);
        Matcher mURL, mIMG;

        URL url_IMG;
        // int pageNb;
        //String str;


        try {


            //从页面获取特定元素
            List<DomElement> aList = page.getByXPath("//ul[@class='clearfix cube-list']//a[@class='cover']");
            //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+aList.toString());


//            //输出从页面获取的特定元素
//            for(DomElement dom:aList) {
//
//                i=i+1;
//
//                System.out.println(dom.asXml());
//                System.out.println("*********************************************************");
//            }
//
//            System.out.println("===============一共有"+ i +"个视频==================");


            //根据正则匹配并输出视频链接
            mURL = pURL.matcher(aList.toString());  //注意：直接把aList toString会改变List原本的内容
            while (mURL.find()) {
                i_Video = i_Video + 1;

                //System.out.println("!!!!!!!!!!!!!我是视频链接!!!!!!!!!!!!!!!!!!!" + mURL.group());
                jTest.append("!!!!!!!!!!!!!我是视频链接!!!!!!!!!!!!!!!!!!!" + mURL.group()+"\n");
                jTest.setCaretPosition(jTest.getText().length());

            }
            //System.out.println("查找到" + i_Video + "条链接");
            jTest.append("查找到" + i_Video + "条链接\n");

            //System.out.println("========================================================================================");
            jTest.append("================================================================\n");
            //查找封面链接
            for (DomElement dom2 : aList) {
                mIMG = pIMG.matcher(dom2.asXml());
                mIMG.find();
                i_Img = i_Img + 1;
                //url_IMG = new URL("https:" + mIMG.group());
                //new ShowImage(url_IMG);
                creatFile("https:" + mIMG.group(),ID);
                //System.out.println("https:" + mIMG.group() + "!!!!!!!!!!!!!我是封面链接!!!!!!!!!!!!!!!!!!!");

                jTest.append("https:" + mIMG.group() + "\n");
                jTest.setCaretPosition(jTest.getText().length());

            }

           //System.out.println("找到" + i_Img + "个封面");
            jTest.append("找到" + i_Img + "个封面\n");
            jTest.setCaretPosition(jTest.getText().length());

            page.cleanUp();

        } catch (Exception e) {

        }


//        DomNodeList<DomElement> aList=page.getElementsByTagName("a");
//        for(DomElement dom:aList) {
//            System.out.println(dom.asXml());
//        }


        //System.out.println(div.toString());


        //String pageXml = page.asXml();

        // System.out.println(pageXml);
        return i_Video;
    }

    private static int get_PageNumber(HtmlPage page,JTextArea jPageNumber) {

        //获取页数
        String s = null;
        String regxPN = "[1-9]\\d*";
        int pageNb = 0;
        Pattern pPN = Pattern.compile(regxPN);
        Matcher mPN;
        List<DomElement> tList = page.getByXPath("//ul[@class='be-pager']//span[@class='be-pager-total']");


        for (DomElement dome : tList) {

            if (s == null) s = dome.asText();
            //System.out.println("===============" + dome.asText() + "视频==================");

            //jPageNumber.append("===============" + dome.asText() + "视频==================\n");
        }


        mPN = pPN.matcher(s);
        while (mPN.find()) {
            s = mPN.group().replace(" ", "");
            pageNb = Integer.parseInt(s);
           // System.out.println("视频页数：" + pageNb);

            jPageNumber.append("视频页数：" + pageNb+"\n");

        }

        return pageNb;
    }
    private static String get_UPID(HtmlPage page,JTextArea jupid) {

        //获取up用户名
        String s = null;

        List<DomElement> tList = page.getByXPath("//div[@class='h-basic']//span[@id='h-name']");


        for (DomElement dome : tList) {

            if (s == null) s = dome.asText();
            //System.out.println("=============== " + dome.asText() + " 的主页==================");
            jupid.append("=============== " + dome.asText() + " 的主页==================\n");
        }




       return s;
    }

    private static void creatFile(String ImgUrl,String dir) throws Exception {

        //new一个URL对象
        URL url = new URL(ImgUrl);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\"+dir+"\\" + ImgUrl.substring(68));
        //获取父目录
        File fileParent = imageFile.getParentFile();
        //判断是否存在
        if (!fileParent.exists()) {
            //创建父目录文件
            fileParent.mkdirs();
        }
        imageFile.createNewFile();
        //创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据
        outStream.write(data);
        //关闭输出流
        outStream.close();

    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static void start (JTextArea ja,String number){

        //String number = "16539048";//宝剑嫂113362335  欣小萌8366990  小潮院长5970160   徐珺大哥34579852  单页13437296 仙若16539048 咬人猫116683
        //4位重复7375428
        String string = "https://space.bilibili.com/" + number + "/video?tid=0&page=0&keyword=&order=pubdate";
        String url;


        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        //初始化浏览器
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

        //添加请起头
        webClient.addRequestHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36 Edg/83.0.478.44");

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(true);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX


        HtmlPage page1 = make_page(webClient, string, 50, false,ja);

//        System.out.println("                     //\n" +
//                "         \\\\         //\n" +
//                "          \\\\       //\n" +
//                "    ##DDDDDDDDDDDDDDDDDDDDDD##\n" +
//                "    ## DDDDDDDDDDDDDDDDDDDD ##   ________   ___   ___        ___   ________   ___   ___        ___\n" +
//                "    ## hh                hh ##   |\\   __  \\ |\\  \\ |\\  \\      |\\  \\ |\\   __  \\ |\\  \\ |\\  \\      |\\  \\\n" +
//                "    ## hh    //    \\\\    hh ##   \\ \\  \\|\\ /_\\ \\  \\\\ \\  \\     \\ \\  \\\\ \\  \\|\\ /_\\ \\  \\\\ \\  \\     \\ \\  \\\n" +
//                "    ## hh   //      \\\\   hh ##    \\ \\   __  \\\\ \\  \\\\ \\  \\     \\ \\  \\\\ \\   __  \\\\ \\  \\\\ \\  \\     \\ \\  \\\n" +
//                "    ## hh                hh ##     \\ \\  \\|\\  \\\\ \\  \\\\ \\  \\____ \\ \\  \\\\ \\  \\|\\  \\\\ \\  \\\\ \\  \\____ \\ \\  \\\n" +
//                "    ## hh      wwww      hh ##      \\ \\_______\\\\ \\__\\\\ \\_______\\\\ \\__\\\\ \\_______\\\\ \\__\\\\ \\_______\\\\ \\__\\\n" +
//                "    ## hh                hh ##       \\|_______| \\|__| \\|_______| \\|__| \\|_______| \\|__| \\|_______| \\|__|\n" +
//                "    ## MMMMMMMMMMMMMMMMMMMM ##\n" +
//                "    ##MMMMMMMMMMMMMMMMMMMMMM##                                                             \n" +
//                "         \\/            \\/ ");
        //获取视频页数


        String sID=get_UPID(page1,ja);
        int i = get_PageNumber(page1,ja);
        int total = 0;
        int j;
        if (i != 0) {

            for (j = 1; j <= i; j++) {

                url = "https://space.bilibili.com/" + number + "/video?tid=0&page=" + j + "&keyword=&order=pubdate";
                page1 = make_page(webClient, url, 500, false,ja);
                //String url2="https://space.bilibili.com/"+number+"/video?tid=0page="+j+"&keyword=&order=pubdate";
                //System.out.println("========================================================================================");
                ja.append("================================================\n");

                //System.out.println(url);
                ja.append(url+"\n");
                total = total + Test(page1,sID,ja);
                //System.out.println("已找到目标个数："+total);
                ja.append(""+total+"\n");


                //System.out.println("我是第" + j + "页");
                ja.append("我是第" + j + "页\n");


                //System.out.println("`````````````````````````````````````````````````````````````````````````````````````````````````");
                ja.append("```````````````````````````````````````````````````````````\n");
                ja.setCaretPosition(ja.getText().length());

               // page1.cleanUp();
            }

        } else {
            url = "https://space.bilibili.com/" + number + "/video?tid=0&page=0&keyword=&order=pubdate";
            page1 = make_page(webClient, url, 500, false,ja);
            //System.out.println("视频只有一页");
            ja.append("视频只有一页\n");
            Test(page1,sID,ja);

        }
        webClient.close();

    }
}



