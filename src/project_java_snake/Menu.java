/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_java_snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Admin
 */
public class Menu implements Commons {
    
    public Rectangle playButton = new Rectangle(B_WIDTH/2 - 20, 200, 50, 25);
    public Rectangle helpButton = new Rectangle(B_WIDTH/2 - 40, 250, 95, 25);
    public Rectangle quitButton = new Rectangle(B_WIDTH/2 - 20, 300, 50, 25);
    
    public void render(Graphics g)
    {        
        Graphics2D g2d = (Graphics2D)g;
        
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        
        g.drawString("SNAKE GAME", Commons.B_WIDTH/2 - 85, 160);
        
        Font fnt1 = new Font("arial", Font.BOLD, 20);
        g.setFont(fnt1);
        g.drawString("Play", playButton.x + 5, playButton.y + playButton.height - 5);
        g.drawString("About Us", helpButton.x + 5, helpButton.y + helpButton.height - 5);
        g.drawString("Quit", quitButton.x + 5, quitButton.y + quitButton.height - 5);
        
        g2d.draw(playButton);
        g2d.draw(helpButton);
        g2d.draw(quitButton);
        
    }
}
