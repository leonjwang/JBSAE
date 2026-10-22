<div align="center">
  <img src="assets/JBSAE.png" alt="JBSAE Logo" width="128" />
  <h1>JBSAE</h1>
  <p><strong>A lightweight, high-performance 2D game engine and standard library for Java, built from scratch on LWJGL 3.</strong></p>

  <p>
    <img src="https://img.shields.io/badge/Java-17%2B-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java" />
    <img src="https://img.shields.io/badge/LWJGL-3-blue?style=flat-square" alt="LWJGL 3" />
    <img src="https://img.shields.io/badge/OpenGL-3.2%2B-5586A4?style=flat-square&logo=opengl" alt="OpenGL 3.2+" />
    <img src="https://img.shields.io/badge/OpenAL-Audio-green?style=flat-square" alt="OpenAL" />
    <img src="https://img.shields.io/badge/License-MIT-lightgrey?style=flat-square" alt="MIT License" />
  </p>
</div>

---

## Overview

**JBSAE** is a custom-built Java game framework and engine developed over the course of nearly six years. Rather than relying on heavy third-party middleware or even the standard `java.util` collections, JBSAE re-engineers the entire stack from the ground up—from custom Cuckoo-hashed data structures and primitive collections that eliminate autoboxing overhead, to a batched OpenGL rendering pipeline, 3D spatial audio via OpenAL, and an ergonomic static API inspired by [Arc](https://github.com/Anuken/Arc) and [LibGDX](https://libgdx.com/).

Designed for minimal garbage collection pressure, high throughput, and developer ergonomics, JBSAE allows complete interactive applications and games to be bootstrapped in just a few lines of code.

---

## Key Features & Technical Highlights

### Custom Data Structures & Zero-Autoboxing Collections (`jbsae.struct`)
Standard Java collections (`ArrayList`, `HashMap`, `HashSet`) incur significant memory and GC overhead when handling primitive types or high-frequency game loop operations. JBSAE implements its own complete collection hierarchy:
- **Cuckoo Hashing (`Map`, `Set`)**: Implements a 3-way Cuckoo hash table with an overflow stash, achieving worst-case $O(1)$ lookups and deletions with high load factors (`0.8f`).
- **Primitive Specializations (`jbsae.struct.prim`)**: Dedicated unboxed collections (`IntSeq`, `FloatSeq`, `IntMap`, `FloatMap`, `IntSet`, `FloatSet`, `IntQueue`, `FloatQueue`) and primitive iterators (`IntIterator`, `FloatIterator`) that eliminate `Integer`/`Float` boxing allocations entirely.
- **Dynamic Arrays & Ring Buffers (`Seq`, `Queue`)**: `Seq<T>` supports both ordered and $O(1)$ unordered (tail-swap) removal; `Queue<T>` provides a zero-allocation circular deque.
- **Spatial & Priority Trees (`jbsae.struct.tree`)**: Includes a generic `QuadTree<T extends Pos2>` for fast 2D spatial partitioning and broad-phase collision queries, alongside a binary `Heap<T>` priority queue.

### Batched 2D OpenGL Renderer (`jbsae.graphics`)
- **Hardware-Accelerated Batching (`Renderer`)**: Streams vertex data (`position`, `color`, `texCoord`) into a native off-heap `FloatBuffer` (64K floats) backed by OpenGL 3.2+ Core Profile VAOs/VBOs (`GL_DYNAMIC_DRAW`), automatically flushing only on texture swaps or buffer saturation.
- **Z-Ordered Deferred Drawing (`SortedDraw`)**: Queues draw requests (`RectDrawRequest`, `RotatedRectDrawRequest`, `DrawQuadRequest`) and buckets them by floating-point Z-layers via `FloatMap` before flushing to the GPU.
- **CPU Image Manipulation (`Pixmap`)**: Supports pixel-level procedural texture generation, HSV/RGB color manipulation, and real-time GPU texture uploads.
- **Bitmap Font Engine (`Font`, `Glyph`)**: Parses `.fnt` descriptors into an `IntMap<Glyph>` for fast multi-line text rendering.

### Precision Game Loops (`jbsae.core.loop`)
- **Fixed & Dynamic Timesteps**: Choose between deterministic fixed-step updates (`FixedLoop`) or variable delta-time updates (`DynamicLoop`) with configurable FPS and UPS targets.
- **Sub-Millisecond Frame Pacing**: Combines `LockSupport.parkNanos` with `Thread.onSpinWait()` busy-waiting to achieve jitter-free frame pacing without burning CPU cycles or suffering from OS thread-scheduler drift.

### 3D Spatial Audio Engine (`jbsae.audio`)
- **OpenAL Integration (`Sounds`, `Source`, `Listener`)**: Full 3D positional audio (`AL_EXPONENT_DISTANCE`) supporting dynamic pitch shifting, gain control, velocity/Doppler vectors, looping, and automatic source lifecycle management.

### Mathematics, Geometry & Easing (`jbsae.math`, `jbsae.util`)
- **Vectors, Matrices & Shapes**: Mutable, chainable `Vec2`, `Vec3`, `Point2`, `Range2` (AABB), `Circle2`, `Shape2` (SAT polygon collision), and column-major `Mat4` (orthographic, perspective, frustum, and TRS transformations).
- **Rich Interpolation Suite (`Interpf`)**: Over 30 built-in easing curves (`POW`, `EXP`, `SIN`, `SMOOTH`, `SMOOTHER`, `CIRCLE`, `ELASTIC`, `BOUNCE`, `SWING`) plus composable multi-phase interpolation (`interpPhases`).
- **Xorshift128+ PRNG (`Rand`)**: High-speed, seedable pseudo-random number generator initialized via `MurmurHash3` mixing.
- **Pre-Allocated Scratch Objects (`Tmp`)**: Global static scratch instances (`Tmp.v1`, `Tmp.r1`, `Tmp.c1`, etc.) to avoid temporary object allocations inside hot update/render loops.

### Asset Pipeline, Binary Serialization & Logging (`jbsae.core`, `jbsae.files`)
- **Automatic Asset Discovery (`Assets`)**: Automatically indexes the `assets/` directory during development to generate `assetlist.lst`, seamlessly transitioning to classpath/JAR resource loading in production builds for textures (`.png`), shaders (`.vert`, `.frag`), fonts (`.fnt`), and audio (`.wav`, `.ogg`, `.mp3`).
- **Compact Binary Save System (`SaveFi`, `Read`, `Write`)**: Stream-based binary serialization for primitives and strings with zero reflection overhead.
- **Ring-Buffered Crash Logging (`Log`)**: Configurable via the `JBSAE_LOG_LEVEL` environment variable; maintains an in-memory circular queue of log events and automatically dumps stack traces and history to a timestamped `.log` file upon unhandled exceptions.

---

## Architecture

```text
src/
├── jbsae/
│   ├── JBSAE.java           # Engine entry point & global subsystem service locator
│   ├── Tmp.java             # Pre-allocated scratch variables for zero-GC math/rendering
│   ├── Log.java             # Ring-buffered logger & automatic crash dump writer
│   ├── core/                # Screen lifecycle, Asset manager, Fixed & Dynamic game loops
│   ├── graphics/            # OpenGL 3.2+ batch renderer, Shaders, Textures, Pixmap, Fonts, Z-sorted Draw
│   ├── audio/               # OpenAL context, 3D positional Sound Sources & Listener
│   ├── input/               # GLFW keyboard, mouse position, button & scroll callbacks
│   ├── math/                # Vec2/Vec3, Mat4, 2D collision shapes, Xorshift128+ PRNG, Easing curves
│   ├── struct/              # Seq, Queue, Cuckoo Map/Set, QuadTree, Heap & unboxed primitive collections
│   ├── files/               # File abstraction (Fi), asset loaders & binary save/load streams
│   ├── func/                # Specialized primitive functional interfaces (Boolf, Floatf, Intc, etc.)
│   └── util/                # Static utility facades (Drawf, Mathf, Interpf, Colorf, Structf, Stringf)
├── examples/                # Playable games, visualizers & engine demos
└── tests/                   # Unit tests & JMH-style throughput benchmarks vs. java.util
```

---

## Quick Start

Bootstrapping a window and rendering loop in JBSAE takes just a few lines of code using the `Screen` lifecycle and static utilities:

```java
import jbsae.core.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Mathf.*;

public class Demo extends Screen {

    @Override
    public void init() {
        Drawf.font(assets.fonts.get("brandbe.fnt"));
    }

    @Override
    public void update() {
        if (input.pressed.contains('Q')) exit();
    }

    @Override
    public void draw() {
        // Bind default white square texture & draw a rotating, oscillating quad
        assets.textures.get("square.png").bind();
        Drawf.fill(Tmp.c1.hsv(time.millis() / 10f, 0.8f, 1f));
        Drawf.rectc(width / 2f, height / 2f, 120 + sint(500) * 30, 120 + cost(500) * 30, time.frames * 2f);

        // Render text on a higher Z-layer
        Drawf.layer(1f);
        Drawf.fill(Colorf.WHITE);
        Drawf.text("FPS: " + time.fps, 20, height - 40, 24);
        Drawf.layer(0f);
    }

    public static void main(String[] args) {
        width = 800;
        height = 600;
        programName = "JBSAE Demo";
        jbsae(new Demo());
    }
}
```

---

## Included Demos & Benchmarks

### Interactive Examples (`src/examples`)
| Demo | Description |
| :--- | :--- |
| **`Minesweeper`** | Full-featured Minesweeper game with recursive flood-fill chain reveals, particle physics (`FallingTile`), screen shake, and fade transitions. |
| **`Pong`** | Multi-ball Pong against an AI paddle with dynamic ball trails, velocity scaling, and delta-time physics. |
| **`InterpVisualizer`** | Interactive gallery of all 35+ easing curves in `Interpf` with real-time hover scrubbing and motion trails. |
| **`HanoiVisualization`** | Visual step-by-step solver for the Towers of Hanoi using `IntQueue` state stacks. |
| **`AudioTest`** | Interactive HSV color wheel generated via `Pixmap` that modulates OpenAL audio pitch and gain based on polar mouse coordinates. |
| **`CollisionTest`** | Stress-tests `QuadTree` spatial indexing with 10,000 entities and 10,000 range queries per frame. |
| **`RenderTest`** | Stress-tests the OpenGL batch renderer and `Pixmap` edge-detection outlining across hundreds of thousands of quads. |
| **`SaveFileTest`** | Interactive 20x20 pixel editor demonstrating custom binary file serialization (`SaveFi`, `Read`, `Write`). |

### Data Structure Benchmarks (`src/tests`)
The `src/tests` package includes both correctness suites (`SeqTest`, `QueueTest`, `MapTest`, `SetTest`) and JIT-warmed throughput benchmarks (`SeqBenchmark`, `QueueBenchmark`, `MapBenchmark`, `SetBenchmark`) comparing JBSAE's custom collections directly against `java.util.ArrayList`, `ArrayDeque`, `HashMap`, and `HashSet` across millions of operations:
- **`Seq` vs. `ArrayList`**: Benchmarks sequential insertion, random access, iteration, and $O(1)$ unordered front removal (`ordered(false)`) vs. $O(N)$ shifting.
- **`Map` / `Set` vs. `HashMap` / `HashSet`**: Benchmarks 2,000,000 insertions (with resizing), successful/failed lookups, removals, and key iteration using 3-way Cuckoo hashing.

---

## Getting Started & Running

### Prerequisites
- **JDK 17+** (uses modern Java features such as pattern matching for `instanceof`).
- **OS Support**: Pre-bundled LWJGL 3 jars and native binaries are included in `lib/LWJGL3/` for **macOS (Apple Silicon arm64 & Intel x64)** and **Windows (x64)**.

### Running in IntelliJ IDEA
1. Clone the repository and open the root directory in **IntelliJ IDEA** (the project configuration is pre-defined in `JBSAE.iml`).
2. Ensure your Project SDK is set to **Java 17** or newer.
3. *(macOS Note)*: When running graphical examples on macOS, add `-XstartOnFirstThread` to your Run Configuration's VM options as required by GLFW.
4. Run any `main` method inside `src/examples/` or `src/tests/`.

---

## Acknowledgments & License

- Special thanks to [SilverTiger/lwjgl3-tutorial](https://github.com/SilverTiger/lwjgl3-tutorial) for the foundational LWJGL 3 OpenGL references, and to [Anuken/Arc](https://github.com/Anuken/Arc) for API design inspiration.
- Licensed under the [MIT License](LICENSE.md). Copyright (c) Leon Wang.
