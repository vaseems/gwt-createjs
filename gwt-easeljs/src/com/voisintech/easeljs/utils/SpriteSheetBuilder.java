package com.voisintech.easeljs.utils;

import com.voisintech.easeljs.display.DisplayObject;
import com.voisintech.easeljs.display.SpriteSheet;
import com.voisintech.easeljs.geom.Rectangle;

/*
 * SpriteSheetBuilder
 * Visit http://createjs.com/ for documentation, updates and examples.
 *
 * Copyright (c) 2010 gskinner.com, inc.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
public class SpriteSheetBuilder {
	private String ERR_DIMENSIONS = "frame dimensions exceed max spritesheet dimensions";
	private String ERR_RUNNING = "a build is already running";

	private SpriteSheetBuilder() {

	}

	/**
	 * The SpriteSheetBuilder allows you to generate sprite sheets at run time
	 * from any display object. This can allow you to maintain your assets as
	 * vector graphics (for low file size), and render them at run time as
	 * sprite sheets for better performance.
	 * 
	 * Sprite sheets can be built either synchronously, or asynchronously, so
	 * that large sprite sheets can be generated without locking the UI.
	 * 
	 * Note that the "images" used in the generated sprite sheet are actually
	 * canvas elements, and that they will be sized to the nearest power of 2 up
	 * to the value of <code>maxWidth</code> or <code>maxHeight</code>.
	 * 
	 * @class SpriteSheetBuilder
	 * @extends EventDispatcher
	 * @constructor
	 **/
	public static native SpriteSheetBuilder getBuilder()/*-{
		return new createjs.SpriteSheetBuilder();
	}-*/;

	/**
	 * The maximum width for the images (not individual frames) in the generated
	 * sprite sheet. It is recommended to use a power of 2 for this value (ex.
	 * 1024, 2048, 4096). If the frames cannot all fit within the max
	 * dimensions, then additional images will be created as needed.
	 * 
	 * @property maxWidth
	 * @type Number
	 * @default 2048
	 */
	public static int maxWidth = 2048;

	/**
	 * The maximum height for the images (not individual frames) in the
	 * generated sprite sheet. It is recommended to use a power of 2 for this
	 * value (ex. 1024, 2048, 4096). If the frames cannot all fit within the max
	 * dimensions, then additional images will be created as needed.
	 * 
	 * @property maxHeight
	 * @type Number
	 * @default 2048
	 **/
	public static int maxHeight = 2048;

	/**
	 * The sprite sheet that was generated. This will be null before a build is
	 * completed successfully.
	 * 
	 * @property spriteSheet
	 * @type SpriteSheet
	 **/
	public static SpriteSheet spriteSheet = null;

	/**
	 * The scale to apply when drawing all frames to the sprite sheet. This is
	 * multiplied against any scale specified in the addFrame call. This can be
	 * used, for example, to generate a sprite sheet at run time that is
	 * tailored to the a specific device resolution (ex. tablet vs mobile).
	 * 
	 * @property scale
	 * @type Number
	 * @default 1
	 **/
	public static int scale = 1;

	/**
	 * The padding to use between frames. This is helpful to preserve
	 * antialiasing on drawn vector content.
	 * 
	 * @property padding
	 * @type Number
	 * @default 1
	 **/
	public static int padding = 1;

	/**
	 * A number from 0.01 to 0.99 that indicates what percentage of time the
	 * builder can use. This can be thought of as the number of seconds per
	 * second the builder will use. For example, with a timeSlice value of 0.3,
	 * the builder will run 20 times per second, using approximately 15ms per
	 * build (30% of available time, or 0.3s per second). Defaults to 0.3.
	 * 
	 * @property timeSlice
	 * @type Number
	 * @default 0.3
	 **/
	public static double timeSlice = 0.3;

	/**
	 * A value between 0 and 1 that indicates the progress of a build, or -1 if
	 * a build has not been initiated.
	 * 
	 * @property progress
	 * @type Number
	 * @default -1
	 * @readonly
	 **/
	public static int progress = -1;

	/**
	 * Adds a frame to the {{#crossLink "SpriteSheet"}}{{/crossLink}}. Note that
	 * the frame will not be drawn until you call {{#crossLink
	 * "SpriteSheetBuilder/build"}}{{/crossLink}} method. The optional setup
	 * params allow you to have a function run immediately before the draw
	 * occurs. For example, this allows you to add a single source multiple
	 * times, but manipulate it or its children to change it to generate
	 * different frames.
	 * 
	 * Note that the source's transformations (x, y, scale, rotate, alpha) will
	 * be ignored, except for regX/Y. To apply transforms to a source object and
	 * have them captured in the sprite sheet, simply place it into a
	 * {{#crossLink "Container"}}{{/crossLink}} and pass in the Container as the
	 * source.
	 * 
	 * @method addFrame
	 * @param {DisplayObject} source The source {{#crossLink
	 *        "DisplayObject"}}{{/crossLink}} to draw as the frame.
	 * @param {Rectangle} [sourceRect] A {{#crossLink
	 *        "Rectangle"}}{{/crossLink}} defining the portion of the source to
	 *        draw to the frame. If not specified, it will look for a
	 *        <code>getBounds</code> method, bounds property, or
	 *        <code>nominalBounds</code> property on the source to use. If one
	 *        is not found, the frame will be skipped.
	 * @param {Number} [scale=1] Optional. The scale to draw this frame at.
	 *        Default is 1.
	 * @param {Function} [setupFunction] Optional. A function to call
	 *        immediately before drawing this frame.
	 * @param {Array} [setupParams] Parameters to pass to the setup function.
	 * @param {Object} [setupScope] The scope to call the setupFunction in.
	 * @return {Number} The index of the frame that was just added, or null if a
	 *         sourceRect could not be determined.
	 **/
	public static native int addFrame(DisplayObject source,
			Rectangle sourceRect, int scale, Object setupFunction,
			Object[] setupParams, Object setupScope)/*-{
		return p.addFrame(source, sourceRect, scale, setupFunction, setupParams, setupScope);
	}-*/;
}
