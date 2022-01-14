This guide covers a few technical changes developers will likely want to make when creating their own Pixel Dungeon mod based on PD source code.

## Application name, version name, and package name

There are a number of variables defined in the root [build.gradle](/build.gradle) file that you may want to change:
- `appName` defines the user-visible name of your app. Change this to whatever you wish to call your mod.
- `appPackageName` defines the internal name of your app. The Android operating system uses this name to distinguish your app from others. You must change this from its initial value or Android will think your mod is Speedrun Pixel Dungeon.
- `appVersionCode` defines the internal version number of your app. You want to increment this whenever releasing a new version. Read the next section for more details on this one.
- `appVersionName` defines the user-visible version name of your app. Change this to whatever you like, and increment it whenever you release a new version.

The other variables do not need to be changed when setting up your own mod, they are mainly technical configurations that do not need to be adjusted.

Note that some guides may recommend that you change the package structure (i.e. the folder names) of the application. This used to be required for Pixel Dungeon mods, but is now optional as the `appPackageName` variable can be used instead.

## Application version code

Speedrun Pixel Dungeon has an internal version code which should be incremented with each release. It is defined with the `appVersionCode` variable in the root [build.gradle](/build.gradle) file.

## Credits & Supporter button

If you are making your own mod, you will likely want to add yourself to the credits or change the current supporter link. Feel free to adjust these however you like, the relevant code is in [AboutScene.java](/core/src/main/java/com/quasistellar/speedrunpixeldungeon/scenes/AboutScene.java) and [SupporterScene.java](/core/src/main/java/com/quasistellar/speedrunpixeldungeon/scenes/SupporterScene.java). If you wish to disable the supporter link, simply comment out the line `add(btnSupport);` in [TitleScene.java](/core/src/main/java/com/quasistellar/speedrunpixeldungeon/scenes/TitleScene.java).

Note that due to the GPLv3 license, any edits you make to the credits scene must avoid removing any existing credits, though you can reposition them however you like. Additionally, while not required, I would appreciate leaving in a reference and a link to my Patreon, though you are free to add your own as well.

**Note that if you plan to distribute on Google Play, Google has a history of removing apps which mention Patreon, as they want all revenue earned via Google Play apps to go through them. It is therefore strongly advised that you disable the supporter button or replace the text/links within it if you want to release on Google Play.**

## Update Notification

Speedrun Pixel Dungeon includes a github-based update notification which likely will not be useful for mod developers unless it is modified.

To simply disable the notification change `:services:updates:githubUpdates` to `:services:updates:debugUpdates` for the release configurations in the build.gradle files in the [desktop](/desktop/build.gradle) and [android](/android/build.gradle) modules. The debug updates module does nothing by default and so works just fine in release builds.

To modify the notification to point to your own github releases, go to [GitHubUpdates.java](/services/updates/githubUpdates/src/main/java/com/quasistellar/speedrunpixeldungeon/services/updates/GitHubUpdates.java) and change the line: `httpGet.setUrl("https://api.github.com/repos/QuasiStellar/speedrun-pixel-dungeon/releases");` to match your own username and repository name. The github updater looks for a title, body of text followed by three dashes, and the phrase \` internal version number: # \` in your release.

More advanced modders can change the format for releases if they like, or make entirely new update notification services.

## News Feed

Speedrun Pixel Dungeon includes a news feed which pulls blog posts from [ShatteredPixel.com](http://ShatteredPixel.com). The articles there may not be useful to your mod so you may wish to remove them.

To simply disable news entirely, comment out the line `add(btnNews);` in [TitleScene.java](/core/src/main/java/com/quasistellar/speedrunpixeldungeon/scenes/TitleScene.java).

You can also point the news checker to a different feed by modifying the URLs in [ShatteredNews.java](/services/news/shatteredNews/src/main/java/com/quasistellar/speedrunpixeldungeon/services/news/ShatteredNews.java). Note that the current logic expects an atom feed and is slightly customized to ShatteredPixel.com, but the logic can be modified to work with other xml feed types.

More advanced modders can also write their own news checker services and use those.

## Translations

Speedrun Pixel Dungeon supporters a number of languages which are translated via a [community translation project](https://www.transifex.com/shattered-pixel/shattered-pixel-dungeon/).

If you plan to add new text to the game, maintaining these translations may be difficult or impossible, and so you may wish to remove them:
- In [Languages.java](/core/src/main/java/com/quasistellar/speedrunpixeldungeon/messages/Languages.java) remove all of the enum constants except for ENGLISH.
- In the [messages resource folders](/core/src/main/assets/messages) remove all of the .properties files which include an underscore followed by a language code (e.g. remove actors_ru.properties, but not actors.properties)
- Finally remove the language picker by commenting out the line `add( langsTab );` in [WndSettings.java](/core/src/main/java/com/quasistellar/speedrunpixeldungeon/windows/WndSettings.java)
- Optionally, if you are multilingual or have translators and wish to retain some languages, do not comment out the language picker and only remove the enums/resources for the languages you won't be using.

If you want to have a language other than English as the base language, you can simply remove the .properties files that do not have a language code, and remove the underscore+language code from the language you want to use. The game will consider this language to be English internally however, so you may want to look into where the ENGLISH variable is used and make adjustments accordingly, and possibly rename it.