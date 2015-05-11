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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import vault.clockwork.Game;
import vault.clockwork.scene.Actor;
import vault.clockwork.system.Physics;

/**
 *
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class TurretActor extends Actor {
	private final Body body;
	private final Fixture fixture;
	
	private final Sprite sprBall;
	
	/**
	 * Ctor.
	 * Create new physic body on the world.
	 * @see Actor#Actor(int) 
	 * @param id Turret unique id.
	 */
	public TurretActor(int id) {
		super(id);
		
		// body shape
		CircleShape shape = new CircleShape();
		shape.setRadius(32.f * Physics.SCALE);
		
		// create physics body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set((float)Math.random()*1.f, (float)Math.random()*1.f);
		body = Game.physics.world.createBody(bodyDef);
		fixture = body.createFixture(shape, 2.f);
		fixture.setRestitution(.4f);
		fixture.setUserData(this);
		
		shape.dispose();
		
		// create the ball sprite
		sprBall = new Sprite(Game.assets.get("assets/dragonball.png", Texture.class));
		sprBall.setBounds(-42.f, -42.f, 84.f, 84.f);
		sprBall.setOriginCenter();
	}
	
	/**
	 * @see Actor#update(float) 
	 * @param delta 
	 */
	@Override
	public void update(float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			DebugScreenActor.info.append("Turret recreation!\n");
			Game.scene.ACTION_2.add(new TurretActor(id));
			this.remove();
		}
	}
	
	/**
	 * @see Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch) 
	 * @param batch 
	 */
	@Override
	public void draw(SpriteBatch batch) {
		sprBall.setCenter(
			body.getPosition().x * Physics.SCALE_INV,
			body.getPosition().y * Physics.SCALE_INV
		);
		
		batch.begin();
		sprBall.draw(batch);
		batch.end();
	}
	
	/**
	 * Remove actor on impact with WielokatActor.
	 * @param actor
	 * @param contact 
	 */
	@Override
	public void onHit(Actor actor, Contact contact) {
		if(actor instanceof WielokatActor || actor instanceof DustbinActor) {
			this.remove();
		}
		
		if(actor instanceof ObstacleActor){
			((ObstacleActor)actor).playImpactSound();
		}
	}
	
	/**
	 * Remove physic body from the world.
	 * @see Actor#dispose() 
	 */
	@Override
	public void dispose() {
		Game.physics.world.destroyBody(body);
	}
}
