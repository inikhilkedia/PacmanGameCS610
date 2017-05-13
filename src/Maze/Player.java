package Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Player extends Rectangle
{

	private static final long serialVersionUID = 1L;


	public boolean right,left,up,down;
	private int speed = 1;
	int PLAYER1 = 0;

	public Player(int x,int y)
	{
		setBounds(x,y,25,25);
	}

	public void tick()
	{
		if(right && canMove(x+speed,y))
			{
				x+=speed;
				System.out.println("Right Pressed");
			}
		if(left && canMove(x-speed,y))
			{
				x-=speed;
				System.out.println("Left Pressed");
			}
		if(up && canMove(x,y-speed))
			{
				y-=speed;
				System.out.println("Up Pressed");
			}
		if(down && canMove(x,y+speed))
			{
				y+=speed;
				System.out.println("Down Pressed");
			}
		
		Level level = Game.level;
		
		for (int i = 0; i < level.candy.size(); i++)
		{
			if(this.intersects(level.candy.get(i)))
			{
				level.candy.remove(i);
				PLAYER1 = PLAYER1 + 1;
				break;
			}
		}
		if(level.candy.size() == 0)
		{
			//game ends, we win!
			System.exit(1);
		}else
		{
			//System.out.println(PLAYER1);
		}
			
	}
	private boolean canMove(int nextx, int nexty)
	{
		Rectangle bounds = new Rectangle(nextx, nexty, width, height);
		Level level = Game.level;
		
		for(int xx = 0; xx < level.tiles.length; xx++)
		{
			for(int yy = 0; yy < level.tiles[0].length; yy++)
			{
				if(level.tiles[xx][yy] != null)
				{
					if(bounds.intersects(level.tiles[xx][yy]))
					{
						return false;
					}
				}
			}
		}
		
		return true;
		 
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
	}
}