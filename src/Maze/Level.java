package Maze;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Level 
{
	public int width;
	public int height;
	
	public Tile[][] tiles; 
	
	public List<Candy> candy;
	 
	public Level(String path)
	{
		candy = new ArrayList<>();
		try
		{
			BufferedImage map=ImageIO.read(getClass().getResource("/map/map.png"));
			this.width = map.getWidth();
			this.height = map.getHeight();
			int[] pixels = new int [width*height];
			tiles =new Tile[width][height];
			map.getRGB(0, 0, width, height, pixels, 0, width);
			
			for(int xx = 0; xx < width; xx++)
			{
				for (int yy =   0; yy < height; yy++)
				{
					int val = pixels[xx + (yy*width)];
					if(val == 0xFF000000)
					{
						//this is a tile (Black)
						tiles[xx][yy] = new Tile(xx*32,yy*32);		
					}else if(val == 0xFF0000FF )
					{
						//player
						Game.player.x = xx*32;
						Game.player.y = yy*32;
						
					}else
					{
						//dots in the pacman
						candy.add(new Candy((xx*32),(yy*32)));
					}
				}
			}
		}  
		catch(IOException e)  
		{
			e.printStackTrace();
		}
		
	}
	public void render(Graphics g)
	{
		for(int x = 0; x < width; x++)
		{
			for(int y  = 0; y < height; y++)
			{
				if(tiles[x][y] != null) tiles[x][y].render(g);
			}
		}
		for(int i = 0; i < candy.size(); i++)
		{
			candy.get(i).render(g);
		}
	}
}
