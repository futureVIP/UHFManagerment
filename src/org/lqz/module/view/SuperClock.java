package org.lqz.module.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.Calendar;
import javax.swing.*;
public class SuperClock
{
    public static void main(String []args)
    {
        new SCframe();
    }
}
class SCframe extends JFrame
{
    SCpanel panel=new SCpanel();
    public SCframe()
    {
        final Point origin=new Point(0,0);
        int w=440;
        int h=440;
        setUndecorated(true);
        //setDefaultLookAndFeelDecorated(true);
        com.sun.awt.AWTUtilities.setWindowShape(this, new Ellipse2D.Double(0, 0,w,h));
        com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.75f);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension D=kit.getScreenSize();
        setBounds((D.width-w)/2,(D.height-h)/2,w,h);
        panel.setBackground(Color.black);
        add(panel);
        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                origin.x=e.getX();
                origin.y=e.getY();
            }
        }) ;

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Point p=getLocation();
                //窗口当前位置+鼠标当前位置-鼠标按下位置
                setLocation(p.x+e.getX()-origin.x,p.y+e.getY()-origin.y);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    System.exit(0);
                }
            }
        });
        while(true)
        {
            panel.repaint();
        }
    }

}
class SCpanel extends JPanel
{
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        Calendar curtime =Calendar.getInstance() ;

        //绘制表盘
        g2.translate(220,220);
        int i=0;
        double E=0.0;
        double R=200;
        double []x1=new double[31];
        double []y1=new double[31];
        double []_x1=new double[31];
        double [] _y1=new double[31];
        double []x2=new double[31];
        double []_x2=new double[31];
        double []y2=new double[31];
        double [] _y2=new double[31];
        final double PI=3.1416;
        g2.setPaint(Color.white);                        //设置颜色
        g2.setStroke(new BasicStroke(3));           //设置线条宽度
        E=0.0;
        for(i=0;i<31;i++)
        {
            if(i%5==0)                                          //设置红色
            {
                g2.setPaint(Color.red);
            }
            if(i%15==0)
            {
                x1[i]=(R-12.0)*Math.cos(E*PI/180.0);
                y1[i]=(R-12.0)*Math.sin(E*PI/180.0);
            }
            else
            {
                x1[i]=(R-5.0)*Math.cos(E*PI/180.0);
                y1[i]=(R-5.0)*Math.sin(E*PI/180.0);
            }
            _x1[i]=-x1[i];
            _y1[i]=-y1[i];
            x2[i]=(R+5.0)*Math.cos(E*PI/180.0);
            y2[i]=(R+5.0)*Math.sin(E*PI/180.0);
            _x2[i]=-x2[i];
            _y2[i]=-y2[i];
            g2.drawLine((int)x1[i],(int)y1[i],(int)x2[i],(int)y2[i]);
            g2.drawLine((int)_x1[i],(int)_y1[i],(int)_x2[i],(int)_y2[i]);
            E+=6.0;
            if(i%5==0)
            {
                g2.setPaint(Color.white);
            }
        }

        //绘制指针
        int hour=curtime.get(Calendar.HOUR_OF_DAY);
        int minute=curtime.get(Calendar.MINUTE);
        int second=curtime.get(Calendar.SECOND);

        g2.setPaint(Color.RED);                        //设置颜色
        g2.setStroke(new BasicStroke(1));           //设置线条宽度
        g2.drawLine(0,0,(int)((R-15.0)*Math.cos( ((double) second/60.0-0.25)*PI*2.0) ),(int)((R-15.0)*Math.sin( ((double) second/60.0-0.25)*PI*2.0) ));
        g2.setPaint(Color.BLUE);
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(0,0,(int)((R-25.0)*Math.cos( ((double) minute/60.0-0.25)*PI*2.0) ),(int)((R-25.0)*Math.sin( ((double) minute/60.0-0.25)*PI*2.0) ));
        g2.setPaint(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(7));
        g2.drawLine(0,0,(int)((R-70.0)*Math.cos( ((double) hour/12.0-0.25)*PI*2.0) ),(int)((R-70.0)*Math.sin( ((double) hour/12.0-0.25)*PI*2.0) ));
        g2.setPaint(Color.white);
        g2.setStroke(new BasicStroke(1));
        g2.fillArc(-7,-7,14,14,0,360);
    }
}
