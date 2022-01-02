# Eevee.apk

I decided to post the source code of the Eevee application on GitHub for fun.
It still has some native C++ functions, which I used to make it a little harder to decompile the application.

This is my first experience with Android programming.

## What it can do

* Work like a calculator. On startup, there are two fields for data entry and data signature. If everything is correct, the server will return the answer. For example:

Data: ```1000, 7```

Signature: ```eOtvkSvIDaqpW83kKHUMNHnuX1Fn/uuq4APjnccYzfc3dojBMHVybeZFU2X1bd0QwEfPirGHlT5ViOQ5I7DiFQ```

Response: ```993```
____

* Generate a device ID based on its fingerprint and android id, store it in the database and send it for verification at the user's request

* Dynamically change the background image and accent colors

* In the last update I also made my chat room! Now you can choose a nickname and chat with others. However, no one needs it :(
