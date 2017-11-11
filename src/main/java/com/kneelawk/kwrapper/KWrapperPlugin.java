package com.kneelawk.kwrapper;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.bundling.Jar;

import com.google.common.collect.ImmutableMap;

public class KWrapperPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		if (project.getPlugins().hasPlugin(JavaPlugin.class)) {
			KWrapperExtension ext = project.getExtensions().create("kwrapper", KWrapperExtension.class, project);

			project.afterEvaluate((p) -> {
				SourceSet launcher = configureSourceSets(p, ext);
				configureLauncherJarTask(p, ext, launcher);
			});
		} else {
			project.getLogger().warn("The KWrapper Plugin is useless without the Java Plugin");
		}
	}

	public SourceSet configureSourceSets(Project project, KWrapperExtension ext) {
		// create the launcher source set
		SourceSetContainer sourceSets = (SourceSetContainer) project.getProperties().get("sourceSets");
		SourceSet launcher = sourceSets.create(ext.launcherSourceSet.get());

		// add the launcher sources to the launcher source set
		launcher.getJava().add(ext.launcherSource);

		return launcher;
	}

	public void configureLauncherJarTask(Project project, KWrapperExtension ext, SourceSet launcher) {
		// create jar task
		Jar launcherJar = project.getTasks().create("launcherJar", Jar.class);
		launcherJar.dependsOn("jar", ext.launcherSourceSet.get() + "Classes");

		// set the jar's base name
		launcherJar.setBaseName(project.getName() + "-all");

		// set the jar's manifest's Main-Class attribute
		launcherJar.getManifest().attributes(ImmutableMap.of("Main-Class", ext.launcherMain));

		// put the application jar into the app dir
		Jar jar = (Jar) project.getTasks().getByName("jar");
		launcherJar.from(jar.getOutputs().getFiles(), (spec) -> spec.into(ext.applicationDir));

		// put runtime dependencies into the libs dir
		Configuration compile = project.getConfigurations().getByName("runtime");
		launcherJar.from(compile, (spec) -> spec.into(ext.librariesDir));

		// put the natives into the natives dir
		launcherJar.from(ext.natives, (spec) -> spec.into(ext.nativesDir));

		// add launcher classes to the jar
		launcherJar.from(launcher.getOutput());

		// add extra includes to the jar
		launcherJar.from(ext.files);

		// set the task's group and description
		launcherJar.setGroup("Build");
		launcherJar.setDescription("Creates a jar with all dependencies included.");
	}
}
