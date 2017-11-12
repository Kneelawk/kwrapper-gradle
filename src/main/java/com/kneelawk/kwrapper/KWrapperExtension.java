package com.kneelawk.kwrapper;

import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileCollection;

public class KWrapperExtension {
	String launcherMain;
	final ConfigurableFileCollection files;

	String launcherSourceSet;
	String applicationDir;
	String librariesDir;
	String nativesDir;

	final ConfigurableFileCollection launcherSource;
	final ConfigurableFileCollection natives;

	public KWrapperExtension(Project project) {
		launcherMain = "Launcher";
		files = project.files();

		launcherSourceSet = "launcher";
		applicationDir = "app";
		librariesDir = "libs";
		nativesDir = "natives";

		launcherSource = project.files(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "src/" + launcherSource + "/java";
			}
		}, "CPControl/src/main/java");
		natives = project.files("natives");
	}

	public void include(Object... objs) {
		files.from(objs);
	}
}
