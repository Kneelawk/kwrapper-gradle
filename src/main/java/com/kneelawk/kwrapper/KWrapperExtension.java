package com.kneelawk.kwrapper;

import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.provider.Property;

public class KWrapperExtension {
	private Property<String> launcherMain;
	private final ConfigurableFileCollection files;

	private Property<String> launcherSourceSet;
	private Property<String> applicationDir;
	private Property<String> librariesDir;
	private Property<String> nativesDir;

	private final ConfigurableFileCollection launcherSource;
	private final ConfigurableFileCollection natives;

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

	public Property<String> getLauncherMain() {
		return launcherMain;
	}

	public void setLauncherMain(Property<String> launcherMain) {
		this.launcherMain = launcherMain;
	}

	public void setLauncherMain(String launcherMain) {
		this.launcherMain.set(launcherMain);
	}

	public void launcherMain(Property<String> launcherMain) {
		this.launcherMain = launcherMain;
	}

	public void launcherMain(String launcherMain) {
		this.launcherMain.set(launcherMain);
	}

	public Property<String> getLauncherSourceSet() {
		return launcherSourceSet;
	}

	public void setLauncherSourceSet(Property<String> launcherSourceSet) {
		this.launcherSourceSet = launcherSourceSet;
	}

	public void setLauncherSourceSet(String launcherSourceSet) {
		this.launcherSourceSet.set(launcherSourceSet);
	}

	public void launcherSourceSet(Property<String> launcherSourceSet) {
		this.launcherSourceSet = launcherSourceSet;
	}

	public void auncherSourceSet(String launcherSourceSet) {
		this.launcherSourceSet.set(launcherSourceSet);
	}

	public Property<String> getApplicationDir() {
		return applicationDir;
	}

	public void setApplicationDir(Property<String> applicationDir) {
		this.applicationDir = applicationDir;
	}

	public void setApplicationDir(String applicationDir) {
		this.applicationDir.set(applicationDir);
	}

	public void applicationDir(Property<String> applicationDir) {
		this.applicationDir = applicationDir;
	}

	public void applicationDir(String applicationDir) {
		this.applicationDir.set(applicationDir);
	}

	public Property<String> getLibrariesDir() {
		return librariesDir;
	}

	public void setLibrariesDir(Property<String> librariesDir) {
		this.librariesDir = librariesDir;
	}

	public void setLibrariesDir(String librariesDir) {
		this.librariesDir.set(librariesDir);
	}

	public void librariesDir(Property<String> librariesDir) {
		this.librariesDir = librariesDir;
	}

	public void librariesDir(String librariesDir) {
		this.librariesDir.set(librariesDir);
	}

	public Property<String> getNativesDir() {
		return nativesDir;
	}

	public void setNativesDir(Property<String> nativesDir) {
		this.nativesDir = nativesDir;
	}

	public void setNativesDir(String nativesDir) {
		this.nativesDir.set(nativesDir);
	}

	public void nativesDir(Property<String> nativesDir) {
		this.nativesDir = nativesDir;
	}

	public void nativesDir(String nativesDir) {
		this.nativesDir.set(nativesDir);
	}

	public ConfigurableFileCollection getFiles() {
		return files;
	}

	public ConfigurableFileCollection getLauncherSource() {
		return launcherSource;
	}

	public ConfigurableFileCollection getNatives() {
		return natives;
	}

	public void include(Object... objs) {
		files.from(objs);
	}
}
