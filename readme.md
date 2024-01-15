# BetterPlace

BetterPlace is a Spigot plugin that aims to ~~create a better place lol~~ enhance the building experience in minecraft by adding new placement behaviors to blocks. Alleviating some of the hassle that come with repeated placing of blocks in akward orientations. Sometimes blocks are just a hassle to place (looking at you glazed terracotta). 

While a _chisel item_ is all fine and dandy, I felt there could be further improvements made to building that alleviate some pain. Mainly repetitive task of getting into position or placing and mining support blocks for other blocks.
The goal of BetterPlace is not to reimplement functions of other plugins like a chisel, but to provide new features that integrate well with vanilla. Feature should always be simple, easily discoverable and understandable by anyone, intuitive, and most importantly not in the way of someone that does not want them or know how they work yet. 

## Features

Currently only one feature
- **Rotation Lock:** Inspired by Quark. Shift+Attack with a block that can be rotated to lock block placement into the direction you are facing.

## Installation

1. Download the latest release from the [Releases](https://github.com/lerokko/BetterPlace/releases) page.
2. Place the JAR file into the `plugins` folder of your server.
3. Restart your Spigot/Paper server.
4. Give players you want to access the feature the necessary [permissions](#Permissions)

## Configuration

Currently, there are the following options to customize BetterPlace:

- **blacklist:** You can exclude blocks from the plugins features here. By default, all blocks are included that could be placed in an invalid state. These blocks are not supported by BetterPlace, but you still have the option to enable them anyway

Edit the `config.yml` file to modify these options.

## Commands

- `/bpunlock` - If you have a rotation lock enabled you can remove it with this command. Rotation locks are also disabled by deselecting the current item or punching (with sneaking) but this is just an extra fallback.

## Permissions

- `bplace.place.rotated` - Required to use the rotation/placement lock feature.

## Support and Issues

If you encounter any issues or have questions, please [create an issue](https://github.com/lerokko/BetterPlace/issues) on the GitHub repository.

## Planned features

No, ETA. For now, I do not intend to add some sort of wrench as there are other plugins that supplement this quite well. At least at the moment the scope of this plugin is intended to fill gaps I have not seen in any other plugins.

- HI `Reload command` - duh
- HI `Inversion place` - Inspired by Create. Sneaking would invert the direction a block (with rotation) is placed. So a piston or observer would be facing away from you.
- HI `Configurable Text` - Add the action bar text as strings to the config.yml
- LO `Relative rotation` - Rotation lock is not aligned to the minecraft world (north, south, etc) but block place turned relative to the player (i.e. "always sideways"). 
- HI `Blacklist inversion` - Option to turn the blacklist into a whitelist.
- LO `Spereate blacklists for each feature` - The option to have an extra file for each feature that (if not empty) can be used to disabled blocks only for a specific feature.
- LO `Quick copy blockstate` - The option to copy a blocks blockstate and apply it to all block you are placing of the same itemstack. With config options for which attributes are allowed to be copied. May require a special item in the off-hand. This is sort of inspired by litematica's easy-place a creative modes CTLR-pick block.  
- HI `In-place preview for glazed teracotta` - Using the recently introduced display entities to give a preview in-world where and how a terracotta block would place. Compatible with rotation lock and inversion placement. Would also include a whitelist, so you can customize which blocks it works on.

## Contributing

Feel free to contribute to the development of BetterPlace. This is my first plugin and biggest coding project so far, so there is a lot of code that can be (should be) improved. Things are nested inefficiently and some parts should be in their own classes. I know, I know. If you want feel free to improve the code and add planned features or [request new ones](https://github.com/lerokko/BetterPlace/issues).

## License

BetterPlace is licensed under the [MIT License](LICENSE).
