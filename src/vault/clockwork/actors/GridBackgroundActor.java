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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import vault.clockwork.Game;
import vault.clockwork.scene.Actor;

/**
 * Draws the blueprint grid background.
 * Essentialy provided for the editor usage.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class GridBackgroundActor extends Actor {	
	/**
	 * The background tilling sprite.
	 */
	private final Sprite background;
	
	/**
	 * Texel size in screen-space coords.
	 */
	private final float pixelSize;
	
	/**
	 * Background parallax multiplier.
	 * Effective range between 0.0 - 1.0
	 */
	public float parallax = 1.f;
	
	/**
	 * Ctor.
	 * @see Actor#Actor(int) 
	 * @param id Unique actor identifier.
	 */
	public GridBackgroundActor(int id) {
		super(id);
		
		// create the blueprint sprite
		this.background = new Sprite(
			Game.assets.get("assets/blueprint.png", Texture.class)
		);
		this.background.getTexture().setWrap(
			Texture.TextureWrap.Repeat,
			Texture.TextureWrap.Repeat
		);
		
		// change size to identity screen-coords
		this.background.setBounds(-1.f, -1.f, 2.f, 2.f);
		this.pixelSize = 1.f/(float)this.background.getTexture().getWidth();
	}
	
	/**
	 * Draw the background grid.
	 * @see Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch) 
	 * @param batch Sprite batching
	 */
	@Override
	public void draw(SpriteBatch batch) {
		// change drawing projection to identity
		Matrix4 oldProj = batch.getProjectionMatrix();
		batch.setProjectionMatrix(new Matrix4());
		
		float scale = Game.mainCamera.zoom,
			w = (float)Gdx.graphics.getWidth() * pixelSize * scale, 
			h = (float)Gdx.graphics.getHeight() * pixelSize * scale,
			x = (float)Game.mainCamera.position.x * pixelSize * parallax,
			y = (float)Game.mainCamera.position.y * pixelSize * parallax;
		
		background.setU(x - w/2);
		background.setU2(x + w/2);
		background.setV(y + h/2);
		background.setV2(y - h/2);
		
		// fill-up the screen
		batch.begin();
		background.draw(batch);
		batch.end();
		
		// reverse camera projection
		batch.setProjectionMatrix(oldProj);
	}
}
