#JScribble [![](https://jitpack.io/v/AlbinEriksson/JScribble.svg)](https://jitpack.io/#AlbinEriksson/JScribble)
JScribble is a simple scripting library for Java.
##Main features
All you need to do to get started is to code a script handler, which you can fill with your own functions. Then, with a script executor, load a script file and handler and let the script access your functions.
As this is a new project, more features will be implemented later.
##Getting started
You have to first setup either Maven or Gradle for your project.
Take a look at the example code (src/main/java/examples) so you know how to start coding.
###Maven setup:
Add the jitpack.io repository to your pom.xml file:
```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```
Then, add the JScribble dependency:
```xml
<dependencies>
	<dependency>
		<groupId>com.github.AlbinEriksson</groupId>
		<artifactId>JScribble</artifactId>
		<version>1.0</version>
	</dependency>
</dependencies>
```
###Gradle setup:
Add the jitpack.io repository to your root build.gradle file:
```gradle
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}
```
Then, add the JScribble dependency:
```gradle
dependencies {
	compile 'com.github.AlbinEriksson:JScribble:1.0'
}
```
###Development versions
The version number you see above is the latest release version, but you can also replace it with the short commit hash, if you want development versions.
##Contact
Any form of contribution or feedback is very appreciated. This project was created by myself only, so my code is not perfect, and I want it to improve.
If you have any questions, send them by e-mail (my address is on my profile) and I can reply and also add it here.