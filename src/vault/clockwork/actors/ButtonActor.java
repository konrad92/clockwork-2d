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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import javax.crypto.spec.PSource;
import vault.clockwork.Game;
import vault.clockwork.Vault;
import vault.clockwork.scene.Actor;
import vault.clockwork.system.Physics;


/**
 *
 * @author Agnieszka Makowska https://github.com/Migemiley
 */
public class ButtonActor extends ObstacleActor{
	private final Sprite spr;
	
	public final Vector2 position = new Vector2();
	public float angle = 0;
	
	private float timer = 0;
	
	private boolean mouseOver = false;
	
	public ButtonActor(int id, Texture texture){
		super(id);
		
		spr = new Sprite(texture);
		spr.setBounds(0.f, 0.f, texture.getWidth(), texture.getHeight());
		
	}
	
	public ButtonActor(int id){
		super(id);
		
		spr = new Sprite(Game.assets.get("assets/button.png", Texture.class));
		spr.setBounds(0.f, 0.f, 200.f, 150.f);
	}
	
	@Override
	public void update(float delta) {
		spr.setRotation(angle + (float)(4.0 * Math.sin(timer)));
		timer += delta;
	}
	
	@Override
	public void draw(SpriteBatch batch){
		// mouse over
		mouseOver = isMouseOver();
		
		// scale lerp
		if(mouseOver) {
			spr.setScale(spr.getScaleX() + (1.2f - spr.getScaleX())*0.2f);
		} else {
			spr.setScale(spr.getScaleX() + (1 - spr.getScaleX())*0.2f);
		}
		
		if(Vault.comicShader.isCompiled()) {
			batch.setShader(Vault.comicShader);
		}
		batch.begin();
		spr.draw(batch);
		batch.end();
		batch.setShader(null);
	}
	
	@Override
	public void debug(ShapeRenderer gizmo) {
		Rectangle rect = spr.getBoundingRectangle();
		
		gizmo.setColor(Color.MAGENTA);
		gizmo.begin(ShapeRenderer.ShapeType.Line);
		gizmo.rect(rect.x, rect.y, rect.width, rect.height);
		gizmo.end();
	}
	
	/**
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
		return angle;
	}
	
	/**
	 * @see Actor#setRotation(float) 
	 * @param newAngle
	 */
	@Override
	public void setRotation(float newAngle) {
		angle = newAngle;
	}
	
	@Override
	public void dispose() {
	}
	
	public boolean isMouseOver() {
		Rectangle rect = spr.getBoundingRectangle();
		Vector3 mousePointer = new Vector3(
			Gdx.input.getX(),
			Gdx.input.getY(),
			0
		);
		
		Vector3 unproj = Game.mainCamera.unproject(mousePointer).scl(
			Gdx.graphics.getWidth()/2,
			Gdx.graphics.getHeight()/2,
			1
		);
		return rect.contains(unproj.x, unproj.y);
	}
}
