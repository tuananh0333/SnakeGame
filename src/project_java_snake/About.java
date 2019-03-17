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
import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public class About implements Commons {
    
    public Rectangle backButton = new Rectangle((B_WIDTH/4) * 3, 50, 90, 40);
    
    public void render(Graphics g)
    {
       Graphics2D g2d = (Graphics2D)g;
        
        Font fnt0 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        
        g.drawString("SNAKE GAME", Commons.B_WIDTH/2 - 85, 160);
        
        Font fnt1 = new Font("arial", Font.BOLD, 15);
        g.setFont(fnt1);
        g.drawString("A project of",10, 200);
        g.drawString("Lê Tuấn Anh",40, 220);
        g.drawString("17211TT0300",200, 220);
        g.drawString("Trần Bình Văn",40, 240);
        g.drawString("17211TT0053",200, 240);
        g.drawString("Lê Duy Anh Tú",40, 260);
        g.drawString("17211TT0065",200, 260);
        g.setFont(fnt0);
        g.drawString("How to play?", Commons.B_WIDTH/2 - 85, 310);
        g.setFont(fnt1);
        g.drawString("Control Your snake, eat the foods to grow bigger.",10, 340);
        g.drawString("You lose when the snake runs into the screen border or itself",10, 360);
        g.drawString("Use UP, LEFT, RIGHT, DOWN buttons to play",10, 390);
        g.drawString("Press ESC to return to main menu",10, 410);
        g.drawString("Press P to pause or resume game",10, 430);
        
        Font fnt2 = new Font("arial", Font.BOLD, 20);
        g.setFont(fnt2);
        g.drawString("<- Back", backButton.x + 7, backButton.y + backButton.height - 12);
        
        g2d.draw(backButton);
    }
}
