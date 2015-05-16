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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
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
	 * Stany rozgrywki `roncki`.
	 */
	public interface HandState {
		/**
		 * Gdy gracz wejdzie w stan.
		 */
		public default void enter() {
			// dummy method
		}
		
		/**
		 * Gdy gracz wyjdzie ze stanu.
		 */
		public default void leave() {
			// dummy method
		}
		
		/**
		 * Aktualizuj stan.
		 * @param delta 
		 */
		public void update(float delta);
		
		/**
		 * Rysuj stan.
		 * @param batch 
		 */
		public void draw(SpriteBatch batch);
	};
	
	/**
	 * Hand texture filename.
	 */
	static public final String
		HAND_TEXTURE = "assets/hand.png",
		STAMINABAR_BG_TEXTURE = "assets/stamina-bar-bg.png",
		STAMINABAR_MD_TEXTURE = "assets/stamina-bar-md.png",
		STAMINABAR_FG_TEXTURE = "assets/stamina-bar-fg.png";
	
	/**
	 * Preload the actor resources.
	 */
	static public void preload() {
		Game.assets.load(HAND_TEXTURE, Texture.class);
		Game.assets.load(STAMINABAR_BG_TEXTURE, Texture.class);
		Game.assets.load(STAMINABAR_FG_TEXTURE, Texture.class);
		Game.assets.load(STAMINABAR_MD_TEXTURE, Texture.class);
	}
	
	/**
	 * Hand position on the scene.
	 */
	public final Vector2 position = new Vector2();
	
	/**
	 * Hand sprite.
	 */
	private final Sprite sprHand;
	private final Sprite sprStaminaBG;
	private final Sprite sprStaminaFG;
	private final Sprite sprStaminaMD;
	
	/**
	 * Paper ball actor assigned for cooldown process.
	 */
	private PaperBallActor paperBall;
	
	/**
	 * Stamina level. Determines strength of the shoot.
	 */
	private float staminaLevel = 0.f;
	
	/**
	 * Stan gotowosci gracza do wystrzalu.
	 */
	public final HandState STATE_IDLE = new HandState() {
		@Override
		public void update(float delta) {
		}

		@Override
		public void draw(SpriteBatch batch) {
			// update sprite position
			sprHand.setPosition(
				position.x - sprHand.getOriginX(),
				position.y - sprHand.getOriginY()
			);

			// draw-up the hand sprite
			batch.begin();
			sprHand.draw(batch);
			batch.end();
		}
	};
	
	/**
	 * Stan gotowosci gracza do wystrzalu.
	 */
	public final HandState STATE_READY = new HandState() {
		@Override
		public void enter() {
			paperBall = null;
		}
		
		@Override
		public void update(float delta) {
			Vector2 rotateBy = getPointerVector().nor();

			// flip the hand
			if(rotateBy.x < 0) {
				sprHand.setFlip(false, true);
			} else {
				sprHand.setFlip(false, false);
			}

			// shoot the paper ball
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				setState(STATE_LOADING);
			}

			// follow the cursor
			//sprHand.setRotation(rotateBy.angle());
			lerpRotateTo(rotateBy, 5.f*delta, 20.f);
		}

		@Override
		public void draw(SpriteBatch batch) {
			STATE_IDLE.draw(batch);
		}
	};
	
	/**
	 * Stan ladowania sily.
	 */
	public final HandState STATE_LOADING = new HandState() {
		@Override
		public void enter() {
			staminaLevel = 0.f;
		}
		
		@Override
		public void update(float delta) {
			staminaLevel = Math.min(staminaLevel + 1.4f * delta, 1.f);
			
			if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				shootPaperBall();
				setState(STATE_READY);
			}
		}

		@Override
		public void draw(SpriteBatch batch) {
			STATE_IDLE.draw(batch);
			
			// draw stamina bar
			float scale =  Game.mainCamera.zoom;
		
			sprStaminaBG.setScale(Game.mainCamera.zoom);
			sprStaminaBG.setCenter(position.x, position.y + 200.f * scale);

			sprStaminaFG.setScale(Game.mainCamera.zoom);
			sprStaminaFG.setCenter(position.x, position.y + 200.f * scale);

			// stamina level
			float level = (float)sprStaminaMD.getTexture().getWidth() * Math.min(staminaLevel, 1.f);
			sprStaminaMD.setScale(Game.mainCamera.zoom);
			sprStaminaMD.setPosition(position.x - sprStaminaBG.getWidth()*.5f, sprStaminaBG.getY());
			sprStaminaMD.setSize(level, sprStaminaMD.getHeight());
			sprStaminaMD.setRegionWidth((int)(level));

			// draw-up the stamina sprites
			batch.begin();
			sprStaminaBG.draw(batch);
			sprStaminaMD.draw(batch);
			sprStaminaFG.draw(batch);
			batch.end();
		}
	};
	
	/**
	 * Aktualny stan rozgrywki.
	 */
	private HandState currentState = null;
	
	/**
	 * Ctor.
	 * @param id Unique actor ID 
	 */
	public HandActor(int id) {
		super(id, TYPE_PLAYER);
		
		position.set(100.f, 100.f);
		
		// create hand sprite
		sprHand = new Sprite(Game.assets.get(HAND_TEXTURE, Texture.class));
		sprHand.setOrigin(270.f, 94.f);
		sprHand.setScale(.8f);
		setPosition(Vector2.Y.cpy().scl(200.f));
		
		// create stamina bar sprites
		sprStaminaBG = new Sprite(Game.assets.get(STAMINABAR_BG_TEXTURE, Texture.class));
		sprStaminaFG = new Sprite(Game.assets.get(STAMINABAR_FG_TEXTURE, Texture.class));
		sprStaminaMD = new Sprite(Game.assets.get(STAMINABAR_MD_TEXTURE, Texture.class));
		
		// zmien stan rozgrywki
		setState(STATE_READY);
	}
	
	/**
	 * Controll the hand by mouse pointer.
	 * @see Actor#update(float) 
	 * @param delta 
	 */
	@Override
	public void update(float delta) {
		// aktualizacja aktualnie rozgrywanego stanu
		if(currentState != null) {
			currentState.update(delta);
		}
	}
	
	/**
	 * @see Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch) 
	 * @param batch 
	 */
	@Override
	public void draw(SpriteBatch batch) {
		// rysowanie aktualnie rozgrywanego stanu
		if(currentState != null) {
			currentState.draw(batch);
		}
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
	 * Zmiany zwroconej referencji dadza efekt.
	 * @see Actor#getPosition() 
	 * @return 
	 */
	@Override
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * @see Actor#setPosition(com.badlogic.gdx.math.Vector2) 
	 * @param newPosition 
	 */
	@Override
	public void setPosition(Vector2 newPosition) {
		position.set(newPosition);
	}
	
	/**
	 * @see Actor#getRotation() 
	 * @return 
	 */
	@Override
	public float getRotation() {
		return sprHand.getRotation();
	}
	
	/**
	 * @see Actor#setRotation(float) 
	 * @param newAngle degrees
	 */
	@Override
	public void setRotation(float newAngle) {
		sprHand.setRotation(newAngle);
	}
	
	/**
	 * Zmien aktualnie rozgrywany stan.
	 * @param newState 
	 */
	public void setState(HandState newState) {
		// leave current state
		if(currentState != null) {
			currentState.leave();
		}
		
		// enter to the new state
		currentState = newState;
		if(currentState != null) {
			currentState.enter();
		}
	}
	
	/**
	 * Wystrzeliwuje kulke, gdy ta nie jest jeszcze wystrzelona.
	 */
	public void shootPaperBall() {
		if(paperBall != null) {
			return;
		}
		
		// kierunek i sila wystrzalu
		Vector2 force = Vector2.X.cpy().setAngle(getRotation()).scl(staminaLevel * 500.f);
		
		// stworz kulke
		paperBall = new PaperBallActor(0);
		paperBall.setPosition(position);
		paperBall.applyForce(force);
		Game.scene.ACTION_1.add(paperBall);
	}
	
	/**
	 * Get the pointer vector, from the hand to the cursor.
	 * @return 
	 */
	public Vector2 getPointerVector() {
		Vector3 rotateBy = Game.mainCamera.unproject(new Vector3(
			Gdx.input.getX() - position.x / Game.mainCamera.zoom,
			Gdx.input.getY() + position.y / Game.mainCamera.zoom,
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
