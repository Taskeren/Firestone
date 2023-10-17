<div style="text-align: center">
<h1>Firestone</h1>
<h5>Patching things more effective</h5>
<hr>
</div>

## What is Firestone?

Firestone is an application runs scripts that mostly used for patching/modifying
assets or configurations in a modpack.

For example, you hold a server running GT: New Horizons with something you added.
Whenever you upgrade your server, you need to re-do all the things you did before,
which can be very annoying. And Firestone can help you with that. You can write a
script to do all the things you want, and run it with Firestone.

## How to use Firestone?

- Download a distribution pack from [releases](https://github.com/Taskeren/Firestone/releases/latest).
- Extract it to the folder (such as `.minecraft`, it depends on how you describe the paths in your script).
- run command like `./firestone the-script.kts [optional-arguments]`, details listed below.

### Arguments

| Argument                | Description                                                                                                              |
|-------------------------|--------------------------------------------------------------------------------------------------------------------------|
| the-script.kts          | The script file you want to run.<br/>It also support URLs with `url:` prefix, like `url:http://example.com/example.kts`. |
| `--allow-remote-script` | Allow directly run the remote downloaded scripts. Be aware, it can be malicious!                                         |

## Writing scripts

Firestone uses Kotlin as the scripting language. Standard library is fully supported, which you can achieve
almost everything you want.
But for convenience, Firestone Core provides some useful objects and functions for modifying specific files.

### Modifiers

Modifiers are used to modify the text files, including Json, plain text, etc.

You can read source code for more information. (There should be a more detailed documentation in the future.)

<!-- TODO: Add more detailed info -->

## License

Firestone is licensed under the Apache License 2.0. See [LICENSE](LICENSE) for more information.
