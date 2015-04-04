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
package vault.clockwork.screens;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Gdx.gl;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import vault.clockwork.Game;
import vault.clockwork.transitions.FadeTransition;

/**
 * Loading process screen.
 * Screen that performs async assets loading.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class LoaderScreen implements Screen {
    /**
     * 2D ortho projection.
     */
    private Matrix4 ortho2D;
    
    /**
     * The general renderers.
     */
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    
    /**
     * Fade-in transition.
     */
    private FadeTransition fadeIn;
    
    /**
     * Single machine texture.
     */
    private Texture machineTexture;
    
    /**
     * Gears texture regions.
     */
    private TextureRegion[] gears;
    
    /**
     * Tick frame.
     */
    private float frame = 0.f, fade = 0.f;
    
    /**
     * Next game screen after load finish.
     */
    public final GameScreen next;
    
    /**
     * Clear global assets when loader shown.
     */
    public boolean clearAssets;
    
    /**
     * Loader screen constructor.
     * @param next Next game screen.
     */
    public LoaderScreen(GameScreen next) {
        this(next, true);
    }
    
    /**
     * Loader screen constructor.
     * @param next Next game screen.
     * @param clearAssets Perform assets clear.
     */
    public LoaderScreen(GameScreen next, boolean clearAssets) {
        this.next = next;
        this.clearAssets = clearAssets;
    }
    
    /**
     * Performed on screen change at this one.
     * Load loader resources for single session.
     */
    @Override
    public void show() {
        // create oetho projection
        this.ortho2D = new Matrix4().setToOrtho2D(0.f, 0.f, 400.f, 300.f);
        
        // create the general renderers
        this.spriteBatch = new SpriteBatch();
        this.spriteBatch.setProjectionMatrix(ortho2D);
        
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setProjectionMatrix(ortho2D);
        
        // create fade-in transition
        this.fadeIn = new FadeTransition(FadeTransition.FadeType.FADE_IN, 0.4f, false);
        this.fadeIn.delay = .5f;

        // load texture from file
        this.machineTexture = new Texture(Gdx.files.internal("assets/machine.png"));
        
        // create sprite texture regions
        this.gears = new TextureRegion[]{
            new TextureRegion(machineTexture,  0, 0, 41, 41),
            new TextureRegion(machineTexture, 41, 0, 27, 27),
        };
        
        // clear game assets
        if(this.clearAssets) {
            Game.assets.clear();
        }
        
        // prepare next screen to load
        if(this.next != null) {
            this.next.prepare();
        }
    }

    /**
     * Frame update.
     * Update asset manager and render some loader screen stuff.
     * @param delta 
     */
    @Override
    public void render(float delta) {
        // clear target buffer
        gl.glClearColor(0.f, 0.f, 0.f, 1.f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
        // calculate screen center offset
        float centerx = (float)(200 - machineTexture.getWidth()/2);
        
        // draw machine sprite
        spriteBatch.begin();
        spriteBatch.draw(gears[0], centerx, 64.f, 20.5f, 20.5f, 41.f, 41.f, 1.f, 1.f, frame*100.f);
        spriteBatch.draw(gears[1], centerx+28.f, 48.f, 13.5f, 13.5f, 27.f, 27.f, 1.f, 1.f, -frame*100.f);
        spriteBatch.end();
        
        // draw fade out on load finish
        if(Game.assets.update()) {
            if(!this.fadeIn.update(delta)) {
                this.fadeIn.start();
            }
            this.fadeIn.render();
            
            // skip to the next screen
            if(this.fadeIn.isDone()) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(this.next);
            }
        }
        
        // tick the frame
        frame += delta*Math.max(0.f, 1.f - fadeIn.current()*1.5f);
    }

    /**
     * Handle window resizing.
     * Performed when window client resized.
     * @param width
     * @param height 
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * Handle pause event.
     * Performed on window focus lost.
     */
    @Override
    public void pause() {
    }

    /**
     * Handle resume event.
     * Performed on window focus gain.
     */
    @Override
    public void resume() {
    }

    /**
     * Performed when screen were changed.
     * Dispose all resources of the loader.
     */
    @Override
    public void hide() {
        this.dispose();
    }

    /**
     * Dispsoe resources.
     * Release all unused resources to bypass the memoryleaks.
     */
    @Override
    public void dispose() {
        machineTexture.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
        fadeIn.dispose();
    }
}
