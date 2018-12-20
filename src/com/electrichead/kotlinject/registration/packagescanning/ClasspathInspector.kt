package com.electrichead.kotlinject.registration.packagescanning

import java.io.File
import java.io.FilenameFilter
import java.util.ArrayList
import java.util.jar.JarFile
import kotlin.reflect.KClass

class ClasspathInspector {
    fun allKnownClasses(where: (op: Collection<Class<*>>) -> Boolean): ArrayList<Class<*>> {
        val classFiles = ArrayList<Class<*>>()
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

    private fun getClassesFromPath(path: File): Collection<Class<*>> {
        return if (path.isDirectory) {
            getClassesFromDirectory(path)
        } else {
            getClassesFromJarFile(path)
        }
    }

    private fun fromFileToClassName(fileName: String): String {
        return fileName.substring(0, fileName.length - 6).replace("[/\\\\]".toRegex(), "\\.")
    }

    private fun getClassesFromJarFile(path: File): List<Class<*>> {
        val classes = ArrayList<Class<*>>()

        try {
            if (!path.canRead()) {
                return classes
            }

            val jar = JarFile(path)
            val en = jar.entries()
            while (en.hasMoreElements()) {
                val entry = en.nextElement()

                when {
                    entry.name.endsWith("class") -> {
                        val className = fromFileToClassName(entry.name)
                        val clazz = loadClass(className)
                        when {
                            clazz != null -> classes.add(clazz)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to read classes from jar file: $path", e)
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

    private fun getClassesFromDirectory(path: File): List<Class<*>> {
        val classes = ArrayList<Class<*>>()

        // get jar files from top-level directory
        val jarFiles = listFiles(path, FilenameFilter { dir, name -> name.endsWith(".jar") }, false)
        for (file in jarFiles) {
            classes.addAll(getClassesFromJarFile(file))
        }

        val classFiles = listFiles(path, FilenameFilter { dir, name -> name.endsWith(".class") }, true)

        val substringBeginIndex = path.absolutePath.length + 1
        for (classfile in classFiles) {
            var className = classfile.absolutePath.substring(substringBeginIndex)
            className = fromFileToClassName(className)
            try {
                classes.add(Class.forName(className))
            } catch (e: Throwable) {
            }

        }

        return classes
    }

    private fun listFiles(directory: File, filter: FilenameFilter?, recurse: Boolean): List<File> {
        val files = ArrayList<File>()
        val entries = directory.listFiles()

        for (entry in entries!!) {
            if (filter == null || filter!!.accept(directory, entry.name)) {
                files.add(entry)
            }

            if (recurse && entry.isDirectory) {
                files.addAll(listFiles(entry, filter, recurse))
            }
        }

        return files
    }
}