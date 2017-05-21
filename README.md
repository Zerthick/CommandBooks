# CommandBooks
A simple Minecraft Command Book Plugin

## Commands

`/cb` - Displays version information about the CommandBooks Plugin. (Aliases: cb, commandbooks)  
`/cb create [uses]` - Creates a Command Book from the book currently held in your main hand with an optional max number of uses  

## Permissions
`commandbooks.use`  
`commandbooks.command.info`  
`commandbooks.command.create`      

## Creating a Command Book
To create a Command Book simply open a regular Minecraft book and type into it the commands you would like to be executed by the book and sign it.
Commands can be multiple lines, however new commands should start on a new line.
To have a command be executed by the console instead of the player prefix it with `$`.
To use the player's name inside a command use `[p]`.

Example:

    $time set day
    $op [p]
    say I've set the time to day and opped myself!

This book would have the console set the time to day, op the player, and then the player would send a message to the chat.

Finally, to create the command book, hold the book in your main hand and execute `/cb create`.
The title of the book should become encased in square brackets to signify the command book has been created.
When creating the book you can optionally specify a max number of uses Ex: `/cb create 5` to limit the amount of times a book can be used.
Once all the uses are used up, the book will be destroyed.

## Using a Command Book
To use a command book simply hold it in your hand and left-click!

## Support Me
I will **never** charge money for the use of my plugins, however they do require a significant amount of work to maintain and update. If you'd like to show your support and buy me a cup of tea sometime (I don't drink that horrid coffee stuff :P) you can do so [here](https://www.paypal.me/zerthick)
