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
import com.badlogic.gdx.physics.box2d.Contact;
import vault.clockwork.Game;
import vault.clockwork.editor.PropSerialized;
import vault.clockwork.editor.props.DecorationProp;
import vault.clockwork.scene.Actor;
import vault.clockwork.system.Physics;

/**
 *
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class DecorationActor extends Actor {
	private final Sprite sprDecoration;
	
	/**
	 * Pozycja dekoracji.
	 * Dekoracje są wycentrowane do tej pozycji.
	 */
	public final Vector2 position = new Vector2();
	
	/**
	 * Skalowanie obiektu.
	 */
	public float scale = 1;
	
	/**
	 * Kąt nachylenia obiektu dekoracji.
	 */
	public float angle = 0;
	
	/**
	 * Editor constructor.
	 * @param prop 
	 */
	public DecorationActor(PropSerialized prop) {
		this((DecorationProp)prop);
	}
	
	/**
	 * Editor constructor.
	 * @param prop 
	 */
	public DecorationActor(DecorationProp prop) {
		this(prop.id, Game.assets.get(prop.image, Texture.class));
		
		this.setPosition(prop.position);
		this.setRotation(prop.angle);
		this.scale = prop.scale;
	}
	
	/**
	 * Ctor.
	 * Create new physic body on the world.
	 * @see Actor#Actor(int) 
	 * @param id Turret unique id.
	 */
	public DecorationActor(int id, Texture texture) {
		super(id);

		// create the ball sprite
		sprDecoration = new Sprite(texture);
		sprDecoration.setOriginCenter();
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
		sprDecoration.setCenter(position.x, position.y);
		sprDecoration.setRotation(angle);
		sprDecoration.setScale(scale);
		
		batch.begin();
		sprDecoration.draw(batch);
		batch.end();
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
	 * @see Actor#getRotation() 
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
