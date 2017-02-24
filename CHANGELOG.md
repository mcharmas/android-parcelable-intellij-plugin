# Change Log

##Version 0.7.1

* Replaced `protected` with `private` access modifier for generated constructors with explicit `Parcel` parameter.
* Removed lint warning: `Access can be private`.

##Version 0.7.0

* Added map parcelation
* Parcelation is not generated for fields with transient modifier
* Date parcelation fixes
* Enum parcelation fixes

##Version 0.6.3

* Now generating parcelable also for base cass fields
* Fixed primitive type arrays serialisation
* Added @Override annotation on Creator methods
* Added short support
* Minor bugfixes

##Version 0.6.2

* Fixed list serialization
* Added support for parcelable array serialization
* Added support for string list serization
* Added inheritance support

##Version 0.6

* CREATOR is now final - proguard will not touch it now
* fixed parcelable class loader

##Version 0.2

* added nullable primitives support
* added primitive array support
* added boolean sparse array support
* added list list serialization

##Version 0.1

* working primitive type serialization
* initial release - proof of concept
