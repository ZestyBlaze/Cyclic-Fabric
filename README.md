# Cyclic-Fabric
A Slow Port of Cyclic, taking chunks at a time

# Differences from the Forge Version
- The Horse Carrots no longer use `EntityInteract` to trigger their effects, they now use `Item.interactLivingEntity`
- The Laser Item now uses built in Item methods to handle energy drain to reduce the need for packets being sent
- Tidied up various pieces of code
