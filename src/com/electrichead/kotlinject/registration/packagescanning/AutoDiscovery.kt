package com.electrichead.kotlinject.registration.packagescanning

import com.electrichead.kotlinject.registration.TypeRegistry
import java.io.File
import java.util.*
import kotlin.reflect.KClass

class AutoDiscovery(typeRegistry: TypeRegistry) {
    val registry = typeRegistry

    // Java
    fun fromPackageContaining(iface: java.lang.Class<*>, bindChoice: (op: BindingOperations) -> IBindingStrategy): AutoDiscovery {
        return fromPackageContaining(iface.kotlin, bindChoice)
    }

    inline fun <reified T: Any> fromPackageContaining(noinline bindChoice: (op: BindingOperations) -> IBindingStrategy) : AutoDiscovery {
        return fromPackageContaining(T::class, bindChoice)
    }

    fun fromPackageContaining(t : KClass<*>, bindChoice: (op: BindingOperations) -> IBindingStrategy) : AutoDiscovery {
        val packageName = t.java.`package`.name
        val classes = getClasses(packageName)
        bindChoice(BindingOperations()).bind(registry, classes)
        return this
    }

    fun getClasses(packageName: String): Array<Class<*>> {
        val classLoader = Thread.currentThread().contextClassLoader!!
        val path = packageName.replace('.', '/')
        val resources = classLoader.getResources(path)
        val dirs = ArrayList<File>()
        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            dirs.add(File(resource.file))
        }
        val classes = ArrayList<Class<*>>()
        for (directory in dirs) {
            classes.addAll(findClasses(directory, packageName))
        }
        return classes.toTypedArray()
    }

    private fun findClasses(directory: File, packageName: String): List<Class<*>> {
        val classes = ArrayList<Class<*>>()
        if (!directory.exists()) {
            return classes
        }
        val files = directory.listFiles()
        for (file in files!!) {
            if (file.isDirectory) {
                assert(!file.name.contains("."))
                classes.addAll(findClasses(file, packageName + "." + file.name))
            } else if (file.name.endsWith(".class")) {
                classes.add(Class.forName(packageName + '.'.toString() + file.name.substring(0, file.name.length - 6)))
            }
        }
        return classes
    }
}

