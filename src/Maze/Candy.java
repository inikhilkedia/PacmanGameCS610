package Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Candy extends Rectangle
{
	static final long serialVersionUID = 1L;

	public Candy(int x, int y)
	{
		setBounds(x+10,y+10,8,8);
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
	
}
