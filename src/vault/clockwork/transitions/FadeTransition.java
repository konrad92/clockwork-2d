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
package vault.clockwork.transitions;

import static com.badlogic.gdx.Gdx.gl;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

/**
 * Basic fade out transition.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class FadeTransition implements Transition {
    /**
     * Fade transition types.
     */
    public enum FadeType {
        FADE_OUT,
        FADE_IN
    };
    
    /**
     * The fade type to perform.
     */
    private final FadeType fadeType;
    
    /**
     * Transition started flag.
     */
    private boolean started;
    
    /**
     * Processing ticks.
     * Float-determined transition ticks.
     */
    private float tick;
    
    /**
     * Time of the transition in secconds.
     */
    private final float performTime;
    
    /**
     * The shape renderer for the transition.
     */
    private ShapeRenderer renderer;
    
    /**
     * Full-screen projection matrix.
     */
    private final Matrix4 screenMatrix = new Matrix4();
    
    /**
     * isDone delay ticks.
     */
    public float delay = 0.f;
    
    /**
     * Fade color to perform.
     */
    public Color fadeColor = Color.BLACK;
    
    /**
     * Transition constructor.
     * @param fade Fade type to perform on this transition.
     * @param time The time of the transition in secconds.
     * @param startnow Start from the creation.
     */
    public FadeTransition(FadeType fade, float time, boolean startnow) {
        this.fadeType = fade;
        this.performTime = time;
        
        if(startnow) {
            this.start();
        }
    }
    
    /**
     * Start transition.
     * Also can be used to restart the transition.
     */
    @Override
    public void start() {
        this.started = true;
        this.tick = 0.f;
        
        // recreate the shape renderer
        if(this.renderer != null) {
            this.renderer.dispose();
        }
        
        this.renderer = new ShapeRenderer();
    }
    
    /**
     * Update transition fading.
     * @param delta Frames delta time for update ticks perform.
     * @return If the transition is runned, returns <b>TRUE</b>.
     */
    @Override
    public boolean update(float delta) {
        if(!this.started) {
            return false;
        }
        
        tick += delta;
        if(tick >= performTime + delay) {
            tick = performTime + delay;
            this.started = false;
        }
        return true;
    }

    /**
     * Render fade transition.
     * Renders full-screen rectangle with alpha.
     */
    @Override
    public void render() {
        Color fade = fadeColor.cpy();
        
        if(fadeType == FadeType.FADE_IN) {
            fade.a = Math.min(1.f, this.current() * fade.a);
        }
        else {
            fade.a = Math.max(0.f, (1.f - this.current()) * fade.a);
        }
        
        gl.glEnable(GL20.GL_BLEND);
        renderer.setProjectionMatrix(screenMatrix);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(fade);
        renderer.rect(-1.f, -1.f, 2.f, 2.f);
        renderer.end();
        gl.glEnable(GL20.GL_BLEND);
    }
    
    /**
     * Current transition process.
     * @return Transition process status 0.0f - 1.0f
     */
    @Override
    public float current() {
        return Math.min(1.f, Math.max(0.f, this.tick) / this.performTime);
    }

    /**
     * Transition started flag.
     * @return <b>TRUE</b> when transition is already runned.
     */
    @Override
    public boolean isStarted() {
        return this.started;
    }

    /**
     * Determines transition done.
     * @return <b>TRUE</b> when the transition is done.
     */
    @Override
    public boolean isDone() {
        return this.tick >= this.performTime + this.delay;
    }

    /**
     * Dispose all transition resources.
     */
    @Override
    public void dispose() {
        if(this.renderer != null) {
            this.renderer.dispose();
        }
    }
}
