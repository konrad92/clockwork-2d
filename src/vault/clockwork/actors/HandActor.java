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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import vault.clockwork.Game;
import vault.clockwork.scene.Actor;
import vault.clockwork.scene.Entity;

/**
 * A floating hand controlled by the player.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class HandActor extends Actor {
	/**
	 * Hand texture directory.
	 */
	static public final String HAND_TEXTURE = "assets/hand.png";
	
	/**
	 * Hand position on the scene.
	 */
	public final Vector2 position = new Vector2();
	
	/**
	 * Hand sprite.
	 */
	private final Sprite sprHand;
	
	/**
	 * Ctor.
	 * @param id Unique actor ID 
	 */
	public HandActor(int id) {
		super(id, 1);
		
		position.set(100.f, 100.f);
		
		// create hand sprite
		sprHand = new Sprite(Game.assets.get(HAND_TEXTURE, Texture.class));
		//sprHand.setOriginCenter();
		sprHand.setOrigin(270.f, 94.f);
		System.out.println(sprHand.getOriginX());
		System.out.println(sprHand.getOriginY());
	}
	
	/**
	 * Controll the hand by mouse pointer.
	 * @see Actor#update(float) 
	 * @param delta 
	 */
	@Override
	public void update(float delta) {
		// unproject screen coordinates to world
		Vector3 rotateBy = Game.mainCamera.unproject(new Vector3(
			Gdx.input.getX() - position.x,
			Gdx.input.getY() + position.y,
			0.f
		));
		
		if(rotateBy.x < 0) {
			sprHand.setFlip(false, true);
		} else {
			sprHand.setFlip(false, false);
		}
		
		// follow the cursor
		sprHand.rotate();
	}
	
	/**
	 * @see Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch) 
	 * @param batch 
	 */
	@Override
	public void draw(SpriteBatch batch) {
		sprHand.setPosition(
			position.x - sprHand.getOriginX(),
			position.y - sprHand.getOriginY()
		);
		
		batch.begin();
		sprHand.draw(batch);
		batch.end();
	}
	
	/**
	 * @see Entity#debug(com.badlogic.gdx.graphics.glutils.ShapeRenderer) 
	 * @param gizmo 
	 */
	@Override
	public void debug(ShapeRenderer gizmo) {
		gizmo.begin(ShapeRenderer.ShapeType.Line);
		gizmo.circle(position.x, position.y, 16.f);
		gizmo.end();
	}
}
