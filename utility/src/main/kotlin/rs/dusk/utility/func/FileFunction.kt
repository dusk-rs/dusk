package rs.dusk.utility.func

import io.github.classgraph.ClassGraph
import java.io.File
import java.io.FileNotFoundException

class FileFunction {

    companion object {

        inline fun <reified T> getChildClassesOf(): MutableList<T> {
            val kClass = T::class
            val name = kClass.qualifiedName
            val result = ClassGraph().enableClassInfo().blacklistClasses(name).scan()
            val classes = mutableListOf<T>()
            result.use { result ->
                val subclasses = result.getSubclasses(name)
                subclasses.forEach {
                    val clazz = result.loadClass(it.name, true).newInstance() as T
                    classes.add(clazz)
                }
            }
            return classes
        }

        fun find(path: String): File {
            val file = File(path)
            if (!file.exists()) {
                throw FileNotFoundException("Unable to find that file! [file=$file]")
            }
            return file
        }

    }
}