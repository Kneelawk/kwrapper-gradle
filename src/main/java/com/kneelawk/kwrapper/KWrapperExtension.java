package com.kneelawk.kwrapper;

import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.provider.Property;

public class KWrapperExtension {
	final Property<String> launcherMain;
	final ConfigurableFileCollection files;

	final Property<String> launcherSourceSet;
	final Property<String> applicationDir;
	final Property<String> librariesDir;
	final Property<String> nativesDir;

	final ConfigurableFileCollection launcherSource;
	final ConfigurableFileCollection natives;

	public KWrapperExtension(Project project) {
		launcherMain = project.getObjects().property(String.class);
		launcherMain.set("Launcher");
		files = project.files();

		launcherSourceSet = project.getObjects().property(String.class);
		launcherSourceSet.set("launcher");
		applicationDir = project.getObjects().property(String.class);
		applicationDir.set("app");
		librariesDir = project.getObjects().property(String.class);
		librariesDir.set("libs");
		nativesDir = project.getObjects().property(String.class);
		nativesDir.set("natives");

		launcherSource = project.files(launcherSourceSet.map((s) -> "src/" + s + "/java"), "CPControl/src/main/java");
		natives = project.files("natives");
	}

	public void include(Object... objs) {
		files.from(objs);
	}
}
