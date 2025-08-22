package com.habilisadi.file.adapter.out.persistence

import com.habilisadi.file.common.application.port.out.BaseRedisRepository
import com.habilisadi.file.infrastructure.config.EnabledRedisRepositories
import com.habilisadi.file.infrastructure.config.InterfaceIncludingScanner
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.type.AnnotationMetadata
import org.springframework.core.type.filter.AssignableTypeFilter

class RedisRepositoryRegistrar : ImportBeanDefinitionRegistrar {

    override fun registerBeanDefinitions(
        importingClassMetadata: AnnotationMetadata,
        registry: BeanDefinitionRegistry
    ) {
        val attributes = importingClassMetadata.getAnnotationAttributes(EnabledRedisRepositories::class.java.name)
        val basePackages = (attributes?.get("basePackages") as? Array<*>)?.map { it.toString() }
            ?: listOf("com.habilisadi")

        val scanner = InterfaceIncludingScanner()
        scanner.addIncludeFilter(AssignableTypeFilter(BaseRedisRepository::class.java))

        scanner.addExcludeFilter { metadataReader, _ ->
            val className = metadataReader.classMetadata.className
            className.endsWith("Impl") || !metadataReader.classMetadata.isInterface
        }

        basePackages.forEach { basePackage ->
            try {
                if (basePackage.contains("*")) {
                    throw IllegalArgumentException("basePackage must not contain *")
                }

                val classLoader = Thread.currentThread().contextClassLoader
                val path = basePackage.replace('.', '/')
                val packageSearchPath = "classpath*:$path/**/*.class"

                val resolver = PathMatchingResourcePatternResolver(classLoader)
                val resources = resolver.getResources(packageSearchPath)

                resources.forEach { resource ->
                    try {
                        val className = resource.url.path
                            .substringAfter("/classes/kotlin/main/")
                            .substringBefore(".class")
                            .replace('/', '.')

                        val clazz = Class.forName(className)

                        if (clazz.isInterface &&
                            BaseRedisRepository::class.java.isAssignableFrom(clazz) &&
                            clazz != BaseRedisRepository::class.java
                        ) registerRedisRepository(registry, clazz)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun registerRedisRepository(registry: BeanDefinitionRegistry, repositoryInterface: Class<*>) {
        val beanDefinition = BeanDefinitionBuilder
            .genericBeanDefinition(RedisRepositoryFactoryBean::class.java)
            .addConstructorArgValue(repositoryInterface)
            .addConstructorArgReference("redisTemplate")
            .addConstructorArgReference("objectMapper")
            .beanDefinition

        val beanName = repositoryInterface.simpleName.replaceFirstChar { it.lowercase() }
        registry.registerBeanDefinition(beanName, beanDefinition)

    }

}