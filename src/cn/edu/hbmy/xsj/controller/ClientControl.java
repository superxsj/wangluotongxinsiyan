package cn.edu.hbmy.xsj.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientControl {

    private Socket socket;
    public int x1,y1,x2,y2,color;
    public byte type,strock;

    public Graphics2D g;

    public ClientControl(Graphics2D g,Socket socket) {
        this.g=g;
        this.socket=socket;
    }

    //不断接受服务器发送过来的信息
    public void receiveData(){
        new Thread(){
            public void run() {
                try {
                    while(true){
                        //获取输入输出流
                        InputStream ins = socket.getInputStream();
                        OutputStream ous=socket.getOutputStream();
                        //将字节流包装成数据流
                        DataInputStream dis=new DataInputStream(ins);
                        DataOutputStream dos= new DataOutputStream(ous);
//                      System.out.println("kehuduan kaishi dushuju。。。。。。");
                        //读图形类型
                        type=dis.readByte();

                        //读坐标
                        x1=dis.readInt();
                        y1=dis.readInt();
                        x2=dis.readInt();
                        y2=dis.readInt();
                        //读粗细
                        strock=dis.readByte();
                        //读颜色
                        color=dis.readInt();
                        //读完数据之后画图形
//                      System.out.print("客户端接受数据:");
//                      System.out.println("type:"+type+"x1:"+x1+"y1:"+y1+"x2:"+y2+"strock:"+strock+"Color:"+type);
                        drawGra();
                    }
                } catch (Exception e) {
//                  System.out.println("kehuudan tuichu。。。。。");
                    e.printStackTrace();
                }
            };
        }.start();
    }

    //发送图形数据给服务器
    public void sendData(int type,int x1,int y1,int x2,int y2,int strock){
        try {
            //获取输入输出流
            InputStream ins = socket.getInputStream();
            OutputStream ous=socket.getOutputStream();
            //将字节流包装成数据流
            DataInputStream dis=new DataInputStream(ins);
            DataOutputStream dos= new DataOutputStream(ous);
            //写图形类型
            dos.writeByte(type);
            //写坐标
            dos.writeInt(x1);
            dos.writeInt(y1);
            dos.writeInt(x2);
            dos.writeInt(y2);
            //写粗细
            dos.writeByte(strock);
            //写颜色
            dos.writeInt(g.getColor().getRGB());
            dos.flush();
//          System.out.println("type:"+type+"x1:"+x1+"y1:"+y1+"x2:"+y2+"strock:"+strock+"Color:"+type);
//          System.out.println("客户端已经发送数据......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //绘制图形
    public void drawGra() {
        g.setColor(new Color(color));
        g.setStroke(new BasicStroke(strock));
        if(type==1){
            g.drawLine(x1, y1, x2, y2);
//          System.out.println("hutu 。。。。。");
        }
    }

}

