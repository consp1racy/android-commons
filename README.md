# commons-android
Collection of custom utilities for Android development.

If you find this library to your liking know this: It's mainly for use in my own projects. I maintain no changelog and anything can change at any time.

## Commons

### commons [ ![Download](https://api.bintray.com/packages/consp1racy/maven/net.xpece.android%3Acommons/images/download.svg) ](https://bintray.com/consp1racy/maven/net.xpece.android%3Acommons/_latestVersion)

* A bloody mess of classes I used over time in development.

### commons-dimen [ ![Download](https://api.bintray.com/packages/consp1racy/maven/net.xpece.android%3Acommons-dimen/images/download.svg) ](https://bintray.com/consp1racy/maven/net.xpece.android%3Acommons-dimen/_latestVersion)

* Handy `Dimen` class for encapsulating resolved dimensions,
* Kotlin extension functions for obtaining `Dimen` objects.

### commons-dimen-lazy [ ![Download](https://api.bintray.com/packages/consp1racy/maven/net.xpece.android%3Acommons-dimen/images/download.svg) ](https://bintray.com/consp1racy/maven/net.xpece.android%3Acommons-dimen-lazy/_latestVersion)

* `LazyDimen` class for encapsulating dimensions for lazy resolution,
* Functions for obtaining `LazyDimen` objects.

### commons-resources [ ![Download](https://api.bintray.com/packages/consp1racy/maven/net.xpece.android%3Acommons-resources/images/download.svg) ](https://bintray.com/consp1racy/maven/net.xpece.android%3Acommons-resources/_latestVersion)

* Kotlin extension methods 
  * for resolving theme attributes,
  * for accessing appcompat resources easier.

### commons-services [ ![Download](https://api.bintray.com/packages/consp1racy/maven/net.xpece.android%3Acommons-services/images/download.svg) ](https://bintray.com/consp1racy/maven/net.xpece.android%3Acommons-services/_latestVersion)

* Kotlin extension properties for accessing system services.
  * Each service/property has been carefully examined for nullability.

## Extras

* Collection of widgets with features missing from the framework or support libraries.

### appcompat-extra [ ![Download](https://api.bintray.com/packages/consp1racy/maven/net.xpece.android%3Aappcompat-extra/images/download.svg) ](https://bintray.com/consp1racy/maven/net.xpece.android%3Aappcompat-extra/_latestVersion)

* Proper compound drawable support with tint on all platforms for following widgets:
  * `TextView`, `EditText`, `Button`, `CheckedTextView`
* Following widgets now respect `android:enabled` XML attribute:
  * `ImageView`, `ImageButton`

| Extras version     | AppCompat version | Features                               |
| ------------------ | ----------------- | -------------------------------------- |
| 1.2.1              | [25.4.0, 27.0.0)  |                                        |

### design-extra [ ![Download](https://api.bintray.com/packages/consp1racy/maven/net.xpece.android%3Adesign-extra/images/download.svg) ](https://bintray.com/consp1racy/maven/net.xpece.android%3Adesign-extra/_latestVersion)

* Proper compound drawable support with tint on all platforms for following widgets:
  * `TextInputEditText`
* Following widgets now respect `android:enabled` XML attribute:
  * `FloatingActionButton`

| Extras version     | AppCompat version | Features                               |
| ------------------ | ----------------- | -------------------------------------- |
| 1.2.1              | [25.4.0, 27.0.0)  |                                        |

## Widgets

* Complex widgets that backport or enhance their framework counterparts.

### widget-cardbutton [ ![Download](https://api.bintray.com/packages/consp1racy/maven/net.xpece.android%3Awidget-cardbutton/images/download.svg) ](https://bintray.com/consp1racy/maven/net.xpece.android%3Awidget-cardbutton/_latestVersion)

* `Button` with shadow all the way to <s>Gingerbread</s> ICS.
* Selector can be drawn in front of or behind text.
* Fake ripple (fading solid color) all the way to ICS.
* Background can be any drawable, color or gradient.

| CardButton version     | AppCompat version | Features                                            |
| ---------------------- | ----------------- | --------------------------------------------------- |
| 25.0.0-10              | [25.0.0, 25.4.0)  | Solid background, border, foreground ripple, shadow |
| 25.0.0-11 *Deprecated* | [25.0.0, 25.4.0)  | Custom background support                           |
| 25.4.0-1               | [25.4.0]          | Solid background, border, foreground ripple, shadow |
| 25.4.0-2 *Deprecated*  | [25.4.0]          | Custom background support                           |
| 26.0.0-1               | [26.0.0, )        | Solid background, border, foreground ripple, shadow |
| 26.0.0-2               | [26.0.0, )        | Custom background support                           |
| 26.0.0-4               | [26.0.0, )        | Optionally draw selector behind text and drawables  |
| 26.0.0-5               | [26.0.0, )        | Fixed compound drawable layout preview, maybe       |
| 26.0.0-6               | [26.0.0, )        | Fixed API26+ focus highlight when not in touch mode |

## Get it!

    repositories {
        maven { url 'https://dl.bintray.com/consp1racy/maven/' }
    }
        
    dependencies {
        compile 'net.xpece.android:commons:x.x.x'
        
        compile 'net.xpece.android:commons-dimen:x.x.x'
        compile 'net.xpece.android:commons-dimen-lazy:x.x.x'
        compile 'net.xpece.android:commons-resources:x.x.x'
        compile 'net.xpece.android:commons-services:x.x.x'
        
        compile 'net.xpece.android:appcompat-extra:x.x.x'
        compile 'net.xpece.android:design-extra:x.x.x'
        
        compile 'net.xpece.android:widget-cardbutton:x.x.x'
    }
