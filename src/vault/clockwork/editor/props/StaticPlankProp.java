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
package vault.clockwork.editor.props;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import vault.clockwork.actors.StaticPlankActor;
import vault.clockwork.editor.PropActor;
import vault.clockwork.editor.PropSerialized;

/**
 * General static plank editor prop.
 * @author Agnieszka Makowska https://github.com/Migemiley
 */
public class StaticPlankProp extends PropSerialized {

	public float width = 30, height = 160;
	
	public float angle = 0;
	/**
	 * Ctor.
	 */
	public StaticPlankProp() {
		this.layer = 2;
	}
	
	/**
	 * @param gizmo 
	 */
	@Override
	public void draw(ShapeRenderer gizmo) {
		Matrix4 transform = new Matrix4();
		transform.translate(position.x, position.y, 0);
		transform.rotate(0, 0, 1, angle);
		
		gizmo.setTransformMatrix(transform);
		gizmo.rect(-width, -height, width*2, height*2);
		gizmo.setTransformMatrix(new Matrix4());
		
		gizmo.setColor(Color.ORANGE);
		gizmo.line(position, position.cpy().add(
			new Vector2(width, height).nor().scl(64.f)
		));
	}

	/**
	 * StaticPlankActor class.
	 * @return 
	 */
	@Override
	public Class<? extends PropActor> getActorClass() {
		return StaticPlankActor.class;
	}
}
