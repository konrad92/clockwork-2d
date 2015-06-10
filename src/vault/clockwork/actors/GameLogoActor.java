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
package vault.clockwork.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import vault.clockwork.Game;
import vault.clockwork.Vault;
import vault.clockwork.scene.Actor;

/**
 *
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class GameLogoActor extends Actor {
	/**
	 * Logo texture filename.
	 */
	static public final String LOGO_TEXTURE = "assets/dustbin-rush.png";
	
	/**
	 * Preload the actor resources.
	 */
	static public void preload() {
		Game.assets.load(LOGO_TEXTURE, Texture.class);
	}
	
	/**
	 * Position on the scene.
	 */
	public final Vector2 position = new Vector2();
	
	/**
	 * Actor angle.
	 */
	public float angle = 0;
	
	/**
	 * Logo sprite.
	 */
	public Sprite sprLogo;
	
	/**
	 * Ctor.
	 */
	public GameLogoActor() {
		super(-1, -1);
		
		// create the sprite
		sprLogo = new Sprite(Game.assets.get(LOGO_TEXTURE, Texture.class));
		sprLogo.setOriginCenter();
		sprLogo.setScale(.8f);
	}
	
	/**
	 * @see Actor#update(float) 
	 * @param delta 
	 */
	@Override
	public void update(float delta) {
		
	}
	
	/**
	 * @see Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch) 
	 * @param batch 
	 */
	@Override
	public void draw(SpriteBatch batch) {
		sprLogo.setCenter(position.x, position.y);
		sprLogo.setRotation(angle);
		
		// setup the shader usage
		if(Game.config.shaders && Vault.comicShader.isCompiled()) {
			batch.setShader(Vault.comicShader);
		}
		
		// draw-up the sprite
		batch.begin();
		sprLogo.draw(batch);
		batch.end();
		
		// RESET the shader usage
		batch.setShader(null);
	}
	
	/**
	 * @see Actor#getPosition() 
	 * @return 
	 */
	@Override
	public Vector2 getPosition() {
		return this.position;
	}
	
	/**
	 * @see Actor#setPosition(com.badlogic.gdx.math.Vector2) 
	 * @param newPosition 
	 */
	@Override
	public void setPosition(Vector2 newPosition) {
		this.position.set(newPosition);
	}
	
	/**
	 * @see Actor#getRotation(float)
	 * @return 
	 */
	@Override
	public float getRotation() {
		return this.angle;
	}
	
	/**
	 * @see Actor#setRotation(float)
	 * @param newAngle
	 */
	@Override
	public void setRotation(float newAngle) {
		this.angle = newAngle;
	}
}
