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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import vault.clockwork.Game;
import vault.clockwork.Vault;
import vault.clockwork.scene.Actor;
import vault.clockwork.scene.Entity;

/**
 * A floating hand controlled by the player.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class HandActor extends Actor {
	/**
	 * Hand texture filename.
	 */
	static public final String HAND_TEXTURE = "assets/hand.png";
	
	/**
	 * Preload the actor resources.
	 */
	static public void preload() {
		Game.assets.load(HAND_TEXTURE, Texture.class);
	}
	
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
		sprHand.setOrigin(270.f, 94.f);
		sprHand.setScale(.8f);
	}
	
	/**
	 * Controll the hand by mouse pointer.
	 * @see Actor#update(float) 
	 * @param delta 
	 */
	@Override
	public void update(float delta) {
		// unproject screen coordinates to world
		Vector2 rotateBy = getPointerVector().nor();
		
		// flip the hand
		if(rotateBy.x < 0) {
			sprHand.setFlip(false, true);
		} else {
			sprHand.setFlip(false, false);
		}
		
		// follow the cursor
		//sprHand.setRotation(rotateBy.angle());
		lerpRotateTo(rotateBy, 5.f*delta, 20.f);
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
		
		// setup the shader usage
		batch.setShader(Vault.comicShader);
		
		// draw-up the sprite
		batch.begin();
		sprHand.draw(batch);
		batch.end();
		
		// RESET the shader usage
		batch.setShader(null);
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
	
	/**
	 * Get the pointer vector, from the hand to the cursor.
	 * @return 
	 */
	public Vector2 getPointerVector() {
		Vector3 rotateBy = Game.mainCamera.unproject(new Vector3(
			Gdx.input.getX() - position.x,
			Gdx.input.getY() + position.y,
			0.f
		));
		
		return new Vector2(rotateBy.x, rotateBy.y);
	}
	
	/**
	 * Calculate angle difference of hand by a given vector.
	 * @param by
	 * @return 
	 */
	private float angleDifference(Vector2 by) {
		return by.angle(Vector2.X.cpy().setAngle(sprHand.getRotation()));
	}
	
	/**
	 * Lerp interpolation of the angle.
	 * @param target
	 * @param factor 
	 */
	private void lerpRotateTo(Vector2 target, float factor) {
		sprHand.rotate(angleDifference(target) * -factor);
	}
	
	/**
	 * Lerp interpolation of the angle.
	 * Clamp the interpolation.
	 * @param target
	 * @param factor 
	 */
	private void lerpRotateTo(Vector2 target, float factor, float clamp) {
		sprHand.rotate(MathUtils.clamp(angleDifference(target) * -factor, -clamp, clamp));
	}
}
