package com.voisintech.easeljs.utils;

/**
 * The Ticker provides a centralized tick or heartbeat broadcast at a set
 * interval. Listeners can subscribe to the tick event to be notified when a set
 * time interval has elapsed.
 * 
 * Note that the interval that the tick event is called is a target interval,
 * and may be broadcast at a slower interval during times of high CPU load. The
 * Ticker class uses a static interface (ex. <code>Ticker.getPaused()</code>)
 * and should not be instantiated.
 * 
 * <h4>Example</h4> createjs.Ticker.addEventListener("tick", handleTick);
 * function handleTick(event) { // Actions carried out each frame if
 * (!event.paused) { // Actions carried out when the Ticker is not paused. } }
 * 
 * To update a stage every tick, the {{#crossLink "Stage"}}{{/crossLink}}
 * instance can also be used as a listener, as it will automatically update when
 * it receives a tick event:
 * 
 * createjs.Ticker.addEventListener("tick", stage);
 * 
 * @class Ticker
 * @uses EventDispatcher
 * @static
 **/
public class Ticker {

	private static String RAF_SYNCHED = "synched";
	private static String RAF = "raf";
	private static String TIMEOUT = "timeout";
	private static boolean useRAF = false;
	private static String timingMode = null;
	private static int maxDelta = 0;

	private Ticker() {

	}

	/**
	 * Starts the tick. This is called automatically when the first listener is
	 * added.
	 * 
	 * @method init
	 * @static
	 **/
	public static native void init()/*-{
		Ticker.init();
	}-*/;

	/**
	 * Stops the Ticker and removes all listeners. Use init() to restart the
	 * Ticker.
	 * 
	 * @method reset
	 * @static
	 **/
	public static native void reset()/*-{
		Ticker.reset();
	}-*/;

	/**
	 * Sets the target time (in milliseconds) between ticks. Default is 50 (20
	 * FPS).
	 * 
	 * Note actual time between ticks may be more than requested depending on
	 * CPU load.
	 * 
	 * @method setInterval
	 * @static
	 * @param {Number} interval Time in milliseconds between ticks. Default
	 *        value is 50.
	 **/
	public static native void setInterval(int interval)/*-{
		Ticker.setInterval(interval);
	}-*/;

	/**
	 * Returns the current target time between ticks, as set with {{#crossLink
	 * "Ticker/setInterval"}}{{/crossLink}}.
	 * 
	 * @method getInterval
	 * @static
	 * @return {Number} The current target interval in milliseconds between tick
	 *         events.
	 **/
	public static native void getInterval()/*-{
		Ticker.getInterval();
	}-*/;

	/**
	 * Sets the target frame rate in frames per second (FPS). For example, with
	 * an interval of 40, <code>getFPS()</code> will return 25 (1000ms per
	 * second divided by 40 ms per tick = 25fps).
	 * 
	 * @method setFPS
	 * @static
	 * @param {Number} value Target number of ticks broadcast per second.
	 **/
	public static native int setFPS(int value)/*-{
		return Ticker.setFPS(value);
	}-*/;

	/**
	 * Returns the target frame rate in frames per second (FPS). For example,
	 * with an interval of 40, <code>getFPS()</code> will return 25 (1000ms per
	 * second divided by 40 ms per tick = 25fps).
	 * 
	 * @method getFPS
	 * @static
	 * @return {Number} The current target number of frames / ticks broadcast
	 *         per second.
	 **/
	public static native void getFPS()/*-{
		Ticker.getFPS();
	}-*/;

	/**
	 * Returns the average time spent within a tick. This can vary significantly
	 * from the value provided by getMeasuredFPS because it only measures the
	 * time spent within the tick execution stack.
	 * 
	 * Example 1: With a target FPS of 20, getMeasuredFPS() returns 20fps, which
	 * indicates an average of 50ms between the end of one tick and the end of
	 * the next. However, getMeasuredTickTime() returns 15ms. This indicates
	 * that there may be up to 35ms of "idle" time between the end of one tick
	 * and the start of the next.
	 * 
	 * Example 2: With a target FPS of 30, getFPS() returns 10fps, which
	 * indicates an average of 100ms between the end of one tick and the end of
	 * the next. However, getMeasuredTickTime() returns 20ms. This would
	 * indicate that something other than the tick is using ~80ms (another
	 * script, DOM rendering, etc).
	 * 
	 * @method getMeasuredTickTime
	 * @static
	 * @param {Number} [ticks] The number of previous ticks over which to
	 *        measure the average time spent in a tick. Defaults to the number
	 *        of ticks per second. To get only the last tick's time, pass in 1.
	 * @return {Number} The average time spent in a tick in milliseconds.
	 **/
	public static native int getMeasuredTickTime(int ticks)/*-{
		return Ticker.getMeasuredTickTime(ticks);
	}-*/;

	/**
	 * Returns the actual frames / ticks per second.
	 * 
	 * @method getMeasuredFPS
	 * @static
	 * @param {Number} [ticks] The number of previous ticks over which to
	 *        measure the actual frames / ticks per second. Defaults to the
	 *        number of ticks per second.
	 * @return {Number} The actual frames / ticks per second. Depending on
	 *         performance, this may differ from the target frames per second.
	 **/
	public static native int getMeasuredFPS(int ticks)/*-{
		return Ticker.getMeasuredFPS(ticks);
	}-*/;

	/**
	 * Changes the "paused" state of the Ticker, which can be retrieved by the
	 * {{#crossLink "Ticker/getPaused"}}{{/crossLink}} method, and is passed as
	 * the "paused" property of the <code>tick</code> event. When the ticker is
	 * paused, all listeners will still receive a tick event, but the
	 * <code>paused</code> property will be false.
	 * 
	 * Note that in EaselJS v0.5.0 and earlier, "pauseable" listeners would
	 * <strong>not</strong> receive the tick callback when Ticker was paused.
	 * This is no longer the case.
	 * 
	 * <h4>Example</h4> createjs.Ticker.addEventListener("tick", handleTick);
	 * createjs.Ticker.setPaused(true); function handleTick(event) {
	 * console.log("Paused:", event.paused, createjs.Ticker.getPaused()); }
	 * 
	 * @method setPaused
	 * @static
	 * @param {Boolean} value Indicates whether to pause (true) or unpause
	 *        (false) Ticker.
	 **/
	public static native void setPaused(int value)/*-{
		Ticker.setPaused(value);
	}-*/;

	/**
	 * Returns a boolean indicating whether Ticker is currently paused, as set
	 * with {{#crossLink "Ticker/setPaused"}}{{/crossLink}}. When the ticker is
	 * paused, all listeners will still receive a tick event, but this value
	 * will be false.
	 * 
	 * Note that in EaselJS v0.5.0 and earlier, "pauseable" listeners would
	 * <strong>not</strong> receive the tick callback when Ticker was paused.
	 * This is no longer the case.
	 * 
	 * <h4>Example</h4> createjs.Ticker.addEventListener("tick", handleTick);
	 * createjs.Ticker.setPaused(true); function handleTick(event) {
	 * console.log("Paused:", createjs.Ticker.getPaused()); }
	 * 
	 * @method getPaused
	 * @static
	 * @return {Boolean} Whether the Ticker is currently paused.
	 **/
	public static native void getPaused()/*-{
		return Ticker.getPaused();
	}-*/;

}
