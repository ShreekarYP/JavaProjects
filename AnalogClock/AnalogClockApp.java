import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class AnalogClockApp extends JFrame {

    public AnalogClockApp()
    {

        
        setTitle("Analog Clock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        setLocationRelativeTo(null);
        ClockPanel clock=new ClockPanel();
        add(clock);


        Timer timer= new Timer(1000, e->{

            clock.setCurrentTime();
            clock.repaint();
        });

        timer.start();
        clock.setCurrentTime();

    }

    private class ClockPanel extends JPanel
    {
       
       private int centerX;
       private int centerY;
       private int clockRadius;
       private Color backgroundColor;

       
       
       public ClockPanel()
        {
            setBackground(Color.lightGray);
        }

        public void setCurrentTime()
        {
            //repaint the color panel to update the clock handel
            repaint();
        }
        /*public void setClockBackgroundColor(Color color) {
            backgroundColor = color;
            repaint();// extra
        }*/

        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d=(Graphics2D) g;

            // calculate clock dimension and center
            clockRadius=Math.min(getWidth(),getHeight())/2-20;
            centerX=getWidth() /2;
            centerY=getHeight() /2;

            //Draw cock face (circle)
            g2d.setColor(new Color(60,60,100));
            g2d.fillOval(centerX - clockRadius ,centerY - clockRadius,2 * clockRadius, 2 * clockRadius);

            //draw hour numbers

            g2d.setFont(new Font( "italic",Font.BOLD,20));
            g2d.setColor(Color.WHITE);

            for(int hour=1;hour<=12;hour++)
            {
                double angle=Math.toRadians(90-(360/12) * hour);
                int x= (int)(centerX + clockRadius * 0.8 * Math.cos(angle));
                int y= (int)(centerY - clockRadius * 0.8 * Math.sin(angle));
                g2d.drawString(Integer.toString(hour), x-7,y+5);
            }

            //get current time

            SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm:ss");
            String currentTime = sdf.format(new Date());

            // Extract hours, minutes, seconds

            int hours = Integer.parseInt(currentTime.substring(0,2));
            int minutes = Integer.parseInt(currentTime.substring(3,5));
            int seconds = Integer.parseInt(currentTime.substring(6,8));

            // calculte rotattion angles for clock hands
            double hourAngle=Math.toRadians(90-(360/12) * hours);
            double minutesAngle=Math.toRadians(90-(360/60) * minutes);
            double secondsAngle=Math.toRadians(90-(360/60) * seconds);


            // draw clock hands
            //draw the hour hand in yellow

            drawClockHand(g2d, centerX, centerY, clockRadius * 0.5, hourAngle, 6, Color.black);

            //draw the minutes hand in yellow

            drawClockHand(g2d, centerX, centerY, clockRadius * 0.7, minutesAngle, 4, Color.black);

            //draw the seconds hand in red

            drawClockHand(g2d, centerX, centerY, clockRadius * 0.8, secondsAngle, 2, new Color(255,90,90));

        }

        private void drawClockHand(Graphics2D g2d, int x, int y, 
                              double length,double angle,int thickness,Color color)
        {

            g2d.setStroke(new BasicStroke(thickness));
            g2d.setColor(color);
            int x2=(int) (x + length * Math.cos(angle));
            int y2=(int) (y - length * Math.sin(angle));//this mist

            // Draw the clock hand from the center to the calculated position

            g2d.drawLine(x, y, x2, y2);

        }

    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(()->{
           
            AnalogClockApp app=new AnalogClockApp();
           app.setVisible(true);
           /*ClockPanel clockPanel = (ClockPanel) app.getContentPane().getComponent(0);
            clockPanel.setClockBackgroundColor(Color.DARK_GRAY);*/

        });
        
    }

    
}
