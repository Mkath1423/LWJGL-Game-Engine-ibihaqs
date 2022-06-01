# Diff Details

Date : 2022-05-31 02:09:04

Directory d:\GitRepos\ics4-final-project-ibihaqs

Total : 65 files,  1224 codes, 783 comments, 600 blanks, all 2607 lines

[Summary](results.md) / [Details](details.md) / [Diff Summary](diff.md) / Diff Details

## Files
| filename | language | code | comment | blank | total |
| :--- | :--- | ---: | ---: | ---: | ---: |
| [assets/shaders/default.glsl](/assets/shaders/default.glsl) | GLSL | -4 | 0 | 2 | -2 |
| [assets/shaders/multitex.glsl](/assets/shaders/multitex.glsl) | GLSL | 27 | 0 | 10 | 37 |
| [assets/shaders/panel.glsl](/assets/shaders/panel.glsl) | GLSL | 26 | 0 | 9 | 35 |
| [src/main/java/engine/AssetManager.java](/src/main/java/engine/AssetManager.java) | Java | 23 | 21 | 11 | 55 |
| [src/main/java/engine/Inputs/BoolState.java](/src/main/java/engine/Inputs/BoolState.java) | Java | -30 | 0 | -13 | -43 |
| [src/main/java/engine/Inputs/ButtonState.java](/src/main/java/engine/Inputs/ButtonState.java) | Java | 28 | 34 | 13 | 75 |
| [src/main/java/engine/Inputs/Input.java](/src/main/java/engine/Inputs/Input.java) | Java | 68 | 74 | 19 | 161 |
| [src/main/java/engine/Inputs/InputAxis.java](/src/main/java/engine/Inputs/InputAxis.java) | Java | 13 | 84 | 0 | 97 |
| [src/main/java/engine/Inputs/KeyListener.java](/src/main/java/engine/Inputs/KeyListener.java) | Java | -16 | 45 | -4 | 25 |
| [src/main/java/engine/Inputs/MouseListener.java](/src/main/java/engine/Inputs/MouseListener.java) | Java | -5 | 55 | 3 | 53 |
| [src/main/java/engine/Main.java](/src/main/java/engine/Main.java) | Java | 8 | 0 | 8 | 16 |
| [src/main/java/engine/Window.java](/src/main/java/engine/Window.java) | Java | -3 | -1 | 5 | 1 |
| [src/main/java/engine/components/Component.java](/src/main/java/engine/components/Component.java) | Java | 2 | 3 | 0 | 5 |
| [src/main/java/engine/components/ImageRenderer.java](/src/main/java/engine/components/ImageRenderer.java) | Java | 38 | 0 | 17 | 55 |
| [src/main/java/engine/components/KeyboardControl.java](/src/main/java/engine/components/KeyboardControl.java) | Java | 0 | 2 | 0 | 2 |
| [src/main/java/engine/components/LineRenderer.java](/src/main/java/engine/components/LineRenderer.java) | Java | 82 | 10 | 31 | 123 |
| [src/main/java/engine/components/PanelRenderer.java](/src/main/java/engine/components/PanelRenderer.java) | Java | 63 | 0 | 25 | 88 |
| [src/main/java/engine/components/QuadRenderable.java](/src/main/java/engine/components/QuadRenderable.java) | Java | 20 | 0 | 11 | 31 |
| [src/main/java/engine/components/Renderable.java](/src/main/java/engine/components/Renderable.java) | Java | -6 | -1 | 2 | -5 |
| [src/main/java/engine/components/SpriteGroup.java](/src/main/java/engine/components/SpriteGroup.java) | Java | 0 | 0 | 1 | 1 |
| [src/main/java/engine/components/SpriteRenderer.java](/src/main/java/engine/components/SpriteRenderer.java) | Java | 1 | -6 | -5 | -10 |
| [src/main/java/engine/components/Transform.java](/src/main/java/engine/components/Transform.java) | Java | 48 | 21 | 19 | 88 |
| [src/main/java/engine/gameobjects/GameObject.java](/src/main/java/engine/gameobjects/GameObject.java) | Java | 17 | 36 | 0 | 53 |
| [src/main/java/engine/geometry/Circle.java](/src/main/java/engine/geometry/Circle.java) | Java | 25 | 28 | 29 | 82 |
| [src/main/java/engine/geometry/Quad.java](/src/main/java/engine/geometry/Quad.java) | Java | 22 | 22 | 18 | 62 |
| [src/main/java/engine/physics/Collision.java](/src/main/java/engine/physics/Collision.java) | Java | 30 | 17 | 36 | 83 |
| [src/main/java/engine/physics/Move.java](/src/main/java/engine/physics/Move.java) | Java | 48 | 36 | 52 | 136 |
| [src/main/java/engine/renderer/BufferIDs.java](/src/main/java/engine/renderer/BufferIDs.java) | Java | 14 | 0 | 4 | 18 |
| [src/main/java/engine/renderer/Camera.java](/src/main/java/engine/renderer/Camera.java) | Java | 20 | 2 | 9 | 31 |
| [src/main/java/engine/renderer/EBO.java](/src/main/java/engine/renderer/EBO.java) | Java | -24 | -1 | -9 | -34 |
| [src/main/java/engine/renderer/EBOFormat.java](/src/main/java/engine/renderer/EBOFormat.java) | Java | 46 | 3 | 13 | 62 |
| [src/main/java/engine/renderer/Layer.java](/src/main/java/engine/renderer/Layer.java) | Java | 9 | -3 | 4 | 10 |
| [src/main/java/engine/renderer/QuadRenderBatch.java](/src/main/java/engine/renderer/QuadRenderBatch.java) | Java | 37 | 6 | 16 | 59 |
| [src/main/java/engine/renderer/QuadRenderer.java](/src/main/java/engine/renderer/QuadRenderer.java) | Java | 101 | 11 | 39 | 151 |
| [src/main/java/engine/renderer/RenderBatch.java](/src/main/java/engine/renderer/RenderBatch.java) | Java | 16 | 2 | 3 | 21 |
| [src/main/java/engine/renderer/Renderer.java](/src/main/java/engine/renderer/Renderer.java) | Java | 29 | 0 | 5 | 34 |
| [src/main/java/engine/renderer/Shader.java](/src/main/java/engine/renderer/Shader.java) | Java | -3 | 0 | -2 | -5 |
| [src/main/java/engine/renderer/Sprite.java](/src/main/java/engine/renderer/Sprite.java) | Java | 0 | 17 | 1 | 18 |
| [src/main/java/engine/renderer/SpriteMap.java](/src/main/java/engine/renderer/SpriteMap.java) | Java | 0 | 29 | 0 | 29 |
| [src/main/java/engine/renderer/Texture.java](/src/main/java/engine/renderer/Texture.java) | Java | 24 | 41 | 9 | 74 |
| [src/main/java/engine/renderer/VAO.java](/src/main/java/engine/renderer/VAO.java) | Java | 24 | 3 | 5 | 32 |
| [src/main/java/engine/renderer/VBO.java](/src/main/java/engine/renderer/VBO.java) | Java | 31 | 0 | 15 | 46 |
| [src/main/java/engine/scenes/Scene.java](/src/main/java/engine/scenes/Scene.java) | Java | 15 | 0 | 2 | 17 |
| [src/main/java/engine/scenes/SceneManager.java](/src/main/java/engine/scenes/SceneManager.java) | Java | 9 | 6 | 3 | 18 |
| [src/main/java/engine/serializer/SaveStates.java](/src/main/java/engine/serializer/SaveStates.java) | Java | 52 | 11 | 53 | 116 |
| [src/main/java/engine/util/Time.java](/src/main/java/engine/util/Time.java) | Java | 0 | 11 | 1 | 12 |
| [src/main/java/nebula/FollowMouse.java](/src/main/java/nebula/FollowMouse.java) | Java | 22 | 5 | 8 | 35 |
| [src/main/java/nebula/MainGameScene.java](/src/main/java/nebula/MainGameScene.java) | Java | 87 | 6 | 32 | 125 |
| [src/main/java/nebula/Player.java](/src/main/java/nebula/Player.java) | Java | 54 | 1 | 19 | 74 |
| [src/main/java/nebula/Stats.java](/src/main/java/nebula/Stats.java) | Java | 4 | 0 | 2 | 6 |
| [src/main/java/pocketplanets/Planet.java](/src/main/java/pocketplanets/Planet.java) | Java | 3 | 1 | -2 | 2 |
| [src/main/java/pocketplanets/Resource.java](/src/main/java/pocketplanets/Resource.java) | Java | 6 | 0 | 0 | 6 |
| [src/main/java/pocketplanets/Stats.java](/src/main/java/pocketplanets/Stats.java) | Java | -3 | 0 | -3 | -6 |
| [src/main/java/pocketplanets/buildings/Building.java](/src/main/java/pocketplanets/buildings/Building.java) | Java | -1 | -1 | -1 | -3 |
| [src/main/java/pocketplanets/buildings/CityBuilding.java](/src/main/java/pocketplanets/buildings/CityBuilding.java) | Java | -1 | 0 | 0 | -1 |
| [src/main/java/pocketplanets/buildings/ExtractorBuilding.java](/src/main/java/pocketplanets/buildings/ExtractorBuilding.java) | Java | -7 | 7 | -3 | -3 |
| [src/main/java/pocketplanets/buildings/FuelStationBuilding.java](/src/main/java/pocketplanets/buildings/FuelStationBuilding.java) | Java | 12 | -1 | 2 | 13 |
| [src/main/java/pocketplanets/buildings/ShipyardBuilding.java](/src/main/java/pocketplanets/buildings/ShipyardBuilding.java) | Java | -1 | 0 | 0 | -1 |
| [src/main/java/pocketplanets/ships/CargoShip.java](/src/main/java/pocketplanets/ships/CargoShip.java) | Java | 0 | 1 | 0 | 1 |
| [src/main/java/pocketplanets/ships/Ship.java](/src/main/java/pocketplanets/ships/Ship.java) | Java | 8 | 12 | 7 | 27 |
| [src/main/java/pocketplanets/testing/BatchingTestScene.java](/src/main/java/pocketplanets/testing/BatchingTestScene.java) | Java | 68 | 3 | 21 | 92 |
| [src/main/java/pocketplanets/testing/SampleComponent.java](/src/main/java/pocketplanets/testing/SampleComponent.java) | Java | 23 | 8 | 9 | 40 |
| [src/main/java/pocketplanets/testing/SampleScene.java](/src/main/java/pocketplanets/testing/SampleScene.java) | Java | 0 | 90 | 22 | 112 |
| [src/main/java/pocketplanets/testing/SampleSwinging.java](/src/main/java/pocketplanets/testing/SampleSwinging.java) | Java | 24 | 8 | 11 | 43 |
| [src/main/java/pocketplanets/testing/TestScene.java](/src/main/java/pocketplanets/testing/TestScene.java) | Java | 1 | 25 | 6 | 32 |

[Summary](results.md) / [Details](details.md) / [Diff Summary](diff.md) / Diff Details