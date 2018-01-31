package MainProgram;

/*
 * SplashScreen.java
 *
 * Created on June 21, 2009, 4:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Tuong Huy
 */
import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JFrame {
    
    private int duration;
    
    public SplashScreen(int d) {
        duration = d;
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logo.png"));
        Image image = icon.getImage();
        setIconImage(image);
        setTitle("ChildCare Splash Screen");
    }
    
    // Set up contraints so that the user supplied component and the
    // background image label overlap and resize identically
    private static final GridBagConstraints gbc;
    static {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
    }
    /**
     * Wraps a Swing JComponent in a background image. Simply invokes the overloded
     * variant with Top/Leading alignment for background image.
     *
     * @param component - to wrap in the a background image
     * @param backgroundIcon - the background image (Icon)
     * @return the wrapping JPanel
     */
    public static JPanel wrapInBackgroundImage(JComponent component, Icon backgroundIcon) {
        return wrapInBackgroundImage(component, backgroundIcon, JLabel.TOP, JLabel.LEADING);
    }
    
    /**
     * Wraps a Swing JComponent in a background image. The vertical and horizontal
     * alignment of background image can be specified using the alignment
     * contants from JLabel.
     *
     * @param component - to wrap in the a background image
     * @param backgroundIcon - the background image (Icon)
     * @param verticalAlignment - vertical alignment. See contants in JLabel.
     * @param horizontalAlignment - horizontal alignment. See contants in JLabel.
     * @return the wrapping JPanel
     */
    public static JPanel wrapInBackgroundImage(JComponent component,Icon backgroundIcon, int verticalAlignment, int horizontalAlignment) {
        
        // make the passed in swing component transparent
        component.setOpaque(false);
        
        // create wrapper JPanel
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        
        // add the passed in swing component first to ensure that it is in front
        backgroundPanel.add(component, gbc);
        
        // create a label to paint the background image
        JLabel backgroundImage = new JLabel(backgroundIcon);
        
        // set minimum and preferred sizes so that the size of the image
        // does not affect the layout size
        backgroundImage.setPreferredSize(new Dimension(1,1));
        backgroundImage.setMinimumSize(new Dimension(1,1));
        
        // align the image as specified.
        backgroundImage.setVerticalAlignment(verticalAlignment);
        backgroundImage.setHorizontalAlignment(horizontalAlignment);
        
        // add the background label
        backgroundPanel.add(backgroundImage, gbc);
        
        // return the wrapper
        return backgroundPanel;
    }
    
    
    // A simple little method to show a title screen in the center
    // of the screen for the amount of time given in the constructor    
    public void showSplash() {
                                
        //JPanel content = (JPanel)getContentPane();
        //content.setBackground(Color.white);    
        
        // Set the window's bounds, centering the window
        int width = 400;
        int height =300;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x,y,width,height);       
        
        // Display it
        setVisible(true);
        // Wait a little while, maybe while loading resources
        try { Thread.sleep(duration); } catch (Exception e) {}
        
        setVisible(false);
    }
    
    public void showSplashAndExit() {
        showSplash();
        System.exit(0);
    }
    
    public static void main(String[] args) {
        
        // Throw a nice little title page up on the screen first            
        SplashScreen splash = new SplashScreen(5000);
        
        JPanel foregroundPanel = new JPanel(new BorderLayout(10, 10));
        foregroundPanel.setBackground(Color.white);
        
        Color oraRed = new Color(156, 20, 20,  255);
        foregroundPanel.setBorder(BorderFactory.createLineBorder(oraRed, 1));
        foregroundPanel.setOpaque(false);
        
        foregroundPanel.add(new JLabel(" Loading ... Please wait"), BorderLayout.SOUTH);
        
        
        splash.setContentPane(wrapInBackgroundImage(foregroundPanel, new ImageIcon(BackgroundImagePanelExample.class.getResource("bgMain.png"))));
        
        //hiddle title bar
        splash.setUndecorated(true);
        splash.showSplash();

        //Call to view login form
        new login.LoginUser(null, true).setVisible(true);
       
        splash.dispose();
    }
}