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
package vault.clockwork.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import vault.clockwork.Game;
import vault.clockwork.Vault;
import vault.clockwork.screens.MenuScreen;
import vault.clockwork.screens.StageScreen;
import vault.clockwork.system.SceneController;

/**
 *
 * @author Konrad Nowakowski <konrad.x92@gmail.com>
 */
public class MenuController implements SceneController {
	public boolean showMenu = false;
	private Sprite sprMenu;
	private OrthographicCamera camera;
	
	public MenuController() {
		sprMenu = new Sprite(Game.assets.get(Vault.MENU_BACK_TO_MENU, Texture.class));
		
		camera = new OrthographicCamera();
	}
	
	@Override
	public void prePerform() {
	}

	@Override
	public void postPerform() {
	}

	@Override
	public void preUpdate(float delta) {
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			showMenu = !showMenu;
		}
		
		if(showMenu) {
			if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
				Game.app.setNextScreen(new MenuScreen());
				return;
			}
			else if(Gdx.input.isKeyJustPressed(Input.Keys.N)) {
				showMenu = false;
			}
		}
	}

	@Override
	public void postUpdate(float delta) {
	}

	@Override
	public void preDraw(SpriteBatch batch) {
	}

	@Override
	public void postDraw(SpriteBatch batch) {
		if(showMenu) {
			camera.setToOrtho(false);
			camera.update();
			
			Matrix4 oldProj = batch.getProjectionMatrix();
			batch.setProjectionMatrix(camera.combined);
			
			sprMenu.setCenter(camera.viewportWidth/2, camera.viewportHeight/2);
			if(Vault.comicShader.isCompiled()) {
				batch.setShader(Vault.comicShader);
			}
			batch.begin();
			sprMenu.draw(batch);
			batch.end();
			batch.setShader(null);
		}
	}

	@Override
	public void preDebug(ShapeRenderer gizmo) {
	}

	@Override
	public void postDebug(ShapeRenderer gizmo) {
	}

	@Override
	public void dispose() {
	}
}
