# IntelliJ Plugin for Android Parcelable boilerplate code generation

This tool generates Android [Parcelable](https://developer.android.com/reference/android/os/Parcelable.html) implementation based on fields in the class.

## Installation

Project was built using Java 7 compiler so ensure that you are running your IntelliJ using Java 7 to avoid problems.
Download ParcelableGenerator.jar, go to IntelliJ *Preferences* -> *Plugins* -> *Install plugin from disk...*.

## Usage

Just press alt+insert in editor and select Generate Parcelable. It allows you to select
fields, which will be serialized.

![Screenshot](screenshot.png)

## Supported parcelable types

* primitive types - ```long, int, float, double, boolean, byte, String```
* primitive type wrappers (written with ```Parcel.writeValue(Object)``` - ```Integer, Long, Float, Double, Boolean, Byte```
* primitive type arrays ```boolean[], byte[], char[], double[],  float[], int[], long[]```
* std java serializable types: ```Date, BigDecimal```
* list type of any object (**Warning: validation is not performed**)

## TODO:

* support for Parcelable fields parcelation
* validation of list arguments
* display warning about not serialized fields
* map and bundle support
* sparse array support
* Active Objects support (Binders and stuff)

## License

    Copyright (C) 2013 Michał Charmas (http://blog.charmas.pl)

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	     http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
