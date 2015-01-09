AnnoCat
=======


Description
-----------

AnnoCat is a java library for categorizing annotations. This is possible by annotating annotations.
For instance one can implement several Verifier annotations (MinMaxVerifier, ColorVerifier, NotNullVerifier, etc. ..) and categorize each one as a Verifier.
AnnoCat gives you a basic approach addressing this issue.

For examples of usage take a look at the UnitTests.

Since this project is still in beta there might be changes in an incompatible way to earlier versions. As soon as there is a release it will remain backward compatible.


Features
--------
 - Provides an easy way to categorize annotations
 - Extensible: you can provide your own CategoryFactory
 - Fast: results are cached
 - Compact: uses no external libraries
