package view;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import cn.edu.hbmy.xsj.controller.ClientControl;
public class DrawUI extends JFrame{

    public Socket socket;
    public Graphics2D g;
    public ClientControl control;

    //界面初始化的socket
    public DrawUI() {
        try {
            socket = new Socket("localhost",9090);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initFrame() {
        //设置窗体属性
        this.setTitle("网络画板");
        this.setSize(600, 500);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //拿到窗体的画笔
        g = (Graphics2D)this.getGraphics();
        g.setColor(Color.RED);
        //控制器初始化
        control = new ClientControl(g,socket);
        //添加鼠标监听
        DrawListener listener = new DrawListener(control);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
        //控制器接收数据并处理数据
        control.receiveData();
    }

}