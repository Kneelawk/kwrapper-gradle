package com.kneelawk.kwrapper;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.bundling.Jar;

import com.google.common.collect.ImmutableMap;

public class KWrapperPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.getPlugins().withType(JavaPlugin.class, (javaPlugin) -> {
			KWrapperExtension ext = project.getExtensions().create("kwrapper", KWrapperExtension.class, project);

			project.afterEvaluate((p) -> {
				SourceSet launcher = configureSourceSets(p, ext);
				configureLauncherJarTask(p, ext, launcher);
			});
		});
	}

	public SourceSet configureSourceSets(Project project, KWrapperExtension ext) {
		// create the launcher source set
		SourceSetContainer sourceSets = (SourceSetContainer) project.getProperties().get("sourceSets");
		SourceSet launcher = sourceSets.create(ext.launcherSourceSet);

		// add the launcher sources to the launcher source set
		launcher.getJava().srcDir(ext.launcherSource);

		return launcher;
	}

	public void configureLauncherJarTask(Project project, KWrapperExtension ext, SourceSet launcher) {
		// create jar task
		Jar launcherJar = project.getTasks().create("launcherJar", Jar.class);
		Jar jar = (Jar) project.getTasks().getByName(JavaPlugin.JAR_TASK_NAME);
		launcherJar.dependsOn(jar, launcher.getClassesTaskName());

		// set the jar's base name
		launcherJar.setBaseName(project.getName() + "-all");

		// set the jar's manifest's Main-Class attribute
		launcherJar.getManifest().attributes(ImmutableMap.of("Main-Class", ext.launcherMain));

		// put the application jar into the app dir
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

		// make sure this task is run as part of the build life cycle
		Task assemble = project.getTasks().getByName("assemble");
		assemble.dependsOn(launcherJar);
	}
}
