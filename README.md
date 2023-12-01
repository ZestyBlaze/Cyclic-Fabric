# Cyclic-Fabric
A Slow Port of Cyclic, taking chunks at a time

# Differences from the Forge Version
- The Horse Carrots no longer use `EntityInteract` to trigger their effects, they now use `Item.interactLivingEntity`
- The Laser Item now uses built in Item methods to handle energy drain to reduce the need for packets being sent
- Tidied up various pieces of code
- Corrected a log message in `DisarmEnchant`
- Moved the `DisarmEnchant` percent level and percent list in the config to be with the rest of the `DisarmEnchant` config options
