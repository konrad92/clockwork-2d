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
package vault.clockwork.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import java.util.Iterator;
import vault.clockwork.Game;
import vault.clockwork.scene.Actor;

/**
 * Scene main system.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class Scene implements System {
	/**
	 * Scene layer class.
	 */
	public class Layer implements Disposable, Iterable<Actor> {
		/**
		 * Scene assigned with the layer.
		 * Allows only the single scene assignement.
		 */
		public final Scene scene;
		
		/**
		 * Actors on the layer.
		 */
		public final Array<Actor> actors = new Array<>();
		
		/**
		 * Actors to remove from the layer.
		 */
		public final Array<Actor> remove = new Array<>();
		
		/**
		 * Ctor.
		 * Adding layer to the given scene.
		 * @param scene Assign layer with the scene.
		 */
		public Layer(Scene scene) {
			this.scene = scene;
			
			// put layer onto scene layers stack
			if(!this.scene.layers.contains(this, true)) {
				this.scene.layers.add(this);
			}
		}

		/**
		 * Act the update of the layer.
		 * Performs update on all actors.
		 * @param delta Delta time to perform the update.
		 */
		public void update(float delta) {
			for(Actor actor : actors) {
				if(actor.active) {
					actor.update(delta);
				}
			}
			
			// remove actors from the layer
			if(remove.size > 0) {
				for(Actor actor : remove) {
					if(actor.destroy()) {
						actors.removeValue(actor, true);
						actor.dispose();
					}
				}
				remove.clear();
			}
		}
		
		/**
		 * Draw the layer.
		 * Performs draw on all actors.
		 * @param batch Sprite batch as rendering target.
		 */
		public void draw(SpriteBatch batch) {
			for(Actor actor : actors) {
				if(actor.active && actor.visible) {
					actor.draw(batch);
				}
			}
		}
		
		/**
		 * Draw debug information of the layer.
		 * Performs debug information drawing on all actors.
		 * @param gizmo
		 */
		public void debug(ShapeRenderer gizmo) {
			for(Actor actor : actors) {
				actor.debug(gizmo);
			}
		}
		
		/**
		 * Dispose layer by disposing all assigned actors.
		 * @see Disposable#dispose() 
		 */
		@Override
		public void dispose() {
			for(Actor actor : actors) {
				actor.dispose();
			}
			
			// clear actors set
			actors.clear();
		}
		
		/**
		 * @see Iterable#iterator() 
		 */
		@Override
		public Iterator<Actor> iterator() {
			return this.actors.iterator();
		}
	}
	
	/**
	 * Generic scene layers.
	 */
	public final Layer
		BACKGROUND,
		ACTION_1,
		ACTION_2, 
		ACTION_3,
		FOREGROUND,
		GUI,
		DEBUG;
	
	/**
	 * Scene layers.
	 */
	public final Array<Layer> layers = new Array<>();
	
	/**
	 * Sprite batching instance.
	 */
	public final SpriteBatch batch = new SpriteBatch();
	
	/**
	 * Shape renderer for gizmos.
	 */
	public final ShapeRenderer gizmo = new ShapeRenderer();
	
	/**
	 * Scene orthographic camera.
	 */
	public final OrthographicCamera camera = new OrthographicCamera();
	
	/**
	 * Ctor.
	 */
	public Scene() {
		// init orthographic camera
		this.camera.setToOrtho(true);
		this.camera.update();
		
		// create generic layers
		this.BACKGROUND = new Layer(this);
		this.ACTION_1 = new Layer(this);
		this.ACTION_2 = new Layer(this);
		this.ACTION_3 = new Layer(this);
		this.FOREGROUND = new Layer(this);
		this.GUI = new Layer(this);
		this.DEBUG = new Layer(this);
	}
	
	/**
	 * Add actor to the scene.
	 * Performs creation event.
	 * @param layer Layer
	 * @param actor
	 * @return 
	 */
	public Actor add(int layer, Actor actor) {
		return this.add(this.layers.get(layer), actor);
	}
	
	/**
	 * Add actor to the scene layer.
	 * Performs creation event if actor are not already assigned to any layer.
	 * Otherwise just change actor's layer to the new one.
	 * @param layer Targetting layer.
	 * @param actor Actor to add.
	 * @return Actor instance.
	 */
	public Actor add(Layer layer, Actor actor) {
		if(actor == null) {
			throw new NullPointerException("Actor does not exists");
		}
		
		if(actor.getLayer() == null) {
			actor.setLayer(layer);
			actor.create();
		} else {
			actor.setLayer(layer);
		}
		return actor;
	}
	
	/**
	 * Update the whole scene.
	 */
	@Override
	public void perform() {
		// act actors update
		for(Layer layer : this.layers) {
			layer.update(Gdx.graphics.getDeltaTime());
		}
		
		// assign camera projection matrix
		batch.setProjectionMatrix(camera.combined);
		gizmo.setProjectionMatrix(camera.combined);
		
		// draw actors' sprites
		for(Layer layer : this.layers) {
			layer.draw(batch);
		}
		
		// draw scene debug information
		if(Game.DEBUG) {
			for(Layer layer : this.layers) {
				layer.debug(gizmo);
			}
		}
	}
	
	/**
	 * Clear-up the scene with the generic layers create.
	 */
	public void clear() {
		// clear up the layers
		for(Layer layer : this.layers) {
			layer.dispose();
		}
	}

	/**
	 * @see Disposable#dispose() 
	 */
	@Override
	public void dispose() {
		// clearup the scene
		this.clear();
		
		// dispose the scene
		this.gizmo.dispose();
		this.batch.dispose();
	}
}
