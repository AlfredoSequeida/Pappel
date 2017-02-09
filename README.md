Pappel
=======

-Pappel is a beautiful, powerful, simplistic, and user driven open source notetaking platform for the general and poweruser alike. Created by
[AlfredoSequeida](http://alfredosequeida.wix.com/alfredosequeida).

## Table of contents

- [Get Started](#get-started)
- [Documentation](#documentation)
- [Contributing](#contributing)
- [Copyright and license](#copyright-and-license)

## Getting Started

### Dependencies 
Pappel requires the following depenencies to run as intended: 

- Java Runtime Envirorment 1.8.0 or higher for execution
- [PhantonJS](https://github.com/ariya/phantomjs) for pdf exporting options

### Installing Pappel
-Installing Pappel is simple, simply grab the .jar file and run it!
-Or if you preffer, grab the source code and compile it yourself. 

## Documentation

### Markdown
Pappel utilizes the power of markdown in order to allow for fast and easy text formating. This is made possible using Markdown4J, which supports the standard markdown formating as well as extra addon based additions, which can be found [here](https://code.google.com/archive/p/markdown4j/).

If you are not familiar with markdown you can learn [here](http://daringfireball.net/projects/markdown/syntax)
.


### Getting familiar with the user interface 

Pappel is easy to naviage around and only has a few buttons to focus on 
simplicity and productavity. 

The Preview Button
--------------------
In order to conserve space on lower resolution screens, Pappel features a preview button to toggle between markdown and its final result. 

The Export Button 
-------------------
Exporting in Pappel has been made convinient and a click away. Simply select the preview button and slesct one of the available exporting features. **Note**: If you have trouble using one of the exporting features, makes sure that you have succesfully installed all of the dependecies.

The Delete Button
-------------------
Deleting a note is simple! Just slect the delete button and dont worry, you will be asked twice to make sure that is what you really want. 

The Distraction Free Button
----------------------------
Probaby on of Pappel's coolest features, the distraction free button allows you to fully emerge in your content without the distraction of other things im your view. You will be emerged in your content and nothing else. To exit this mode simply either hit the escape key or click on the selection free button again.

The Settings Button
--------------------
In Pappel's settings you will find the following features

- Apperance - Chose on of the available themes to make Pappel your own
- Cloud Integration - Sleep calmer knowing your data is backed up with one of the cloud services options. To Use one, select on the image of the provider and follwo the given instructions.
- General - Find the General settings for Pappel

The Add Buttons
-----------------
Within the colums for your displayed saved notes, you can use the add buttons to wither add a new note or notebook

Creating Content
-----------------
To create your note - simply give your note a tile where the word "Tile" appears and your content where the phrase "Let's create something!" appears - and dont worry about saving your content - Pappel takes care of this for you.

The Action Bar
----------------
Productivity at it's best; the action bar allows for users to focus more on their content and less on what they have to do to achieve this content by giving users the avility to write away without
restraining from their keyboards with the concept of commands. To display the action bar simpy hit the F2 button on your keyboard. You can find Pappel's action bar commands bellow:

usage : command [argumets]
example: web-search: What is Pappel? 

- web-search: - search the web using google's search engine for anything you want
-
- web-address: - go to a web page 
- add-image: - add images to your document (image can include a url or localy hosted file)
- add-note: - add a new note (argument should be the desired note name) 
- add-notebook: - add  anew notebook (argument should be the desired notebook name) 

The Web Action Window 
-----------------------
The web action window interfaces with Pappel to allow easy pinning of content to your note. This window will appear when using its corespoding commands such as 'web-search:' or 'web-address:' To pin content, simply click on the pin button that looks like a paper clip on the top right of the web action window and the content that is currently on your skin will attach to your note as a link.