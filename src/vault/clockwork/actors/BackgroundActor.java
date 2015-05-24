/*
 * The MIT License
 *
 * Copyright 2015 Konrad Nowakowski <konrad.x92@gmail.com>.
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
import com.badlogic.gdx.math.Vector2;
import vault.clockwork.Game;
import vault.clockwork.editor.PropSerialized;
import vault.clockwork.editor.props.BackgroundProp;
import vault.clockwork.scene.Actor;

/**
 * Aktor do rysowania danego tla.
 * @author Konrad Nowakowski <konrad.x92@gmail.com>
 */
public class BackgroundActor extends Actor {
	/**
	 * The background tilling sprite.
	 */
	private final Sprite background;
	
	/**
	 * Texel size in screen-space coords.
	 */
	private final float pixelSize;
	
	/**
	 * Przesuniecie tla.
	 */
	public final Vector2 position = new Vector2();
	
	/**
	 * Background parallax multiplier.
	 * Effective range between 0.0 - 1.0
	 */
	public final Vector2 parallax = new Vector2();
	
	/**
	 * Skala (rozciagniecie) tla.
	 */
	public float scaled = 1.f;
	
	/**
	 * Multiplikator odleglosci kamery.
	 */
	public float zoomed = 1.f;
	
	/**
	 * Editor constructor.
	 * @param prop 
	 */
	public BackgroundActor(PropSerialized prop) {
		this((BackgroundProp)prop);
	}
	
	/**
	 * Editor constructor #2.
	 * @param prop 
	 */
	public BackgroundActor(BackgroundProp prop) {
		this(prop.id, prop.background);
		
		// background parameters
		this.position.set(prop.offset_x, prop.offset_y);
		this.parallax.set(prop.parallax_x, prop.parallax_y);
		this.scaled = prop.scaled;
		this.zoomed = prop.zoomed;
	}
	
	/**
	 * Ctor.
	 * @see Actor#Actor(int) 
	 * @param id Unique actor identifier.
	 * @param filename Sciezka do pliku graficznego.
	 */
	public BackgroundActor(int id, String filename) {
		this(id, filename, new Vector2(1.f, 1.f), 1.f, 1.f);
	}
	
	/**
	 * Ctor.
	 * @param id Unique actor identifier.
	 * @param filename Sciezka do pliku graficznego.
	 * @param parallax Mnoznik parallax.
	 * @param scaled Skala jednostkowa.
	 * @param zoomed Mnoznik skali, zalezny od zoomu kamery.
	 * @see Actor#Actor(int) 
	 */
	public BackgroundActor(int id, String filename, Vector2 parallax, float scaled, float zoomed) {
		super(id, TYPE_BACKGROUND);
		
		// background options
		this.parallax.set(parallax);
		this.scaled = scaled;
		this.zoomed = zoomed;
		
		// create the blueprint sprite
		this.background = new Sprite(
			Game.assets.get(filename, Texture.class)
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
		batch.setProjectionMatrix(new Matrix4());
		
		float scale = scaled + (Game.mainCamera.zoom - 1) * zoomed,
			w = (float)Gdx.graphics.getWidth() * pixelSize * scale, 
			h = (float)Gdx.graphics.getHeight() * pixelSize * scale,
			x = (Game.mainCamera.position.x + position.x) * pixelSize * parallax.x,
			y = (Game.mainCamera.position.y + position.y) * pixelSize * parallax.y;
		
		background.setU(x - w*.5f - .5f);
		background.setU2(x + w*.5f - .5f);
		background.setV(-y - h*.5f - .5f);
		background.setV2(-y + h*.5f - .5f);
		
		// fill-up the screen
		batch.begin();
		background.draw(batch);
		batch.end();
		
		// reverse camera projection
		batch.setProjectionMatrix(Game.mainCamera.combined);
	}
	
	/**
	 * @see Actor#getPosition() 
	 * @return Background position.
	 */
	@Override
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * @see Actor#setPosition(com.badlogic.gdx.math.Vector2) 
	 * @param newPosition Nowa pozycja tla.
	 */
	@Override
	public void setPosition(Vector2 newPosition) {
		position.set(position);
	}
}
