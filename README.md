# Starships

## Overview
Starships is an arcade-style space shooter inspired by the classic Asteroids game. Navigate your starship through a dangerous field of asteroids, destroying them with your weapons while avoiding collisions. Features power-ups, multiple weapon systems, and support for multiple players!

## Game Features

### Gameplay
- **Asteroid Destruction**: Shoot and destroy asteroids of various sizes and health levels
- **Ship Controls**: Intuitive movement controls for rotating and accelerating your ship
- **Lives System**: Multiple lives per player with game over when all lives are lost
- **Scoring**: Earn points by destroying asteroids and surviving longer

### Advanced Features
- **Power-Up System**: Collect power-ups to enhance your starship's abilities:
  - Double Canon: Fire two bullets at once for increased firepower
  - Invulnerability: Temporary shield against asteroid collisions
- **Dynamic Asteroids**: Asteroids change appearance based on their remaining health
- **Save/Load System**: Save your game progress and continue later
- **Pause Functionality**: Pause the game at any time

### Technical Features
- **Multi-Player Support**: Play with friends on the same screen
- **Customizable Controls**: Configure keyboard bindings for each player
- **Collision System**: Advanced collision detection and handling
- **State Management**: Robust game state system with normal and paused states

## Game Elements

### Starships
Your controllable spacecraft with the ability to rotate, accelerate, and fire weapons. Each player controls their own starship with customizable controls.

### Asteroids
Floating space rocks that come in various sizes and health levels. They change appearance as they take damage, showing visual feedback of their remaining health.

### Weapons
- **Classic Weapon**: Standard firing mode with regular damage
- **Double Weapon**: Enhanced firing mode that shoots two bullets simultaneously

### Power-Ups
Special items that temporarily enhance your starship's capabilities:
- **Double Canon**: Upgrades your weapon system to fire multiple projectiles
- **Invulnerability**: Makes your ship temporarily immune to collisions

## Game Mechanics

### Movement
- Navigate your ship by rotating left/right and accelerating forward
- Objects that move off-screen will reappear on the opposite side

### Combat
- Fire bullets to destroy asteroids
- Different asteroids require different amounts of damage to destroy
- Score points for each asteroid destroyed

### Collisions
- Ship-to-asteroid collisions result in the loss of a life
- Bullets destroy asteroids over time, depending on asteroid health
- Power-ups are collected by ship collision

## Technical Architecture
Starships is built using a component-based architecture with:
- Visitor pattern for rendering and collision handling
- Adapter pattern for UI integration
- State pattern for game state management
- Serialization system for save/load functionality

## Graphics
The game features a clean, retro-inspired visual style with:
- Spacecraft with rotation visualization
- Asteroids that visually change based on health (yellow → blue → red)
- Visual effects for weapons and power-ups
- Elliptical and rectangular collision boundaries for different game elements
