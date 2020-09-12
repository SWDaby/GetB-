import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

class ShowImage extends JFrame {

    URL url;
    public ShowImage(URL url) {
        // TODO Auto-generated constructor stub
        setSize(1024,600);
        setTitle(url.toString());
        setVisible(true);

        //this.filename=filename;
        this.url=url;
    }
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        Image img = Toolkit.getDefaultToolkit().getImage(url);
        //Image img=getToolkit().getImage(url);//获取Image对象加载图像
        //int h=img.getHeight(this);//获取图像高度
        //int w=img.getWidth(this);//获取图像宽度




        //g.drawImage(img, 0, 0, this);//原图

        g.drawImage(img,0,0,1024,600,this);//
        // g.drawImage(img, 280,80,w*2,h/3,this);
        //g.drawImage(img, 500,80,w/2,h*2,this);
    }
    public static void main(String[] args) {
//        URL url1;
//        try {
//            url1 = new URL("https://i1.hdslb.com/bfs/archive/d97ec763c166527de4e8523cfedcc06970ee240a.jpg");
//            new ShowImage(url1);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        String str = "https://i2.hdslb.com/bfs/archive/470ff8955ec832b27cead7a6359aca06d78d03d4.jpg";//https://i2.hdslb.com/bfs/archive/470ff8955ec832b27cead7a6359aca06d78d03d4.jpg
//        String result = str.substring(69);
//        System.out.println(result);
//

//        CreatUI creatUI=new CreatUI();
//        creatUI.ShowUI();
//        creatUI.getjTextArea().append("\njjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");





    }



}


class CreatUI extends JFrame implements Runnable{
    int width=600;
    int height=500;
    Color color= new Color(255,255,253);
    Dimension screensiz=Toolkit.getDefaultToolkit().getScreenSize();
    //JFrame jFrame=new JFrame("BiliBili"); //创建窗体
    JButton jButton=new JButton("解析");
    JTextField jTextField=new JTextField(10);
    JTextArea jTextArea=new JTextArea(10,40);
    JTextArea ta=new JTextArea(20,50);

    JLabel jLabel1=new JLabel("请输入up主页号：");
    JLabel jLabel2=new JLabel("Detail：");
    Container container=getContentPane();

    Font font=new Font("微软雅黑",Font.BOLD,14);
    //创建容器以放置其他控件
    Image myimage=Toolkit.getDefaultToolkit().getImage("src/main/resources/icon.jpg");
    public void ShowUI(){

        //创建文本区域组件
        setIconImage(myimage);
        container.setLayout(null);

        jLabel1.setFont(font);
        jLabel2.setFont(font);
        jButton.setFont(font);
        jButton.setBackground(color);


        //创建JScrollPane()面板对象,并将文本域对象添加到面板中
        JScrollPane sp=new JScrollPane(ta);
        sp.setBounds(10,100,570,350);

        ta.setEditable(false);


        jLabel1.setBounds(11,10,150,30);
        jTextField.setBounds(10,40,450,20);
        jButton.setBounds(480,35,100,30);
        jLabel2.setBounds(11,70,80,30);








        //将该面板添加到该容器中
        container.add(jLabel1);
        container.add(jButton);
        container.add(jLabel2);
        container.add(jTextField);
        //container.add(ta);
        container.add(sp);
        //container.add(jButton);

       // container.add(jTextArea);

        setBounds((screensiz.width-width)/2, (screensiz.height-height)/2, width, height);
        setTitle("BiliBili  -Daby ver1.0");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(color);
        setVisible(true);





        //

       // container.add(jTextArea);
}

    public JTextArea getjTextArea() {
        return ta;
    }

    public JButton getjButton() {
        return jButton;
    }

    public JTextField getjTextField() {
        return jTextField;
    }

    public void run() {

    }
}