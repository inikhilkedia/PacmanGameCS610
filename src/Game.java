import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable,KeyListener 
{

	private static final long serialVersionUID = 1L;

	private boolean isRunning = false;

	public static final int WIDTH = 640,HEIGHT = 580;
	public static final String TITLE = "Pac-Man";

	private Thread thread;
	public static Player player;
	public static Player2 player2;
	public static Level level;
	
	public static final int PAUSE_SCREEN = 0, GAME = 1; 
	public static int STATE = -1;
	public boolean isEnter = false;
	
	public Game()
	{
		Dimension dimension = new Dimension(Game.WIDTH,Game.HEIGHT);
		setPreferredSize(dimension);
		setMinimumSize(dimension); 
		setMaximumSize(dimension);

		addKeyListener(this);
		
		STATE = PAUSE_SCREEN; 
	}

	public synchronized void start()
	{
		if(isRunning) return;
		isRunning = true;
		thread= new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(isRunning) return;
		isRunning = false;
		try
		{
			thread.join();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

	private void tick()
	{
		if(STATE == GAME)
		{
			player.tick();
			player2.tick();
		}else if(STATE == PAUSE_SCREEN)
		{
			if(isEnter)
			{
				player2 = new Player2(Game.WIDTH/2,Game.HEIGHT/2);
				player = new Player(Game.WIDTH/2,Game.HEIGHT/2);
				level = new Level("/map/map.png"); 
				STATE = GAME;
			}
		}
	}
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
		if(STATE == GAME)
		{
			int ScorePx=640;
			int ScorePy=100;
			int Px = Game.WIDTH - ScorePx;
			int Py = Game.HEIGHT - ScorePy;
			g.setColor(new Color(0,0,0));
			g.fillRect(Px, Py, ScorePx, ScorePy);
			
			g.setColor(Color.white);
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,19));
			g.drawString("Player Green : " + player.PLAYER1, Px, Py+30);
			g.drawString("Player Blue : " + player2.PLAYER2, Px, Py+80);
			if(player.PLAYER1 > player2.PLAYER2)
			{
				g.drawString("Player GREEN is winning!!", Px+400, Py+55);
			}else if(player.PLAYER1 < player2.PLAYER2)
			{
				g.drawString("Player BLUE is winning!!", Px+400, Py+55);
			}else
			{
				g.drawString("Same Score", Px+400, Py+55);
			}
			
			
			player.render(g);
			player2.render(g);
			level.render(g);
		} else if(STATE == PAUSE_SCREEN)
		{
			int boxWidth=500;
			int boxHeight=200;
			int xx = Game.WIDTH/2 - boxWidth/2;
			int yy = Game.HEIGHT/2 - boxHeight/2;
			g.setColor(new Color(0,0,150));
			g.fillRect(xx, yy, boxWidth, boxHeight);
			
			g.setColor(Color.white);
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,19));
			g.drawString("Press enter to Start the game", xx + 110, yy + 100);
		}
		g.dispose();
		bs.show();
	}
	
	
	@Override
	public void run()
	{
		requestFocus();
		int fps =0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 78;
		double delta = 0;
		double ns = 1000000000/targetTick;

		while (isRunning)
		{
			long now = System.nanoTime();
			delta+=(now - lastTime)/ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				render();
				fps++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) 
			{
				//System.out.println(fps);
				fps = 0;
				timer+=1000;
			}

		}
	}

	
	public static void main(String[] args)
	{
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setTitle(Game.TITLE);
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);

		game.start();
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(STATE == GAME)
		{
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = true;
			if(e.getKeyCode() == KeyEvent.VK_D) player2.right = true;
			if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = true;
			if(e.getKeyCode() == KeyEvent.VK_A) player2.left = true;
			if(e.getKeyCode() == KeyEvent.VK_UP) player.up = true;
			if(e.getKeyCode() == KeyEvent.VK_W) player2.up = true;
			if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = true;
			if(e.getKeyCode() == KeyEvent.VK_S) player2.down = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			isEnter = true; 
		}	
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = false;
		if(e.getKeyCode() == KeyEvent.VK_D) player2.right = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = false;
		if(e.getKeyCode() == KeyEvent.VK_A) player2.left = false;
		if(e.getKeyCode() == KeyEvent.VK_UP) player.up = false;
		if(e.getKeyCode() == KeyEvent.VK_W) player2.up = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = false;
		if(e.getKeyCode() == KeyEvent.VK_S) player2.down = false;
	}
	
}