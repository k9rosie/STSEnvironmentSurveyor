package ie.k9ros.stsenvironmentsurveyor.controller

import com.badlogic.gdx.controllers.Controller
import com.badlogic.gdx.controllers.ControllerListener
import com.badlogic.gdx.controllers.Controllers
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Disposable

class WebController : Controller, Disposable {
    var pressedButtons = arrayListOf<Int>()
    private val listeners: com.badlogic.gdx.utils.Array<ControllerListener> = com.badlogic.gdx.utils.Array()
    private var connected = true

    override fun dispose() {
        synchronized(listeners) { listeners.clear() }
        connected = false
    }

    fun notifyListenersButtonUp(button: Int) {
        val managerListeners = Controllers.getListeners()
        synchronized(managerListeners) {
            for (listener in managerListeners) {
                if (listener.buttonUp(this, button)) break
            }
        }
        synchronized(listeners) {
            for (listener in listeners) {
                if (listener.buttonUp(this, button)) break
            }
        }
    }

    fun notifyListenersButtonDown(button: Int) {
        val managerListeners = Controllers.getListeners()
        synchronized(managerListeners) {
            for (listener in managerListeners) {
                if (listener.buttonDown(this, button)) break
            }
        }
        synchronized(listeners) {
            for (listener in listeners) {
                if (listener.buttonDown(this, button)) break
            }
        }
    }

    override fun getButton(keyCode: Int): Boolean =
        if (pressedButtons.contains(keyCode)) {
            pressedButtons.remove(keyCode)
            true
        } else {
            false
        }

    override fun getAxis(p0: Int): Float = 0f

    override fun getPov(p0: Int): PovDirection = PovDirection.center

    override fun getSliderX(p0: Int): Boolean = false

    override fun getSliderY(p0: Int): Boolean = false

    override fun getAccelerometer(p0: Int): Vector3 = Vector3(0f, 0f, 0f)

    override fun setAccelerometerSensitivity(p0: Float) {
    }

    override fun getName(): String = "Xbox One Web Controller"

    override fun addListener(controllerListener: ControllerListener?) {
        synchronized(listeners) { if (!listeners.contains(controllerListener, true)) listeners.add(controllerListener) }
    }

    override fun removeListener(controllerListener: ControllerListener?) {
        synchronized(listeners) { listeners.removeValue(controllerListener, true) }
    }

    // methods from advanced interface that are not supported by most controllers
    fun canVibrate(): Boolean {
        return false
    }

    fun isVibrating(): Boolean {
        return false
    }

    fun startVibration(duration: Int, strength: Float) {}

    fun cancelVibration() {}

    fun supportsPlayerIndex(): Boolean {
        return false
    }

    fun isConnected(): Boolean {
        return connected
    }

    fun getPlayerIndex(): Int {
        return -1
    }

    fun setPlayerIndex(index: Int) {}
}
