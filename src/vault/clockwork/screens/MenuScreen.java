/*
 * The MIT License
 *
 * Copyright 2015 Konrad Nowakowski https://github.com/konrad92.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package vault.clockwork.screens;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Gdx.gl;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vault.clockwork.Game;
import vault.clockwork.Vault;
import vault.clockwork.actors.BackgroundActor;
import vault.clockwork.actors.ButtonActionListener;
import vault.clockwork.actors.ButtonActor;
import vault.clockwork.actors.GameLogoActor;
import vault.clockwork.actors.GridBackgroundActor;
import vault.clockwork.scene.Actor;


/**
 * 
 * @author Agnieszka Makowska https://github.com/Migemiley
 */
public class MenuScreen implements GameScreen {
	/**
	 * @see GameScreen#prepare() 
	 */
	@Override
	public void prepare() {
		// preload actor resources
		Game.assets.load("assets/button.png", Texture.class);
		Game.assets.load("assets/blueprint.png", Texture.class);
		Game.assets.load("assets/dustbin-rush.png", Texture.class);
		
		// buttony
		Game.assets.load("assets/menu-start.png", Texture.class);
		Game.assets.load("assets/menu-opcje.png", Texture.class);
		Game.assets.load("assets/menu-wyjscie.png", Texture.class);
		Game.assets.load("assets/menu-wroc.png", Texture.class);
		
		// poziomy
		Game.assets.load("assets/levels/a.png", Texture.class);
	}
	
	private int showNext = 0;

	/**
	 * Ctor.
	 */
	public MenuScreen() {
	}
	
	/**
	 * @see GameScreen#show() 
	 */
	@Override
	public void show() {
		//Game.scene.BACKGROUND.add(new GridBackgroundActor(1));
		Game.scene.BACKGROUND.add(new BackgroundActor(-1, "assets/blueprint.png"));
		Actor logo = Game.scene.FOREGROUND.add(new GameLogoActor());
		logo.setPosition(new Vector2(100.f, 100.f));
		logo.setRotation(-16.f);
		Game.mainCamera = new OrthographicCamera();
		
		showMainMenu();
	}

	/**
	 * @see GameScreen#render(float) 
	 * @param delta 
	 */
	@Override
	public void render(float delta) {
        // clear target buffer
        gl.glClearColor(0.1f, 0.2f, 0.1f, 1.f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		// update shaders
		if(Vault.comicShader.isCompiled()) {
			Vault.comicShader.begin();
			Vault.comicShader.setUniformf("u_ticks", (float)(Math.random()*2*Math.PI));
			Vault.comicShader.setUniformf("u_strength",(float)(Math.random()*0.0015f));
			Vault.comicShader.end();
		}
		
		Game.mainCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Game.mainCamera.translate(-Gdx.graphics.getWidth()/2, -Gdx.graphics.getHeight()/2);
			
		Game.mainCamera.update();
		Game.scene.batch.setProjectionMatrix(Game.mainCamera.combined);
		
		if(showNext > 0) {
			if(showNext == 1) {
				showMainMenu();
			}
			else if(showNext == 2) {
				showStages();
			}
			showNext = 0;
		}
		
		// perform game systems
		Game.performSystems();
	}
	
	public void showMainMenu() {
		// wyczysc warstwe GUI
		Game.scene.GUI.dispose();
		
		// stworz obiekty menu na nowo
		Game.scene.GUI.add(new ButtonActor(1, Game.assets.get("assets/menu-start.png", Texture.class), -200, 30, new ButtonActionListener() {
			@Override
			public void clicked(ButtonActor btn) {
				Game.app.getScreenAs(MenuScreen.class).showNext = 2;
			}
		}));
		Game.scene.GUI.add(new ButtonActor(2, Game.assets.get("assets/menu-opcje.png", Texture.class), -200, -80, new ButtonActionListener() {
			// otworz plik cfg
			@Override
			public void clicked(ButtonActor btn) {
				ProcessBuilder pb = new ProcessBuilder(Game.config.textEditor, "config.cfg");
				try {
					pb.start().waitFor();
					Game.reConfigure();
				} catch (IOException | InterruptedException ex) {
					Logger.getLogger(MenuScreen.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}));
		Game.scene.GUI.add(new ButtonActor(3, Game.assets.get("assets/menu-wyjscie.png", Texture.class), -200, -210, new ButtonActionListener() {
			// zamknij gre
			@Override
			public void clicked(ButtonActor btn) {
				Gdx.app.exit();
			}
		}));
	}
	
	public void showStages() {
		// wyczysc warstwe GUI
		Game.scene.GUI.dispose();
		
		// stworz obiekty menu na nowo
		Game.scene.GUI.add(new ButtonActor(1, Game.assets.get("assets/levels/a.png", Texture.class), -300, -200, new ButtonActionListener() {
			@Override
			public void clicked(ButtonActor btn) {
				Game.app.setNextScreen(new StageScreen("a"));
			}
		}));
		
		Game.scene.GUI.add(new ButtonActor(1, Game.assets.get("assets/menu-wroc.png", Texture.class), 300, -200, new ButtonActionListener() {
			@Override
			public void clicked(ButtonActor btn) {
				Game.app.getScreenAs(MenuScreen.class).showNext = 1;
			}
		}));
	}
}
