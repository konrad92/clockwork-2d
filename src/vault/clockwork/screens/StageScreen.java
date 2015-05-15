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

import static com.badlogic.gdx.Gdx.gl;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import vault.clockwork.Game;
import vault.clockwork.Vault;
import vault.clockwork.actors.DustbinActor;
import vault.clockwork.actors.GameLogoActor;
import vault.clockwork.actors.GridBackgroundActor;
import vault.clockwork.actors.GroundActor;
import vault.clockwork.actors.HandActor;
import vault.clockwork.actors.PaperBallActor;
import vault.clockwork.actors.PlanetActor;
import vault.clockwork.actors.PlankActor;
import vault.clockwork.actors.StaticPlankActor;
import vault.clockwork.actors.StoneActor;
import vault.clockwork.controllers.CameraController;

/**
 * Playable stage screen.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class StageScreen implements GameScreen {
	/**
	 * Kontroluje kamere, tj. podazanie za aktorem.
	 */
	private final CameraController camera = new CameraController();
	
	/**
	 * Preload all screen resources here.
	 * @see GameScreen#prepare() 
	 */
	@Override
	public void prepare() {
		Game.assets.load("assets/turret.png", Texture.class);
		Game.assets.load("assets/blueprint.png", Texture.class);
		Game.assets.load("assets/dragonball.png", Texture.class);
		Game.assets.load("assets/bin.png", Texture.class);
		Game.assets.load("assets/paperball.png", Texture.class);
		Game.assets.load("assets/wood.png", Texture.class);
		Game.assets.load("assets/planet.png", Texture.class);
		Game.assets.load("assets/space.png", Texture.class);
		Game.assets.load("assets/kamyk.png", Texture.class);
		Game.assets.load(Vault.SOUND_PAPERHIT, Sound.class);
		Game.assets.load(Vault.SOUND_WOODBOUNCE, Sound.class);
		Game.assets.load(Vault.SOUND_KOSZ1, Sound.class);
		Game.assets.load(Vault.SOUND_KOSZ2, Sound.class);
		Game.assets.load(Vault.SOUND_KOSZ3, Sound.class);
		Game.assets.load(Vault.SOUND_KOSZ4, Sound.class);
		Game.assets.load(Vault.SOUND_KOSZ5, Sound.class);
		
		// preload resources
		GameLogoActor.preload();
		HandActor.preload();
		PaperBallActor.preload();
	}

	/**
	 * Prepare the scene to show-up.
	 */
	@Override
	public void show() {
		reConfigure();
		
		// add scene controllers
		Game.scene.controllers.add(camera);
		
		// register input processors
		Game.inputMultiplexer.addProcessor(camera);
		
		// create turret actor
		Game.scene.BACKGROUND.add(new GridBackgroundActor(-1));
		Game.scene.ACTION_3.add(new DustbinActor(1,160, 110, 40, 0, -150));
                
		Game.scene.ACTION_1.add(new GroundActor(-1));
		Game.scene.ACTION_1.add(new PlanetActor(-1));
		
		Game.scene.ACTION_2.add(new HandActor(0));
		Game.scene.ACTION_2.add(new PlankActor(2, 1, 60.f, 120.f));
		Game.scene.ACTION_2.add(new StaticPlankActor(3));
		Game.scene.ACTION_2.add(new StoneActor(4));
	}

	/**
	 * Update screen logic and perform the systems.
	 * @see Screen#render(float) 
	 */
	@Override
	public void render(float delta) {
        // clear target buffer
        gl.glClearColor(0.1f, 0.2f, 0.1f, 1.f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		// update shaders
		Vault.comicShader.begin();
		Vault.comicShader.setUniformf("u_ticks", (float)(Math.random()*2*Math.PI));
		Vault.comicShader.setUniformf("u_strength",(float)(Math.random()*0.0015f));
		Vault.comicShader.end();
		
		// perform game systems
		Game.performSystems();
	}
}
