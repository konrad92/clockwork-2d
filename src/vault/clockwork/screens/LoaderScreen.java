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
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import vault.clockwork.Game;
import vault.clockwork.scene.Actor;

/**
 * Loading process screen.
 * Screen that performs async assets loading.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class LoaderScreen implements Screen {
	/**
	 * Loader gear actor.
	 */
	public class GearActor extends Actor {
		public Sprite sprite;
		public float speed = 45.f;
		
		/**
		 * Ctor.
		 * @param id Unique identifier. 
		 * @param type Gear type: 0 - big, 1 - small
		 * @param texture Gear texture.
		 */
		public GearActor(int id, int type, Texture texture) {
			super(id, type);
			
			float hw = (float)Gdx.graphics.getWidth()/2;
			
			// create gear sprite
			sprite = new Sprite(texture);
			if(type == 0) {
				sprite.setRegion(0, 0, 41, 41);
				sprite.setSize(41.f, 41.f);
				sprite.setCenter(hw, 200.f);
			} else {
				sprite.setRegion(41, 0, 27, 27);
				sprite.setSize(27.f, 27.f);
				sprite.setCenter(hw + 22.f, 200.f - 22.f);
				speed *= -1.f;
			}
			
			// center the sprite origin
			sprite.setOriginCenter();
		}
		
		/**
		 * @param delta
		 * @see Actor#update(float) 
		 */
		@Override
		public void update(float delta) {
			sprite.setRotation(sprite.getRotation() + speed*delta);
		}
		
		/**
		 * @param batch
		 * @see Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch)  
		 */
		@Override
		public void draw(SpriteBatch batch) {
			batch.begin();
			sprite.draw(batch);
			batch.end();
		}
	}
    /**
     * Single machine texture.
     */
    private Texture machineTexture;
    
    /**
     * Next game screen after load finish.
     */
    public final GameScreen next;
    
    /**
     * Clear global assets when loader shown.
     */
    public boolean clearAssets;
    
    /**
     * Loader screen constructor.
     * @param next Next game screen.
     */
    public LoaderScreen(GameScreen next) {
        this(next, true);
    }
    
    /**
     * Loader screen constructor.
     * @param next Next game screen.
     * @param clearAssets Perform assets clear.
     */
    public LoaderScreen(GameScreen next, boolean clearAssets) {
        this.next = next;
        this.clearAssets = clearAssets;
    }
    
    /**
     * Performed on screen change at this one.
     * Load loader resources for single session.
     */
    @Override
    public void show() {
        // load texture from file
        this.machineTexture = new Texture(Gdx.files.internal("assets/machine.png"));
        
        // create gear actors
		Game.scene.BACKGROUND.add(new GearActor(0, 0, machineTexture));
		Game.scene.BACKGROUND.add(new GearActor(0, 1, machineTexture));
		Game.scene.BACKGROUND.camera = new OrthographicCamera();
		//Game.scene.add(0, new GearActor(0, 0, machineTexture));
		//Game.scene.add(0, new GearActor(0, 1, machineTexture));
        
        // clear game assets
        if(this.clearAssets) {
            Game.assets.clear();
        }
        
        // prepare next screen to load
        if(this.next != null) {
            this.next.prepare();
        }
    }

    /**
     * Frame update.
     * Update asset manager and render some loader screen stuff.
     * @param delta 
     */
    @Override
    public void render(float delta) {
        // clear target buffer
        gl.glClearColor(0.f, 0.f, 0.f, 1.f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
        // draw fade out on load finish
        if(Game.assets.update()) {
			Game.app.setScreen(next);
        }
        
		// perform game systems
		Game.performSystems();
    }

    /**
     * Handle window resizing.
     * Performed when window client resized.
     * @param width
     * @param height 
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * Handle pause event.
     * Performed on window focus lost.
     */
    @Override
    public void pause() {
    }

    /**
     * Handle resume event.
     * Performed on window focus gain.
     */
    @Override
    public void resume() {
    }

    /**
     * Performed when screen were changed.
     * Dispose all resources of the loader.
     */
    @Override
    public void hide() {
		Game.scene.clear();
    }

    /**
     * Dispsoe resources.
     * Release all unused resources to bypass the memoryleaks.
     */
    @Override
    public void dispose() {
        machineTexture.dispose();
    }
}
