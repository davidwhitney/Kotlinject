package com.electrichead.kotlinject.registration.packagescanning

import java.io.File
import java.io.FilenameFilter
import java.util.*
import java.util.jar.JarFile
import kotlin.reflect.KClass

class ClasspathInspector {
    fun allKnownClasses(where: (op: Collection<KClass<*>>) -> Boolean): List<KClass<*>> {
        val classFiles = mutableListOf<KClass<*>>()
        val classLocations = classLocationsForCurrentClasspath()

        for (file in classLocations) {
            val classes = getClassesFromPath(file)
            if (where(classes)) {
                classFiles.addAll(classes)
            }
        }

        return classFiles
    }

    private fun classLocationsForCurrentClasspath(): List<File> {
        val urls = ArrayList<File>()
        val javaClassPath = System.getProperty("java.class.path")
        if (javaClassPath != null) {
            for (path in javaClassPath.split(File.pathSeparator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                urls.add(File(path))
            }
        }
        return urls
    }

    private fun getClassesFromPath(path: File): Collection<KClass<*>> {
        return if (path.isDirectory) {
            getClassesFromDirectory(path)
        } else {
            getClassesFromJarFile(path)
        }
    }

    private fun fromFileToClassName(fileName: String): String {
        return fileName.substring(0, fileName.length - 6).replace("[/\\\\]".toRegex(), "\\.")
    }

    private fun getClassesFromJarFile(path: File): List<KClass<*>> {
        if (!path.canRead()) {
            return emptyList()
        }

        val classes = mutableListOf<KClass<*>>()
        val jar = JarFile(path)
        for (entry in jar.entries()) {
            if (!entry.name.endsWith("class")) continue
            val className = fromFileToClassName(entry.name)
            val clazz = loadClass(className) ?: continue
            classes.add(clazz.kotlin)
        }

        return classes
    }

    private fun loadClass(className: String): Class<*>? {
        return try {
            Class.forName(className)
        } catch (ex: Throwable) {
            null
        }
    }

    private fun getClassesFromDirectory(path: File): List<KClass<*>> {
        val classes = mutableListOf<KClass<*>>()

        val jarFiles = listFiles(path, FilenameFilter { _, name -> name.endsWith(".jar") }, false)
        for (file in jarFiles) {
            classes.addAll(getClassesFromJarFile(file))
        }

        val classFiles = listFiles(path, FilenameFilter { _, name -> name.endsWith(".class") }, true)

        val substringBeginIndex = path.absolutePath.length + 1
        for (classfile in classFiles) {
            var className = classfile.absolutePath.substring(substringBeginIndex)
            className = fromFileToClassName(className)
            try {
                classes.add(Class.forName(className).kotlin)
            } catch (e: Throwable) {
            }

        }

        return classes
    }

    private fun listFiles(directory: File, filter: FilenameFilter?, recurse: Boolean): List<File> {
        val files = ArrayList<File>()
        val entries = directory.listFiles()

        for (entry in entries!!) {
            if (filter == null || filter.accept(directory, entry.name)) {
                files.add(entry)
            }

            if (recurse && entry.isDirectory) {
                files.addAll(listFiles(entry, filter, recurse))
            }
        }

        return files
    }
}