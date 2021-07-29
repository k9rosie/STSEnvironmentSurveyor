package ie.k9ros.stsenvironmentsurveyor.patches

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.desktop.DesktopLauncher
import ie.k9ros.stsenvironmentsurveyor.Surveyor
import javassist.CannotCompileException
import javassist.expr.ExprEditor
import javassist.expr.FieldAccess


@SpirePatch(clz = DesktopLauncher::class, method = "main")
object ChangeWindowTitle {
    @JvmStatic
    fun Instrument(): ExprEditor {
        return object : ExprEditor() {
            @Throws(CannotCompileException::class)
            override fun edit(f: FieldAccess) {
                if (f.isWriter && f.className.equals(LwjglApplicationConfiguration::class.java.name)
                    && f.fieldName.equals("title")
                ) {
                    f.replace("\$proceed(\"${Surveyor.port} \" + $1);")
                }
            }
        }
    }
}