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

import static com.badlogic.gdx.Gdx.gl;
import com.badlogic.gdx.graphics.GL20;
import vault.clockwork.transitions.FadeTransition;
import vault.clockwork.transitions.Transition;

/**
 *
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class MenuScreen extends GameScreen {
    private Transition fade;
    
    @Override
    public void prepare() {
    }

    @Override
    public void show() {
        // create the fade transition
        this.fade = new FadeTransition(FadeTransition.FadeType.FADE_OUT, .4f, true);
    }

    @Override
    public void render(float delta) {
        gl.glClearColor(0.2f, 0.3f, 0.5f, 1.f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
        if(fade.update(delta)) {
            fade.render();
        }
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        fade.dispose();
    }
}
